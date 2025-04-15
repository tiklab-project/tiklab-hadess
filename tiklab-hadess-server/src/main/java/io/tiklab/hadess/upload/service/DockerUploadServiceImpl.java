package io.tiklab.hadess.upload.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tiklab.core.Result;
import io.tiklab.hadess.common.*;
import io.tiklab.hadess.library.model.*;
import io.tiklab.eam.passport.user.service.UserPassportService;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.repository.model.*;
import io.tiklab.hadess.repository.service.NetworkProxyService;
import io.tiklab.hadess.repository.service.RepositoryGroupService;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.upload.common.response.DockerResponse;
import io.tiklab.hadess.upload.model.error.DockerErrorResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


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

    @Autowired
    NetworkProxyService networkProxyService;



    public static Map<String , String> blobsDataSize = new HashMap<>();

    public static Map<String , InputStream> blobsData = new HashMap<>();

    //添加制品数据到数据库的状态
    public static Map<String , Boolean> insertLibState = new HashMap<>();

    //添加制品数据到数据库的状态
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

        //制品名称
        String beforePath = StringUtils.substringBefore(repositoryPath, "/blobs/");
        String libraryName = StringUtils.substringAfterLast(beforePath, "/");

        List<Library> libraryList = libraryService.findLibraryList(repository.getId(), libraryName);
        if (CollectionUtils.isEmpty(libraryList)){
            return Result.error(404,"can not find library,制品"+libraryName+"没有找到");
        }


        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery()
                .setFileName(sha256)
                .setRepositoryId(repository.getId())
                .setLibraryId(libraryList.get(0).getId()));
        if (CollectionUtils.isEmpty(libraryFileList)){
            return Result.error(404,"制品不存在:"+sha256);
        }
        String fileUrl = libraryFileList.get(0).getFileUrl();
        String blobsPath=repositoryAddress+"/"+fileUrl;
        String substringAfter = StringUtils.substringAfter(repositoryPath, "/");

        //sha256的文件位置
       // String blobsPath = repositoryAddress + "/" +  repository.getId()+"/"+substringAfter;
        File blobsFile = new File(blobsPath);

        if (blobsFile.exists()){
            //创建文件
            long fileLength = blobsFile.length();
            return Result.ok(String.valueOf(fileLength));
        }
        return Result.error(400,"文件不存在："+sha256);
    }

    @Override
    public Result uploadData(InputStream inputStream, String repositoryPath) throws Exception {

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
       // libraryService.createLibraryData(libraryName, "docker", repository);

        outputStream.flush(); ExecutorService executorService = Executors.newCachedThreadPool();

        // 提交任务到线程池
        Future<Library> future = executorService.submit(() -> {
            Boolean aBoolean = insertLibState.get(libraryName);
            while (aBoolean!=null&&!aBoolean){
            }
            insertLibState.put(libraryName,false);
            //创建制品
            Library library = libraryService.createLibraryData(libraryName, "docker", repository);
            insertLibState.put(libraryName,true);
            return library;
        });

        //获取结果
        Library library = future.get();
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

        libraryFileService.redactLibraryFile(libraryFile);
        //创建文件

        blobsDataSize.put(fileName, String.valueOf(fileLength));

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
    public String createFile(String digest,String repositoryPath)  {
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
    public String createManifests(InputStream inputStream, String repositoryPath,String authorization) throws IOException, NoSuchAlgorithmException {

        String version = repositoryPath.substring(repositoryPath.lastIndexOf("/") + 1);
        //制品库名称
        String repositoryName = repositoryPath.substring(0, repositoryPath.indexOf("/"));

        //制品名称
        String libraryName =getLibraryName(repositoryPath,"/manifests");
        Repository repository = repositoryService.findRepository(repositoryName,"docker");

        String path = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName;
        //写入文件blobs
        String blobsPath = path+"/tags";
        Path blobs = FileUtil.copyFileData(inputStream, blobsPath, version);

        //写入manifests
        File TagFile = new File(blobsPath + "/" + version);
        String readFile = RepositoryUtil.readFile(TagFile);
        String sha256 = RepositoryUtil.sha256Encryption(readFile);
        String fileName = "sha256:" + sha256;
        InputStream blobsInputStream = Files.newInputStream(blobs);
        String manifestsPath = path+"/manifests";
        FileUtil.copyFileData(blobsInputStream, manifestsPath, fileName);


        //推送 创建版本、文件
        List<Library> libraryList = libraryService.likeLibraryListNo(new LibraryQuery().setName(libraryName)
                .setRepositoryId(repository.getId()));

        if (!CollectionUtils.isEmpty(libraryList)){
            Library library = libraryList.get(0);

            LibraryVersion libraryVersion = new LibraryVersion();
            libraryVersion.setLibrary(library);
            libraryVersion.setRepository(repository);
            libraryVersion.setVersion(version);
            libraryVersion.setLibraryType("docker");
           // libraryVersion.setSize(aLong);

            //用户信息
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            //用户信息
            String userData = new String(decode, "UTF-8");
            String[] split = userData.split(":");
            libraryVersion.setPusher(split[0]);
            String libraryVersionId = versionService.createLibraryVersionSplice(libraryVersion,fileName);
            libraryVersion.setId(libraryVersionId);

            //创建描述文件
            String fileUrl = repository.getId() + "/" + libraryName + "/manifests/" + fileName;
            long fileLength = fileUrl.length();
            String size = RepositoryUtil.formatSize(fileLength);

            LibraryFile libraryFile = new LibraryFile();
            libraryFile.setRepository(repository);
            libraryFile.setLibrary(library);
            libraryFile.setLibraryVersion(libraryVersion);

            libraryFile.setFileName(fileName);
            libraryFile.setFileSize(size);
            libraryFile.setSize(fileLength);

            libraryFile.setFileUrl(fileUrl);
            libraryFile.setRelativePath("tag/" + fileName);
            libraryFileService.redactLibraryFile(libraryFile);
            
            //创建文件blobs制品文件信息
            addBlobsFile(libraryFile,readFile,repository.getId() + "/" + libraryName);

        }

        return readFile ;
    }

    @Override
    public Map<String, String> downloadManifests(String requestAddress,String userAgent) {
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
        //仓库类型为组合库
        if (("group").equals(repository.getRepositoryType())){
            return groupRepoManifests(repository, requestAddress,userAgent);
        }
        //仓库类型为代理库
        if (("remote").equals(repository.getRepositoryType())){
            return remoteRepoManifests(repository, requestAddress,userAgent);
        }

        //仓库类型为本地库
        List<LibraryFile> libraryFile = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, version);
        List<LibraryFile> libraryFiles = libraryFile.stream().filter(a -> a.getFileUrl().contains("/manifests/")).toList();
        if (CollectionUtils.isEmpty(libraryFiles)){
            return putMapData("404",
                    responseError(repositoryName+" 制品文件不存在",repositoryName+"/"+libraryName,version));
        }

        for (LibraryFile file:libraryFiles){
            String s = yamlDataMaService.repositoryAddress() + "/" + file.getFileUrl();
            File filePath = new File(s);
            if (!filePath.exists()){
                continue;
            }
            String readFile = RepositoryUtil.readFile(filePath);
            return putMapData("200",file.getFileName(),readFile);
        }

        return putMapData("404",
                responseError(libraryName+"制品存在local库中，且文件内容不存在",libraryName,version));
    }

    /**
     *  远程拉取Manifests
     * @param proxyList 代理地址
     * @param requestAddress 请求地址
     */
   public Map<String, String> RemotePullManifests( List<RepositoryRemoteProxy> proxyList,String requestAddress ,String userAgent){

       String name = StringUtils.substringAfterLast(requestAddress,"/");

       //版本
       String version = requestAddress.substring(requestAddress.lastIndexOf("/") + 1);

       String relativePath = StringUtils.substringAfter(requestAddress,"/" );

       //判断镜像是否有仓库名，没有就添加默认的仓库名library
       String imageName = StringUtils.substringBefore(relativePath, "/manifests");
       if (!imageName.contains("/")){
           relativePath="library/"+relativePath;
           imageName="library/"+imageName;
       }

       for (RepositoryRemoteProxy remoteProxy:proxyList){
           String relativeAbsoluteUrl=remoteProxy.getRemoteProxy().getAgencyUrl()+"/v2/"+relativePath;
           try {

               //获取docker的认证token
               String dockerToken = getDockerToken(imageName);
               if (("获取token失败").equals(dockerToken)){
                   putMapData("404",
                           responseError(dockerToken,imageName,version));
               }
                //读取manifests列表
               String manifestsData = restTemplateGet(relativeAbsoluteUrl, dockerToken);

               //解析manifests内容，并判断是否为镜像索引或者镜像清单
               Map<String,String> digestMap = getDigest(manifestsData,userAgent);
               String digest = digestMap.get("data");

               //为镜像清单直接返回
               if (("manifest").equals(digest)){
                   if (!name.startsWith("sha256")){
                       String s = RepositoryUtil.sha256Encryption(manifestsData);
                       name="sha256:"+s;
                   }
                   //添加数据
                   addLibraryData(remoteProxy.getRepository(),requestAddress,name,manifestsData);
                   return putMapData("200",name,manifestsData);
               }


               //为空代表 没有匹配到对应的系统和应用
               if (ObjectUtils.isEmpty(digest)){
                   String os = digestMap.get("os");
                   String arch = digestMap.get("arch");
                   return putMapData("500",
                           responseError("no matching manifest for "+os+"/"+arch +" in the manifest list entries",imageName,version));
               }

               //为镜像索引
               String s = StringUtils.substringBeforeLast(relativeAbsoluteUrl, "/");
               String s1 = s + "/" + digest;
               String manifestsData1 = restTemplateGet(s1, dockerToken);
               //转换MediaType格式。返回结果为ok代表不需要转换
               Map<String, String> convertMediaType = convertMediaType(manifestsData1);
               if (!("ok").equals(convertMediaType.get("name"))){
                   digest=convertMediaType.get("name");
                   manifestsData1=convertMediaType.get("data");
               }

               //添加数据
               addLibraryData(remoteProxy.getRepository(),requestAddress,digest,manifestsData1);


               return putMapData("200",digest,manifestsData1);
           }catch (Exception e){
               e.printStackTrace();
               logger.info("docker拉取manifests转发："+remoteProxy.getRemoteProxy().getAgencyUrl()+"失败："+e.getMessage());
               return putMapData("500",
                       responseError("远程拉取镜像错误",imageName,version));
           }
       }
       return putMapData("404",
               responseError("镜像文件不存在",imageName,version));
   }


    /**
     *  转发代理地址拉取数据
     * @param proxyList 代理地址
     * @param requestAddress 客户端请求地址
     */
    public void forwardingRemote(HttpServletResponse response, List<RepositoryRemoteProxy> proxyList
            ,String requestAddress) throws IOException {
        String relativePath = StringUtils.substringAfter(requestAddress,"/" );

        String imageName;
        if (requestAddress.contains("/blobs")){
            imageName = StringUtils.substringBefore(relativePath, "/blobs");
        }else {
            imageName = StringUtils.substringBefore(relativePath, "/manifests");
        }

        //判断镜像是否有仓库名，没有就添加默认的仓库名library
        if (!imageName.contains("/")){
            relativePath="library/"+relativePath;
            imageName="library/"+imageName;
        }

        for (int i=0 ;i<proxyList.size();i++){
            RepositoryRemoteProxy remoteProxy = proxyList.get(i);
            String relativeAbsoluteUrl=remoteProxy.getRemoteProxy().getAgencyUrl()+"/v2/"+relativePath;
            try {
                //获取docker的认证token
                String dockerToken = getDockerToken(imageName);
                if (("获取token失败").equals(dockerToken)){
                    response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
                    PrintWriter writer = response.getWriter();
                    writer.write(responseError(dockerToken,imageName,null));
                    writer.close();
                    return;
                }

                //获取本地存储的地址
                String storePath =getStoreFilePathAtRemote(remoteProxy.getRepository().getId(), requestAddress);

                //执行转发，代理地址
                restTemplateGetByte(response,relativeAbsoluteUrl, storePath,dockerToken);
                return;
            }catch (Exception e){
                logger.info("docker拉取Blobs转发："+remoteProxy.getRemoteProxy().getAgencyUrl()+"失败："+e.getMessage());
                //循环到最后一个代理地址都失败返回错误信息
                if (i+1==proxyList.size()){
                    e.printStackTrace();
                    response.setContentType("application/vnd.docker.distribution.manifest.v2+json");
                    PrintWriter writer = response.getWriter();
                    writer.write(responseError(e.getMessage(),imageName,null));
                    writer.close();
                }
            }
        }
    }


    /**
     * 获取digest
     * @param manifestsData manifestsData
     * @param userAgent 客户端请求头信息
     */
    public Map<String, String> getDigest(String manifestsData, String userAgent) throws JsonProcessingException {
        Map<String, String> hashMap = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(manifestsData);


        String mediaType = objectMapper.convertValue(jsonNode.get("mediaType"), String.class);

        //表示是属于镜像索引。包含多个镜像清单（manifests）
        if (("application/vnd.oci.image.index.v1+json").equals(mediaType)
                ||("application/vnd.docker.distribution.manifest.list.v2+json").equals(mediaType)){
            JsonNode manifests = jsonNode.get("manifests");
            List<Map> manifestsList = objectMapper.convertValue(manifests, List.class);

            String[] split = userAgent.split(" ");

            String os=null;
            String arch=null;
            for (String data:split){
                //获取客户端请求的系统 windows、linux
                if (data.startsWith("os/")){
                     os = StringUtils.substringAfter(data, "os/");
                }
                //获取客户端请求的镜像类型 arm、arm64、amd64等
                if (data.startsWith("arch/")){
                     arch = StringUtils.substringAfter(data, "arch/");
                }
            }


            String digest=null;
            for (Map manifest:manifestsList){
                Object o = manifest.get("platform");
                Map platformMap = objectMapper.convertValue(o, Map.class);
                String architecture = platformMap.get("architecture").toString();
                String os1 = platformMap.get("os").toString();
                Object o1 = platformMap.get("variant");
                if (architecture.equals(arch)&&os1.equals(os)){
                     digest = manifest.get("digest").toString();
                     break;
                }
            }
            hashMap.put("data",digest);
            hashMap.put("os",os);
            hashMap.put("arch",arch);
            return hashMap;
        }

        //表示属于镜像清单。描述单个镜像的特定版本
        hashMap.put("data","manifest");
        return hashMap;
    }

    @Override
    public void downloadMirroringData(HttpServletResponse response,String repositoryPath) throws IOException {

        //仓库名称
        String repositoryName = repositoryPath.substring(0,repositoryPath.indexOf("/"));
        Repository repository = repositoryService.findRepository(repositoryName,"docker");


        //客户端请求拉取仓库为组合库
        if (("group").equals(repository.getRepositoryType())){
            downloadByGroupRep(response,repository,repositoryPath);
            return ;
        }
        //客户端请求拉取仓库为代理库
        if (("remote").equals(repository.getRepositoryType())){
            downloadByRemoteRep(response,repository,repositoryPath);
            return ;
        }
        //客户端请求拉取仓库为本地库
        if (("local").equals(repository.getRepositoryType())){
            downloadByLocalRep(response,repository,repositoryPath);
        }
    }


    /**
     * docker拉取manifests->客户端请求的为组合库
     * @param repository 仓库
     * @param requestAddress 客户端请求地址
     */
    public Map<String, String> groupRepoManifests(Repository repository,String requestAddress,String userAgent){

        String repositoryName = repository.getName();
        //版本
        String version = requestAddress.substring(requestAddress.lastIndexOf("/") + 1);
        //制品名称
        String libraryName = getLibraryName(requestAddress,"/manifests");

        //查询组合库关联的仓库
        List<RepositoryGroup> repositoryGroups = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));
        if (CollectionUtils.isEmpty(repositoryGroups)){
            logger.info("Docker拉取-"+repositoryName+"为组合库，且为关联其他仓库");
            return putMapData("404",
                    responseError(repositoryName+"为组合库，且未关联其他仓库",libraryName,version));
        }


        List<String> rpyIds = repositoryGroups.stream().map(a -> a.getRepository().getId()).toList();
        String[] repositoryIds = rpyIds.toArray(new String[rpyIds.size()]);

        //通过仓库id、 制品、版本查询文件
        List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repositoryIds, libraryName, version);

        //查询镜像及其层的元数据结构是否存在
        List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getFileUrl().contains("/manifests/")).toList();
        if (CollectionUtils.isEmpty(libraryFiles)){
            //通过代理代理库的ID，查询对应的代理地址
            List<RepositoryRemoteProxy> remoteProxies = getProxyAddress(repositoryGroups);
            if (CollectionUtils.isEmpty(remoteProxies)){
                return putMapData("404",
                        responseError(repositoryName+"关联的代理库未设置代理地址",libraryName,version));
            }
            logger.info("配置组合库拉取Manifests文件，数据库未查询到进入代理拉取");
            //进入代理拉取远程的docker镜像
            return RemotePullManifests(remoteProxies,requestAddress,userAgent);
        }

        //先判断文件是否是local库中的文件。如果是存在local库中就只取local库中的
        List<RepositoryGroup> localGroupList = repositoryGroups.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType())).toList();
        List<LibraryFile> fileList = libraryFiles.stream()
                .filter(LibraryFile -> localGroupList.stream()
                        .anyMatch(RepositoryGroup -> RepositoryGroup.getRepository().getId() .equals(LibraryFile.getRepository().getId())) )
                .toList();
        if (!CollectionUtils.isEmpty(fileList)){
            for (LibraryFile file:fileList){
                String s = yamlDataMaService.repositoryAddress() + "/" + file.getFileUrl();
                File filePath = new File(s);
                if (!filePath.exists()){
                    continue;
                }
                String readFile = RepositoryUtil.readFile(filePath);
                return putMapData("200",file.getFileName(),readFile);
            }
            return putMapData("404",
                    responseError(libraryName+"制品存在local库中，且文件内容不存在",libraryName,version));
        }

        //都是remote库中的文件
        LibraryFile libraryFile = libraryFiles.get(0);
        String path = yamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();
        File filePath = new File(path);

        //制品数据库数据存在，文件不存在
        if (!filePath.exists()){
            List<RepositoryRemoteProxy> proxyAddress = getProxyAddress(repositoryGroups);
            if (CollectionUtils.isEmpty(proxyAddress)){
                return putMapData("404",
                        responseError(repositoryName+"关联的代理库未设置代理地址",libraryName,version));
            }
            logger.info("配置组合库拉取Manifests文件，未查询到文件进入代理拉取");
            //进入代理拉取远程的docker镜像
            return RemotePullManifests(proxyAddress,requestAddress,userAgent);
        }
        String readFile = RepositoryUtil.readFile(filePath);
        return putMapData("200",libraryFile.getFileName(),readFile);

    }

    /**
     * docker拉取manifests->客户端请求的为代理库
     * @param repository 仓库
     * @param requestAddress 客户端请求地址
     */
    public Map<String, String> remoteRepoManifests(Repository repository,String requestAddress,String userAgent){

        String repositoryName = repository.getName();
        //版本
        String version = requestAddress.substring(requestAddress.lastIndexOf("/") + 1);
        //制品名称
        String libraryName = getLibraryName(requestAddress,"/manifests");


        //通过仓库id、 制品、版本查询文件
        List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, version);

        //查询镜像及其层的元数据结构是否存在
        List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getFileUrl().contains("/manifests/")).toList();
        if (CollectionUtils.isEmpty(libraryFiles)){
            List<RepositoryRemoteProxy> remoteProxies = remoteProxyService.findAgencyByRepId(repository.getId());
            if (CollectionUtils.isEmpty(remoteProxies)){
                return putMapData("404",
                        responseError(repositoryName+"未设置代理地址",libraryName,version));
            }
            logger.info("配置代理库拉取Manifests文件，数据库数据不存在到进入代理拉取");
            //进入代理拉取远程的docker镜像
            return RemotePullManifests(remoteProxies,requestAddress,userAgent);
        }

        //存在manifests文件 直接本地拉取
        LibraryFile libraryFile = libraryFiles.get(0);
        String path = yamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();
        File filePath = new File(path);

        //制品数据库数据存在，文件不存在去中央仓库拉取
        if (!filePath.exists()){
            List<RepositoryRemoteProxy> remoteProxies = remoteProxyService.findAgencyByRepId(repository.getId());
            if (CollectionUtils.isEmpty(remoteProxies)){
                return putMapData("404",
                        responseError(repositoryName+"未设置代理地址",libraryName,version));
            }
            logger.info("配置代理库拉取Manifests文件，文件不存在进入代理拉取");
            //进入代理拉取远程的docker镜像
            return RemotePullManifests(remoteProxies,requestAddress,userAgent);
        }
        String readFile = RepositoryUtil.readFile(filePath);
        return putMapData("200",libraryFile.getFileName(),readFile);

    }



    /**
     * docker拉取(blobs、manifests)->客户端请求的为组合库
     * @param repository 仓库
     * @param requestAddress 客户端请求地址
     */
    public void downloadByGroupRep(HttpServletResponse response,Repository repository,String requestAddress) throws IOException {

        //制品名称
        String libraryName;
        if (requestAddress.contains("blobs")){
             libraryName =getLibraryName(requestAddress,"/blobs");
        }else {
             libraryName =getLibraryName(requestAddress,"/manifests");
        }
        //文件名字
        String fileName = StringUtils.substringAfterLast(requestAddress, "/");

        //查询需要拉取文件
        List<RepositoryGroup> repositoryGroups = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery()
                .setRepositoryGroupId(repository.getId()));
        List<String> rpyIds = repositoryGroups.stream().map(a -> a.getRepository().getId()).toList();
        String[] repositoryIds = rpyIds.toArray(new String[rpyIds.size()]);
        List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repositoryIds, libraryName, null);
        List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> fileName.equals(a.getFileName())).toList();

        if (CollectionUtils.isEmpty(libraryFiles)){

            //读取并解析Manifests内容并放入response
            List<LibraryFile> manifestsFiles = libraryFileList.stream().filter(a -> a.getFileUrl().contains("/manifests/")).toList();
            readManifests(response,manifestsFiles.get(0).getFileUrl(),fileName);

             logger.info("docker拉取，数据库数据不存在进入代理拉取");
             //查询代理地址，转发远程的拉取docker镜像
             List<RepositoryRemoteProxy> remoteProxies = getProxyAddress(repositoryGroups);
             forwardingRemote(response,remoteProxies,requestAddress);
             return;
        }

        //先判断文件是否是local库中的文件。如果是存在local库中就只取local库中的
        List<LibraryFile> manifestsLibraryFile = libraryFileList.stream().filter(a -> a.getFileUrl().contains("/manifests/")).toList();
        List<RepositoryGroup> localGroupList = repositoryGroups.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType())).toList();
        List<LibraryFile> fileList = libraryFiles.stream()
                .filter(LibraryFile -> localGroupList.stream()
                        .anyMatch(RepositoryGroup -> RepositoryGroup.getRepository().getId() .equals(LibraryFile.getRepository().getId())) )
                .toList();
        if (!CollectionUtils.isEmpty(fileList)){
            for (LibraryFile file:fileList){
                List<LibraryFile> list = manifestsLibraryFile.stream().filter(a -> file.getLibrary().getId().equals(a.getLibrary().getId())).toList();

                String s = yamlDataMaService.repositoryAddress() + "/" + file.getFileUrl();
                File filePath = new File(s);
                if (!filePath.exists()){
                    continue;
                }
                if (requestAddress.contains("manifests")){
                    String read = RepositoryUtil.readFile(filePath);
                    //返回信息给客户端
                    DockerResponse.dockerReadManifest(response,s,read);
                    return;
                }

                //客户端请求blobs文件时候，需要读取Manifest内容中对应blobs的数据
                readManifests(response,list.get(0).getFileUrl(),fileName);
                readBlobsFile(response,filePath);
                return;
            }
            //返回错误信息
            DockerResponse.errorMirroringData(response,
                    responseError(libraryName+"制品存在local库中，且文件内容不存在",libraryName,null));
            return;
        }



        //都是remote库中的文件
        LibraryFile libraryFile = libraryFiles.get(0);
        List<LibraryFile> list = manifestsLibraryFile.stream().filter(a -> libraryFile.getLibrary().getId().equals(a.getLibrary().getId())).toList();

        String path = yamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();
        File filePath = new File(path);

        //制品数据库数据存在，文件不存在
        if (!filePath.exists()){
            logger.info("配置组合库拉取blobs文件，文件不存在进入代理拉取");
            //查询代理地址，转发远程的拉取docker镜像
            List<RepositoryRemoteProxy> proxyAddress = getProxyAddress(repositoryGroups);
            forwardingRemote(response,proxyAddress,requestAddress);
             return;
        }


        if (requestAddress.contains("manifests")){
            String read = RepositoryUtil.readFile(filePath);
            //返回信息给客户端
            DockerResponse.dockerReadManifest(response,path,read);
            return;
        }

        //客户端请求blobs文件时候，需要读取Manifest内容中对应blobs的数据
        readManifests(response,list.get(0).getFileUrl(),fileName);
        readBlobsFile(response,filePath);
    }

    /**
     * docker拉取(blobs、manifests)->客户端请求的为代理库
     * @param repository 仓库
     * @param requestAddress 客户端请求地址
     */
    public void downloadByRemoteRep(HttpServletResponse response,Repository repository,String requestAddress) throws IOException {
        //制品名称
        String libraryName;
        if (requestAddress.contains("blobs")){
            libraryName =getLibraryName(requestAddress,"/blobs");
        }else {
            libraryName =getLibraryName(requestAddress,"/manifests");
        }

        //通过制品、文件名获取对应数制品文件数据
        String fileName = StringUtils.substringAfterLast(requestAddress, "/");
        List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, null);
        List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> fileName.equals(a.getFileName())).toList();

        //文件不存在，转发远程拉取
        if (CollectionUtils.isEmpty(libraryFiles)){
            //读取并解析Manifests内容并放入response
            List<LibraryFile> manifestsFiles = libraryFileList.stream().filter(a -> a.getFileUrl().contains("/manifests/")).toList();
            readManifests(response,manifestsFiles.get(0).getFileUrl(),fileName);

            logger.info("docker拉取，数据库数据不存在进入代理拉取");
            //查询代理地址，转发远程的拉取docker镜像
            List<RepositoryRemoteProxy> remoteProxies = remoteProxyService.findAgencyByRepId(repository.getId());
            forwardingRemote(response,remoteProxies,requestAddress);
            return;
        }

        //存在本地直接拉取
        LibraryFile libraryFile = libraryFiles.get(0);
        List<LibraryFile> manifestsLibraryFile = libraryFileList.stream().filter(a -> a.getFileUrl().contains("/manifests/")).toList();
        List<LibraryFile> list = manifestsLibraryFile.stream().filter(a -> libraryFile.getLibrary().getId().equals(a.getLibrary().getId())).toList();

        String path = yamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();
        File filePath = new File(path);

        //制品数据库数据存在，文件不存在。转发远程
        if (!filePath.exists()){
            logger.info("配置组合库拉取blobs文件，文件不存在进入代理拉取");
            //查询代理地址，转发远程的拉取docker镜像
            List<RepositoryRemoteProxy> remoteProxies = remoteProxyService.findAgencyByRepId(repository.getId());
            forwardingRemote(response,remoteProxies,requestAddress);
            return;
        }

        //拉取manifests数据
        if (requestAddress.contains("manifests")){
            String read = RepositoryUtil.readFile(filePath);
            //返回信息给客户端
            DockerResponse.dockerReadManifest(response,path,read);
            return;
        }


        //客户端请求blobs文件时候，需要读取Manifest内容中对应blobs的数据的类型、大小返回给客户端
        readManifests(response,list.get(0).getFileUrl(),fileName);
        readBlobsFile(response,filePath);
    }

    /**
     * docker拉取(blobs、manifests)->客户端请求的为本地库
     * @param repository 仓库
     * @param requestAddress 客户端请求地址
     */
    public void downloadByLocalRep(HttpServletResponse response,Repository repository,String requestAddress) throws IOException {
        //制品名称
        String libraryName;
        if (requestAddress.contains("blobs")){
            libraryName =getLibraryName(requestAddress,"/blobs");
        }else {
            libraryName =getLibraryName(requestAddress,"/manifests");
        }

        //通过制品、文件名获取对应数制品文件数据
        String fileName = StringUtils.substringAfterLast(requestAddress, "/");
        List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, null);
        List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> fileName.equals(a.getFileName())).toList();
       //文件不存在
        if (CollectionUtils.isEmpty(libraryFiles)){
            logger.info("docker拉取，配置本地库且数据库数据不存在");
            //返回错误信息
            DockerResponse.errorMirroringData(response,
                    responseError(libraryName+"数据不存在",libraryName,null));

        }

        String repositoryAddress = yamlDataMaService.repositoryAddress();

        String manifestsPath = repositoryAddress + "/" +libraryFiles.get(0).getFileUrl();
        File file = new File(manifestsPath);
        if (!file.exists()){
            logger.info("docker拉取，配置本地库文件不存在");
            //返回错误信息
            DockerResponse.errorMirroringData(response,
                    responseError(libraryName+"文件不存在",libraryName,null));
        }

        //拉取manifests数据
        if (requestAddress.contains("manifests")){
            String read = RepositoryUtil.readFile(file);
            //返回信息给客户端
            DockerResponse.dockerReadManifest(response,manifestsPath,read);
            return;
        }


        //客户端请求blobs文件时候，需要读取Manifest内容中对应blobs的数据的类型、大小返回给客户端
        readManifests(response,libraryFiles.get(0).getFileUrl(),fileName);
        readBlobsFile(response,file);
    }



    /**
     * 获取代理地址
     * @param repositoryGroups 仓库组list
     */
    public List<RepositoryRemoteProxy> getProxyAddress(List<RepositoryGroup> repositoryGroups){
        //通过代理代理库的ID，查询对应的代理地址
        List<RepositoryGroup> groupList = repositoryGroups.stream().filter(a -> ("remote").equals(a.getRepository().getRepositoryType())).toList();
        List<String> reRpyIdList = groupList.stream().map(a -> a.getRepository().getId()).toList();
        String[] reRpyIds = reRpyIdList.toArray(new String[reRpyIdList.size()]);
        List<RepositoryRemoteProxy> proxyList = remoteProxyService.findAgencyByRpyIds(reRpyIds);

        return proxyList;
    }

    /**
     * 读取blobs文件并直接返回
     * @param response response
     */
    public void readBlobsFile(HttpServletResponse response,File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        BufferedInputStream in = new BufferedInputStream(inputStream);
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
        ServletOutputStream outputStream = response.getOutputStream();
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
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
     * 解析Manifests文件并添加到response
     * @param manifestsData Manifests内容
     * @param pushFileName 客户端上传的filename
     */
    public void analysisManifests(HttpServletResponse response,String manifestsData,String pushFileName) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Docker-Content-Digest", pushFileName);

        //向客户端返回文件名字大小和mediaType
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(manifestsData);
        JsonNode config = jsonNode.get("config");
        String digest = objectMapper.convertValue(config.get("digest"), String.class);
        if (pushFileName.equals(digest)){
            String mediaType = objectMapper.convertValue(config.get("mediaType"), String.class);
            String size = objectMapper.convertValue(config.get("size"), String.class);
            response.setContentType(mediaType);
            response.setHeader("Content-Length",size);
            return;
        }

        JsonNode layers = jsonNode.get("layers");
        List<Map> layerList = objectMapper.convertValue(layers, List.class);
        List<Map> layerList1 = layerList.stream().filter(a -> pushFileName.equals(a.get("digest"))).toList();
        if (!CollectionUtils.isEmpty(layerList1)){
            Object mediaType = layerList1.get(0).get("mediaType");
            Object size = layerList1.get(0).get("size");
            response.setContentType(String.valueOf(mediaType));
            response.setHeader("Content-Length",String.valueOf(size));
        }
    }

    /**
     * 写入文件数据
     * @param inputStream 文件流
     * @param folderPath 文件路径
     * @param  fileName 文件名字
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
     * 输入结果数据
     * @param code code
     * @param  data 数据
     */
    public  Map<String, String> putMapData(String code,String name,String data){
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code",code);
        resultMap.put("name",name);
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
        String tokenUrl =HadessFinal.DOCKER_TOKEN+"?service=registry.docker.io&scope=repository:"+imageName+ ":pull";
        //String tokenUrl = "https://auth.docker.io/token?service=registry.docker.io&scope=repository:" +imageName+ ":pull";
        logger.info("docker获取token地址"+tokenUrl);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(RepositoryUtil.getNetworkProxy(networkProxyService));
        ResponseEntity<JSONObject> forEntity = restTemplate.getForEntity(tokenUrl, JSONObject.class);
        int code = forEntity.getStatusCode().value();
        if (code==200){
            JSONObject resultBody = forEntity.getBody();
            String accessToken = resultBody.get("access_token").toString();
            return accessToken;
        }
        logger.info("docker获取token失败");
        return "获取token失败";
    }

    /**
     * 执行get请求
     * @param relativeAbsoluteUrl 地址
     * @param token
     */
    public String restTemplateGet(String relativeAbsoluteUrl,String token) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(RepositoryUtil.getNetworkProxy(networkProxyService));
        HttpHeaders newHeaders = new HttpHeaders();
        //token不为空的时候为获取manifest数据
        if (!ObjectUtils.isEmpty(token)){
            newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            newHeaders.set("Accept", "application/vnd.docker.distribution.manifest.v2+json");
        }
       
        ResponseEntity<JSONObject> retryResponse = restTemplate.exchange(relativeAbsoluteUrl, HttpMethod.GET,
                new HttpEntity<>(newHeaders), JSONObject.class);

        String jsonString = retryResponse.getBody().toJSONString();

        return jsonString;

    }



    /**
     * 执行get请求
     * @param relativeAbsoluteUrl 转发地址
     * @param token 认证token
     * @param storePath 本地存储地址
     */
    public void restTemplateGetByte(HttpServletResponse response,String relativeAbsoluteUrl,
                                      String storePath,String token) {
        RestTemplate restTemplate = new RestTemplate();
        Path localFilePath = Paths.get(storePath);
        restTemplate.setRequestFactory(RepositoryUtil.getNetworkProxy(networkProxyService));
        HttpHeaders newHeaders = new HttpHeaders();
        //token不为空的时候为获取manifest数据
        if (!ObjectUtils.isEmpty(token)){
            newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            newHeaders.set("Accept", "application/vnd.docker.distribution.manifest.v2+json");
        }
        HttpEntity<String> entity = new HttpEntity<>(newHeaders);

        restTemplate.execute(relativeAbsoluteUrl, HttpMethod.GET, request -> {
            // 复制HttpEntity中的headers
            request.getHeaders().putAll(entity.getHeaders());}, clientHttpResponse -> {
            try (InputStream inputStream = clientHttpResponse.getBody();
                 OutputStream outputStream = response.getOutputStream();
                 OutputStream fileOutput = Files.newOutputStream(localFilePath, StandardOpenOption.CREATE)) {

                byte[] buffer = new byte[8192]; // 8KB缓冲区
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {

                    //返回给客户端
                    outputStream.write(buffer, 0, bytesRead);
                    outputStream.flush();

                    //写入本地服务中
                    if (storePath.contains("/blobs")){
                        fileOutput.write(buffer, 0, bytesRead);
                        fileOutput.flush();
                    }
                }
                // inputStream.transferTo(outputStream);
            }
            return null;
        });
    }





    /**
     * 读取并解析Manifests内容
     * @param response response
     * @param pushFileName 客户端上传的filename
     */
    public void readManifests(HttpServletResponse response,String filePath,String pushFileName) throws IOException {
        String s = yamlDataMaService.repositoryAddress() + "/" + filePath;
        File file = new File(s);
        String manifestsData = RepositoryUtil.readFile(file);
        //读取 Manifests文件信息,并添加到response中
        analysisManifests(response,manifestsData,pushFileName);
    }


    /**
     * 转换mediaType 格式为manifest.v2+json。 docker pull只支持application/vnd.docker.distribution.manifest.v2+json 格式
     * @param manifestsData manifestsData内通
     */
    public Map<String, String> convertMediaType(String manifestsData) throws  NoSuchAlgorithmException {
        Map<String, String> hashMap = new HashMap<>();
        // 解析JSON字符串为JSONObject
        JSONObject jsonObject = JSON.parseObject(manifestsData);

        // 获取versions对象
        String allMediaType = jsonObject.get("mediaType").toString();
        if (!("application/vnd.docker.distribution.manifest.v2+json").equals(allMediaType)){
            jsonObject.put("mediaType", "application/vnd.docker.distribution.manifest.v2+json");
            // 描述了镜像配置的格式
            JSONObject config = jsonObject.getJSONObject("config");
            String configMediaType = config.getString("mediaType");
            if (!("application/vnd.docker.container.image.v1+json").equals(configMediaType)){
                config.put("mediaType","application/vnd.docker.container.image.v1+json");
            }

            //便利镜像清单
            JSONArray layers = jsonObject.getJSONArray("layers");
            for (int i = 0; i < layers.size(); i++) {
                JSONObject layer = layers.getJSONObject(i);
                String layerMediaType = layer.getString("mediaType");
                if (layerMediaType.startsWith("application/vnd.oci.image")) {
                    String  type;
                    if (layerMediaType.endsWith("gzip")){
                         type="application/vnd.docker.image.rootfs.diff.tar.gzip";
                    }else {
                        type="application/vnd.docker.image.rootfs.diff.tar";
                    }
                    layer.put("mediaType", type);
                }
            }
            // 将Java对象转换为JSON字符串
            String updatedJsonString = jsonObject.toJSONString();
            String sha256 = RepositoryUtil.sha256Encryption(updatedJsonString);
            String fileName = "sha256:" + sha256;
            hashMap.put("name",fileName);
            hashMap.put("data",updatedJsonString);
            return hashMap;
        }
        hashMap.put("name","ok");
        return hashMap;
    }

    /**
     * docker远程下载 -> 获取文件本地存储地址
     * @param pathData 客户端请求路径
     */
    public String getStoreFilePathAtRemote(String repId,String pathData) throws IOException {
        String substringAfter = StringUtils.substringAfter(pathData, "/");

        String filePath = StringUtils.substringBefore(substringAfter, "/sha256");
        String path = yamlDataMaService.repositoryAddress() + "/" + repId + "/" + filePath ;
        // 规范化路径并创建目录（如果不存在）
        Path dirPath = Paths.get(path);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        return  yamlDataMaService.repositoryAddress() + "/" + repId + "/" + substringAfter ;
    }



    /**
     * 添加制品信息
     * @param repository 制品库
     * @param requestAddress 客户端请求路径
     * @param manifestsName manifests的名字
     * @param manifestsData    manifests的内容
     */
    public void  addLibraryData(Repository repository,String requestAddress,
                                String manifestsName,String manifestsData) throws IOException {
        //版本
        String version = requestAddress.substring(requestAddress.lastIndexOf("/") + 1);
        //制品名称
        String libraryName = getLibraryName(requestAddress,"/manifests");


        //创建制品
        Library library = libraryService.createLibraryData(libraryName, "docker", repository);

        //创建版本
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setPusher("central warehouse");
        libraryVersion.setLibrary(library);
        libraryVersion.setVersion(version);
        libraryVersion.setRepository(repository);
        libraryVersion.setLibraryType("docker");
        String versionId = versionService.redactLibraryVersion(libraryVersion);
        libraryVersion.setId(versionId);

        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setRepository(repository);
        libraryFile.setLibraryVersion(libraryVersion);
        String path = repository.getId() + "/" + libraryName;

        //创建文件manifests制品文件信息
        String size = RepositoryUtil.formatSize(manifestsData.length());
        String manifestsPath = path + "/manifests";
        libraryFile.setFileName(manifestsName);
        libraryFile.setFileSize(size);
        libraryFile.setSize((long)manifestsData.length());
        libraryFile.setFileUrl(manifestsPath+"/"+manifestsName);
        libraryFile.setRelativePath("manifests/" + manifestsName);
        libraryFileService.redactLibraryFile(libraryFile);

        //创建文件blobs制品文件信息
        addBlobsFile(libraryFile,manifestsData,path);

        //写入数据manifests
        String repositoryAddress = yamlDataMaService.repositoryAddress();
        String manifestsFolder = repositoryAddress +"/"+ manifestsPath;
        InputStream  data  =  new ByteArrayInputStream(manifestsData.getBytes());
        writeManifestFile(data, manifestsFolder, manifestsName,version);
    }

    /**
     * 写入Manifest内容
     * @param libraryFile libraryFile
     * @param manifestsData manifestsData
     * @param path path
     */
    public void addBlobsFile(LibraryFile libraryFile,String manifestsData,String path) throws JsonProcessingException {
        String blobsPath = path + "/blobs/";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(manifestsData);

        //添加镜像配置的信息
        JsonNode config = jsonNode.get("config");
        String configDigest = objectMapper.convertValue(config.get("digest"), String.class);
        Integer configSize = objectMapper.convertValue(config.get("size"), Integer.class);
        libraryFile.setFileName(configDigest);
        libraryFile.setSize((long)configSize);
        libraryFile.setFileSize(RepositoryUtil.formatSize(configSize));
        libraryFile.setFileUrl(blobsPath+configDigest);
        libraryFile.setRelativePath("blobs/" + configDigest);
        libraryFileService.redactLibraryFile(libraryFile);


        //添加镜像的文件系统层，每一层都代表了镜像的一个增量变化
        JsonNode layers = jsonNode.get("layers");
        List<Map> layersList = objectMapper.convertValue(layers, List.class);
        for (Map layer:layersList){
            String digest = layer.get("digest").toString();
            Integer size = Integer.valueOf(layer.get("size").toString());
            libraryFile.setFileName(digest);
            libraryFile.setSize((long)size);
            libraryFile.setFileSize(RepositoryUtil.formatSize(size));
            libraryFile.setFileUrl(blobsPath+digest);
            libraryFile.setRelativePath("blobs/" + digest);
            libraryFileService.redactLibraryFile(libraryFile);
        }
    }

    /**
     * 写入tags、Manifest内容
     * @param inputStream 文件流
     * @param folderPath 文件存储位置
     * @param fileName manifests的名字
     * @param version 版本名字
     *
     */
    public Long writeManifestFile(InputStream inputStream,String folderPath,
                                  String fileName ,String version) throws IOException {
       //添加数据到Manifest
        Path filePath = FileUtil.copyFileData(inputStream, folderPath, fileName);

        //创建blobs文件
        String replace = folderPath.replace("/manifests", "/tags");
        Path replacePath = Paths.get(replace);
        if (!Files.exists(replacePath)) {
            Files.createDirectories(replacePath);
        }
        Path tagsPath = replacePath.resolve(version);
        Files.copy(filePath, tagsPath, StandardCopyOption.REPLACE_EXISTING);

        // 返回文件大小
        return Files.size(filePath);
    }

}
