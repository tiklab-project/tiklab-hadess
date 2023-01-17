package net.tiklab.xpack.library.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.user.user.model.User;
import net.tiklab.xpack.library.dao.LibraryDao;
import net.tiklab.xpack.library.entity.LibraryEntity;
import net.tiklab.xpack.library.model.*;

import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.repository.model.RepositoryQuery;
import net.tiklab.xpack.repository.service.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
* LibraryServiceImpl
*/
@Service
public class LibraryServiceImpl implements LibraryService {

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

    @Value("${repository.library:null}")
    String repositoryLibrary;

    @Value("${repository.test:null}")
    String testLibrary;

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
        List<LibraryEntity> libraryEntityList = libraryDao.findLibraryList(libraryQuery);

        List<Library> libraryList = BeanMapper.mapList(libraryEntityList,Library.class);

        joinTemplate.joinQuery(libraryList);

        return libraryList;
    }

    @Override
    public Pagination<Library> findLibraryPage(LibraryQuery libraryQuery) {
        Pagination<LibraryEntity>  pagination = libraryDao.findLibraryPage(libraryQuery);

        List<Library> libraryList = BeanMapper.mapList(pagination.getDataList(),Library.class);

        joinTemplate.joinQuery(libraryList);

        return PaginationBuilder.build(pagination,libraryList);
    }

    @Override
    public void mavenSubmit(String contextPath, OutputStream outputStream, InputStream inputStream) throws IOException {
       String test =testLibrary;

        String url = StringUtils.substringBeforeLast(contextPath, "/");
        String path=repositoryLibrary+url;

        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        String filePath=repositoryLibrary+contextPath;
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        boolean endsWith = contextPath.endsWith(".jar");
        if (endsWith){
            //jar文件用FileOutputStream 写入
            FileOutputStream fos = new FileOutputStream(filePath);
            byte[] b = new byte[1024];
            while ((inputStream.read(b)) != -1) {
                fos.write(b);// 写入数据
            }
            inputStream.close();
            fos.close();// 保存数据
        }else {
            String line=null;
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            //写入内容
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            while((line=reader.readLine())!=null){
                writer.write(line+"\n");
            }
            writer.close();
        }

        //创建制品
        librarySplice(contextPath);
        }

    /**
     *  制品创建、修改
     * @param contextPath     全路径
     * @return
     */
        public void librarySplice(String contextPath){
            Library library = new Library();
            String libraryVersionId=null;
            String document = contextPath.substring(contextPath.lastIndexOf("/") + 1);
            int indexOf = document.indexOf("maven-metadata");
            if (indexOf==-1){
                String[]  single=contextPath.split("/");
                int length = single.length;
                String repositoryName = single[3];
                String packageName = single[length - 3];
                String version = single[length - 2];
                //查询制品库是否存在
                List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
                if(CollectionUtils.isNotEmpty(repositoryList)){
                    library.setName(packageName);
                    //查询制品包是否有创建
                    List<Library> libraryList = this.findLibraryList(new LibraryQuery().setName(packageName));

                    String libraryId=null;
                    if(CollectionUtils.isEmpty(libraryList)){
                        //创建制品信息
                        library.setLibraryType("maven");
                        library.setRepository(repositoryList.get(0));
                         libraryId = this.createLibrary(library);
                    }else {
                        libraryId = libraryList.get(0).getId();
                    }
                    library.setId(libraryId);
                    //制品版本创建、修改
                     libraryVersionId = libraryVersionSplice(library, repositoryList.get(0), version);
                    //制品文件
                    libraryFileSplice(library, contextPath,libraryVersionId);
                    // 制品maven
                    String groupId=null;
                    for (int a= 1; a<length-3-3;a++){
                        String s = single[3 + a];
                        if (groupId==null){
                            groupId=s+".";
                        }else {
                            if(a==length-7){
                                groupId =  groupId+s;
                            }else {
                                groupId =  groupId+s+".";
                            }

                        }
                    }
                    libraryMavenSplice(packageName,groupId,library);
                }
            }
        }


    /**
     *  制品maven创建、修改
     * @param artifactId     artifactId
     * @param groupId  groupId
     * @param library  library
     * @return
     */
    public void libraryMavenSplice(String artifactId,String groupId, Library library ){
        List<LibraryMaven> libraryMavenList = libraryMavenService.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(library.getId())
                .setArtifactId(artifactId).setGroupId(groupId));
        if (CollectionUtils.isEmpty(libraryMavenList)){
            LibraryMaven libraryMaven = new LibraryMaven();
            libraryMaven.setArtifactId(artifactId);
            libraryMaven.setGroupId(groupId);
            libraryMaven.setLibrary(library);
            libraryMavenService.createLibraryMaven(libraryMaven);
        }
    }

    /**
     *  制品版本创建、修改
     * @param library     制品
     * @param repository  制品库
     * @param version  版本
     * @return
     */
    public String libraryVersionSplice( Library library,Repository repository, String version){
        LibraryVersion libraryVersion = new LibraryVersion();

        String libraryVersionId=null;

        List<LibraryVersion> libraryVersionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(library.getId()).
                setRepositoryId(repository.getId()).setVersion(version));

        if (CollectionUtils.isNotEmpty(libraryVersionList)){
            libraryVersionId = libraryVersionList.get(0).getId();
            libraryVersionList.get(0).setPushTime(new Timestamp(System.currentTimeMillis()));
            libraryVersionService.updateLibraryVersion(libraryVersionList.get(0));
        }else {
            libraryVersion.setLibrary(library);
            libraryVersion.setVersion(version);
            User user = new User();
            user.setId("111111");
            libraryVersion.setUser(user);
            libraryVersion.setPushTime(new Timestamp(System.currentTimeMillis()));
            libraryVersion.setRepository(repository);
            // libraryVersion.setSize();
             libraryVersionId = libraryVersionService.createLibraryVersion(libraryVersion);

            library.setNewVersion(version);
             this.updateLibrary(library);
        }
        return libraryVersionId;
    }

    /**
     *  制品文件创建、修改
     *  @param library     制品
     * @param contextPath   文件全路径
     *  @param versionId   制品版本id
     * @return
     */
    public void libraryFileSplice( Library library,String contextPath,String versionId){
        //截取文件名称
        String fileName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        //截取文件路径
        String fileUrl = StringUtils.substringBeforeLast(contextPath, "/");
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);

        int indexOf = fileName.indexOf("maven-metadata");
        if (indexOf==-1){
            libraryFile.setFileName(fileName);
        }
        libraryFile.setFileSize("1kb");
        libraryFile.setFileUrl(repositoryLibrary+fileUrl);
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setId(versionId);
        libraryFile.setLibraryVersion(libraryVersion);

        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(versionId)
                .setFileName(fileName));
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            libraryFile.setId(libraryFileList.get(0).getId());
            libraryFileService.updateLibraryFile(libraryFile);
        }else {
            libraryFileService.createLibraryFile(libraryFile);
        }
    }

}