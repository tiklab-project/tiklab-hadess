/*
package io.thoughtware.hadess.upload.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.thoughtware.core.exception.SystemException;
import io.thoughtware.eam.passport.user.service.UserPassportService;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.UserCheckService;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.hadess.library.service.LibraryFileService;
import io.thoughtware.hadess.library.service.LibraryService;
import io.thoughtware.hadess.library.service.LibraryVersionService;
import io.thoughtware.hadess.repository.model.*;
import io.thoughtware.hadess.repository.service.RepositoryGroupService;
import io.thoughtware.hadess.repository.service.RepositoryRemoteProxyService;
import io.thoughtware.hadess.repository.service.RepositoryService;
import io.thoughtware.hadess.upload.service.NpmUploadService;
import io.thoughtware.user.user.model.User;
import io.thoughtware.user.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


*/
/**
 * NpmUploadServiceImpl-npm上传下载
 *//*

@Service
public class NpmUploadServiceImpI implements NpmUploadService {

    private static Logger logger = LoggerFactory.getLogger(NpmUploadServiceImpI.class);
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
    public Map npmLogin(BufferedReader reader) {
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
            resultMap.put("success",true);
            return resultMap;
        }catch (Exception e){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("success",false);
            resultMap.put("error","Bad username or password");
            return resultMap;
        }
    }

    @Override
    public Integer npmSubmit(String contextPath, InputStream inputStream) {
        try{
            //读出流中的数据
            JSONObject jsonObjectData = readData(inputStream);

            //npm登陆用户信息
            Object maintainers = jsonObjectData.get("maintainers");
            JSONArray jsonArray = (JSONArray) maintainers;
            Object UserData = jsonArray.get(0);
            if (ObjectUtils.isEmpty(UserData)){
                return HttpServletResponse.SC_UNAUTHORIZED;
            }

            //判断登录信息 是否正确
            JSONObject userData = (JSONObject) UserData;
            String name = userData.get("name").toString();

            List<User> userList = userCheckService.npmUserCheckByName(name);
            if (CollectionUtils.isEmpty(userList)){
                return HttpServletResponse.SC_UNAUTHORIZED;
            }

            return npmSubmitData(contextPath,jsonObjectData,name);
        }catch (Exception e){
            logger.info("提交npm失败："+e.getMessage());
            return HttpServletResponse.SC_BAD_REQUEST;
          //  throw new SystemException(e.getMessage());
        }
    }

    @Override
    public Map<String,String> npmPullJson(Repository repository, String requestFullURL) {
        String contextPath = yamlDataMaService.getUploadRepositoryUrl(requestFullURL);
        logger.info("npm拉取(json)-请求地址："+contextPath);
        String libraryVersion = findLibraryVersion(contextPath,null);
        //本地制品库制品文件不存在 走代理
        if (StringUtils.isEmpty(libraryVersion)){
            logger.info("npm拉取02(json)-进入代理通道");
            return callAgencyNpm(contextPath);
        }
        logger.info("npm拉取02(json)-本地服务器拉取");
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code","200");
        resultMap.put("data",libraryVersion);
        return resultMap;
    }
    @Override
    public Map<String,Object> npmPullTgzData(Repository repository,String requestFullURL) {
        String contextPath = yamlDataMaService.getUploadRepositoryUrl(requestFullURL);
        logger.info("npm拉取01(tgz)-请求地址："+contextPath);
        String url = StringUtils.substringBeforeLast(contextPath, "/-/");
        String repositoryName = url.substring(url.indexOf("/") + 1);
        int startIndex = contextPath.lastIndexOf(repositoryName) + repositoryName.length()+1;
        String version = contextPath.substring(startIndex, contextPath.lastIndexOf(".tgz"));

        String versionData = findLibraryVersion(url,version);

        //本地制品库制品文件不存在 走代理
        if (StringUtils.isEmpty(versionData)){
            logger.info("npm拉取02(tgz)-进入代理通道");
            return callAgencyNpm(contextPath);
        }
        logger.info("npm拉取02(tgz)-进入本地服务器拉取");
        JSONObject allData =(JSONObject) JSONObject.parse(versionData);

        //tga的内容
        JSONObject tgaData =(JSONObject) allData.get("_attachments");
        Set<String> tgaKey = tgaData.keySet();
        String tgzName = tgaKey.stream().findFirst().orElse("null");

        JSONObject tgaSecondData =(JSONObject) tgaData.get(tgzName);
        Object data = tgaSecondData.get("data");
        String jsonString = JSONObject.toJSONString(data);
        String substring = jsonString.substring(1,jsonString.length()-1);

        byte[] decodedBytes = Base64.getDecoder().decode(substring);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code","200");
        resultMap.put("data",decodedBytes);
        return resultMap;
    }





    */
/**
     *  npm上传-文件的处理（存制品到数据库）
     * @param contextPath: 客户端请求路径
     * @param allData:   上传的文件
     * @param  userName
     * @return
     *//*

    public Integer npmSubmitData(String contextPath,JSONObject allData,String userName) throws IOException {

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

        int repositoryIndex = contextPath.indexOf("/",1);
        //仓库名称
        String repositoryName=contextPath.substring(0,repositoryIndex);
        //制品名称
        String libraryName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
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
            String replace = contextPath.replace(repositoryName, repositoryList.get(0).getId());
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
            libraryVersion.setContentJson(jsonData);
            libraryVersion.setLibrary(library);
            libraryVersion.setRepository(repositoryList.get(0));
            libraryVersion.setHash(dist);
            libraryVersion.setSize(Long.valueOf(length));
            libraryVersion.setVersion(version);
            libraryVersion.setLibraryType("npm");
            libraryVersion.setPullUser("");

            String libraryVersionId =libraryVersionService.libraryVersionSplice(libraryVersion,tgzName);


            //创建制品文件
            LibraryFile libraryFile = new LibraryFile();
            libraryFile.setLibrary(library);
            libraryFile.setFileName(tgzName);
            libraryFile.setFileSize(size);
            libraryFile.setFileUrl(replace+"/"+tgzName);
            libraryFile.setRepository(repositoryList.get(0));
            libraryFile.setRelativePath(tgzName);
            libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);
            return HttpServletResponse.SC_OK;
        }else {
          return HttpServletResponse.SC_NOT_FOUND;
        }
    }


    */
/**
     *  npm上传-保存npm文件
     *  @param path: tgz文件相对路径
     * @param tgzPath: tgz文件的绝对路径
     *  @param decodedBytes:   npm  tag文件
     * @return
     *//*

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

    */
/**
     *  npm拉取-查询npm的json数据
     * @param contextPath: contextPath
     * @param  version 版本
     * @return
     *//*

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


    */
/**
     *  根据代理信息  转发远程库
     * @param
     * @return
     *//*

    public Map callAgencyNpm(String contextPath){

        String repositoryName = contextPath.substring(0, contextPath.indexOf("/",1));
        List<String> remoteProxy = remoteProxyService.findAgencyUrl(repositoryName);
        if (CollectionUtils.isEmpty(remoteProxy)){
            Map resultMap = new HashMap<>();
            logger.info("npm拉取错误-没有配置代理地址");
           resultMap.put("code","404");
           return resultMap;
        }else {
            Map<String,String> map = execCallAgency(remoteProxy, 0, contextPath);
           */
/* if (map.get("code").equals("200")){
                pullCreateLibrary(contextPath,map);
            }*//*

             return map;
        }
    }


    */
/**
     *  根据代理地址执行远程拉取
     * @param  remoteProxy 代理地址
     * @param  index 索引
     * @return  data 读取的数据
     *//*

    public Map<String,String> execCallAgency(List<String> remoteProxy,Integer index,String contextPath){
        Map resultMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String substring = contextPath.substring(contextPath.indexOf("/", 1) );
        try {
            String agencyUrl = remoteProxy.get(index);
            String url=agencyUrl+substring;
            url = url.trim();
            if (contextPath.contains("/-/")){
                ResponseEntity<byte[]> entity = restTemplate.getForEntity(url, byte[].class);
                byte[] entityBody = entity.getBody();
                resultMap.put("code",entityBody);
                resultMap.put("agencyUrl",agencyUrl);
                logger.info("npm拉取(tgz)-代理拉取状态："+entity.getStatusCode());
                return resultMap;
                }else {
                    String entityBody = RepositoryUtil.httpGet(url);
                    // ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
                    // String entityBody = forEntity.getBody();
                    resultMap.put("code","200");
                    resultMap.put("data",entityBody);
                    resultMap.put("agencyUrl",agencyUrl);
                    logger.info("npm拉取(json)-代理拉取状态："+200);
            }} catch (Exception e) {
                if (index+1<remoteProxy.size()){
                    String agencyUrl = remoteProxy.get(index + 1);
                    logger.info("maven拉取-远程路径："+remoteProxy.get(0)+"拉取错误执行其他远程路径："+agencyUrl);
                    execCallAgency(remoteProxy,index + 1,contextPath);
                }else {
                    logger.info("npm拉取-远程路径报错："+e.getMessage());
                    resultMap.put("code","500");
                    resultMap.put("data",e.getMessage());
                }
            }
        return resultMap;
    }


    public void pullCreateLibrary(String contextPath,Map<String,String> map){
        String repositoryName = contextPath.substring(0, contextPath.indexOf("/",1));
        Repository repository = repositoryService.findRepositoryByName(repositoryName);
        if (("group").equals(repository.getRepositoryType())){
            List<RepositoryGroup> repositoryGroupList = repositoryGroupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));
            List<RepositoryGroup> RepositoryGroups = repositoryGroupList.stream().filter(a -> ("remote").equals(a.getRepository().getRepositoryType())).collect(Collectors.toList());
            List<String> ids = RepositoryGroups.stream().map(a -> a.getRepository().getId()).collect(Collectors.toList());
            String[] repositoryIds = ids.toArray(new String[ids.size()]);

            RepositoryRemoteProxy agencyByRpyIdAndPath = remoteProxyService.findAgencyByRpyIdAndPath(repositoryIds, map.get("agencyUrl"));

            String libraryName = contextPath.substring(contextPath.indexOf("/", 1)+1 );

            //创建制品
            Library library = libraryService.createLibraryData(libraryName, "npm",agencyByRpyIdAndPath.getRepository());

            String data = map.get("data");


        }
    }


    */
/**
     *  读取输入流中的数据
     * @param  inputStream 数据流
     * @return  data 读取的数据
     *//*

    public  JSONObject readData(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(inputStream.available());
        BufferedInputStream in = new BufferedInputStream(inputStream);
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        int len = 0;
        while (-1 != (len = in.read(buffer, 0, buf_size))) {
            bos.write(buffer, 0, len);
        }
        String data = bos.toString();
        JSONObject objectData =(JSONObject) JSONObject.parse(data);
        return  objectData;
    }

}
*/
