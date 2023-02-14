package net.tiklab.xpack.updownload.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.user.user.model.User;
import net.tiklab.user.user.model.UserQuery;
import net.tiklab.user.user.service.UserService;
import net.tiklab.xpack.library.model.Library;
import net.tiklab.xpack.library.model.LibraryFile;
import net.tiklab.xpack.library.model.LibraryFileQuery;
import net.tiklab.xpack.library.model.LibraryVersion;
import net.tiklab.xpack.library.service.LibraryFileService;
import net.tiklab.xpack.library.service.LibraryMavenService;
import net.tiklab.xpack.library.service.LibraryService;
import net.tiklab.xpack.library.service.LibraryVersionService;
import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.repository.model.RepositoryQuery;
import net.tiklab.xpack.repository.service.RepositoryService;
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
import java.util.Set;

@Service
public class LibraryMavenMutualServiceImpl implements LibraryMavenMutualService {
    private static Logger logger = LoggerFactory.getLogger(LibraryMavenMutualServiceImpl.class);
    @Value("${repository.library:null}")
    String repositoryLibrary;

    @Autowired
    LibraryService libraryService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryMavenService libraryMavenService;

    @Autowired
    UserService userService;


    @Override
    public Map<String,Object> mavenSubmit(String contextPath, InputStream inputStream,String userData, String method) throws IOException {

        List<User> userList = findUser(userData);
        User  user=null;
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
            return findRepository(mavenInstall);
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
        logger.warn("传入路径repositoryLibrary:{}",repositoryLibrary);

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
                    return result(220,"OK",gainFileData(file));


                }else {
                    return result(200,"OK",gainFileData(file));
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

        if (indexOf!=-1){
            //创建制品
            return librarySplice(contextPath,file,user) ;
        }else {
            return result(201,"Create",null);
        }
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
     *  maven提交-制品相关
     * @param mavenInstall     code
     * @return
     */
    public Map<String, Object> findRepository( String mavenInstall) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        int index = mavenInstall.indexOf("/",1);
        String repositoryName = mavenInstall.substring(0, index);
        String endFileUrl = mavenInstall.substring(index);
        String fileUrl=repositoryLibrary+"/test"+endFileUrl;
        //查询制品库
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
        if (CollectionUtils.isNotEmpty(repositoryList)){
            //查询是否存在制品文件
            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setFileUrl(fileUrl));
            if (CollectionUtils.isNotEmpty(libraryFileList)){
                return readFileContent(fileUrl,resultMap);
            }else {
                //local库不存在、就根据远程代理路径拉取
            }
        }else {
            resultMap.put("code",404);
            resultMap.put("msg","repository not found");
            return resultMap;
        }
        return null;
    }
    /**
     *  拉取后-读取文件内容
     * @param filePath: 文件内容
     * @return
     */
    public Map<String, Object> readFileContent(String filePath, Map<String, Object> resultMap)  throws IOException {

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
