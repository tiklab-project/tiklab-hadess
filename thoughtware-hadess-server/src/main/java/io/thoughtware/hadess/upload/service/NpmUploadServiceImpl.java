package io.thoughtware.hadess.upload.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.thoughtware.core.Result;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.hadess.repository.model.*;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.eam.passport.user.service.UserPassportService;
import io.thoughtware.hadess.upload.model.LibraryUploadData;
import io.thoughtware.hadess.upload.model.LibraryUploadResult;
import io.thoughtware.hadess.upload.model.NpmPubData;
import io.thoughtware.user.user.service.UserService;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.service.LibraryFileService;
import io.thoughtware.hadess.library.service.LibraryService;
import io.thoughtware.hadess.library.service.LibraryVersionService;
import io.thoughtware.hadess.repository.service.RepositoryGroupService;
import io.thoughtware.hadess.repository.service.RepositoryRemoteProxyService;
import io.thoughtware.hadess.repository.service.RepositoryService;
import io.thoughtware.hadess.common.UserCheckService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;


/**
 * NpmUploadServiceImpl-npm上传下载
 */
@Service
public class NpmUploadServiceImpl implements NpmUploadService {

    private static Logger logger = LoggerFactory.getLogger(NpmUploadServiceImpl.class);
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    RepositoryGroupService repositoryGroupService;

    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Autowired
    UserService userService;

    @Autowired
    UserPassportService userPassportService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    UserCheckService userCheckService;


    @Override
    public LibraryUploadResult uploadEntrance(LibraryUploadData uploadData) {
        Map<String, String> map = new HashMap<>();

        String authorization = uploadData.getAuthorization();
        String relativePath = uploadData.getRelativePath();
        try {
            //类型为yarn类型
            if (uploadData.getAgentType().contains("yarn")) {
                //提交的时候校验用户信息请求
                if (relativePath.contains("/-/user/org.couchdb.user")) {
                    return this.verifyUserInfo(authorization);
                }else {
                    //请求 push
                    if (("PUT").equals(uploadData.getMethod())){
                        //用户信息
                        String userName = getUserName(authorization, "Bearer");
                        LibraryUploadResult uploadResult = npmSubmitData(uploadData, userName);
                        if (uploadResult.getStatus()==200){
                            map.put("success","true");
                            String jsonString = JSON.toJSONString(map);
                            return resultString(200,jsonString,1 );
                        }
                        return uploadResult;
                    }
                    if ("DELETE".equals(uploadData.getMethod())){
                        map.put("ok","true");
                        String jsonString = JSON.toJSONString(map);
                        return resultString(200,jsonString,1 );
                    }
                }
            }else {
                if(!uploadData.getAgentType().contains("got")){
                    //登陆
                    if (uploadData.getReferer().contains("adduser")){
                        return  this.npmLogin(uploadData.getUserReader());
                    }
                    //提交制品
                    if (uploadData.getReferer().contains("publish")){
                        String userName = getUserName(authorization, "Basic");
                        return npmSubmitData(uploadData, userName);
                    }
                    //拉取制品
                    if (uploadData.getReferer().contains("install")){
                        //拉取第一次交互 拉取json
                        if (!relativePath.endsWith(".tgz")){
                            return pullJson(uploadData);

                        }else{
                            //拉取第二次交互 拉取tag包文件
                            return pullTgzData(uploadData);
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.info("拉取报错："+e.getMessage());
            throw new SystemException(e);
        }
        return resultString(500,"拉取错误",0);
    }

    /**
     * npm 类型登陆
     * @param reader  提交的用户信息
     */
    public LibraryUploadResult npmLogin(BufferedReader reader) {
        StringBuilder result = new StringBuilder();
        String lineTxt = null;
        try {
            while ((lineTxt = reader.readLine()) != null) {
                result.append(lineTxt).append("\n");
            }
        }catch (Exception e){
            throw new SystemException(e);
        }

        JSONObject allData =(JSONObject) JSONObject.parse(result.toString());
        String userName = allData.get("name").toString();
        String password = allData.get("password").toString();
        try{
            userCheckService.npmUserCheck(userName, password);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result",true);
            String jsonString = JSON.toJSONString(resultMap);
            return resultString(201,jsonString,1);
        }catch (Exception e){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result",false);
            resultMap.put("error","Bad username or password");
            String jsonString = JSON.toJSONString(resultMap);
            return resultString(401,jsonString,1);
        }
    }


    /**
     *  npm上传-文件的处理（存制品到数据库）
     * @param uploadData: uploadData
     * @param name:   用户名
     * @return
     */
    public LibraryUploadResult npmSubmitData(LibraryUploadData uploadData,String name) throws IOException {
        String relativePath = uploadData.getRelativePath();

        //读取上传的数据
        String readData = readData(uploadData.getInputStream());
        //替换上传内容的Tarball
        String s = replaceTarball(readData, uploadData.getAbsolutePath());
        JSONObject allData =(JSONObject) JSONObject.parse(s);


        //tga的内容
        JSONObject tgaData =(JSONObject) allData.get("_attachments");
        Set<String> tgaKey = tgaData.keySet();
        String tgzName = tgaKey.stream().findFirst().orElse("null");

        //version的内容
        JSONObject versionsObject =(JSONObject) allData.get("versions");
        Set<String> versionsKey = versionsObject.keySet();
        String versionsKeyName = versionsKey.stream().findFirst().orElse("null");
        JSONObject versionObject =(JSONObject)versionsObject.get(versionsKeyName);
        JSONObject distObject =(JSONObject)versionObject.get("dist");
        String dist = distObject.get("shasum").toString();

        //仓库名称
        String repositoryName=StringUtils.substringBefore(relativePath,"/");
        //制品名称
        String libraryName =StringUtils.substringAfterLast(relativePath,"/");
        String jsonData = allData.toJSONString().replaceAll("repository/" + libraryName, "repository/" + repositoryName + "/" + libraryName);

        //tgz内容
        JSONObject tgaSecondData =(JSONObject) tgaData.get(tgzName);
        Object data = tgaSecondData.get("data");
        String jsonString = JSONObject.toJSONString(data);
        String substring = jsonString.substring(1,jsonString.length()-1);

        //版本
        JSONObject versionData =(JSONObject) allData.get("versions");
        Set<String> versionKey = versionData.keySet();
        String version = versionKey.stream().findFirst().orElse("null");

        //查询制品库是否存在
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
        if (CollectionUtils.isNotEmpty(repositoryList)){
            //将制品库的名字替换成制品库的id
            String replace = relativePath.replace(repositoryName, repositoryList.get(0).getId());
            String path=yamlDataMaService.repositoryAddress()+"/"+replace;
            //tag 文件存储的绝对路径
            String filePath=path+"/"+tgzName;

            //base64解密
            byte[] decodedBytes = Base64.getDecoder().decode(substring);
            //创建tgz文件
            writeFile(path,filePath,decodedBytes);

            int length = decodedBytes.length;

            String size = RepositoryUtil.formatSize(length);

            //创建制品
            Library library = libraryService.createLibraryData(libraryName, "npm",repositoryList.get(0));

            //创建制品版本
            LibraryVersion libraryVersion = new LibraryVersion();
            libraryVersion.setSize(Long.valueOf(length));
            libraryVersion.setContentJson(jsonData);
            libraryVersion.setLibrary(library);
            libraryVersion.setRepository(repositoryList.get(0));
            libraryVersion.setHash(dist);
            libraryVersion.setVersion(version);
            libraryVersion.setLibraryType("npm");
            //libraryVersion.setPullUser(name);
            libraryVersion.setPusher(name);
            String libraryVersionId =libraryVersionService.createLibraryVersionSplice(libraryVersion,tgzName);


            //创建制品文件
            LibraryFile libraryFile = new LibraryFile();
            libraryFile.setLibrary(library);
            libraryFile.setFileName(tgzName);
            libraryFile.setFileSize(size);
            libraryFile.setFileUrl(replace+"/"+tgzName);
            libraryFile.setRepository(repositoryList.get(0));
            libraryFile.setRelativePath(tgzName);
            libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);
            return resultString(200,null,2);
        }else {
            return  resultString(404,"制品库不存在",0);
        }
    }


    /**
     * 第一次交互拉取 json
     * 如果客户端的.npm 文件夹下面npm文件已经存在 就只执行这次交互（拉取信息）
     * 第一次请求是不带版本的、
     * @param uploadData 拉取制品数据
     */
    public LibraryUploadResult pullJson(LibraryUploadData uploadData) {
        Repository repository = uploadData.getRepository();
        //相对路径
        String relativePath = uploadData.getRelativePath();
        logger.info("npm拉取(json)-请求地址："+relativePath);


        NpmPubData npmPubData = new NpmPubData();
        //npmPubData.setRepository(repository);
        npmPubData.setRequestFullURL(uploadData.getAbsolutePath());
        npmPubData.setStoragePath(relativePath);
        if (repository.getRepositoryType().equals("group")){
            List<RepositoryGroup> repositoryGroupList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));
            //组合没有关联本地库和远程库
            if (CollectionUtils.isEmpty(repositoryGroupList)){
                logger.info("npm拉取(json)报错-没有关联的制品库");
                return  resultString(404,"组合库"+uploadData.getRepository().getName()+"没有关联制品库",0) ;
            }

            //过滤远程库
            List<RepositoryGroup> remote = repositoryGroupList.stream().filter(a -> ("remote").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(remote)){
                List<Repository> repositoryList = remote.stream().map(RepositoryGroup::getRepository).collect(Collectors.toList());
                //替换npmPubData 对象的里面的制品库为代理库
                npmPubData.setRepositoryList(repositoryList);
            }

            //组合库关联的制品库
            List<String> rpyIdList = repositoryGroupList.stream().map(a -> a.getRepository().getId()).collect(Collectors.toList());
            LibraryVersion libraryVersion = findLibraryVersion(rpyIdList, relativePath, null);
            //本地制品库制品文件不存在 走代理
            if (ObjectUtils.isEmpty(libraryVersion)){
                //制品不存在且没有关联远程库
                if (CollectionUtils.isEmpty(remote)){
                    logger.info("npm拉取(json)-本地制品库制品文件不存在且没有关联远程库");
                    return  resultString(500,"没有关联远程库",0) ;
                }
                logger.info("npm拉取(json)-本地制品库制品文件不存在进入代理通道");
                return pullProxyJson(npmPubData);
            }
            logger.info("npm拉取(json)-本地服务器拉取");
            return  resultString(200,libraryVersion.getContentJson(),1);
        }
        logger.info("npm拉取(json)报错-不是配置的组合库");
        return  resultString(404,"请配置组合库拉取制品库",0);
    }


    /**
     * 第一次交互拉取转发远程拉取json
     * @param npmPubData 拉取制品数据
     */
    public LibraryUploadResult  pullProxyJson(NpmPubData npmPubData) {
        String contextPath = npmPubData.getStoragePath();

        //通过仓库ids查询代理地址
        List<String> rpyIds = npmPubData.getRepositoryList().stream().map(Repository::getId).collect(Collectors.toList());
        String[] repositoryIds = rpyIds.toArray(new String[rpyIds.size()]);
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRpyIds(repositoryIds);
        if (CollectionUtils.isNotEmpty(remoteProxyList)) {
            //代理地址
            String after = contextPath.substring(contextPath.indexOf("/")+1);
            for (RepositoryRemoteProxy remoteProxy:remoteProxyList){
                String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
                try {
                    //转发第三方 路径
                    String decode = URLDecoder.decode(after, "UTF-8");
                    String sendPath = agencyUrl +"/"+decode;
                    String entityBody = RepositoryUtil.httpGet(sendPath);
                    //替换Tarball （修改第二次请求路径）
                    String json = replaceTarball(entityBody,remoteProxy.getRepository().getName(), npmPubData.getRequestFullURL());
                    return resultString(200,json,1);
                }catch (Exception e){
                    logger.info("npm拉取(json)转发远程拉取报错:"+e.getMessage());
                    return resultString(500,"转发远程拉取报错",0);
                }
            }
        }
        return resultString(500,"拉取错误",0);
    }



    /**
     * 第二次交互拉取tag
     * @param uploadData 拉取制品数据
     */
    public  LibraryUploadResult pullTgzData(LibraryUploadData uploadData) {
        String relativePath = uploadData.getRelativePath();
        //客户端请求绝对路径
        String absolutePath = uploadData.getAbsolutePath();
        Repository repository = uploadData.getRepository();

        //添加npmPubData 类
        NpmPubData npmPubData = new NpmPubData();
        logger.info("npm拉取(tgz)-请求地址："+relativePath);
        String url = StringUtils.substringBeforeLast(relativePath, "/-/");
        String libraryName = url.substring(url.indexOf("/") + 1);
        npmPubData.setLibraryName(libraryName);

        //如果请求路径中有特殊字符
        if (libraryName.contains("%2f")){
            libraryName= StringUtils.substringAfter(libraryName,"%2f");
        }
        int startIndex = relativePath.lastIndexOf(libraryName) + libraryName.length()+1;
        String version = relativePath.substring(startIndex, relativePath.lastIndexOf(".tgz"));


        List<Repository> repositoryList = new ArrayList<>();
        repositoryList.add(repository);
        npmPubData.setRepositoryList(repositoryList);
        npmPubData.setRepository(repository);
        npmPubData.setRequestFullURL(absolutePath);
        npmPubData.setStoragePath(relativePath);

        npmPubData.setVersion(version);

        List<String> rpyIdList=new ArrayList<>();
        rpyIdList.add(repository.getId());

        LibraryVersion libraryVersion = findLibraryVersion(rpyIdList, relativePath, version);
        try {
            //本地制品库制品文件不存在 走代理
            if (ObjectUtils.isEmpty(libraryVersion)){
                logger.info("npm拉取(tgz)-版本不存在进入代理通道");
                return pullProxyTag(npmPubData);
            }

            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(libraryVersion.getId()));
            //文件为空走第三方代理
            if (CollectionUtils.isEmpty(libraryFileList)){
                logger.info("npm拉取(tgz)-制品文件不存在进入代理通道");
                return pullProxyTag(npmPubData);
            }

            String fileUrl = libraryFileList.get(0).getFileUrl();
            String filePath = yamlDataMaService.repositoryAddress() + "/" + fileUrl;
            File file = new File(filePath);
            //hadess服务内文件不存在也走缓存
            if(!file.exists()){
                logger.info("npm拉取(tgz)-存储的文件file不存在进入代理通道");
                return pullProxyTag(npmPubData);
            }

            //走本地制品库拉取
            logger.info("npm拉取(tgz)-存储的文件存在进入本地拉取:"+libraryName);
            byte[] bytes = RepositoryUtil.readFileData(file);
            return  resultByte(200,bytes);
        }catch (Exception e){
            logger.info("npm拉取(tgz)-存储的文件file进入本地拉取报错:"+e.getMessage());
            return  resultString(500,"npm拉取(tgz)-存储的文件file进入本地拉取报错",0);
        }
    }


    /**
     * 第二次交互拉取转发远程拉取tag
     * @param npmPubData 拉取制品数据
     */
    public LibraryUploadResult  pullProxyTag(NpmPubData npmPubData) throws Exception {
        String contextPath = npmPubData.getStoragePath();

        //通过仓库ids查询代理地址
        List<String> rpyIds = npmPubData.getRepositoryList().stream().map(Repository::getId).collect(Collectors.toList());
        String[] repositoryIds = rpyIds.toArray(new String[rpyIds.size()]);
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRpyIds(repositoryIds);
        if (CollectionUtils.isNotEmpty(remoteProxyList)){
            //代理地址list
            List<String> proxyPathList = remoteProxyList.stream().map(a -> a.getRemoteProxy().getAgencyUrl()).collect(Collectors.toList());

            String fileTgzName = StringUtils.substringAfter(contextPath, "/");
            RestTemplate restTemplate = new RestTemplate();
            for (String proxyPath:proxyPathList){

                //转发第三方 路径
                String decode = URLDecoder.decode(fileTgzName, "UTF-8");
                String sendPath = proxyPath +"/"+decode;
                ResponseEntity<byte[]> entity = restTemplate.getForEntity(sendPath, byte[].class);
                int codeValue = entity.getStatusCodeValue();

                //拉取成功创建写入hadess服务
                if (codeValue==200){
                    byte[] entityBody = entity.getBody();
                    npmPubData.setTgzData(entityBody);

                    //拉取成功创建 并写入信息
                    createAndWritePullLibrary(npmPubData,fileTgzName);

                    return resultByte(codeValue,entityBody);
                }
            }
        }
        logger.info("制品库没有配置代理地址");
        return resultString(500,"制品库没有配置代理地址",0);
    }




    /**
     * 拉取json 内容截取
     *  替换json 里面的Tarball （Tarball的value 是发起第二次请求的路径）
     *  拉取第三方默认json Tarball的value是第三方的请求路径
     * @param  text 制品json
     * @param remoteName 代理地址名字
     * @param requestFullURL 客户端请求全路径
     */
    public String replaceTarball(String text,String remoteName,String requestFullURL){
        // 解析JSON字符串为JSONObject
        JSONObject jsonObject = JSON.parseObject(text);

        // 获取versions对象
        JSONObject versions = jsonObject.getJSONObject("versions");

        //组合仓库名字
        String substringAfter = StringUtils.substringAfter(requestFullURL,"repository/");
        String oldRpyName=StringUtils.substringBefore(substringAfter,"/");

        //将旧仓库名字修改为远程的仓库名字
        requestFullURL= requestFullURL.replace(oldRpyName,remoteName);

        // 遍历每个版本的dist对象
        for (String version : versions.keySet()) {
            JSONObject dist = versions.getJSONObject(version).getJSONObject("dist");
            String substring = dist.get("tarball").toString().substring(dist.get("tarball").toString().indexOf("/-/"));

            String tarball = requestFullURL + substring;
            // 替换tarball字段的值
            dist.put("tarball", tarball);
        }
        // 将Java对象转换为JSON字符串
        String updatedJsonString = jsonObject.toJSONString();

        return updatedJsonString;
    }

    /**
     *  推送内容json截取
     *  替换推送制品内容json 里面的Tarball （Tarball的value 拉取时发起的请求）
     *  推送的制品内容json 里面的Tarball  (yarn push 默认:http://registry.yarnpkg.com/..../-/..tgz   npm push 默认值不带制品库)
     * @param  text 制品json
     * @param requestFullURL 客户端请求全路径
     */
    public String replaceTarball(String text,String requestFullURL){
        // 解析JSON字符串为JSONObject
        JSONObject jsonObject = JSON.parseObject(text);

        // 获取versions对象
        JSONObject versions = jsonObject.getJSONObject("versions");

        // 遍历每个版本的dist对象
        for (String version : versions.keySet()) {
            JSONObject dist = versions.getJSONObject(version).getJSONObject("dist");
            String substring = dist.get("tarball").toString().substring(dist.get("tarball").toString().indexOf("/-/"));

            String tarball = requestFullURL + substring;
            // 替换tarball字段的值
            dist.put("tarball", tarball);
        }
        // 将Java对象转换为JSON字符串
        String updatedJsonString = jsonObject.toJSONString();

        return updatedJsonString;
    }


    /**
     *  读取输入流中的数据
     * @param  inputStream 数据流
     * @return  data 读取的数据
     */
    public  String readData(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
        BufferedInputStream in = new BufferedInputStream(inputStream);
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        return bos.toString();
    }

    /**
     *  校验用户信息
     * @param  authorization 用户认证信息
     */
    public LibraryUploadResult verifyUserInfo(String authorization ) {
        try {
            //用户信息
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            //用户信息
            String userData = new String(decode, "UTF-8");

            String[]  userObject=userData.split(":");
            String userName = userObject[0];
            String password = userObject[1];

            //账号校验
            userCheckService.npmUserCheck(userName,password);
            Map<String, String> map = new HashMap<>();
            map.put("ok","true");
            map.put("token",basic);
            map.put("id","org.couchdb.user:"+userName);
            map.put("rev","_we_dont_use_revs_any_more");
            String jsonString = JSON.toJSONString(map);
            return resultString(201,jsonString,1);
        }catch (Exception e){
            return resultString(401,"authorization",2);
        }
    }

    /**
     *  通过认证信息获取用户名
     * @param  authorization 用户认证信息
     * @param type
     */

    public String getUserName(String authorization,String type) throws UnsupportedEncodingException {
        //用户信息
        String basic = authorization.replace(type, "").trim();
        byte[] decode = Base64.getDecoder().decode(basic);
        //用户信息
        String userData = new String(decode, "UTF-8");

        String[]  userObject=userData.split(":");
        String userName = userObject[0];

        return userName;
    }
    /**
     *  返回结果Data数据封装
     * @param code  返回编码
     * @param  details  数据
     */
    public LibraryUploadResult resultByte(int code, byte[] details){
        LibraryUploadResult uploadResult = new LibraryUploadResult();
        uploadResult.setStatus(code);
        uploadResult.setDetails(details);
        uploadResult.setResultType("byte");
        return uploadResult;
    }

    /**
     *  返回结果Data数据封装
     * @param code  返回编码
     * @param  data  数据
     * @param resultType 返回参数类型 0:text/plain、1:application/json
     */
    public LibraryUploadResult resultString(int code, String data,int resultType){
        LibraryUploadResult uploadResult = new LibraryUploadResult();
        uploadResult.setStatus(code);
        uploadResult.setData(data);
        uploadResult.setResultType("string");
        if (resultType==0){
            uploadResult.setContentType("text/plain");
        }
        if (resultType==1){
            uploadResult.setContentType("application/json ");
        }
        return uploadResult;
    }









    /**
     * 第二次请求 根据代理地址执行代理拉取tgz
     * @param  npmPubData       npmPubData
     * @param remoteProxyList   remoteProxyList
     */
    public Result execAgencyTgz(NpmPubData npmPubData,List<String> remoteProxyList){
        Map resultMap = new HashMap<>();

        String contextPath = npmPubData.getStoragePath();
        String fileTgzName = StringUtils.substringAfter(contextPath, "/");
        RestTemplate restTemplate = new RestTemplate();

        try {
            for (String remoteProxy:remoteProxyList){
                //转发第三方 路径
                String decode = URLDecoder.decode(fileTgzName, "UTF-8");
                String sendPath = remoteProxy +"/"+decode;
                ResponseEntity<byte[]> entity = restTemplate.getForEntity(sendPath, byte[].class);
                int codeValue = entity.getStatusCodeValue();

                //拉取成功创建写入hadess服务
                if (codeValue==200){
                    byte[] entityBody = entity.getBody();
                    resultMap.put("code",codeValue);
                    resultMap.put("data",entityBody);

                    npmPubData.setTgzData(entityBody);

                    //拉取成功创建 并写入信息
                    createAndWritePullLibrary(npmPubData,fileTgzName);

                    return resultByte(codeValue,entityBody,"Ok");
                }
            }
        }catch (Exception e){
            logger.info("拉取错误:"+e.getMessage());
            return resultByte(500,null,"拉取错误:"+e.getMessage());
        }
        logger.info("拉取错误");
        return resultByte(500,null,"拉取错误");
    }






    /**
     *  npm上传-保存npm文件
     *  @param path: tgz文件相对路径
     * @param tgzPath: tgz文件的绝对路径
     *  @param decodedBytes:   npm  tag文件
     * @return
     */
    public void writeFile(String path,String tgzPath,byte[] decodedBytes) throws IOException {
        File folder = new File(path);

        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        //创建tga文件
        File file = new File(tgzPath);
        if (!file.exists()){
            file.createNewFile();
        }

        OutputStream OutputStream = new FileOutputStream(tgzPath);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);


        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(OutputStream);
        byte[]  buf=new byte[6];
        int len=-1;
        while((len=dataInputStream.read(buf)) != -1){
            dataOutputStream.write(buf,0,len);
        }
        inputStream.close();
    }

    /**
     *  npm拉取-查询npm的json数据
     * @param contextPath: contextPath
     * @param  version 版本
     * @return
     */
    public String findLibraryVersion(String contextPath,String version){

        String libraryName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        String contentJson =null;
        //通过制品名称查询制品
        LibraryQuery libraryQuery = new LibraryQuery();
        libraryQuery.setLibraryType("npm");
        libraryQuery.setName(libraryName);
        Library library = libraryService.findLibraryByNameAndType(libraryName,"npm");
        if (!ObjectUtils.isEmpty(library)){
            //查询版本
            LibraryVersionQuery libraryVersionQuery = new LibraryVersionQuery();
            libraryVersionQuery.setLibraryId(library.getId());
            libraryVersionQuery.setVersion(version);

            List<LibraryVersion>  libraryVersionList = libraryVersionService.findLibraryVersionList(libraryVersionQuery);
            //版本为空取最后创建的
           if (StringUtils.isEmpty(version)){
              libraryVersionList = libraryVersionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime).reversed()).collect(Collectors.toList());
           }
            if (CollectionUtils.isNotEmpty(libraryVersionList)){
                contentJson = libraryVersionList.get(0).getContentJson();
            }
        }
        return contentJson;
    }

    /**
     *  npm拉取-查询npm的json数据
     * @param contextPath: contextPath
     * @param  version 版本
     * @return
     */
    public LibraryVersion findLibraryVersion(List<String> rpyIds,String contextPath,String version){
        LibraryVersion libraryVersion=null;

        String libraryName = StringUtils.substringAfter(contextPath, "/");
        if (contextPath.endsWith("tgz")){
            String before = StringUtils.substringBefore(contextPath, "/-/");
            libraryName = StringUtils.substringAfter(before, "/");
        }

        //通过制品名字、仓库id、类型查询制品
        String[] rpyIdList = rpyIds.toArray(new String[rpyIds.size()]);
        List<Library> libraryByCondition = libraryService.findLibraryByCondition(libraryName, "npm", rpyIdList);
        if (CollectionUtils.isNotEmpty(libraryByCondition)){
            Library library = libraryByCondition.get(0);
            //制品库类型
            String repositoryType = library.getRepository().getRepositoryType();

            //拉取制品远程制品时候，第一次请求始终走代理拉取
            if (("remote").equals(repositoryType)&&StringUtils.isEmpty(version)){
                return libraryVersion;
            }

            //其他情况都查询下本地是否存在
            LibraryVersionQuery libraryVersionQuery = new LibraryVersionQuery();
            libraryVersionQuery.setLibraryId(library.getId());
            libraryVersionQuery.setVersion(version);
            List<LibraryVersion>  libraryVersionList = libraryVersionService.findLibraryVersionList(libraryVersionQuery);
            //版本为空取最后创建的
            if (StringUtils.isEmpty(version)){
                libraryVersionList = libraryVersionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime).reversed()).collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(libraryVersionList)){
                libraryVersion = libraryVersionList.get(0);
            }
            return libraryVersion;
        }
        return libraryVersion;
    }


    /**callAgencyNpm
     *  根据代理信息  转发远程库
     * @param  npmPubData npmPubData
     */
    public Result callAgencyNpm(NpmPubData npmPubData){
        String contextPath = npmPubData.getStoragePath();

        //通过仓库ids查询代理地址
        List<String> rpyIds = npmPubData.getRepositoryList().stream().map(Repository::getId).collect(Collectors.toList());
        String[] repositoryIds = rpyIds.toArray(new String[rpyIds.size()]);
        List<RepositoryRemoteProxy> remoteProxy = remoteProxyService.findAgencyByRpyIds(repositoryIds);
        if (CollectionUtils.isNotEmpty(remoteProxy)){
            List<String> proxyPathList = remoteProxy.stream().map(a -> a.getRemoteProxy().getAgencyUrl()).collect(Collectors.toList());
            //走第三方代理
            if (!contextPath.contains("/-/")){
                //第一次请求  拉取json
                logger.info("npm拉取json，地址："+contextPath);
                return   pullAgencyJson(npmPubData, remoteProxy);

            }else {
                //第二次请求 拉取tgz信息
                logger.info("npm拉取tgz，地址："+contextPath);
                return   execAgencyTgz(npmPubData, proxyPathList);
            }
        }
        logger.info("npm拉取报错-没有配置代理地址");
        return resultString(500,null,"没有配置代理地址");
    }


    /**
     * 第一次请求 根据代理地址执行代理拉取json
     * @param  npmPubData npmPubData
     * @param remoteProxyList 代理
     */
    public Result pullAgencyJson(NpmPubData npmPubData,List<RepositoryRemoteProxy> remoteProxyList) {
        String contextPath = npmPubData.getStoragePath();
        String after = contextPath.substring(contextPath.indexOf("/")+1);

        for (RepositoryRemoteProxy remoteProxy:remoteProxyList){
            String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
            try {
                //转发第三方 路径
                String decode = URLDecoder.decode(after, "UTF-8");
                String sendPath = agencyUrl +"/"+decode;
                String entityBody = RepositoryUtil.httpGet(sendPath);
                //替换Tarball （修改第二次请求路径）
                String json = replaceTarball(entityBody,remoteProxy.getRepository().getName(), npmPubData.getRequestFullURL());
                return resultString(200,json,"ok");
            }catch (Exception e){
                return resultString(500,"失败",e.getMessage());
            }
        }
        return resultString(500,null,"拉取错误");
    }



    /**
     *  代理拉取后创建并写入信息
     * @param  npmPubData npmPubData
     * @param  fileTgzName 文件名字带/-/
     * @return  remoteProxy 代理地址数据
     */
    public void createAndWritePullLibrary(NpmPubData npmPubData,String fileTgzName) throws IOException {


        //写入文件到服务器中
        String[] split = fileTgzName.split("/-/");
        String path=yamlDataMaService.repositoryAddress()+"/"+npmPubData.getRepository().getId()+"/"+npmPubData.getLibraryName();
        String absolutePath = path + "/" + split[1];
        writeFile(path,absolutePath,npmPubData.getTgzData());

        String fileUrl =npmPubData.getRepository().getId() + "/" + npmPubData.getLibraryName() + "/" + split[1];
        Long aLong = Long.valueOf(npmPubData.getTgzData().length);
        npmPubData.setRelativePath(fileUrl);
        npmPubData.setFileSize(aLong);

        //创建记录
        createPullLibrary(npmPubData,split[1]);
    }

    /**
     *  代理拉取后创建信息
     * @param  npmPubData npmPubData
     * @param  fileName 文件名字
     * @return  remoteProxy 代理地址数据
     */
    public void createPullLibrary(NpmPubData npmPubData,String fileName)  {
        Repository repository = npmPubData.getRepository();


        //创建制品
        Library library = libraryService.createLibraryData(npmPubData.getLibraryName(), "npm",repository);

        //获取远程制品json 数据
        //String sendJsonPath = remoteProxy + "/" + npmPubData.getLibraryName();
        //String entityBody = RepositoryUtil.httpGet(sendPath);

        //创建版本
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setSize(npmPubData.getFileSize());
        //libraryVersion.setContentJson(jsonData);
        libraryVersion.setLibrary(library);
        libraryVersion.setRepository(repository);
        //libraryVersion.setHash(dist);
        libraryVersion.setVersion(npmPubData.getVersion());
        libraryVersion.setLibraryType("npm");
       // libraryVersion.setPullUser(user.getName());
        libraryVersion.setPusher("center");
        String libraryVersionId = libraryVersionService.createLibraryVersionSplice(libraryVersion, fileName);

        //创建制品文件
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setFileName(fileName);
        libraryFile.setFileSize(RepositoryUtil.formatSize(npmPubData.getFileSize()));

        libraryFile.setFileUrl(npmPubData.getRelativePath());
        libraryFile.setRepository(repository);
        libraryFile.setRelativePath(fileName);
        libraryFile.setSize(npmPubData.getFileSize());
        libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);
    }

    /**
     *  返回结果byte封装
     * @param code  返回编码
     * @param  data  数据
     * @return
     */
    public Result resultByte(int code, byte[] data, String msg){
        Result<byte[]> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    /**
     *  返回结果string封装
     * @param code  返回编码
     * @param  data  数据
     * @return
     */
    public Result resultString(int code, String data, String msg){
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
}
