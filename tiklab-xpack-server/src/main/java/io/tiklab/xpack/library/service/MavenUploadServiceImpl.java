package io.tiklab.xpack.library.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.user.user.model.User;
import io.tiklab.user.user.model.UserQuery;
import io.tiklab.user.user.service.UserService;
import io.tiklab.xpack.library.model.Library;
import io.tiklab.xpack.library.model.LibraryFile;
import io.tiklab.xpack.library.model.LibraryFileQuery;
import io.tiklab.xpack.library.model.LibraryVersion;
import io.tiklab.xpack.repository.model.*;
import io.tiklab.xpack.repository.service.RepositoryGroupService;
import io.tiklab.xpack.repository.service.RepositoryRemoteProxyService;
import io.tiklab.xpack.repository.service.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MavenUploadServiceImpl-maven上传下载
 */
@Service
public class MavenUploadServiceImpl implements MavenUploadService {
    private static Logger logger = LoggerFactory.getLogger(MavenUploadServiceImpl.class);
    @Value("${repository.library:null}")
    String repositoryLibrary;

    @Autowired
    LibraryService libraryService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RepositoryGroupService repositoryGroupService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryMavenService libraryMavenService;

    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Autowired
    UserService userService;


    @Override
    public Map<String,Object> mavenSubmit(String contextPath, InputStream inputStream,String userData, String method) throws IOException {

        List<User> userList = findUser(userData);
        User user=null;
        if (CollectionUtils.isEmpty(userList)){
            return result(401,"",null);
        }else {
            user=  userList.get(0);
        }
        //操作流内容
        return operateFileData(inputStream,user,contextPath,method);

    }

    @Override
    public Map<String, Object> mavenInstall(String contextPath) {
        String mavenInstall = contextPath.substring(contextPath.indexOf("maven-install")+14);
        try {
            return findRepository(mavenInstall,contextPath);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *  maven-提交   创建文件夹或者文件
     * @param contextPath     文件夹路径
     * @param inputStream     inputStream
     *  @param method     请求方式  GET PUT
     * @return
     */
    public Map<String, Object> operateFileData(InputStream inputStream, User user,String contextPath, String method) throws IOException {
        String url = StringUtils.substringBeforeLast(contextPath, "/");
        String path=repositoryLibrary+url;
        String filePath=repositoryLibrary+contextPath;
        logger.warn("传入路径repositoryLibrary:{}",path);

        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        String document = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        int indexOf = document.indexOf("maven-metadata");
        if (indexOf!=-1){
            if (("GET").equals(method)){
                if(document.contains(".sha1")||document.contains(".md5")){
                    Map<String, Object> result = result(220, "OK", gainFileData(file));
                    librarySplice(contextPath,file,user);
                    return result;
                }else {
                   /*String shaPath = path+".sha1";*/
                    Map<String, Object> result = result(200, "OK", gainFileData(file));
                    librarySplice(contextPath,file,user);
                   /* File shaValue = new File(shaPath);
                    String ETag="{SHA1{"+gainFileData(shaValue)+"}}";
                    result.put("ETag",ETag);*/
                    return result;
                }
            }
        }
        //用字节流写入文件
        FileOutputStream fos = new FileOutputStream(filePath);
        byte[] b = new byte[1024];
        while ((inputStream.read(b)) != -1) {
            fos.write(b);// 写入数据
        }
        inputStream.close();
        fos.close();// 保存数据
        return librarySplice(contextPath,file,user) ;
    }

    /**
     *  maven-提交   制品创建、修改
     * @param contextPath     全路径
     * @param file     文件
     * @return
     */
    public Map<String,Object> librarySplice(String contextPath,File file,User user) throws IOException {
        //文件大小
        long FileLength = file.length();
        double i =(double)FileLength / 1000;
        long round = Math.round(i);

        String[]  single=contextPath.split("/");
        int length = single.length;
        String repositoryName = single[3];
        String libraryName = single[length - 3];
        String version = single[length - 2];

        int repositoryNameIndex = contextPath.indexOf(repositoryName) + repositoryName.length()+1;
        //文件相对路径
        String relativePath = contextPath.substring(repositoryNameIndex, contextPath.length());

        //查询制品库是否存在
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
        if (CollectionUtils.isNotEmpty(repositoryList)){
            //创建制品
            Library library = libraryService.createLibraryData(libraryName, "maven",repositoryList.get(0));

            //制品版本创建、修改
            LibraryVersion libraryVersion = new LibraryVersion();
            libraryVersion.setLibrary(library);
            libraryVersion.setRepository(repositoryList.get(0));
            libraryVersion.setVersion(version);
            libraryVersion.setLibraryType("maven");
            if (contextPath.endsWith(".jar.sha1")){
                libraryVersion.setHash(gainFileData(file));
            }

            libraryVersion.setUser(user);
            String libraryVersionId =libraryVersionService.libraryVersionSplice(libraryVersion);


            //截取文件名称
            String fileName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
            //截取文件路径
            String fileUrl = StringUtils.substringBeforeLast(contextPath, "/");
            LibraryFile libraryFile = new LibraryFile();
            libraryFile.setLibrary(library);
            libraryFile.setFileName(fileName);
            libraryFile.setFileSize(round+"kb");
            libraryFile.setFileUrl(repositoryLibrary+fileUrl);
            libraryFile.setRepository(repositoryList.get(0));
            libraryFile.setRelativePath(relativePath);
            libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);


            // 制品maven
            libraryMavenService.libraryMavenSplice(libraryName,single,library);
            return result(201,"Create",null);
        }else {
            return result(404,null,null);
        }

    }
    /**
     *  maven提交-获取用户
     * @param userData  提交用户信息
     * @return
     */
    public List<User> findUser(String userData){
        String[]  userObject=userData.split(":");
        String userName = userObject[0];
        String password = userObject[1];


        UserQuery userQuery = new UserQuery();
        userQuery.setName(userName);
        userQuery.setPassword(password);
        List<User> userList = userService.findUserList(userQuery);
        return userList;
    }
    /**
     *  maven拉取-制品相关
     * @param mavenInstall     code
     * @return
     */
    public Map<String, Object> findRepository( String mavenInstall,String contextPath) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        int index = mavenInstall.indexOf("/",1);
        String repositoryName = mavenInstall.substring(0, index);
        //文件相对路径
        String relativePath = mavenInstall.substring(index);

        //查询组合的制品库
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
        if (CollectionUtils.isNotEmpty(repositoryList)){
            Repository repository = repositoryList.get(0);

            //组合库关联的制品库
            List<RepositoryGroup> libraryList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));
            if (CollectionUtils.isNotEmpty(libraryList)){
                List<String> libraryIdList = libraryList.stream().map(a->a.getRepository().getId()).collect(Collectors.toList());
                String[] librarySize = new String[libraryIdList.size()];
                String[] libraryId = libraryIdList.toArray(librarySize);

                //查询制品文件数据
                List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileByLibraryId(libraryId);
                List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> relativePath.equals(a.getRelativePath())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(libraryFiles)){
                    return readFileContent(libraryFiles.get(0).getFileUrl());
                }else {
                   return proxyInstall(libraryList,contextPath);
                }
            }
        }else {
            resultMap.put("code",404);
            resultMap.put("msg","repository not found");
            return resultMap;
        }
        return null;
    }

    /**
     *  maven拉取-远程代理拉取
     * @param libraryList: 文件内容
     * @param  contextPath
     * @return
     */
    public Map<String, Object> proxyInstall( List<RepositoryGroup> libraryList,String contextPath){
        String fileName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        if (fileName.endsWith(".jar")){
            // 过滤代理库信息
            List<RepositoryGroup> groupList = libraryList.stream().filter(a -> ("remote").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(groupList)){
                RepositoryGroup repositoryGroup = groupList.get(0);
                //远程代理路径
                List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findRepositoryRemoteProxyList(new RepositoryRemoteProxyQuery().setRepositoryId(repositoryGroup.getId()));
                if (CollectionUtils.isNotEmpty(remoteProxyList)){

                    //  contextPath.substring()
                    String text="mvn install:install-file " +
                            "-DgroupId=net.tiklab " +
                            "-DartifactId=tiklab-xpack-starter  " +
                            "-Dversion=1.0.0-RELEASE -Dpackaging=jar " +
                            "-Dfile=/Users/limingliang/publicData/test/net/tiklab/tiklab-xpack-starter/1.0.0-SNAPSHOT/tiklab-xpack-starter-1.0.0-SNAPSHOT.jar " +
                            "-DgeneratePom=true";
                    try {
                        Runtime.getRuntime().exec(text);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return null;
        }else {
            return result(300,"OK","ok");
        }
    }



    /**
     *  maven拉取-读取文件内容
     * @param filePath: 文件内容
     * @return
     */
    public Map<String, Object> readFileContent(String filePath)  throws IOException {

        File f = new File(filePath);
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
        return result(200,"OK",bytes);
    }
    /**
     *  获取sha1 或者metadata.xml数据
     *  @param file     文件
     * @return
     */
    public String gainFileData(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        StringBuilder result = new StringBuilder();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
        String lineTxt = null;
        while ((lineTxt = bfr.readLine()) != null) {
            result.append(lineTxt);
        }
        String toString = result.toString();
        String trim = toString.trim();
        return trim;
    }

    /**
     *  结果封装
     * @param code     code
     * @param msg     msg
     * @param data     data
     * @return
     */
    public Map<String, Object> result(int code,String msg,Object data){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code",code);
        resultMap.put("msg",msg);
        resultMap.put("data",data);
        return resultMap;
    }

}
