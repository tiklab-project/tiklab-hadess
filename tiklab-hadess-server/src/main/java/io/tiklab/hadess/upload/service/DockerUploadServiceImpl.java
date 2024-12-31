package io.tiklab.hadess.upload.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tiklab.core.Result;
import io.tiklab.core.exception.SystemException;
import io.tiklab.hadess.library.model.*;
import io.tiklab.eam.passport.user.service.UserPassportService;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.repository.model.*;
import io.tiklab.hadess.repository.service.RepositoryGroupService;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.common.UserCheckService;
import io.tiklab.hadess.upload.model.error.DockerErrorResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.Subject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DockerUploadServiceImpl implements DockerUploadService {
    private static Logger logger = LoggerFactory.getLogger(DockerUploadServiceImpl.class);
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RepositoryGroupService repositoryGroupService;

    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Autowired
    UserPassportService userPassportService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;
    
    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryVersionService versionService;

    @Autowired
    UserCheckService userCheckService;

    public static Map<String , String> blobsDataSize = new HashMap<>();

    public static Map<String , InputStream> blobsData = new HashMap<>();
    @Override
    public Result<String> userCheck(String authorization) {

        //docker第一次访问没有用户信息 为了获取支持的验证机制
        if (ObjectUtils.isEmpty(authorization)){

            return Result.error(402,"用户信息不存在");
        }
        try {
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            //用户信息
            String userData = new String(decode, "UTF-8");
            userCheckService.basicsUserCheck(userData);
            return Result.ok();
        }catch (Exception e){
            logger.info("docker登陆错误："+e.getMessage());
            return Result.error(401,e.getMessage());
        }
    }

    @Override
    public Result v2Sha256Check(String repositoryPath) throws Exception {
        String sha256 = repositoryPath.substring(repositoryPath.indexOf("sha256:") );

        String repositoryAddress = yamlDataMaService.repositoryAddress();

        //仓库名称
        String repositoryName = repositoryPath.substring(0,repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");

        if (ObjectUtils.isEmpty(repository)){
            return Result.error(404,"can not find repository,制品库"+repositoryName+"没有找到，请输入正确的制品库");
        }
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setFileName(sha256).setRepositoryId(repository.getId()));
        if (CollectionUtils.isEmpty(libraryFileList)){
            return Result.error(404,"制品不存在:"+sha256);
        }
        String fileUrl = libraryFileList.get(0).getFileUrl();
        //sha256的文件位置
        String AllFilePath = repositoryAddress + "/" + fileUrl;
        File blobsFile = new File(AllFilePath);
        if (blobsFile.exists()){
            //创建文件
            long fileLength = blobsFile.length();
            return Result.ok(String.valueOf(fileLength));
        }
        return Result.error(400,"文件不存在："+sha256);
    }

    @Override
    public Result uploadData(InputStream inputStream, String repositoryPath) throws IOException {

        String fileName = repositoryPath.substring(repositoryPath.lastIndexOf("/") + 1);

        blobsData.put(fileName,inputStream);

        //制品库名称
        String repositoryName = repositoryPath.substring(0, repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");
        if (ObjectUtils.isEmpty(repository)){
            return Result.error(404,"can not find repository,制品库"+repositoryName+"没有找到，请输入正确的制品库");
        }
        //制品名称
        String libraryName =getLibraryName(repositoryPath,"/blobs");

        //创建制品
        Library library = libraryService.createLibraryData(libraryName, "docker", repository);
        String repositoryAddress = yamlDataMaService.repositoryAddress();

        //blobs的文件夹(数据)
        String blobsFolder = repositoryAddress + "/" + repository.getId() + "/" + libraryName+"/"+"blobs";
        File blobsFolderFile = new File(blobsFolder);
        if (!blobsFolderFile.exists()){
            blobsFolderFile.mkdirs();
        }

        String blobsDoc = blobsFolder + "/" + fileName;
        File blobsFile = new File(blobsDoc);
        if (!blobsFile.exists()){
            blobsFile.createNewFile();
        }

        //用字节流写入文件
        FileOutputStream outputStream = new FileOutputStream(blobsDoc);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        //创建文件
        long fileLength = blobsFile.length();
        String size = RepositoryUtil.formatSize(fileLength);
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setFileName(fileName);
        libraryFile.setFileSize(size);
        libraryFile.setSize(fileLength);
        libraryFile.setRepository(repository);

        String fileUrl = repository.getId() + "/" + libraryName + "/" + "blobs/" + fileName;
        libraryFile.setFileUrl(fileUrl);
        libraryFile.setRelativePath("blobs/" + fileName);
        libraryFileService.redactLibraryFile(libraryFile,null);


        blobsDataSize.put(fileName,String.valueOf(fileLength));
        return Result.ok();
    }

    @Override
    public Result createSession(String repositoryPath) {
        String repositoryName = StringUtils.substringBefore(repositoryPath, "/");
        Repository repository = repositoryService.findRepository(repositoryName,"docker");
        if (ObjectUtils.isEmpty(repository)){
            return Result.error(404,"can not find repository，制品库"+repositoryName+"没有找到，请输入正确的制品库");
        }
        return Result.ok();
    }

    @Override
    public String createFile(String digest,String repositoryPath) throws IOException {
        String fileName = repositoryPath.substring(repositoryPath.lastIndexOf("/") + 1);


        //制品名称
        String libraryName =getLibraryName(repositoryPath,"/blobs/");

        //制品库名称
        String repositoryName = repositoryPath.substring(0, repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");


        String repositoryAddress = yamlDataMaService.repositoryAddress();
        String blobsFolder = repositoryAddress + "/" + repository.getId() + "/" + libraryName+"/"+"blobs/";

        String oldFileName = blobsFolder + fileName;
        String newFileName = blobsFolder + digest;
        File oldFile = new File(oldFileName);
        File newFile = new File(newFileName);

        // 使用 renameTo() 移动文件
        boolean success = oldFile.renameTo(newFile);

        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setRepositoryId(repository.getId()).setFileName(fileName));
        if (!CollectionUtils.isEmpty(libraryFileList)){
            LibraryFile libraryFile = libraryFileList.get(0);
            libraryFile.setFileName(digest);

            String replace = libraryFile.getFileUrl().replace(fileName, digest);
            libraryFile.setFileUrl(replace);

            String relative = libraryFile.getRelativePath().replace(fileName, digest);
            libraryFile.setRelativePath(relative);

            libraryFileService.updateLibraryFile(libraryFile);
        }
       return blobsDataSize.get(fileName);
    }

    @Override
    public String createTag(InputStream inputStream, String repositoryPath,String authorization) throws IOException, NoSuchAlgorithmException {


        String version = repositoryPath.substring(repositoryPath.lastIndexOf("/") + 1);

        //制品库名称
        String repositoryName = repositoryPath.substring(0, repositoryPath.indexOf("/"));


        //制品名称
        String libraryName =getLibraryName(repositoryPath,"/manifests");

        Repository repository = repositoryService.findRepository(repositoryName,"docker");

        String repositoryAddress = yamlDataMaService.repositoryAddress();

        //tags 文件夹路径
        String tagFolder = repositoryAddress + "/" + repository.getId() + "/" + libraryName + "/tags";
        //写入数据
        Long aLong = writeFileData(inputStream, tagFolder, version);

        File TagFile = new File(tagFolder + "/" + version);
        String readFile = readFile(TagFile);

        String sha256 = RepositoryUtil.sha256Encryption(readFile);
        String fileName = "sha256:" + sha256;
        String manFolder = repositoryAddress + "/" + repository.getId() + "/" + libraryName + "/manifests";


        InputStream  data  =  new   ByteArrayInputStream(readFile.getBytes());
        writeFileData(data,manFolder,fileName);

        //创建版本
        List<Library> libraryList = libraryService.likeLibraryListNo(new LibraryQuery().setName(libraryName)
                .setRepositoryId(repository.getId()));

        if (!CollectionUtils.isEmpty(libraryList)){
            Library library = libraryList.get(0);

            LibraryVersion libraryVersion = new LibraryVersion();
            libraryVersion.setLibrary(library);
            libraryVersion.setRepository(repository);
            libraryVersion.setVersion(version);
            libraryVersion.setLibraryType("docker");
            libraryVersion.setSize(aLong);

            //用户信息
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            //用户信息
            String userData = new String(decode, "UTF-8");
            String[] split = userData.split(":");
            libraryVersion.setPusher(split[0]);
            String libraryVersionId = versionService.createLibraryVersionSplice(libraryVersion,fileName);

            //创建描述文件
            String fileUrl = repository.getId() + "/" + libraryName + "/manifests/" + fileName;
            long fileLength = fileUrl.length();
            String size = RepositoryUtil.formatSize(fileLength);

            LibraryFile libraryFile = new LibraryFile();
            libraryFile.setLibrary(library);
            libraryFile.setFileName(fileName);
            libraryFile.setFileSize(size);
            libraryFile.setSize(fileLength);
            libraryFile.setRepository(repository);

            libraryFile.setFileUrl(fileUrl);
            libraryFile.setRelativePath("tag/" + fileName);
            libraryFileService.redactLibraryFile(libraryFile,libraryVersionId);
        }

        return readFile ;
    }

    @Override
    public Map<String, String> pullManifests(String requestAddress) {
        //仓库名称
        String repositoryName = StringUtils.substringBefore(requestAddress, "/");
        //版本
        String version = requestAddress.substring(requestAddress.lastIndexOf("/") + 1);
        //制品名称
        String libraryName = getLibraryName(requestAddress,"/manifests");

        Repository repository = repositoryService.findRepository(repositoryName,"docker");
        if (ObjectUtils.isEmpty(repository)){
            return putMapData("404",
                    responseError(repositoryName+" 制品库不存在",repositoryName+"/"+libraryName,version));
        }


        //仓库类型为组合库。注意： docker镜像在国内通过/v2的方式超时，这个方法暂时不可用
        if (("group").equals(repository.getRepositoryType())){

            List<RepositoryGroup> repositoryGroups = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));
            // 过滤代理库信息
            List<RepositoryGroup> groupList = repositoryGroups.stream().filter(a -> ("remote").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());
            List<RepositoryRemoteProxy> proxyList=null;
            if (!CollectionUtils.isEmpty(groupList)){
                //通过代理代理库的ID，查询对应的代理地址
                List<String> rpyIds = groupList.stream().map(a -> a.getRepository().getId()).collect(Collectors.toList());
                String[] repositoryIds = rpyIds.toArray(new String[rpyIds.size()]);
                proxyList = remoteProxyService.findAgencyByRpyIds(repositoryIds);
            }
            //通过制品名字和类型查询 制品是否存在
            Library library = libraryService.findLibraryByNameAndType(libraryName, "docker");
            if (ObjectUtils.isEmpty(library)&&!CollectionUtils.isEmpty(proxyList)){
                logger.info("Docker拉取-进入远程库校验/manifests");
               return RemotePullManifests(proxyList,requestAddress);
               // String a="sha256:45cb9583f823ccba269220f7f403340e69c32cb5e1a3266db6608e0b783ee179";
              //  return putMapData("200",a);
            }
        }

        List<LibraryFile> libraryFile = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, version);
        if (CollectionUtils.isEmpty(libraryFile)){
             return putMapData("404",
                     responseError(libraryName+" 制品不存在",repositoryName+"/"+libraryName,version));
        }
        //证明数据manifests
        List<LibraryFile> libraryFiles = libraryFile.stream().filter(a -> a.getFileUrl().contains("/manifests/")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(libraryFiles)){
            return putMapData("404",
                    responseError(repositoryName+" 制品文件不存在",repositoryName+"/"+libraryName,version));
        }

        String fileName = libraryFiles.get(0).getFileName();
        return putMapData("200",fileName);
    }

    /**
     *  远程拉取Manifests
     * @param proxyList 代理地址
     * @param requestAddress 请求地址
     */
   public Map<String, String> RemotePullManifests( List<RepositoryRemoteProxy> proxyList,String requestAddress){
       //版本
       String version = requestAddress.substring(requestAddress.lastIndexOf("/") + 1);

       String relativePath = StringUtils.substringAfter(requestAddress,"/" );

       //判断镜像是否有仓库名，没有就添加默认的仓库名library
       String imageName = StringUtils.substringBefore(relativePath, "/manifests");
       if (!imageName.contains("/")){
           relativePath="library/"+relativePath;
           imageName="library/"+imageName;
       }

       for (RepositoryRemoteProxy proxyUrl:proxyList){
           String relativeAbsoluteUrl=proxyUrl+"/v2/"+relativePath;
           try {

               //获取docker的认证token
               String dockerToken = getDockerToken(imageName);
               if (("获取docker失败").equals(dockerToken)){
                   putMapData("404",
                           responseError(dockerToken,imageName,version));
               }

               String manifestsData = restTemplateGet(relativeAbsoluteUrl, dockerToken);

               String sha256Encryption = RepositoryUtil.sha256Encryption(manifestsData);

               return putMapData("200",sha256Encryption);
           }catch (Exception e){
               logger.info("docker拉取manifests转发："+proxyUrl+"失败："+e.getMessage());
           }
       }
       return putMapData("404",
               responseError("镜像文件不存在",imageName,version));
   }

    @Override
    public String verifyManifests(String repositoryPath) {


        return null;
    }

    @Override
    public Map<String, String> readMirroringData(String repositoryPath) throws IOException {

        //仓库名称
        String repositoryName = repositoryPath.substring(0,repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");

        String substring =repositoryPath.substring(repositoryPath.indexOf("/"));

        String repositoryAddress = yamlDataMaService.repositoryAddress();
        String manifestsPath = repositoryAddress + "/" + repository.getId() + substring;
        String readFile = readFile(new File(manifestsPath));

        Map<String, String> map = putMapData("200", readFile);
        long length = new File(manifestsPath).length();
        map.put("size",String.valueOf(length));
        map.put("url",manifestsPath);
        return map;
    }



    public String readFile( File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
        BufferedInputStream in = new BufferedInputStream(inputStream);
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        String string = bos.toString();

        return string;

    }

    /**
     * 写入文件数据
     * @param inputStream 文件流
     */
    public Long writeFileData(InputStream inputStream,String folderPath,String fileName) throws IOException {
        File TagFolderFile = new File(folderPath);
        if (!TagFolderFile.exists()){
            TagFolderFile.mkdirs();
        }

        //tags 文件
        String tagFile = folderPath+"/"+fileName;
        File TagFile = new File(tagFile);
        if (!TagFile.exists()){
            TagFile.createNewFile();
        }

        //用字节流写入文件
        FileOutputStream outputStream = new FileOutputStream(tagFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();

        return TagFile.length();
    }


    /**
     * 输入结果数据
     * @param code code
     * @param  data 数据
     */
    public  Map<String, String> putMapData(String code,String data){
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code",code);
        resultMap.put("data",data);
        return resultMap;
    }

    /**
     * 制品名字
     * @param repositoryPath 请求路径
     */
    public  String getLibraryName(String repositoryPath,String type){
     //制品名称
        String libraryName = repositoryPath.substring(repositoryPath.indexOf("/") + 1, repositoryPath.indexOf(type));
        return libraryName;
    }

    /**
     * 返回的失败结果
     * @param massage massage
     */
    public String responseError(String massage,String imageName,String version){
        DockerErrorResponse errorResponse = new DockerErrorResponse();

        DockerErrorResponse.ErrorDetail errorDetail = new DockerErrorResponse.ErrorDetail();
        errorDetail.setCode("MANIFEST_UNKNOWN");
        errorDetail.setMessage(massage);
        DockerErrorResponse.ErrorDetail.DetailItem detailItem = new DockerErrorResponse.ErrorDetail.DetailItem();
        detailItem.setName(imageName);
        detailItem.setTag(version);

        errorDetail.setDetail(Arrays.asList(detailItem));
        errorResponse.setErrors(Arrays.asList(errorDetail));

        //转json字符串
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(errorResponse);
            return s;
        } catch (JsonProcessingException e) {
            logger.info("转获取img:"+imageName+"失败结果为json失败："+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取docker的token
     * @param imageName imageName
     */
    public String getDockerToken(String imageName){
       // String tokenUrl = "https://auth.docker.io/token?service=registry.docker.io&scope=repository:" +imageName+ ":pull";
        String tokenUrl = "https://auth.docker.io/token?service=registry.docker.io&scope=repository:library/hello-world:pull";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> forEntity = restTemplate.getForEntity(tokenUrl, JSONObject.class);
        int code = forEntity.getStatusCode().value();
        if (code==200){
            JSONObject resultBody = forEntity.getBody();
            String accessToken = resultBody.get("access_token").toString();
            return accessToken;
        }
        logger.info("docker获取token失败");
        return "获取docker失败";
    }

    /**
     * 执行get请求
     * @param relativeAbsoluteUrl 地址
     * @param token
     */
    public String restTemplateGet(String relativeAbsoluteUrl,String token){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders newHeaders = new HttpHeaders();
        //token不为空的时候为获取manifest数据
        if (!ObjectUtils.isEmpty(token)){
            newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            newHeaders.add(HttpHeaders.ACCEPT, "application/vnd.docker.distribution.manifest.v2+json");
        }
       
        ResponseEntity<JSONObject> retryResponse = restTemplate.exchange(relativeAbsoluteUrl, HttpMethod.GET,
                new HttpEntity<>(newHeaders), JSONObject.class);

        String jsonString = retryResponse.getBody().toJSONString();
        return jsonString;

    }
  

}
