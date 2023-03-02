package net.tiklab.xpack.library.service;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonString;
import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.user.system.user.model.User;
import net.tiklab.xpack.library.controller.LibraryController;
import net.tiklab.xpack.library.dao.LibraryDao;
import net.tiklab.xpack.library.entity.LibraryEntity;
import net.tiklab.xpack.library.model.*;

import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.repository.model.RepositoryGroup;
import net.tiklab.xpack.repository.model.RepositoryGroupQuery;
import net.tiklab.xpack.repository.model.RepositoryQuery;
import net.tiklab.xpack.repository.service.RepositoryGroupService;
import net.tiklab.xpack.repository.service.RepositoryService;
import org.apache.catalina.authenticator.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import org.springframework.beans.BeanUtils;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

/**
* LibraryServiceImpl-制品
*/
@Service
public class LibraryServiceImpl implements LibraryService {

    private static Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);
    @Autowired
    LibraryDao libraryDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryMavenService libraryMavenService;
    
    @Autowired
    RepositoryGroupService repositoryGroupService;

    @Value("${repository.library:null}")
    String repositoryLibrary;

    @Value("${repository.test:null}")
    String testLibrary;

    Map data=new HashMap();

    @Override
    public String createLibrary(@NotNull @Valid Library library) {
        LibraryEntity libraryEntity = BeanMapper.map(library, LibraryEntity.class);
        libraryEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        libraryEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return libraryDao.createLibrary(libraryEntity);
    }

    @Override
    public void updateLibrary(@NotNull @Valid Library library) {
        LibraryEntity libraryEntity = BeanMapper.map(library, LibraryEntity.class);
        libraryEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        libraryDao.updateLibrary(libraryEntity);
    }

    @Override
    public void deleteLibrary(@NotNull String id) {
        libraryDao.deleteLibrary(id);
    }

    @Override
    public Library findOne(String id) {
        LibraryEntity libraryEntity = libraryDao.findLibrary(id);

        Library library = BeanMapper.map(libraryEntity, Library.class);

        return library;
    }

    @Override
    public List<Library> findList(List<String> idList) {
        List<LibraryEntity> libraryEntityList =  libraryDao.findLibraryList(idList);

        List<Library> libraryList =  BeanMapper.mapList(libraryEntityList,Library.class);
        return libraryList;
    }

    @Override
    public Library findLibrary(@NotNull String id) {
        Library library = findOne(id);

        joinTemplate.joinQuery(library);

        return library;
    }

    @Override
    public List<Library> findAllLibrary() {
        List<LibraryEntity> libraryEntityList =  libraryDao.findAllLibrary();

        List<Library> libraryList =  BeanMapper.mapList(libraryEntityList,Library.class);

        joinTemplate.joinQuery(libraryList);

        return libraryList;
    }

    @Override
    public List<Library> findLibraryList(LibraryQuery libraryQuery) {
        findRepositoryGroup(libraryQuery);
        List<LibraryEntity> libraryEntityList = libraryDao.findLibraryList(libraryQuery);

        List<Library> libraryList = BeanMapper.mapList(libraryEntityList,Library.class);

        joinTemplate.joinQuery(libraryList);

        return libraryList;
    }


    @Override
    public List<Library> findMavenLibraryList(LibraryQuery libraryQuery) {
        findRepositoryGroup(libraryQuery);
        List<Library> mavenLibraryList = libraryDao.findMavenLibraryList(libraryQuery);

        return mavenLibraryList;
    }

    @Override
    public Pagination<Library> findLibraryPage(LibraryQuery libraryQuery) {
        Pagination<LibraryEntity>  pagination = libraryDao.findLibraryPage(libraryQuery);

        List<Library> libraryList = BeanMapper.mapList(pagination.getDataList(),Library.class);

        joinTemplate.joinQuery(libraryList);

        return PaginationBuilder.build(pagination,libraryList);
    }




    /**
     *  查询组合库关联
     * @param libraryQuery     全路径
     * @return
     */
    public void findRepositoryGroup(LibraryQuery libraryQuery){
        Repository repository = repositoryService.findRepository(libraryQuery.getRepositoryId());
        if (ObjectUtils.isNotEmpty(repository)&& "group".equals(repository.getRepositoryType())){
            List<RepositoryGroup> groupItemsList = repositoryGroupService.findRepositoryGroupList(
                    new RepositoryGroupQuery().setRepositoryGroupId(libraryQuery.getRepositoryId()));
            if (CollectionUtils.isNotEmpty(groupItemsList)){
                List<String> repositoryIds = groupItemsList.stream().map(item -> item.getRepository().getId()).collect(Collectors.toList());
                libraryQuery.setRepositoryIds(repositoryIds);
            }
        }
    }

    /**
     *  制品创建
     * @param libraryName     制品文件
     * @param libraryType  制品类型
     * @param repository  制品库信息
     * @return
     */
    public Library createLibraryData(String libraryName,String libraryType,Repository repository){
        Library library = new Library();
        library.setLibraryType(libraryType);
        //查询制品包是否有创建
        List<Library> libraryList = this.findLibraryList(new LibraryQuery().setName(libraryName).setRepositoryId(repository.getId()));
        String libraryId=null;
        if (CollectionUtils.isEmpty(libraryList)){
            library.setName(libraryName);
            //创建制品信息
            library.setRepository(repository);
            libraryId = this.createLibrary(library);
        }else {
            libraryId = libraryList.get(0).getId();
        }
        library.setLibraryType(libraryType);
        library.setId(libraryId);

        return library;
    }






    public  byte[]  readFileContent02(String filePath)  throws IOException {
       /* StringBuilder result = new StringBuilder();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
        String lineTxt = null;
        while ((lineTxt = bfr.readLine()) != null) {
            result.append(lineTxt).append("\n");
        }*/

        String path="/Users/limingliang/publicData/download";
        String filePaths=path+"/"+"tiklab-user-ui-1.0.0.tgz";
        File file = new File(filePaths);
        if (!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(filePaths);
        byte[] b = new byte[1024];
        File file1 = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file1);
        //ByteArrayInputStream inputStream = new ByteArrayInputStream(filePath.getBytes(StandardCharsets.UTF_8));
        while ((fileInputStream.read(b)) != -1) {
            fos.write(b);// 写入数据
        }
        fileInputStream.close();



       /* File f = new File(filePath);
        long length = f.length();
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        byte[] bytes = bos.toByteArray();
        String s = new String(bytes, "UTF-8");*/
        return null;
    }




}

