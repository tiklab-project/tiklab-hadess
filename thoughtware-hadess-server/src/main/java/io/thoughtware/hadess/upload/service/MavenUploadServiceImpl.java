package io.thoughtware.hadess.upload.service;

import io.thoughtware.core.exception.SystemException;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.hadess.library.service.*;
import io.thoughtware.hadess.repository.model.*;
import io.thoughtware.core.Result;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.eam.passport.user.service.UserPassportService;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.user.user.service.UserService;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.UuidGenerator;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.repository.service.RepositoryGroupService;
import io.thoughtware.hadess.repository.service.RepositoryMavenService;
import io.thoughtware.hadess.repository.service.RepositoryRemoteProxyService;
import io.thoughtware.hadess.repository.service.RepositoryService;
import io.thoughtware.hadess.common.UserCheckService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.sql.Timestamp;
import java.util.Base64;
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



    public static Map<String , String> generateMap = new HashMap<>();

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
        logger.info("maven拉取1-路径:"+contextPath);
        //通过访问路径获取仓库名称
        String repositoryName=cutRepositoryName(contextPath);
        //通过访问路径获取文件相对路径
        String relativePath =cutRelativePath(contextPath);

        try {
            //通过制品库名字查询制品库
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
                    logger.info("maven拉取2-进入组合库:"+new Timestamp(System.currentTimeMillis()));
                    Result result = groupRepositoryPull(repository, relativePath);
                    return  result;
                }
            }
        }catch (Exception e){
            throw new SystemException(e);
          /*  logger.info("拉取失败:"+e.getMessage());
            result(500,null,e.getMessage());*/
        }
        return result(404,null,"制品不存在");
    }

    /**
     *  maven拉取-组合库
     * @param repository     组合制品库
     * @param relativePath
     * @return
     */
    public Result groupRepositoryPull(Repository repository,String relativePath ) throws IOException {
        int lastIndexOf = relativePath.lastIndexOf("/");
        //文件名称
        String fileName = relativePath.substring(lastIndexOf+1);
        int lastButOneIndex = relativePath.lastIndexOf("/",lastIndexOf-1);
        String libraryPath = relativePath.substring(0, lastButOneIndex);
        //制品名称
        String libraryName = libraryPath.substring(libraryPath.lastIndexOf("/") + 1);

        String groupId = libraryPath.substring(0, libraryPath.lastIndexOf("/"));
        groupId = groupId.replace("/", ".");

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


        //通过制品名字和groupId 定位制品是否存在
        Library library= libraryService.findMvnLibraryByGroupId(repository,libraryName,groupId,type);
        logger.info("maven拉取3-拉取制品名字:"+libraryName+",groupId:"+groupId);
        if (!ObjectUtils.isEmpty(library)){
            logger.info("maven拉取4-制品存在进入本地服务器拉取");
            String repositoryPath = yamlDataMaService.repositoryAddress();
            String fileAbsolutePath;

            LibraryFileQuery libraryFileQuery = new LibraryFileQuery().setLibraryId(library.getId()).setFileName(fileName);
            //快照版本拉取拉取maven-metadata.xml 正式版本不会拉取， 一个版本的每个时间搓都有单独的maven-metadata.xml
            if (upperCase.contains("SNAPSHOT")){
                List<LibraryVersion> versionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(library.getId()).setVersion(version));
                if (CollectionUtils.isEmpty(versionList)){
                    logger.info("maven拉取错误-快照版本的制品版本为空");
                    return result(404,null,fileName+"文件不存在");
                }
                libraryFileQuery.setLibraryVersionId(versionList.get(0).getId()) ;
            }

            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(libraryFileQuery);
            //制品库类型
            String repositoryType = library.getRepository().getRepositoryType();


            //正式版本和快照版本外层的 maven-metadata 文件数据不会存在数据库中  (快照版本制品下面有一个maven-metadata文件、每个时间戳下面也有一个maven-metadata文件)
            if (CollectionUtils.isEmpty(libraryFileList)){

                /*
                * 仓库类型为代理库 并且制品文件表 还不存在
                * 2. 拉取失败下次可以重新拉取
                * */
                if (("remote").equals(repositoryType)){
                    logger.info("maven拉取5-制品存在制品文件不存在进入代理拉取通道");
                    //走代理拉取
                    return proxyPull(repository,relativePath);
                }

                //仓库类型为正式版本 并且是maven-metadata文件
                if (("local").equals(repositoryType)&&fileName.contains("maven-metadata")){
                    List<RepositoryGroup> libraryList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));

                    List<RepositoryGroup> collect = libraryList.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());

                    List<RepositoryGroup> collect1 = collect.stream().filter(b -> ("maven-snapshots").equals(b.getRepository().getName())).collect(Collectors.toList());
                    fileAbsolutePath = repositoryPath + "/" +collect1.get(0).getRepository().getId()+"/"+ relativePath;
                }else {
                    logger.info("maven拉取错误该文件为空："+fileName);
                    return result(404,null,fileName+"文件不存在");
                }
            }else {
                fileAbsolutePath = repositoryPath + "/" + libraryFileList.get(0).getFileUrl();
            }
            /*
             * 制品和制品文件都存在、制品库类型为代理库时
             *  数据备份数据库数据全量同步、制品文件只备份本地制品的,  就会出现代理库有数据没有文件情况，需要重新拉取
             * */
            if (("remote").equals(repositoryType)){
                File file = new File(fileAbsolutePath);
                //制品和制品文件都存在 制品数据不存在 走代理
                if (!file.exists()){
                    logger.info("maven拉取5-制品存在制品数据不存在进入代理拉取通道");
                    return proxyPull(repository,relativePath);
                }
            }
            //私服库拉取 （读取文件信息）
            File file = new File(fileAbsolutePath);
            logger.info("maven拉取5-进入本地服务器拉取");
            return readFileData(file);
        }
        //制品不存在 走代理拉取
        logger.info("maven拉取4-制品不存在进入代理拉取通道");
        return proxyPull(repository,relativePath);
    }

    /**
     *  maven拉取-代理拉取
     * @param repository     组合库
     * @param relativePath 客户端请求路径
     * @return
     */
    public Result proxyPull(Repository repository,String relativePath) throws IOException {
        List<RepositoryGroup> libraryList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));

        // 过滤代理库信息
        List<RepositoryGroup> groupList = libraryList.stream().filter(a -> ("remote").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(groupList)){
            logger.info("maven拉取-进入远程库");
            //这里展示用关联的一个远程库
            RepositoryGroup repositoryGroup = groupList.get(0);

            //远程库
            Repository remoteRpy = repositoryGroup.getRepository();

            //解析相对路径 获取文件名称、版本、groupId
            Map<String, String> relativeMap = resolverRelativePath(relativePath,remoteRpy.getId());
            relativeMap.put("userName","remote");
            relativeMap.put("type","pull");

            //远程代理路径
            List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findRepositoryRemoteProxyList(new RepositoryRemoteProxyQuery().setRepositoryId(repositoryGroup.getRepository().getId()));
            if (CollectionUtils.isNotEmpty(remoteProxyList)){
                logger.info("maven拉取-进入远程库代理地址");
                //转发到远程
                Result result = restTemplateMethod(remoteProxyList, 0, relativeMap, remoteRpy);
                return result;
            }
        }
        return result(404,null,"制品不存在");
    }

    /**
     *  根据代理信息  转发远程库
     * @param remoteProxyList  d代理地址
     * @param relativeMap relativeMap
     * @param  remoteRpy 代理库
     * @return
     */
    public Result restTemplateMethod(List<RepositoryRemoteProxy> remoteProxyList, Integer index,
                                     Map<String, String> relativeMap,Repository remoteRpy) {
        String relativePath = relativeMap.get("relativePath");

        String proxyUrl = remoteProxyList.get(index).getRemoteProxy().getAgencyUrl();
        //设置的代理地址 以/结尾  就去掉相对路径第一个/
        if (proxyUrl.endsWith("/")&&relativePath.startsWith("/")){
            relativePath = relativePath.substring(1);
        }
        String relativeAbsoluteUrl=proxyUrl+"/"+relativePath;
        try {
            logger.info("maven拉取6-开始远程拉取路径："+relativeAbsoluteUrl);
            ResponseEntity<byte[]> entity = getRestTemplate(remoteProxyList.get(index).getRemoteProxy(),relativeAbsoluteUrl);

          //  logger.info("maven拉取6-开始远程拉取路径："+relativeAbsoluteUrl);

            int codeValue = entity.getStatusCodeValue();
            logger.info("maven拉取7-结束执行拉取远程拉取状态："+codeValue);
            byte[] entityBody = entity.getBody();

            //获取数据成功后 写入数据、添加数据库记录
            if (codeValue==200){
                //创建记录
                pullCreateLibrary(relativeMap,remoteRpy,entityBody);
            }

            return result(codeValue,entityBody,"OK");
        }catch (Exception e){
            if (index+1<remoteProxyList.size()){
                RepositoryRemoteProxy proxy = remoteProxyList.get(index + 1);
                logger.info("maven拉取-远程路径："+proxyUrl+"拉取错误执行其他远程路径："+proxy.getRemoteProxy().getAgencyUrl());
                restTemplateMethod(remoteProxyList,index + 1,relativeMap,remoteRpy);
            }else {
                logger.info("maven拉取-远程路径报错："+e.getMessage());
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

            //解析相对路径 获取文件名称、版本、groupId
            Map<String, String> dataMap = resolverRelativePath(relativePath,repositoryId);
            dataMap.put("userName",userName);
            dataMap.put("type","push");


            List<RepositoryMaven> mavenList = mavenService.findRepositoryMavenList(new RepositoryMavenQuery().setRepositoryId(repositoryId));
            String version="1";
            if (StringUtils.isNotEmpty(dataMap.get("version"))){
                version  = dataMap.get("version").toLowerCase();
            }


            //正式版本,版本不能冲突
            if (StringUtils.isNotEmpty(dataMap.get("version"))&&!dataMap.get("version").toUpperCase().endsWith("-SNAPSHOT")){
                List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setRelativePath(relativePath).setRepositoryId(repositoryId));
               //存在相同版本了 且不允许覆盖
                if (CollectionUtils.isNotEmpty(libraryFileList)&&mavenList.get(0).getCoverState()==0){
                   result.setCode(409);
                   result.setMsg("ReasonPhrase: Conflict");
                   return result;
               }
            }

            if (("Release").equals(mavenList.get(0).getVersion())){
                if (version.endsWith("snapshot")){
                    result.setCode(400);
                    result.setMsg("Repository version policy");
                    return result;
                }
            }

            //写入文件
            Result writeFile = writeFile(inputStream,dataMap);
            //文件大小
            String fileSize = writeFile.getData().toString();

            int indexOf = relativePath.indexOf("maven-metadata");
            boolean contains = contextPath.contains("-SNAPSHOT");
            if (indexOf==-1||contains){
                logger.info("提交制品成功，进入创建数据表数据:"+relativePath);
                //写入提交数据记录到数据库
                createLibrary(dataMap,repositoryList.get(0),Long.valueOf(fileSize));
            }

            result.setCode(201);
            result.setMsg("Create");
            return result;
        }
        return Result.error(404,null);
    }

    /**
     *  maven-提交、远程拉取 文件file写入
     * @param inputStream     输入流
     * @return   Result  写入数据的大小
     */
    public Result writeFile(InputStream inputStream,Map<String, String> dataMap) throws IOException {

        String genPath = dataMap.get("genPath");
        String fleName = dataMap.get("fileName");

        //本地库上传
        if (("push").equals( dataMap.get("type"))){
            //快照
            if (dataMap.get("version").endsWith("-SNAPSHOT")){
                //快照时间戳
                genPath = genPath + "/" + dataMap.get("version");
            }
        }

        //存储文件的文件夹位置
        String path=yamlDataMaService.repositoryAddress()+"/"+ genPath;
        File folder = new File(path);
        if (!folder.exists()) {
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

        Result result = new Result<>();
        result.setData(FileLength);
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
     * @param  repository 远程库
     * @param  entityBody   文件
     */
    public void pullCreateLibrary( Map<String, String> dataMap,Repository repository,byte[] entityBody)  {
        //成功后重启线程写入数据
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                //写入本地服务器中
                InputStream inputStream = new ByteArrayInputStream(entityBody);
                //写入文件
                try {
                    writeFile(inputStream,dataMap);
                    int fileLength = entityBody.length;

                    createLibrary(dataMap,repository,Long.valueOf(fileLength));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }});
    }



    /**
     *  推送创建制品信息
     * @param  dataMap 数据整合的map
     * @param  repository 制品库
     * @param  fileSize   文件大小
     */
    public void createLibrary( Map<String, String> dataMap,Repository repository,Long fileSize)  {

        //创建制品
        Library library = libraryService.createMvnLibrary(repository,dataMap.get("libraryName"),dataMap.get("groupId"));

        //制品版本创建、修改
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setLibrary(library);
        libraryVersion.setRepository(repository);
        libraryVersion.setVersion(dataMap.get("version"));
        libraryVersion.setLibraryType("maven");
        libraryVersion.setSize(fileSize);
        if (dataMap.get("relativePath").endsWith(".jar.sha1")){
            String filePath=yamlDataMaService.repositoryAddress()+"/"+dataMap.get("contextPath");
            libraryVersion.setHash(gainFileData(new File(filePath)));
        }
        libraryVersion.setPusher(dataMap.get("userName"));
        String libraryVersionId = libraryVersionService.createLibraryVersionSplice(libraryVersion,dataMap.get("fileName"));
        libraryVersion.setId(libraryVersionId);


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

        String size = RepositoryUtil.formatSize(fileSize);
        libraryFile.setFileSize(size);
        libraryFile.setSize(fileSize);
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

        logger.info("文件"+dataMap.get("fileName")+"大小："+fileSize);
        //修改制品最新版本的大小
        libraryService.updateMvnLibrarySize(libraryFile,libraryVersion,fileSize);
    }

    /**
     *  解析相对路径
     * @param relativePath     客户端请求的相对路径 （不包含制品库）
     * @param repositoryId  仓库id
     * @return
     */
    public Map<String,String> resolverRelativePath(String relativePath,String repositoryId){
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

        //生成的文件存储路径
        String genPath = getFolderLayer(libraryName,repositoryId);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("libraryName",libraryName);
        resultMap.put("version",version);
        resultMap.put("groupId",groupId);
        resultMap.put("fileName",fileName);
        resultMap.put("repositoryId",repositoryId);
        resultMap.put("relativePath",relativePath);

        resultMap.put("genPath",genPath);

        resultMap.put("contextPath",genPath+"/"+fileName);
        //快照版
        if (version.endsWith("-SNAPSHOT")){
            resultMap.put("contextPath",genPath+"/"+version+"/"+fileName);
        }
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
        String generaUrl = generateMap.get(libraryName);


        String url=null;
        //制品文件位置 用随机数据替换过长的groupId
        List<Library> libraryList = libraryService.likeLibraryListNo(new LibraryQuery().setName(libraryName).setRepositoryId(repositoryId));

        List<LibraryFile> libraryFileList=null;
        //已经存在直接获取，不存在生成
        if (CollectionUtils.isNotEmpty(libraryList)){
            libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryList.get(0).getId()));
            if (CollectionUtils.isNotEmpty(libraryFileList)){
                String fileUrl = libraryFileList.get(0).getFileUrl();
                int secondIndex = fileUrl.indexOf("/", fileUrl.indexOf("/", 1) + 1);

                url = fileUrl.substring(fileUrl.indexOf("/")+1,fileUrl.indexOf("/",secondIndex+1));
               //如果已经存如数据库中了 清除缓存中数据
                generateMap.remove(libraryName);
            }
        }


        //制品为空或者制品文件为空
        if (CollectionUtils.isEmpty(libraryList)||ObjectUtils.isEmpty(libraryFileList)){

           url = StringUtils.isNotEmpty(generaUrl) ? generaUrl : generateMath(repositoryId) + "/" + generateMath(repositoryId);

            generateMap.put(libraryName,url);
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

    /**
     *RestTemplate
     * @param relativeAbsoluteUrl 连接地址
     */
    public ResponseEntity<byte[]> getRestTemplate(RemoteProxy remoteProxy,String relativeAbsoluteUrl){
        // 创建请求头对象
        HttpHeaders headers = new HttpHeaders();
        //有账号密码信息来拉取私有镜像
        if (StringUtils.isNotEmpty(remoteProxy.getAccount())&&StringUtils.isNotEmpty(remoteProxy.getPassword())){
            String s = remoteProxy.getAccount()+ ":" +remoteProxy.getPassword();
            String baseString = Base64.getEncoder().encodeToString(s.getBytes());
            headers.set("Authorization","Basic "+baseString);
        }
        // 创建 HttpEntity 包含请求体和请求头
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        //设置连接超时时间
        ClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        ((SimpleClientHttpRequestFactory) factory).setConnectTimeout(10000);

        RestTemplate restTemplate = new RestTemplate(factory);
        ResponseEntity<byte[]> entity = restTemplate.exchange(relativeAbsoluteUrl, HttpMethod.GET, requestEntity, byte[].class);
        // ResponseEntity<byte[]> entity = restTemplate.getForEntity(relativeAbsoluteUrl, byte[].class);
        return entity;
    }
}
