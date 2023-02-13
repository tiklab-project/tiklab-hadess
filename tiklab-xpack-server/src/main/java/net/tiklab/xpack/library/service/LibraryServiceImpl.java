package net.tiklab.xpack.library.service;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonString;
import net.tiklab.beans.BeanMapper;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.user.user.model.User;
import net.tiklab.xpack.library.controller.LibraryController;
import net.tiklab.xpack.library.dao.LibraryDao;
import net.tiklab.xpack.library.entity.LibraryEntity;
import net.tiklab.xpack.library.model.*;

import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.repository.model.RepositoryGroupItems;
import net.tiklab.xpack.repository.model.RepositoryGroupItemsQuery;
import net.tiklab.xpack.repository.model.RepositoryQuery;
import net.tiklab.xpack.repository.service.RepositoryGroupItemsService;
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
* LibraryServiceImpl
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
    RepositoryGroupItemsService repositoryGroupItemsService;

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

    @Override
    public void mavenSubmit(String contextPath, InputStream inputStream) throws IOException {
        String url = StringUtils.substringBeforeLast(contextPath, "/");
        String path=repositoryLibrary+url;
        logger.warn("传入路径repositoryLibrary:{}",repositoryLibrary);
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        String filePath=repositoryLibrary+contextPath;
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        //用字节流写入
        FileOutputStream fos = new FileOutputStream(filePath);
        byte[] b = new byte[1024];
        while ((inputStream.read(b)) != -1) {
            fos.write(b);// 写入数据
        }
        inputStream.close();
        fos.close();// 保存数据
     /*   if (endsWith){
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
        }*/
        //创建制品
        librarySplice(contextPath);
        }

    @Override
    public byte[] mavenInstall(String contextPath) {
        String mavenInstall = contextPath.substring(contextPath.indexOf("maven-install")+13);
        String fileUrl=repositoryLibrary+"/test"+mavenInstall;
        try {
            byte[] bytes = readFileContent(fileUrl);
            return bytes;
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }


    @Override
    public  Object npmPull(String contextPath) {

        Object data1 = data.get("data");

        /*String fileUrl=repositoryLibrary+contextPath;
        String libraryName = contextPath.substring(contextPath.lastIndexOf("/") + 1);

        LibraryQuery libraryQuery = new LibraryQuery();
        libraryQuery.setLibraryType("npm");
        libraryQuery.setName(libraryName);

        try {
            return  findLibraryVersion(libraryQuery, "", "");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        return data1;
    }

    /**
     *  查询组合库关联
     * @param libraryQuery     全路径
     * @return
     */
    public void findRepositoryGroup(LibraryQuery libraryQuery){
        Repository repository = repositoryService.findRepository(libraryQuery.getRepositoryId());
        if (ObjectUtils.isNotEmpty(repository)&& "group".equals(repository.getRepositoryType())){
            List<RepositoryGroupItems> groupItemsList = repositoryGroupItemsService.findRepositoryGroupItemsList(
                    new RepositoryGroupItemsQuery().setRepositoryGroupId(libraryQuery.getRepositoryId()));
            if (CollectionUtils.isNotEmpty(groupItemsList)){
                List<String> repositoryIds = groupItemsList.stream().map(item -> item.getRepository().getId()).collect(Collectors.toList());
                libraryQuery.setRepositoryIds(repositoryIds);
            }
        }
    }



    /**
     *  制品创建、修改
     * @param contextPath     全路径
     * @return
     */
    public void librarySplice(String contextPath){

        String document = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        int indexOf = document.indexOf("maven-metadata");
        if (indexOf==-1){
            String[]  single=contextPath.split("/");
            int length = single.length;
            String repositoryName = single[3];
            String libraryName = single[length - 3];
            String version = single[length - 2];

            //查询制品库是否存在
            List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
           if (CollectionUtils.isNotEmpty(repositoryList)){
               //创建制品
               Library library = createLibraryData(libraryName, "maven",repositoryList.get(0));
               //制品版本创建、修改
               String libraryVersionId = libraryVersionSplice(library, repositoryList.get(0), version);


               //截取文件名称
               String fileName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
               //截取文件路径
               String fileUrl = StringUtils.substringBeforeLast(contextPath, "/");
               LibraryFile libraryFile = new LibraryFile();
               libraryFile.setLibrary(library);
               libraryFile.setFileName(fileName);
               libraryFile.setFileSize("1kb");
               libraryFile.setFileUrl(repositoryLibrary+fileUrl);
               //制品文件
               libraryFileSplice(libraryFile,libraryVersionId);

               // 制品maven
               libraryMavenSplice(libraryName,single,library);
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

    /**
     *  制品maven创建、修改
     * @param artifactId     artifactId
     * @param single  single
     * @param library  library
     * @return
     */
    public void libraryMavenSplice(String artifactId, String[]  single, Library library ){
        int length = single.length;
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
            //更新最新提交时间
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
            libraryVersion.setLibraryType(library.getLibraryType());
            // libraryVersion.setSize();
             libraryVersionId = libraryVersionService.createLibraryVersion(libraryVersion);

            library.setNewVersion(version);
             this.updateLibrary(library);
        }
        return libraryVersionId;
    }

    /**
     *  制品文件创建、修改
     *  @param libraryFile     制品文件
     *  @param versionId   制品版本id
     * @return
     */
    public void libraryFileSplice(LibraryFile libraryFile,String versionId){
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setId(versionId);
        libraryFile.setLibraryVersion(libraryVersion);

        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(versionId)
                .setFileName(libraryFile.getFileName()));
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            libraryFile.setId(libraryFileList.get(0).getId());
            libraryFileService.updateLibraryFile(libraryFile);
        }else {
            libraryFileService.createLibraryFile(libraryFile);
        }
    }




    /**
     *  查询版本
     * @param libraryQuery: 制品查询条件
     * @param version:   版本
     * @param fileName:  文件名称
     * @return
     */
    public   byte[] findLibraryVersion(LibraryQuery libraryQuery,String version,String fileName) throws Exception {

        byte[] bytes=null;
        Object fileContent=null;
        List<Library> libraryList = this.findLibraryList(libraryQuery);

        if (CollectionUtils.isNotEmpty(libraryList)){
            LibraryVersionQuery libraryVersionQuery = new LibraryVersionQuery();
            libraryVersionQuery.setLibraryId(libraryList.get(0).getId());
            //没带版本号就取最新的版本
            if (!StringUtils.isEmpty(version)){
                libraryVersionQuery.setVersion(version);
            }
            List<LibraryVersion>  libraryVersionList = libraryVersionService.findLibraryVersionList(libraryVersionQuery);
            List<LibraryVersion> versions = libraryVersionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime).reversed()).collect(Collectors.toList());

            //查询制品文件
            LibraryFileQuery libraryFileQuery = new LibraryFileQuery();
            libraryFileQuery.setLibraryVersionId(versions.get(0).getId());
            if (!StringUtils.isEmpty(fileName)){
                libraryFileQuery.setFileName(fileName);
            }
            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(libraryFileQuery);
             //fileContent = readNpmFileContent(libraryFileList.get(0).getFileUrl());
            return readFileContent(libraryFileList.get(0).getFileUrl());
        }else {
            //制品库里面不存在就去配置的代理地址拉取
        }
        return null;
    }


    /**
     *  拉取后-读取文件内容
     * @param filePath: 文件内容
     * @return
     */
    public byte[] readFileContent(String filePath)  throws IOException {
        File f = new File(filePath);
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
        String s = new String(bytes, "UTF-8");
        return bytes;
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


    public Object readNpmFileContent(String filePath) throws IOException, ArchiveException {
        File tarFile = new File(filePath);
        String destDir="/Users/limingliang/publicData";
        if(StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        InputStream inputStream = new GzipCompressorInputStream(new FileInputStream(tarFile));
        TarArchiveInputStream tarIn = new TarArchiveInputStream(inputStream, 2048);
        TarArchiveEntry entry = null;
        try {
            while ((entry = tarIn.getNextTarEntry()) != null) {

                if (entry.isDirectory()) {//是目录
                    createDirectory(destDir, entry.getName());//创建空目录
                } else {//是文件
                    File tmpFile = new File(destDir + File.separator + entry.getName());
                    createDirectory(tmpFile.getParent() + File.separator, null);//创建输出目录
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(tmpFile);
                        int length = 0;
                        byte[] b = new byte[2048];
                        while ((length = tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }
                    } finally {
                        IOUtils.closeQuietly(out);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        FileInputStream fileInputStream = new FileInputStream(tarFile);
        ArchiveInputStream archiveInputStream =  new ArchiveStreamFactory().createArchiveInputStream("tar",new GZIPInputStream(fileInputStream));
        TarArchiveEntry tarArchiveEntry = (TarArchiveEntry) archiveInputStream.getNextEntry();
        /*while (tarArchiveEntry != null) {
            System.out.println("数据"+tarArchiveEntry.getName());


    }*/

     //   return tarArchiveEntry;
        return tarFile;

}
    public static void createDirectory(String outputDir,String subDir){
        File file = new File(outputDir);
        if(!(subDir == null || subDir.trim().equals(""))){//子目录不为空
            file = new File(outputDir + File.separator + subDir);
        }
        if(!file.exists()){
            file.mkdirs();
        }
    }

}

