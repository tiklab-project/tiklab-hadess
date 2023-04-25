package io.tiklab.xpack.library.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.tiklab.eam.common.model.EamTicket;
import io.tiklab.eam.passport.user.model.UserPassport;
import io.tiklab.eam.passport.user.service.UserPassportService;
import io.tiklab.user.user.model.User;
import io.tiklab.user.user.model.UserQuery;
import io.tiklab.user.user.service.UserService;
import io.tiklab.xpack.library.model.*;
import io.tiklab.xpack.repository.model.*;
import io.tiklab.xpack.repository.service.RepositoryGroupService;
import io.tiklab.xpack.repository.service.RepositoryRemoteProxyService;
import io.tiklab.xpack.repository.service.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

/**
 * NpmUploadServiceImpl-npm上传下载
 */
@Service
public class NpmUploadServiceImpl implements NpmUploadService {

    @Value("${repository.library:null}")
    String repositoryLibrary;

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

    @Override
    public Map npmLogin(BufferedReader reader) {
        StringBuilder result = new StringBuilder();
        String lineTxt = null;
        try {
            while ((lineTxt = reader.readLine()) != null) {
                result.append(lineTxt).append("\n");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
       return verifyUser(result.toString());
    }

    @Override
    public Integer npmSubmit(String contextPath, InputStream inputStream) {
        String path=repositoryLibrary+contextPath;
        try{
            //读出流中的数据
            JSONObject jsonObjectData = readData(inputStream);

            //npm登陆用户信息
            Object maintainers = jsonObjectData.get("maintainers");
            JSONArray jsonArray = (JSONArray) maintainers;
            Object UserData = jsonArray.get(0);
            if (ObjectUtils.isEmpty(UserData)){
                return 401;
            }

            //判断登录信息 是否正确
            JSONObject userData = (JSONObject) UserData;
            String name = userData.get("name").toString();

            UserQuery userQuery = new UserQuery();
            userQuery.setName(name);
            List<User> userList = userService.findUserList(userQuery);
            if (CollectionUtils.isEmpty(userList)){
                return 401;
            }

            return npmSubmitData(path,jsonObjectData);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public Map<String,Object> npmPullTgzData(String contextPath) {
       String url = StringUtils.substringBeforeLast(contextPath, "/-/");
        String versionData = findLibraryVersion(url);

        //本地制品库制品文件不存在 走代理
        if (StringUtils.isEmpty(versionData)){
            return callAgencyNpm(contextPath);
        }

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



    @Override
    public Map<String,String> npmPull(String contextPath) {
        String libraryVersion = findLibraryVersion(contextPath);
        //本地制品库制品文件不存在 走代理
        if (StringUtils.isEmpty(libraryVersion)){
            return callAgencyNpm(contextPath);
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code","200");
        resultMap.put("data",libraryVersion);
        return resultMap;
    }

    /**
     *  npm登陆
     * @param LoginData: npm上传的登陆数据
     */
    public Map verifyUser(String LoginData){
        JSONObject allData =(JSONObject) JSONObject.parse(LoginData);

        String userName = allData.get("name").toString();
        String password = allData.get("password").toString();

        try{
            EamTicket eamTicket = accountLogin(userName,password,"1");
            Map<String, Object> result = new HashMap<>();
            result.put("success",true);
            result.put("ticket",eamTicket.getTicket());
            return result;
        }catch (Exception e){
            Map<String, Object> result = new HashMap<>();
            result.put("success",false);
            result.put("error","Bad username or password");
            return result;
        }
    }

    /**
     *  npm登陆-账号密码登陆、校验
     * @param userName: 用户名
     * @param  password 密码
     * @return
     */
    public EamTicket accountLogin(String userName,String password,String dirId){
        UserPassport userPassport = new UserPassport();
        userPassport.setAccount(userName);
        userPassport.setPassword(password);
        userPassport.setDirId(dirId);
        EamTicket login = userPassportService.login(userPassport);
        return login;
    }

    /**
     *  npm上传-文件的处理
     * @param path: 上传后存储文件的路径
     * @param allData:   上传的文件
     * @return
     */
    public Integer npmSubmitData(String path,JSONObject allData) throws IOException {

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


        //制品名称
        String frontUrl = path.substring(0, path.lastIndexOf("/"));
        String repositoryName = frontUrl.substring(frontUrl.lastIndexOf("/") + 1);
        String libraryName = path.substring(path.lastIndexOf("/") + 1);

        //tgz内容
        JSONObject tgaSecondData =(JSONObject) tgaData.get(tgzName);
        Object data = tgaSecondData.get("data");
        String jsonString = JSONObject.toJSONString(data);
        String substring = jsonString.substring(1,jsonString.length()-1);

        //版本
        JSONObject versionData =(JSONObject) allData.get("versions");
        Set<String> versionKey = versionData.keySet();
        String version = versionKey.stream().findFirst().orElse("null");

        //tag 文件存储的绝对路径
        String filePath=path+"/"+tgzName;

        //查询制品库是否存在
        List<Repository> repositoryList = repositoryService.findRepositoryList(new RepositoryQuery().setName(repositoryName));
        if (CollectionUtils.isNotEmpty(repositoryList)){

            //base64解密
            byte[] decodedBytes = Base64.getDecoder().decode(substring);
            //创建tgz文件
            writeFile(path,filePath,decodedBytes);

            int length = decodedBytes.length;
            double i =(double)length / 1000;
            long round = Math.round(i);

            //创建制品
            Library library = libraryService.createLibraryData(libraryName, "npm",repositoryList.get(0));

            //创建制品版本
            LibraryVersion libraryVersion = new LibraryVersion();
            libraryVersion.setContentJson(allData.toJSONString());
            libraryVersion.setLibrary(library);
            libraryVersion.setRepository(repositoryList.get(0));
            libraryVersion.setHash(dist);
            libraryVersion.setVersion(version);
            libraryVersion.setLibraryType("npm");
            libraryVersion.setPullUser("");

            String libraryVersionId =libraryVersionService.libraryVersionSplice(libraryVersion);


            //创建制品文件
            LibraryFile libraryFile = new LibraryFile();
            libraryFile.setLibrary(library);
            libraryFile.setFileName(tgzName);
            libraryFile.setFileSize(round+"KB");
            libraryFile.setFileUrl(filePath);
            libraryFile.setRepository(repositoryList.get(0));
            libraryFileService.libraryFileSplice(libraryFile,libraryVersionId);
            return 200;
        }else {
          return 404;
        }
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
     * @return
     */
    public String findLibraryVersion(String contextPath){

        String libraryName = contextPath.substring(contextPath.lastIndexOf("/") + 1);
        String contentJson =null;
        //通过制品名称查询制品
        LibraryQuery libraryQuery = new LibraryQuery();
        libraryQuery.setLibraryType("npm");
        libraryQuery.setName(libraryName);
        List<Library> libraryList = libraryService.findLibraryList(libraryQuery);
        if (CollectionUtils.isNotEmpty(libraryList)){
            //查询版本
            LibraryVersionQuery libraryVersionQuery = new LibraryVersionQuery();
            libraryVersionQuery.setLibraryId(libraryList.get(0).getId());
            /*//没带版本号就取最新的版本
            if (!StringUtils.isEmpty(version)){
                libraryVersionQuery.setVersion(version);
            }*/
            List<LibraryVersion>  libraryVersionList = libraryVersionService.findLibraryVersionList(libraryVersionQuery);
            List<LibraryVersion> versions = libraryVersionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime).reversed()).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(versions)){
                contentJson = versions.get(0).getContentJson();
            }
        }
        return contentJson;
    }

    /**
     *  根据代理信息  转发远程库
     * @param
     * @return
     */
    public Map callAgencyNpm(String contextPath ){
        Map resultMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        String RepositoryNameUrl = contextPath.substring(0, contextPath.lastIndexOf("/"));
        //查询组合制品库
        String repositoryName = RepositoryNameUrl.substring(contextPath.lastIndexOf("/") + 1);
        String agencyUrl = remoteProxyService.findAgencyUrl(repositoryName);
        if (StringUtils.isEmpty(agencyUrl)){
            resultMap.put("code",404);
        }else {
            String url=agencyUrl+agencyUrl;
            if (contextPath.contains("/-/")){
                ResponseEntity<byte[]> entity = restTemplate.getForEntity(url, byte[].class);
                byte[] entityBody = entity.getBody();
                resultMap.put("200",entityBody);
                return resultMap;
            }else {
                ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
                String entityBody = entity.getBody();
                resultMap.put("code",200);
                resultMap.put("data",entityBody);
            }
        }
        return resultMap;
    }

    /**
     *  读取输入流中的数据
     * @param  inputStream 数据流
     * @return  data 读取的数据
     */
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
