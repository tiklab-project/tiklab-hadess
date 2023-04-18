package io.tiklab.xpack.library.service;

import io.tiklab.core.Result;
import io.tiklab.eam.common.model.EamTicket;
import io.tiklab.eam.passport.user.model.UserPassport;
import io.tiklab.eam.passport.user.service.UserPassportService;
import io.tiklab.user.user.model.User;
import io.tiklab.user.user.service.UserService;
import io.tiklab.xpack.library.model.Library;
import io.tiklab.xpack.library.model.LibraryFile;
import io.tiklab.xpack.library.model.LibraryVersion;
import io.tiklab.xpack.repository.model.*;
import io.tiklab.xpack.repository.service.RepositoryGroupService;
import io.tiklab.xpack.repository.service.RepositoryRemoteProxyService;
import io.tiklab.xpack.repository.service.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MavenUploadServiceImpl implements MavenUploadService {

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

    @Autowired
    UserPassportService userPassportService;

    @Autowired
    PullInfoService pullInfoService;

    @Override
    public Result<byte[]> mavenSubmit(String contextPath, InputStream inputStream, String userData) {
        EamTicket eamTicket;
        try {
            //账号校验
            eamTicket=verifyUser(userData);
        }catch (Exception e){
            return Result.error(401,e.getMessage());
        }
        try {
            return fileWriteData(inputStream, eamTicket.getUserId(), contextPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result<byte[]> mavenPull(String contextPath) {
        String repositoryUrl = contextPath.substring(contextPath.indexOf("xpack/maven") + 12);
        int index = repositoryUrl.indexOf("/",1);
        //客服端请求路径制品库名称
        String repositoryName = repositoryUrl.substring(0, index);

        //文件相对路径
        String relativePath = repositoryUrl.substring(index);
        relativePath = relativePath.substring(1);

        try {
            //查询组合的制品库
            List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
            if (CollectionUtils.isNotEmpty(repositoryList)){
                Repository repository = repositoryList.get(0);
                //maven上传 需要对maven-metadata 进行拉取校验
                if ("local".equals(repository.getRepositoryType())){
                    String filePath=repositoryLibrary+contextPath;
                    File file = new File(filePath);

                    String fileData = gainFileData(file);
                    if (StringUtils.isEmpty(fileData)){
                        return Result.error(404,"no find");
                    }
                   return readFileData(file);
                }
                //拉取 走组合库
                if ("group".equals(repository.getRepositoryType())){
                  return  groupRepositoryPull(repository,relativePath,contextPath);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result(404,null,"制品库不存在");
    }

    /**
     *  maven拉取-组合库
     * @param repository     组合制品库
     * @param relativePath
     * @return
     */
    public Result groupRepositoryPull(Repository repository,String relativePath,String contextPath) throws IOException {
        //组合库关联的制品库
        List<RepositoryGroup> libraryList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));
        if (CollectionUtils.isNotEmpty(libraryList)){
            List<String> libraryIdList = libraryList.stream().map(a->a.getRepository().getId()).collect(Collectors.toList());
            String[] librarySize = new String[libraryIdList.size()];
            String[] libraryId = libraryIdList.toArray(librarySize);

            //查询制品文件数据（）
            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileByLibraryId(libraryId);
            List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> relativePath.equals(a.getRelativePath())).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(libraryFiles)){
                String filePath = repositoryLibrary+libraryFiles.get(0).getFileUrl();
                File file = new File(filePath);
               // int length = (int) file.length();
                //私服库拉取
                return readFileData(file);
            }else {
                //走代理拉取
                return proxyPull(libraryList,relativePath,contextPath);
            }
        }
        return null;
    }

    /**
     *  maven拉取-代理拉取
     * @param libraryList     组合库关联库信息
     * @param relativePath
     * @return
     */
    public Result proxyPull(List<RepositoryGroup> libraryList,String relativePath,String contextPath){
        // 过滤代理库信息
        List<RepositoryGroup> groupList = libraryList.stream().filter(a -> ("remote").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(groupList)){
            RepositoryGroup repositoryGroup = groupList.get(0);

            //远程代理路径
            List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findRepositoryRemoteProxyList(new RepositoryRemoteProxyQuery().setRepositoryId(repositoryGroup.getRepository().getId()));
            if (CollectionUtils.isNotEmpty(remoteProxyList)){
                String proxyUrl = remoteProxyList.get(0).getAgencyUrl();
                //设置的代理地址 以/结尾  就去掉相对路径第一个/
                if (proxyUrl.endsWith("/")&&relativePath.startsWith("/")){
                    relativePath = relativePath.substring(1);
                }
                String relativeAbsoluteUrl=proxyUrl+relativePath;
                //转发到远程
                Result<byte[]> result = restTemplateMethod(relativeAbsoluteUrl);
                if (result.getCode()==200){
                    //解析相对路径 获取文件名称、版本、groupId
                    Map<String, String> relativeMap = resolverRelativePath(relativePath);
                    relativeMap.put("userId","111111");
                    relativeMap.put("relativePath",relativePath);
                    relativeMap.put("contextPath",contextPath);

                    int fileLength = result.getData().length;
                    double i =(double)fileLength / 1000;
                    long round = Math.round(i);
                    String fileSize;
                    if (round>0){
                        fileSize = round+"KB";
                    }else {
                        fileSize = fileLength + "B";
                    }
                    //拉取成功创建制品信息
                    // createLibrary(relativeMap,repositoryGroup.getRepository(), fileSize);
                }
                return result;
            }
        }
        return result(404,null,"制品不存在");
    }

    /**
     *  根据代理信息  转发远程库
     * @param relativeAbsoluteUrl  代理绝对路径
     * @return
     */
    public Result restTemplateMethod(String relativeAbsoluteUrl){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> entity = restTemplate.getForEntity(relativeAbsoluteUrl, byte[].class);
        HttpStatus statusCode = entity.getStatusCode();
        byte[] entityBody = entity.getBody();

        return result(200,entityBody,"OK");
    }

    /**
     *  maven-提交   写入文件、创建数据
     * @param contextPath     maven客户端提交路径 （制品文件夹路径+文件路径）
     * @param userId          用户id
     * @param inputStream     inputStream
     * @return
     */
    public Result fileWriteData(InputStream inputStream, String userId,String contextPath) throws IOException {
        String repositoryUrl = contextPath.substring(contextPath.indexOf("xpack/maven") + 12);
        int index = repositoryUrl.indexOf("/",1);
        //客服端请求路径制品库名称
        String repositoryName = repositoryUrl.substring(0, index);

        //文件相对路径
        String relativePath = repositoryUrl.substring(index);
        relativePath = relativePath.substring(1);

        //查询制品库是否存在
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));

        if (CollectionUtils.isNotEmpty(repositoryList)){
            Result result = new Result<>();
            //写入文件
            Result writeFile = writeFile(inputStream, contextPath);

            //文件大小
            String fileSize = writeFile.getData().toString();

            //解析相对路径 获取文件名称、版本、groupId
            Map<String, String> dataMap = resolverRelativePath(relativePath);
            dataMap.put("relativePath",relativePath);
            dataMap.put("userId",userId);
            dataMap.put("contextPath",contextPath);

            int indexOf = contextPath.indexOf("maven-metadata");
            if (indexOf==-1){

                createLibrary(dataMap,repositoryList.get(0),fileSize);
            }

            result.setCode(201);
            result.setMsg("Create");
            return result;
        }
        return Result.error(404,null);
    }

    /**
     *  maven-提交 文件file写入
     * @param inputStream     输入流
     * @param contextPath     maven客户端提交路径 （制品文件夹路径+文件路径）
     * @return   Result  写入数据的大小
     */
    public Result writeFile(InputStream inputStream,String contextPath) throws IOException {

        String url = StringUtils.substringBeforeLast(contextPath, "/");
        String path=repositoryLibrary+url;
        String filePath=repositoryLibrary+contextPath;
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }

        //用字节流写入文件
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        //文件大小
        long FileLength = file.length();
        double i =(double)FileLength / 1000;
        long round = Math.round(i);
        Result result = new Result<>();
        if (round>0){
            result.setData(round+"KB");
        }else {
            result.setData(FileLength+"B");
        }
        result.setCode(2000);
        return result;
    }



    /**
     *  读取文件信息
     *  @param file     文件
     * @return
     */
    public Result readFileData(File file) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        byte[] bytes = bos.toByteArray();

        return  result(200,bytes,"OK");
    }


    /**
     *  拉取远程后创建制品信息
     * @param  dataMap 数据整合的map
     * @param  repository 制品库
     * @param  fileSize   文件大小
     * @param
     * @return
     */
    public void createLibrary( Map<String, String> dataMap,Repository repository,String fileSize) throws IOException {

        //创建制品
        Library library = libraryService.createLibraryData(dataMap.get("libraryName"), "maven",repository);

        //制品版本创建、修改
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setLibrary(library);
        libraryVersion.setRepository(repository);
        libraryVersion.setVersion(dataMap.get("version"));
        libraryVersion.setLibraryType("maven");
        if (dataMap.get("relativePath").endsWith(".jar.sha1")){
            String filePath=repositoryLibrary+dataMap.get("contextPath");
            libraryVersion.setHash(gainFileData(new File(filePath)));
        }
        User user = new User();
        user.setId(dataMap.get("userId"));
        libraryVersion.setUser(user);
        String libraryVersionId = libraryVersionService.libraryVersionSplice(libraryVersion);


        //制品文件 创建、更新
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setFileName(dataMap.get("fileName"));
        libraryFile.setFileSize(fileSize);
        libraryFile.setRepository(repository);
        libraryFile.setFileUrl(dataMap.get("contextPath"));
        libraryFile.setRelativePath(dataMap.get("relativePath"));
        libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);

        // 制品maven  创建、更新
        libraryMavenService.libraryMavenSplice(dataMap.get("libraryName"),dataMap.get("groupId"),library);

    }

    /**
     *  解析相对路径
     * @param relativePath     客户端请求的相对路径 （不包含制品库）
     * @return
     */
    public Map<String,String> resolverRelativePath(String relativePath){
        //制品文件名称
        String fileName = relativePath.substring(relativePath.lastIndexOf("/") + 1);

        //版本
        String versionPath = relativePath.substring(0,relativePath.lastIndexOf("/"));
        String version = versionPath.substring(versionPath.lastIndexOf("/")+1);

        //制品名称
        String libraryPath=versionPath.substring(0,versionPath.lastIndexOf("/"));
        String libraryName = libraryPath.substring(libraryPath.lastIndexOf("/")+1);

        String groupIdPath = libraryPath.substring(0, libraryPath.lastIndexOf("/"));
        String groupId = groupIdPath.replace("/", ".");

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("libraryName",libraryName);
        resultMap.put("version",version);
        resultMap.put("groupId",groupId);
        resultMap.put("fileName",fileName);
        return resultMap;
    }


    /**
     *  获取sha1 或者metadata.xml数据
     *  @param file     文件
     * @return
     */
    public String gainFileData(File file) throws IOException {
        if (file.exists()){
            FileInputStream inputStream = new FileInputStream(file);
            StringBuilder result = new StringBuilder();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = null;
            while ((lineTxt = bfr.readLine()) != null) {
                result.append(lineTxt);
            }

            String toString = result.toString();
            String trim = toString.trim();
            return trim;
        }else {
            return null;
        }

    }


    /**
     *  maven提交-获取用户
     * @param userData  提交用户信息
     * @return
     */
    public EamTicket verifyUser(String userData){
        String[]  userObject=userData.split(":");
        String userName = userObject[0];
        String password = userObject[1];

        UserPassport userPassport = new UserPassport();
        userPassport.setAccount(userName);
        userPassport.setPassword(password);
        userPassport.setDirId("1");
        EamTicket login = userPassportService.login(userPassport);
        return login;
    }

    /**
     *  返回结果封装
     * @param code  返回编码
     * @param  data  数据
     * @return
     */
    public Result result(int code,byte[] data,String msg){
        Result<byte[]> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
}
