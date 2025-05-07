package io.tiklab.hadess.upload.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tiklab.core.Result;
import io.tiklab.hadess.common.*;
import io.tiklab.hadess.library.model.*;
import io.tiklab.hadess.library.service.*;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.model.RepositoryGroup;
import io.tiklab.hadess.repository.model.RepositoryGroupQuery;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxy;
import io.tiklab.hadess.repository.service.RepositoryGroupService;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.upload.common.response.ComposerResponse;
import io.tiklab.hadess.upload.common.response.PypiResponse;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Exporter
public class ComposerUploadServiceImpl implements ComposerUploadService{
    private static Logger logger = LoggerFactory.getLogger(ComposerUploadServiceImpl.class);

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    RepositoryService repositoryService;


    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Autowired
    RepositoryGroupService groupService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryComposerService composerService;


    @Autowired
    LibraryVersionService versionService;

    @Autowired
    LibraryFileService libraryFileService;


    @Autowired
    UserCheckService userCheckService;

    @Override
    public void downloadJsonData(HttpServletRequest request, HttpServletResponse response) {

        String pathData = yamlDataMaService.getUploadRepositoryUrl(request.getRequestURI(),"composer");
        //仓库名称
        String repName = StringUtils.substringBefore(pathData, "/");

        String fileName = StringUtils.substringAfterLast(pathData, repName+"/");
        Enumeration<String> headerNames = request.getHeaderNames();
        String authHeader = request.getHeader("Authorization");

        //客户端请求的网络地址
        StringBuffer requestURL = request.getRequestURL();
        String hostPath = StringUtils.substringBefore(requestURL.toString(), "/composer/");


        Repository repository = repositoryService.findRepository(repName, "composer");
        if (ObjectUtils.isEmpty(repository)){
            logger.info("composer下载，配置的仓库不存在");
            ComposerResponse.errorDoToClient(response,404,"配置的仓库不存在");
            return;
        }

        try {
            //获取根包信息
            if (fileName.endsWith("packages.json")){
                downloadPackageJson(response,repository);
                return;
            }

            // 开发版获取，返回空对象
            if (fileName.endsWith("~dev.json")) {
                response.getWriter().print(new JSONObject().put("packages", new JSONObject()));
                return;
            }
            String data=null;
            //客户端请求推送地址为远程库
            if (("local").equals(repository.getRepositoryType())){
                 data = downloadMetadataByLocalRep(repository, fileName);

            }
            //客户端请求拉取元数据仓库地址为远程库
            if (("remote").equals(repository.getRepositoryType())) {
                data =  downloadMetadataByRemoteRep(repository, fileName,hostPath);
            }
            //客户端请求拉取元数据仓库地址为远程库
            if (("group").equals(repository.getRepositoryType())) {
                 data = downloadMetadataByGroupRep(repository, pathData,hostPath);
            }

            //返回错误信息
            if (data.startsWith("500-")||data.startsWith("404-")){
                String[] split = data.split("-");
                ComposerResponse.errorDoToClient(response,Integer.parseInt(split[0]),split[1]);
                return;
            }

            PypiResponse.correctToClient(response,data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadPackage(HttpServletRequest request, HttpServletResponse response) {
        String pathData = yamlDataMaService.getUploadRepositoryUrl(request.getRequestURI(),"composerPack");

        //仓库
        String repName = StringUtils.substringBefore(pathData, "/");
        Repository repository = repositoryService.findRepository(repName, "composer");

        //客户端请求拉取包文件仓库地址为本地库
        if (("local").equals(repository.getRepositoryType())){
            downloadPackageByLocalRep( response, repository, pathData);
        }else {
            //客户端请求拉取包文件仓库地址为远程库
            downloadPackageByRemoteRep(response, repository, pathData);
        }

    }

    @Override
    public void uploadPackage(HttpServletRequest request, HttpServletResponse response) {
        //用户信息
        String authorization = request.getHeader("Authorization");
        try {
            if (StringUtils.isEmpty(authorization)){
                ComposerResponse.errorUpToClient(response,401,"用户信息不存在");
                return;
            }
            //校验用户信息
            Result<String> userCheckResult = userCheck(authorization);
            if (userCheckResult.getCode()==401){
                ComposerResponse.errorUpToClient(response,401,userCheckResult.getMsg());
                return;
            }

            //上传
            uploadFileData(request,response,userCheckResult.getData());

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }



    /**
     * composer下载->获取获取根包信息package.json，修改metadata-url（第二次请求获取特定包的元数据的路径）
     * @param repository 仓库
     */
    public void downloadPackageJson(HttpServletResponse response,Repository repository) throws JsonProcessingException {
        Map<String, Object> packageMap = new HashMap<>();
        String metadataUrl="/composer/" + repository.getName() + "/p2/%package%.json";
        packageMap.put("metadata-url",metadataUrl);
        packageMap.put("packages", new ArrayList<>());
        packageMap.put("list",HadessFinal.COMPOSER_PACKAGES_LIST);
        packageMap.put("metadata-changes-url",HadessFinal.COMPOSER_METADATA_CHANGES);

        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(packageMap);
        ComposerResponse.correctToClient(response,data);
    }

    /**
     * composer下载->本地库下载包的元数据（版本和依赖详情）
     * @param repository 仓库
     */
    public String downloadMetadataByLocalRep(Repository repository,String fileName) throws IOException {
        String substringAfter = StringUtils.substringAfter(fileName, "p2/");
        String libraryName = StringUtils.substringBeforeLast(substringAfter, ".json");

        //查询制品
        Library composer = libraryService.findLibraryByCondition(libraryName, "composer",repository.getId() );
        if(ObjectUtils.isEmpty(composer)){
            logger.info("composer拉取，配置本地库-制品不存在");
            return "404-制品不存在";

        }
        List<LibraryComposer> libraryComposerList = composerService.findLibraryComposerList(new LibraryComposerQuery().setLibraryId(composer.getId()));
        if (CollectionUtils.isEmpty(libraryComposerList)){
            logger.info("composer拉取，配置本地库-数据库元数据不存在");
            return "404-数据库元数据不存在";
        }
        String filePath = yamlDataMaService.repositoryAddress() + "/" + libraryComposerList.get(0).getMetadataPath();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)){
            logger.info("composer拉取，配置本地库-元数据文件不存在");
            return "404-元数据文件不存在";
        }
        String readString = Files.readString(path);

        return readString;
    }

    /**
     * composer下载->组合库下载包的元数据（版本和依赖详情）
     * @param repository 仓库
     * @param fileName 客户端请求的文件名
     * @param hostPath 网络地址
     */
    public String downloadMetadataByGroupRep(Repository repository,String fileName,String hostPath) throws IOException {
        String substringAfter = StringUtils.substringAfter(fileName, "p2/");
        String libraryName = StringUtils.substringBeforeLast(substringAfter, ".json");

        //查询组合库关联的仓库
        List<RepositoryGroup> groupList = groupService.findRepositoryGroupList(new RepositoryGroupQuery()
                .setRepositoryGroupId(repository.getId()));

        //组合库未关联仓库
        if (CollectionUtils.isEmpty(groupList)){
            logger.info("pypi拉取-组合未关联仓库");
            return "500-组合未关联仓库";
        }

        List<String> repIds = groupList.stream().map(a -> a.getRepository().getId()).toList();
        String[] repIdList = repIds.toArray(new String[repIds.size()]);
        List<Library> libraryList = libraryService.findLibraryByCondition(libraryName, "composer", repIdList);
        //客户端拉取的制品不为空
        if (CollectionUtils.isNotEmpty(libraryList)){
            //客户端拉取的制品不为空且为本地库的
            List<String> localLibraryIds = libraryList.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType()))
                    .map(Library::getId).toList();
            if (CollectionUtils.isNotEmpty(localLibraryIds)){
                String[] localLibraryIdList = localLibraryIds.toArray(new String[localLibraryIds.size()]);

                List<LibraryComposer> libraryComposerList = composerService.findLibraryComposerList(new LibraryComposerQuery().setLibraryIds(localLibraryIdList));
                for (LibraryComposer libraryComposer:libraryComposerList){
                    String metadataPath = yamlDataMaService.repositoryAddress() + "/" + libraryComposer.getMetadataPath();
                    File file = new File(metadataPath);
                    if (!file.exists()){
                        continue;
                    }
                    String metadata = RepositoryUtil.readFile(file);
                    return metadata;
                }
            }
        }
        logger.info("composer download，代理拉取元数据");
        //制品不存在或者存在远程库中。拉取元数据不携带版本所以，防止拉取不到最新版本，所以即使远程库中存在元数据也需要转发到代理地址拉取
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRpyIds(repIdList);
        if (CollectionUtils.isEmpty(remoteProxyList)){
            logger.info("pypi拉取，代理库没有关联代理地址");
            return "500-代理库没有关联代理地址";
        }

        return metadataForwardingRemote(remoteProxyList,substringAfter,hostPath);
    }



    /**
     * composer下载package-本地库
     * @param response response
     * @param repository 仓库
     * @param pathData 客户端请求的部分路径
     */
    public void downloadPackageByLocalRep(HttpServletResponse response,Repository repository,String pathData) {
        try {
        //版本
        String version = StringUtils.substringAfterLast(pathData, "/");
        //制品名字
        String beforeLast = StringUtils.substringBeforeLast(pathData, "/");
        String libraryName = StringUtils.substringAfter(beforeLast, repository.getName() + "/");

        //通过仓库、版本、制品名字查询制品文件
        List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, version);

        String filePath = yamlDataMaService.repositoryAddress() + "/" + libraryFileList.get(0).getFileUrl();
        File file = new File(filePath);
        if (!file.exists()){
            logger.info("package not find");
            ComposerResponse.errorDoToClient(response,500,"package not find");
            return;
        }

        InputStream inputStream = new FileInputStream(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            ComposerResponse.errorDoToClient(response,500,"下载package失败");
        }
    }

    /**
     * composer下载package-远程库
     * @param response response
     * @param repository 仓库
     * @param pathData 客户端请求的部分路径
     */
    public void downloadPackageByRemoteRep(HttpServletResponse response,Repository repository,String pathData) {
        try {
            //版本
            String version = StringUtils.substringAfterLast(pathData, "/");
            //制品名字
            String beforeLast = StringUtils.substringBeforeLast(pathData, "/");
            String libraryName = StringUtils.substringAfter(beforeLast, repository.getName() + "/");

            //查询制品文件是否存在
            List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, version);
            if (CollectionUtils.isNotEmpty(libraryFileList)){
                String fileUrl = libraryFileList.get(0).getFileUrl();
                String filePath = yamlDataMaService.repositoryAddress() + "/" + fileUrl;
                File file = new File(filePath);

                //文件存在直接本地拉取
                if (file.exists()){
                    FileUtil.readFileData(file,response);
                    return;
                }
            }

            //获取远程拉取packaged的地址
            String borderPath = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName;
            String formerFilePath = borderPath+"/"+getFormerJsonName(libraryName);
            Path path = Paths.get(formerFilePath);
            String readString = Files.readString(path);

            //解析json
            JSONObject jsonObject = JSON.parseObject(readString);
            JSONObject packagesJson = jsonObject.getJSONObject("packages");
            JSONArray packageList = packagesJson.getJSONArray(libraryName);
            //过滤当前版本
            List<JSONObject> collect = packageList.stream()
                    .filter(JSONObject.class::isInstance)
                    .map(JSONObject.class::cast)
                    .filter(pkg -> pkg.get("version").equals(version))
                    .toList();

            JSONObject distObject = collect.get(0).getJSONObject("dist");
            String remoteUrl = distObject.getString("url");

            //存储到本地的package的地址
            String packagePath = borderPath + "/" + getPackageName(libraryName, version);

            //转发远程
            restTemplateGetByte(response,remoteUrl,packagePath);

            //添加数据到数据库
            addLibraryData(repository,libraryName,version,"composer center");

        }catch (Exception e){
            e.printStackTrace();
            ComposerResponse.errorDoToClient(response,500,"下载package失败");
        }
    }
    /**
     * 转发远程获取
     * @param relativeAbsoluteUrl 地址
     * @param storePath 本地存储地址
     */
    public void restTemplateGetByte(HttpServletResponse response,String relativeAbsoluteUrl,String storePath){
        RestTemplate restTemplate = new RestTemplate();
        Path localFilePath = Paths.get(storePath);
        restTemplate.execute(relativeAbsoluteUrl, HttpMethod.GET, null, clientHttpResponse -> {
            try (InputStream inputStream = clientHttpResponse.getBody();
                 OutputStream outputStream = response.getOutputStream();
                 OutputStream fileOutput = Files.newOutputStream(localFilePath, StandardOpenOption.CREATE)) {

                byte[] buffer = new byte[8192]; // 8KB缓冲区
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    fileOutput.write(buffer, 0, bytesRead);

                    // 立即刷新确保数据发送
                    outputStream.flush();
                    fileOutput.flush();
                }
                // inputStream.transferTo(outputStream);
            }
            return null;
        });
    }


    /**
     * composer下载->远程库下载包的元数据（版本和依赖详情）
     * @param repository 仓库
     * @param fileName 客户端请求的文件名字
     * @param hostPath 客户端请求的网络地址
     */
    public String downloadMetadataByRemoteRep(Repository repository,String fileName,String hostPath) throws JsonProcessingException {
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRepId(repository.getId());
        if (CollectionUtils.isEmpty(remoteProxyList)){
            logger.info("composer下载，仓库没有配置代理地址");
            return "404-仓库没有配置代理地址";
        }
        //将仓库名字截取掉
        String substringAfter = StringUtils.substringAfter(fileName, "p2/");
        String jsonString = metadataForwardingRemote(remoteProxyList, substringAfter,hostPath);

     /*   //获取根包信息，修改metadata-url（第二次请求获取特定包的元数据的路径）
        if (fileName.endsWith("packages.json")){
            // 创建 JSONObject。将metadata-url名字添加请求的仓库名字
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String metadataUrl = jsonObject.getString("metadata-url");
            String substringAfter = StringUtils.substringAfter(metadataUrl, "/p2/");
            String newMetadataUrl = "/composer/" + repository.getName() + "/p2/"+substringAfter;
            jsonObject.put("metadata-url",newMetadataUrl);

             jsonString = jsonObject.toJSONString();
        }*/
         return jsonString;
    }



    /**
     * 下载元数据数据->转发远程
     * @param remoteProxyList  代理地址
     * @param fileName 转发的名字
     * @param hostPath 客户端请求的网络的地址
     */
    public String metadataForwardingRemote(List<RepositoryRemoteProxy> remoteProxyList,String fileName,String hostPath){

        String filepath="p2/"+fileName;
        //制品的名字
        String libraryName = StringUtils.substringBeforeLast(fileName, ".json");

        for (RepositoryRemoteProxy remoteProxy:remoteProxyList){
            String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
            try {
                if (!agencyUrl.endsWith("/")){
                    agencyUrl=agencyUrl+"/";
                }
                String path= agencyUrl + filepath;
                String data = restTemplateGet(path);


                //修改下载package的路径的元数据
                String updatedDistUrl = updateDistUrl(remoteProxy.getRepository(), data, libraryName, hostPath);

                return updatedDistUrl;
            }catch (Exception e){
                logger.info("composer下载，地址："+agencyUrl);
                e.printStackTrace();
                continue;
            }
        }
        return "500-调用代理地拉取失败";
    }

    /**
     * 下载元数据数据->重新组装拉取package的路径
     * @param repository   仓库
     * @param data 远程返回的数据
     * @param libraryName 制品名称
     * @param hostPath 客户端请求的网络的地址
     */
    public String updateDistUrl(Repository repository,String data,
                              String libraryName,String hostPath)  {
        String[] split = libraryName.split("/");

        //存储元数据json的文件夹
        String jsonBorderPath=yamlDataMaService.repositoryAddress()+"/"+repository.getId()+"/"+libraryName;

        //写入原本的json文件以former 结尾
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        FileUtil.copyFileData(inputStream,jsonBorderPath,split[0] + "-" + split[1] + "-former.json");


        JSONObject jsonObject = JSON.parseObject(data);
        JSONObject packagesJson = jsonObject.getJSONObject("packages");

        JSONArray packageList = packagesJson.getJSONArray(libraryName);
        for (int i = 0; i < packageList.size(); i++) {
            JSONObject packages = packageList.getJSONObject(i);
            String version = packages.getString("version");
            JSONObject distJson = packages.getJSONObject("dist");

            //重新组装拉取package的路径
            String packageUrl = yamlDataMaService.getComposerPackageUrl(hostPath, repository.getName() + "/" + libraryName+"/"+version);
           if (!ObjectUtils.isEmpty(distJson)){
               distJson.put("url",packageUrl);
           }
        }

        return jsonObject.toJSONString();

    }

    /**
     * 上传文件数据
     * @param request 客户端上传的数据
     * @param userName 用户名
     */
    public void uploadFileData(HttpServletRequest request,HttpServletResponse response,String userName){
        try {
            InputStream inputStream = request.getInputStream();
            StringBuffer requestURL = request.getRequestURL();
            String hostPath = StringUtils.substringBefore(requestURL.toString(), "/composer/");

            //版本
            String substring = request.getQueryString();
            String version = StringUtils.substringAfterLast(substring, "=");
            if (ObjectUtils.isEmpty(substring)&&substring.startsWith("version=")){
                ComposerResponse.errorUpToClient(response,500,"命令未定义版本version");
                return;
            }

            //仓库
            String repName = yamlDataMaService.getUploadRepositoryUrl(request.getRequestURI(),"composer");
            Repository repository = repositoryService.findRepository(repName, "composer");
            if (ObjectUtils.isEmpty(repository)){
                logger.info("composer下载，配置的仓库不存在");
                ComposerResponse.errorUpToClient(response,500,"配置的仓库不存在");
                return;
            }
            //客户端请求推送地址为远程库
            if (("remote").equals(repository.getRepositoryType())){
                ComposerResponse.errorUpToClient(response,500,"配置的仓库不能为代理库");
                return;
            }

            //客户端请求拉取元数据仓库地址为组合库
            if (("group").equals(repository.getRepositoryType())){
                List<RepositoryGroup> groupList = groupService.findRepositoryGroupList(new RepositoryGroupQuery()
                        .setRepositoryGroupId(repository.getId()));
                List<RepositoryGroup> localRepList = groupList.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType())).toList();

                //组合库未关联本地仓库。上传只能上传到本地制品库
                if (CollectionUtils.isEmpty(localRepList)){
                    logger.info("composer上传制品，未关联本地库");
                    ComposerResponse.errorUpToClient(response,500,"配置的组合库没有关联本地库");
                    return;
                }
                repository=localRepList.get(0).getRepository();
            }

            //将文件先写入/sample文件夹。先随机生成文件名字存入文件流，再读取文件内容获取真正的文件名字和版本，最后将随机名替换成真正的文件名
            String math =  UuidGenerator.gen(5);
            String path = yamlDataMaService.repositoryAddress() + "/" + repository.getId();
            String borderPath =path+"/sample";
            FileUtil.copyFileData(inputStream,borderPath,math+".zip");

            //读取zip文件中的composer.json数据
            String zipFilePath = borderPath+"/"+math+".zip";
            String fileNameInZip = "composer.json";
            String composerData = FileUtil.readFileInZip(zipFilePath, fileNameInZip);

            //composer.json不存在
            if (("500").equals(composerData)){
                ComposerResponse.errorUpToClient(response,500,"Invalid composer package 无效的composer包");
                FileUtils.delete(new File(zipFilePath));
                return;
            }

            //获取composer.json中的name字段
            JSONObject jsonObject = JSON.parseObject(composerData);
            Object name = jsonObject.get("name");
            //composer.json里面name字段不存在
            if (ObjectUtils.isEmpty(name)){
                ComposerResponse.errorUpToClient(response,500,"package name does not exist.包名不存在");
                FileUtils.delete(new File(zipFilePath));
                return;
            }
            String composerName = name.toString();
            boolean matches = composerName.matches("^[^/]*\\/[^/]*$");
            //composer.json里面name字段不符合规范
            if (!matches){
                ComposerResponse.errorUpToClient(response,500,"Invalid package name.包名格式不正确，参考规范：https://getcomposer.org/doc/04-schema.md#name");
                FileUtils.delete(new File(zipFilePath));
                return;
            }

            //将文件放入composer制品名字下面并移除sample文件夹下面临时存储的文件
            String packageName = getPackageName(composerName, version);
            String filePath = path + "/" +composerName+"/"+packageName;
            FileUtils.copyFile(new File(zipFilePath),new File(filePath));
            FileUtils.delete(new File(zipFilePath));


            //客户端下载包的地址
            String packageUrl = yamlDataMaService.getComposerPackageUrl(hostPath, repository.getName() + "/" + composerName+"/"+version);
            //拼装并写入制品的版本和依赖详情的文件json
            joinAddMetaData(composerData,filePath,version,packageUrl);

            //添加数据库表数据
            addLibraryData(repository,composerName,version,userName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 校验用户信息
     * @param authorization  客户端上传的用户信息
     */
    public Result<String> userCheck(String authorization) {

        //docker第一次访问没有用户信息 为了获取支持的验证机制
        if (ObjectUtils.isEmpty(authorization)){
            logger.info("pypi拉取推送没有用户信息");
            return Result.error(401,"用户信息不存在");
        }
        try {
            String basic = authorization.replace("Basic", "").trim();
            byte[] decode = Base64.getDecoder().decode(basic);
            //用户信息
            String userData = new String(decode, "UTF-8");
            userCheckService.basicsUserCheck(userData);
            String[] split = userData.split(":");
            String userName = split[0];
            return Result.ok(userName);
        }catch (Exception e){
            logger.info("docker登陆错误："+e.getMessage());
            return Result.error(401,e.getMessage());
        }
    }

    /**
     * 执行get请求
     * @param relativeAbsoluteUrl 地址
     */
    public String restTemplateGet(String relativeAbsoluteUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders newHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(relativeAbsoluteUrl, HttpMethod.GET,
                new HttpEntity<>(newHeaders), String.class);

        String jsonString = exchange.getBody();
        return jsonString;
    }



    /**
     * composer上传，拼装并写入制品的版本和依赖详情的文件json
     * @param composerData composer.json的内容
     * @param  filePath 文件的位置
     * @param version 版本
     * @param packageUrl 客户端下载包的地址
     */
    public void  joinAddMetaData(String composerData, String filePath,String version,String packageUrl) throws IOException, NoSuchAlgorithmException {
        //制品名字
        JSONObject composerObject = JSON.parseObject(composerData);
        String composerName = composerObject.get("name").toString();

        //json存储的位置
        String storePath = StringUtils.substringBeforeLast(filePath,"/") + "/" + getFormerJsonName(composerName);

        //向当前上传的文件的composer.json添加版本和文件存储信息
        composerObject.put("version",version);
        composerObject.put("type","library");
        //添加distMap
        Map<String, String> distMap = new HashMap<>();
        String sha1Code = RepositoryUtil.SHA1Encryption(filePath);
        distMap.put("url",packageUrl);
        distMap.put("type","zip");
        distMap.put("shasum",sha1Code);
        distMap.put("reference",sha1Code);
        composerObject.put("dist",distMap);

        //制品中存在json文件了
        Path path = Paths.get(storePath);
        if (Files.exists(path)){
            String readString = Files.readString(path);
            JSONObject libraryObject = JSON.parseObject(readString);
            JSONObject packages = (JSONObject)libraryObject.get("packages");
            JSONArray jsonArray = packages.getJSONArray(composerName);

            boolean isTheSame=false;
            for (int i = 0; i < jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Object jsonVersion = jsonObject.get("version");
                if (version.equals(jsonVersion)){
                    jsonArray.set(i, composerObject);
                    isTheSame=true;
                    break;
                }
            }
            //不存在相同的版本，直接添加到list中
            if (!isTheSame){
                jsonArray.add(composerObject);
            }
            //将修改后的json文件写入
            Files.write(path, libraryObject.toJSONString().getBytes());
            return;
        }

        //不存在json文件
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("minified","composer/2.0");

        Map<String, List> fineMap = new HashMap<>();
        List<Object> arrayList = new ArrayList<>();

        //将添加后的composer.json放入以当前制品为key的list中
        arrayList.add(composerObject);

        fineMap.put(composerName,arrayList);
        hashMap.put("packages",fineMap);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(storePath), hashMap);
    }

    /**
     * 下载->添加制品信息
     * @param repository 制品库
     * @param libraryName 制品名字
     * @param version 版本
     */
    public void  addDownloadLibraryData(Repository repository,String libraryName,
                                String version){
        //创建制品
        Library library = libraryService.createLibraryData(libraryName,"composer",repository);

        //制品版本创建、修改
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setPusher("composer center");
        libraryVersion.setLibrary(library);
        libraryVersion.setVersion(version);
        libraryVersion.setLibraryType("composer");
        String libraryVersionId = versionService.redactLibraryVersion(libraryVersion);
        libraryVersion.setId(libraryVersionId);


    }

    /**
     * 添加制品信息
     * @param repository 制品库
     * @param libraryName 制品名字
     * @param version 版本
     * @param userName 用户名
     */
    public void  addLibraryData(Repository repository,String libraryName,
                                String version,String userName) throws IOException, NoSuchAlgorithmException {

        String[] split = libraryName.split("/");
        String fileName = split[0] + "-" + split[1] + "-" + version + ".zip";
        String composerJsonName = split[0] + "-" + split[1] +".json";

        //文件存储的文件夹路径
        String borderPath = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName+"/";

        //创建制品
        Library library = libraryService.createLibraryData(libraryName,"composer",repository);

        //制品版本创建、修改
        String sha1Code = RepositoryUtil.SHA1Encryption(borderPath + fileName);
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setPusher(userName);
        libraryVersion.setLibrary(library);
        libraryVersion.setVersion(version);
        libraryVersion.setRepository(repository);
        libraryVersion.setHash("SHA1 "+sha1Code);
        libraryVersion.setLibraryType("composer");
        String libraryVersionId = versionService.redactLibraryVersion(libraryVersion);
        libraryVersion.setId(libraryVersionId);

        //创建制品文件
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setRepository(repository);
        libraryFile.setLibrary(library);
        libraryFile.setLibraryVersion(libraryVersion);
        libraryFile.setFileName(fileName);
        libraryFile.setFileUrl(repository.getId() + "/" + libraryName+"/" + fileName);
        File file = new File(borderPath + fileName);
        String size = RepositoryUtil.formatSize(file.length());
        libraryFile.setRelativePath(fileName);
        libraryFile.setFileSize(size);
        libraryFile.setSize(file.length());
        libraryFileService.redactLibraryFile(libraryFile);


        LibraryComposer libraryComposer = new LibraryComposer();
        libraryComposer.setLibraryId(library.getId());
        libraryComposer.setMetadataPath(repository.getId() + "/" + libraryName+"/" + composerJsonName);
        composerService.createLibraryComposer(libraryComposer);
    }

    /**
     * 获取原本json的名字
     * @param libraryName 制品
     */
    public String getFormerJsonName(String libraryName){
        String[] split = libraryName.split("/");
        return split[0] + "-" + split[1] + "-former.json";
    }
    /**
     * 获取制品的package的名字
     * @param libraryName 制品
     */
    public String getPackageName(String libraryName,String version){
        String[] split = libraryName.split("/");
        return split[0] + "-" + split[1] +"-"+version+".zip";
    }
}
