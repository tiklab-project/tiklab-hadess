package io.tiklab.xpack.upload.service;

import io.tiklab.core.Result;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.eam.passport.user.service.UserPassportService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.user.user.service.UserService;
import io.tiklab.xpack.common.RepositoryUtil;
import io.tiklab.xpack.common.UuidGenerator;
import io.tiklab.xpack.common.XpackYamlDataMaService;
import io.tiklab.xpack.library.model.*;
import io.tiklab.xpack.library.service.*;
import io.tiklab.xpack.repository.model.*;
import io.tiklab.xpack.repository.service.RepositoryGroupService;
import io.tiklab.xpack.repository.service.RepositoryMavenService;
import io.tiklab.xpack.repository.service.RepositoryRemoteProxyService;
import io.tiklab.xpack.repository.service.RepositoryService;
import io.tiklab.xpack.upload.MavenUploadService;
import io.tiklab.xpack.common.UserCheckService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Exporter
public class MavenUploadServiceImpl implements MavenUploadService {
    private static Logger logger = LoggerFactory.getLogger(MavenUploadServiceImpl.class);
    @Autowired
    LibraryService libraryService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RepositoryMavenService mavenService;

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

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    UserCheckService userCheckService;

    @Override
    public Result<byte[]> mavenSubmit(String contextPath, InputStream inputStream, String userData) {
        logger.info("userData:"+contextPath+":"+userData);
        String userName;
        try {
            //账号校验
            userName=userCheckService.mavenUserCheck(userData);
        }catch (Exception e){
            return Result.error(401,e.getMessage());
        }

        try {
            return fileWriteData(inputStream, userName, contextPath);
        } catch (IOException e) {
            return Result.error(500,e.getMessage());
        }
    }

    @Override
    public Result<byte[]> mavenPull(String contextPath) {
        logger.info("执行拉取1:"+new Timestamp(System.currentTimeMillis()));
        //仓库名称
        String repositoryName=cutRepositoryName(contextPath);
        //文件相对路径
        String relativePath =cutRelativePath(contextPath);
        try {
            //查询组合的制品库
            Repository repository = repositoryService.findRepositoryByName(repositoryName);
            if (!ObjectUtils.isEmpty(repository)){

                // maven上传 需要对maven-metadata 进行拉取校验
                if ("local".equals(repository.getRepositoryType())){

                    int lastSecondIndex = contextPath.lastIndexOf("/", contextPath.lastIndexOf("/") - 1);

                    //制品名称
                    String libraryName = contextPath.substring(lastSecondIndex + 1,
                            contextPath.lastIndexOf("/"));
                    String upperCase = libraryName.toUpperCase();
                    //快照版本
                    if (upperCase.contains("SNAPSHOT")){
                         libraryName = contextPath.substring(contextPath.lastIndexOf("/",lastSecondIndex - 1) + 1, lastSecondIndex);
                    }

                    //文件名称
                    String fileName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
                    String url = getFolderLayer(libraryName, repository.getId());

                    String filePath=yamlDataMaService.repositoryAddress() + "/" +url+"/"+fileName;

                    File file = new File(filePath);

                    String fileData = gainFileData(file);
                    if (StringUtils.isEmpty(fileData)){
                        return Result.error(404,"no find");
                    }
                   return readFileData(file);
                }
                logger.info("进入拉取:"+new Timestamp(System.currentTimeMillis()));
                //拉取 走组合库
                if ("group".equals(repository.getRepositoryType())){
                    logger.info("执行拉取2:"+new Timestamp(System.currentTimeMillis()));
                  return  groupRepositoryPull(repository,relativePath,contextPath);
                }
            }
        }catch (Exception e){
            logger.info("拉取失败:"+e.getMessage());
            result(500,null,e.getMessage());
        }
        return result(404,null,"制品库不存在");
    }

    /**
     *  maven拉取-组合库
     * @param repository     组合制品库
     * @param relativePath
     * @return
     */
    public Result groupRepositoryPull(Repository repository,String relativePath,String storePath) throws IOException {
        logger.info("执行拉取3:"+relativePath+":"+new Timestamp(System.currentTimeMillis()));
        int lastIndexOf = relativePath.lastIndexOf("/");
        //文件名称
        String fileName = relativePath.substring(lastIndexOf+1);
        int lastButOneIndex = relativePath.lastIndexOf("/",lastIndexOf-1);
        String libraryPath = relativePath.substring(0, lastButOneIndex);
        //制品名称
        String libraryName = libraryPath.substring(libraryPath.lastIndexOf("/") + 1);

        //这是拿到请求文件的版本，区分快照版本和正式版本
        int lastIndex = relativePath.lastIndexOf("/");
        String version = relativePath.substring(relativePath.lastIndexOf("/",lastIndex - 1)+1, lastIndex);
        String upperCase = version.toUpperCase();
        String type;
        if (upperCase.contains("SNAPSHOT")){
            type="Snapshot";
        }else {
            type="Release";
        }
        Library library= libraryService.findLibraryByNameAndType(libraryName,"maven",type);
        logger.info("拉取制品名字："+libraryName);
        if (!ObjectUtils.isEmpty(library)){
            logger.info("进入:library");
            String repositoryPath = yamlDataMaService.repositoryAddress();
            String fileAbsolutePath;

            LibraryFileQuery libraryFileQuery = new LibraryFileQuery().setLibraryId(library.getId()).setFileName(fileName);
            //快照版本拉取拉取maven-metadata.xml 正式版本不会拉取， 一个版本的每个时间搓都有单独的maven-metadata.xml
            if (upperCase.contains("SNAPSHOT")){
                List<LibraryVersion> versionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(library.getId()).setVersion(version));
                if (CollectionUtils.isEmpty(versionList)){
                    return result(404,null,fileName+"文件不存在");
                }
                libraryFileQuery.setLibraryVersionId(versionList.get(0).getId()) ;
            }

            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(libraryFileQuery);
            //制品库类型
            String repositoryType = library.getRepository().getRepositoryType();

            //制品文件为空走代理拉取
            if (CollectionUtils.isEmpty(libraryFileList)){

                logger.info("进入:repositoryType");
                if (("remote").equals(repositoryType)){
                    logger.info("进入:代理拉取制品文件");
                    //走代理拉取
                    return proxyPull(repository,relativePath,storePath);
                }

                if (("local").equals(repositoryType)&&fileName.contains("maven-metadata")){
                    List<RepositoryGroup> libraryList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));

                    List<RepositoryGroup> collect = libraryList.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());

                    List<RepositoryGroup> collect1 = collect.stream().filter(b -> ("maven-snapshots").equals(b.getRepository().getName())).collect(Collectors.toList());
                    fileAbsolutePath = repositoryPath + "/" +collect1.get(0).getRepository().getId()+"/"+ relativePath;
                }else {
                    logger.info(fileName+":文件为空");
                    return result(404,null,fileName+"文件不存在");
                }
            }else {
                fileAbsolutePath = repositoryPath + "/" + libraryFileList.get(0).getFileUrl();
            }

            //私服库拉取 （读取文件信息）
            File file = new File(fileAbsolutePath);
            logger.info("私服拉取");
            return readFileData(file);

        }
        //走代理拉取
        logger.info("进入:代理拉取制品");
        return proxyPull(repository,relativePath,storePath);
    }

    /**
     *  maven拉取-代理拉取
     * @param repository     组合库
     * @param relativePath
     * @return
     */
    public Result proxyPull(Repository repository,String relativePath,String storePath) throws IOException {
        List<RepositoryGroup> libraryList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));

        // 过滤代理库信息
        List<RepositoryGroup> groupList = libraryList.stream().filter(a -> ("remote").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(groupList)){
            RepositoryGroup repositoryGroup = groupList.get(0);
            //远程代理路径
            List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findRepositoryRemoteProxyList(new RepositoryRemoteProxyQuery().setRepositoryId(repositoryGroup.getRepository().getId()));
            if (CollectionUtils.isNotEmpty(remoteProxyList)){
                //转发到远程
                Result<byte[]> result = restTemplateMethod(remoteProxyList,remoteProxyList.get(0), relativePath);
                if (result.getCode()==200){
                    String remoteFileUrl  = repositoryGroup.getRepository().getId() + "/"+relativePath;
                    //解析相对路径 获取文件名称、版本、groupId
                    Map<String, String> relativeMap = resolverRelativePath(relativePath);
                    relativeMap.put("userName","maven");
                    relativeMap.put("relativePath",relativePath);
                    relativeMap.put("contextPath",remoteFileUrl);
                    relativeMap.put("repositoryId",repositoryGroup.getRepository().getId());
                    relativeMap.put("type","pull");


                    //写入本地服务器中
                    //String substring = storePath.substring(storePath.indexOf("/"));
                    InputStream inputStream = new ByteArrayInputStream(result.getData());

                    //写入文件
                    writeFile(inputStream,remoteFileUrl,relativeMap);

                    int fileLength = result.getData().length;
                    double i =(double)fileLength / 1000;
                    long round = Math.round(i);
                    String fileSize;
                    if (round>0){
                        fileSize = round+"KB";
                    }else {
                        fileSize = fileLength + "B";
                    }

                    ExecutorService executorService = Executors.newCachedThreadPool();
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            //拉取成功创建制品信息
                            createLibrary(relativeMap,repositoryGroup.getRepository(), fileSize);
                        }
                    });
                }
                return result;
            }
        }
        return result(404,null,"制品不存在");
    }

    /**
     *  根据代理信息  转发远程库
     * @param remoteProxyList  d代理地址
     * @param relativePath  maven客户端相对路径
     * @return
     */
    public Result restTemplateMethod(List<RepositoryRemoteProxy> remoteProxyList,RepositoryRemoteProxy remoteProxy,String relativePath){
        int defaultNum=0;
        String proxyUrl = remoteProxy.getAgencyUrl();
        //设置的代理地址 以/结尾  就去掉相对路径第一个/
        if (proxyUrl.endsWith("/")&&relativePath.startsWith("/")){
            relativePath = relativePath.substring(1);
        }
        String relativeAbsoluteUrl=proxyUrl+"/"+relativePath;
        try {
            RestTemplate restTemplate = new RestTemplate();
            logger.info("开始执行拉取远程restTemplate路径:"+relativeAbsoluteUrl);
            ResponseEntity<byte[]> entity = restTemplate.getForEntity(relativeAbsoluteUrl, byte[].class);
            int codeValue = entity.getStatusCodeValue();
            logger.info("结束执行拉取远程restTemplate状态:"+codeValue);

            byte[] entityBody = entity.getBody();
            return result(codeValue,entityBody,"OK");
        }catch (Exception e){
            if (defaultNum+1<remoteProxyList.size()){
                RepositoryRemoteProxy proxy = remoteProxyList.get(defaultNum + 1);
                restTemplateMethod(remoteProxyList,proxy,relativePath);
            }else {
                return result(404,null,"null");
            }
        }
        return null;
    }


    /**
     *  maven-提交   写入文件、创建数据
     * @param contextPath     maven客户端提交路径 （制品文件夹路径+文件路径）
     * @param userName          用户
     * @param inputStream     输入数据流文件
     */
    public Result fileWriteData(InputStream inputStream, String userName, String contextPath) throws IOException {

        //仓库名称
        String repositoryName = cutRepositoryName(contextPath);
        
        //文件相对路径
        String relativePath = cutRelativePath(contextPath);

        //查询制品库是否存在
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));

        if (CollectionUtils.isNotEmpty(repositoryList)){
            Result result = new Result<>();

            String repositoryId = repositoryList.get(0).getId();

            //存储地址 用仓库id作为存储的制品
            String storePath = repositoryId + "/" + relativePath;

            //解析相对路径 获取文件名称、版本、groupId
            Map<String, String> dataMap = resolverRelativePath(relativePath);

            List<RepositoryMaven> mavenList = mavenService.findRepositoryMavenList(new RepositoryMavenQuery().setRepositoryId(repositoryId));
            String version="1";
            if (StringUtils.isNotEmpty(dataMap.get("version"))){
                version  = dataMap.get("version").toLowerCase();
            }


            //正式版本,版本不能冲突
            if (StringUtils.isNotEmpty(dataMap.get("version"))&&!dataMap.get("version").toUpperCase().endsWith("-SNAPSHOT")){
                LibraryVersion libraryVersion = libraryVersionService.findVersionByNameAndVer(dataMap.get("libraryName"), dataMap.get("version"));
                /*if (!ObjectUtils.isEmpty(libraryVersion)){
                    result.setCode(409);
                    result.setMsg("ReasonPhrase: Conflict");
                    return result;
                }*/
            }

            if (("Release").equals(mavenList.get(0).getVersion())){
                if (version.endsWith("snapshot")){
                    result.setCode(400);
                    result.setMsg("Repository version policy");
                    return result;
                }
            }
            dataMap.put("relativePath",relativePath);
            dataMap.put("userName",userName);
            dataMap.put("repositoryId",repositoryId);
            dataMap.put("type","push");

            //写入文件
            Result writeFile = writeFile(inputStream, storePath,dataMap);

            //文件大小
            String fileSize = writeFile.getData().toString();

            int indexOf = storePath.indexOf("maven-metadata");
            boolean contains = contextPath.contains("-SNAPSHOT");
            if (indexOf==-1||contains){
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
    public Result writeFile(InputStream inputStream,String contextPath,Map<String, String> dataMap) throws IOException {
       // String url = StringUtils.substringBeforeLast(contextPath, "/");
        String repositoryId = dataMap.get("repositoryId");

        String libraryName = dataMap.get("libraryName");
        String version = dataMap.get("version");

        //获取替换制品groupId 的随机数
        String url = getFolderLayer(libraryName, repositoryId);

        //本地库上传
        if (("push").equals( dataMap.get("type"))){
            //快照
            if (dataMap.get("version").endsWith("-SNAPSHOT")){
                //快照时间戳
                //String snapshotTime = findSnapshotTime(dataMap);
                url = url + "/" + dataMap.get("version");

            /*    contextPath=url +"/"+dataMap.get("fileName");
                dataMap.put("contextPath",contextPath);*/
            }
        }

        String fleName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        dataMap.put("contextPath",url+"/"+fleName);
        //存储文件的文件夹位置
        String path=yamlDataMaService.repositoryAddress()+"/"+ url;
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }

        //存储文件位置
        String filePath = path +"/"+fleName;
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
        String size = RepositoryUtil.formatSize(FileLength);
        Result result = new Result<>();
        result.setData(size);
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
     *  拉取远程后(提交)创建制品信息
     * @param  dataMap 数据整合的map
     * @param  repository 制品库
     * @param  fileSize   文件大小
     * @param
     * @return
     */
    public void createLibrary( Map<String, String> dataMap,Repository repository,String fileSize)  {

        //创建制品
        Library library = libraryService.createLibraryData(dataMap.get("libraryName"), "maven",repository);

        //制品版本创建、修改
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setLibrary(library);
        libraryVersion.setRepository(repository);
        libraryVersion.setVersion(dataMap.get("version"));
        libraryVersion.setLibraryType("maven");
        if (dataMap.get("relativePath").endsWith(".jar.sha1")){
            String filePath=yamlDataMaService.repositoryAddress()+"/"+dataMap.get("contextPath");
            libraryVersion.setHash(gainFileData(new File(filePath)));
        }
        libraryVersion.setPusher(dataMap.get("userName"));
        String libraryVersionId = libraryVersionService.libraryVersionSplice(libraryVersion);

        // 制品maven  创建、更新
        libraryMavenService.libraryMavenSplice(dataMap.get("libraryName"),dataMap.get("groupId"),library);

        //创建制品文件
        String relativePath = dataMap.get("relativePath");
        //快照版本存储maven-metadata 数据库中已经存在就不需要再保存
        if (relativePath.contains("maven-metadata")){
            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setFileUrl(dataMap.get("relativePath")));
            if (CollectionUtils.isNotEmpty(libraryFileList)){
                return;
            }
        }
        //制品文件 创建、更新
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setFileName(dataMap.get("fileName"));
        libraryFile.setFileSize(fileSize);
        libraryFile.setRepository(repository);
        libraryFile.setFileUrl(dataMap.get("contextPath"));
        if (dataMap.get("version").endsWith("-SNAPSHOT")&&!relativePath.contains("maven-metadata")){
            String timeRub = findSnapshotTime(dataMap);
            libraryFile.setSnapshotVersion(timeRub);
            //将相对路径的maven文件的快照时间戳改为 快照版本
            relativePath= dataMap.get("relativePath").replace(timeRub,"SNAPSHOT");
        }
        libraryFile.setRelativePath(relativePath);

        libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);

    }

    /**
     *  解析相对路径
     * @param relativePath     客户端请求的相对路径 （不包含制品库）
     * @return
     */
    public Map<String,String> resolverRelativePath(String relativePath){
        String version;
        String libraryName;
        String groupId=null;
        //制品文件名称
        String fileName = relativePath.substring(relativePath.lastIndexOf("/") + 1);
        String ve = relativePath.substring(relativePath.lastIndexOf("/", relativePath.lastIndexOf("/") - 1) + 1, relativePath.lastIndexOf("/"));
        String upperCase = ve.toUpperCase();
        if (fileName.contains("maven-metadata")&&!upperCase.contains("SNAPSHOT")){
            libraryName=ve;
            //当客户端请求为maven-metadata 不带版本的时候
            version="1";
       }else {
            //版本
            String versionPath = relativePath.substring(0,relativePath.lastIndexOf("/"));
             version = versionPath.substring(versionPath.lastIndexOf("/")+1);
            //制品名称
            String libraryPath=versionPath.substring(0,versionPath.lastIndexOf("/"));
            libraryName = libraryPath.substring(libraryPath.lastIndexOf("/")+1);

            String groupIdPath = libraryPath.substring(0, libraryPath.lastIndexOf("/"));
             groupId = groupIdPath.replace("/", ".");

        }
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
    public String gainFileData(File file){
        try {
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
        }catch (IOException e){
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     *  获取快照时间戳
     * @param
     * @return
     */
    public String findSnapshotTime(Map<String, String> dataMap){
        //除去快照-SNAPSHOT 的版本
        String version = dataMap.get("version").substring(0, dataMap.get("version").indexOf("-SNAPSHOT"));

        //快照时间戳加类型
        String timeName = dataMap.get("fileName").substring(dataMap.get("fileName").lastIndexOf(version) + version.length() + 1);
        String timeRub = timeName.substring(0, timeName.indexOf(".",timeName.indexOf(".")+1));

        if (timeRub.endsWith("-sources")){
            timeRub= timeRub.substring(0,timeRub.indexOf("-sources"));
        }

        return timeRub;
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

    /**
     *  截取仓库的名称
     * @param contextPath  客户端请求路径
     * @return
     */
    public String cutRepositoryName(String contextPath){
        String repositoryName=contextPath.substring(0,contextPath.indexOf("/",1));
        return repositoryName;
    }
    /**
     *  截取jar 的相对路径
     * @param contextPath  客户端请求路径
     * @return
     */
    public String cutRelativePath(String contextPath){
        //文件相对路径
        String relativePath = contextPath.substring(contextPath.indexOf("/",1)+1);
        return relativePath;
    }


    /**
     *获取存储制品路径中的随机数 （通过随机数替换制品的groupId）
     * @param  libraryName 制品名称
     * @param   repositoryId 制品库id
     * @return  仓库id/随机数
     */
    public String getFolderLayer(String libraryName,String repositoryId){
        String url;
        //制品文件位置 用随机数据替换过长的groupId
        List<Library> libraryList = libraryService.likeLibraryListNo(new LibraryQuery().setName(libraryName).setRepositoryId(repositoryId));

        //已经存在直接获取，不存在生成
        if (CollectionUtils.isNotEmpty(libraryList)){
            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryList.get(0).getId()));
            if (CollectionUtils.isNotEmpty(libraryFileList)){
                String fileUrl = libraryFileList.get(0).getFileUrl();
                int secondIndex = fileUrl.indexOf("/", fileUrl.indexOf("/", 1) + 1);

                url = fileUrl.substring(fileUrl.indexOf("/")+1,fileUrl.indexOf("/",secondIndex+1));
            }else {
                //生成随机数
                url= generateMath(repositoryId)+"/"+generateMath(repositoryId);
            }
        }else {
            //生成随机数
            url= generateMath(repositoryId)+"/"+generateMath(repositoryId);
        }
        return repositoryId+"/"+url;
    }

    /**
     *生成随机数
     * @param
     * @return
     */
    public String generateMath(String repositoryId){
        String tenantMath = UuidGenerator.gen(4);
        String lowerCase = tenantMath.toLowerCase();

        List<LibraryFile> libraryLikeFileUrl = libraryFileService.findLibraryLikeFileUrl(repositoryId + "/" + lowerCase);
        //随机生成的id 是否重复
        if (!ObjectUtils.isEmpty(libraryLikeFileUrl)){
            generateMath(repositoryId);
        }
        return lowerCase;
    }
}
