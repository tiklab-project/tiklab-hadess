package io.tiklab.hadess.upload.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.tiklab.core.Result;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.hadess.common.*;
import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.library.model.LibraryFile;
import io.tiklab.hadess.library.model.LibraryVersion;
import io.tiklab.hadess.library.model.LibraryVersionQuery;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.model.RepositoryGroup;
import io.tiklab.hadess.repository.model.RepositoryGroupQuery;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxy;
import io.tiklab.hadess.repository.service.RepositoryGroupService;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.upload.common.response.NugetResponse;
import io.tiklab.hadess.upload.common.response.PypiResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class NugetUploadServiceImpl implements NugetUploadService {
    private static Logger logger = LoggerFactory.getLogger(NugetUploadServiceImpl.class);

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
    LibraryVersionService versionService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    UserCheckService userCheckService;

    @Override
    public void uploadData(HttpServletRequest request, HttpServletResponse response) {
        //获取推送用户信息进行校验
        String authorization = request.getHeader("Authorization");
        Result<String> userCheckResult = userCheck(authorization);
        if (userCheckResult.getCode()==401){
            NugetResponse.errorAuthToClient(response,userCheckResult.getMsg());
            return;
        }

        //截取路径获取仓库名字
        String contextPath = request.getRequestURI();
        String pathData = yamlDataMaService.getUploadRepositoryUrl(contextPath, "nuget");
        if (pathData.endsWith("/")){
             pathData = StringUtils.substringBeforeLast(pathData, "/");
        }
        Repository repository = repositoryService.findRepository(pathData, "nuget");

        if (("remote").equals(repository.getRepositoryType())) {
            logger.info("nuget 不支持代理库推送");
            NugetResponse.errorToClient(response,500,"不支持该仓库推送");
            return;
        }
        if (("group").equals(repository.getRepositoryType())) {
            //查询组合库关联的仓库
            List<RepositoryGroup> groupList = groupService.findRepositoryGroupList(new RepositoryGroupQuery()
                    .setRepositoryGroupId(repository.getId()));

            List<RepositoryGroup> repositoryGroups = groupList.stream().filter(a -> "local".equals(a.getRepository().getRepositoryType())).toList();
            if (CollectionUtils.isEmpty(repositoryGroups)){
                logger.info("nuget 推送组合库中没有关联本地库");
                NugetResponse.errorToClient(response,500,"推送组合库中没有关联本地库");
                return;
            }
             repository = repositoryGroups.get(0).getRepository();
        }
        try {
            //创建解析器，结合 DiskFileItemFactory，用于配置文件存储的临时位置。
            // 调用 parseRequest(request) 方法，该方法会读取整个请求体，自动处理边界分隔符，提取各个部分的数据
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

            //获取流文件
            List<FileItem> fileItems = items.stream().filter(item -> !item.isFormField()).toList();
            InputStream fileContent = fileItems.get(0).getInputStream();

            //将文件先写入/sample文件夹。先随机生成文件名字存入文件流，再读取文件内容获取真正的文件名字和版本，最后将随机名替换成真正的文件名
            String math =  UuidGenerator.gen(5);
            String repBorder = yamlDataMaService.repositoryAddress() + "/" + repository.getId();
            String borderPath =repBorder+"/sample";
            FileUtil.copyFileData(fileContent,borderPath,math+".nupkg");

            //读取临时存储的.nupkg包。获取制品名字和版本
            String temporaryFilePath = borderPath + "/" + math + ".nupkg";
            String nuspecData = FileUtil.readFileInNupkg(temporaryFilePath);
            Document document = Jsoup.parse(nuspecData);
            String libraryName = document.selectFirst("id").text();
            String version = document.selectFirst("version").text();
            String fileName=libraryName+"."+version+".nupkg";

            String libLowerCase = libraryName.toLowerCase();
            String fileLowerCase = fileName.toLowerCase();
            //复制临时文件到真制品文件中，并删除临时文件
            String filePath = repBorder + "/" +libLowerCase+"/"+fileLowerCase;
            FileUtils.copyFile(new File(temporaryFilePath),new File(filePath));
            FileUtils.delete(new File(temporaryFilePath));

            //添加数据库表
            Library library = libraryService.createLibraryData(libraryName, "nuget", repository);
            addLibraryData(library,version,fileName,userCheckResult.getData());
        }catch (Exception e){
            logger.info("nuget上传，失败："+e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void downloadData(HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getRequestURI();
        String pathData = yamlDataMaService.getUploadRepositoryUrl(contextPath, "nuget");
        String[] split = pathData.split("/");

        //请求服务端地址
        StringBuffer requestURL = request.getRequestURL();
        String hostPath = StringUtils.substringBefore(requestURL.toString(), "/nuget/");


        //仓库名字
        String repName = split[0];
        Repository repository = repositoryService.findRepository(repName, "nuget");
        if (ObjectUtils.isEmpty(repository)) {
            NugetResponse.errorToClient(response,404,"Repository not found");
            return;
        }

        //查询制品列表
        if (contextPath.endsWith("/query")){
            findQuery(response,repository);
            return;
        }

        //拉取相关的
        String jsonData=null;
        if (("local").equals(repository.getRepositoryType())) {
            //下载json文件
            if (pathData.endsWith(".json")){
                 jsonData = downloadJsonByLocalRep(repository, pathData,hostPath);
            }else {
                //拉取package文件
                downloadPackageByLocalRep(response,repository, pathData);
            }
        }

        //客户端请求拉取元数据仓库地址为远程库
        if (("remote").equals(repository.getRepositoryType())) {
            //下载json文件
            if (pathData.endsWith(".json")){
                 jsonData =   downloadJsonByRemoteRep(repository, pathData,hostPath);
            }else {
                //拉取package文件
                downloadPackageByRemoteRep(response,repository, pathData);
            }
        }

        //客户端请求拉取元数据仓库地址为组合库
        if (("group").equals(repository.getRepositoryType())) {
            //下载json文件
            if (pathData.endsWith(".json")){
                 jsonData =   downloadJsonByGroupRep(repository, pathData,hostPath);
            }else {
                //拉取package文件
                downloadPackageByGroupRep(response,repository, pathData);
            }
        }


        //获取结果返回给客户端
        if (!ObjectUtils.isEmpty(jsonData)){
            if (jsonData.startsWith("404")||jsonData.startsWith("500")){
                String[] splitData = jsonData.split("-");
                NugetResponse.errorToClient(response,Integer.parseInt(splitData[0]),splitData[1]);
                return;
            }

            NugetResponse.correctToClient(response,jsonData);
        }
    }

    /**
     * nuget查询->查询可用的nuget制品类型
     * @param repository  制品库
     */
    public void findQuery(HttpServletResponse response,Repository repository){


        //配置组合库
        if (("group").equals(repository.getRepositoryType())) {
            //查询组合库关联的仓库
            List<RepositoryGroup> groupList = groupService.findRepositoryGroupList(new RepositoryGroupQuery()
                    .setRepositoryGroupId(repository.getId()));
            if (CollectionUtils.isEmpty(groupList)){
                logger.info("error：nuget查询可用制品失败，组合库未关联其他库");
                NugetResponse.errorToClient(response,500,"nuget查询可用列表，组合库未关联其他库");}
        }
        try {
            String s = RepositoryUtil.restTemplateJsonGet(HadessFinal.NUGET_FIND_QUERY);
            NugetResponse.correctToClient(response,s);
        }catch (Exception e){
            logger.info("error：nuget查询可用制品失败，"+e.getMessage());
            NugetResponse.errorToClient(response,500,"nuget查询可用列表失败");
            e.printStackTrace();
        }
    }

    /**
     * nuget下载->通过本地库下载json文件
     * @param repository  制品库
     * @param pathData 客户端请求地址，不带http的主机地址
     * @param hostPath 客户端请求地址，带http的主机地址
     */
    public String downloadJsonByLocalRep(Repository repository,String pathData,String hostPath) {

        if (pathData.contains("/v3/")){
            String beforeLast = StringUtils.substringBeforeLast(pathData, "/index.json");
            String libraryName = StringUtils.substringAfterLast(beforeLast, "/");
            // 因为NuGet 处理包名的标准方式，请求 URL 的制品小写格式。这里查询出当前仓库所有的制品
            List<Library> libraryList = libraryService.findLibraryList(repository.getId(), null);

            if (CollectionUtils.isNotEmpty(libraryList)){
                List<Library> libraries = libraryList.stream().filter(a -> libraryName.equals(a.getName().toLowerCase())).toList();
                if (CollectionUtils.isNotEmpty(libraries)){
                    List<LibraryVersion> libraryVersionList = versionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(libraryList.get(0).getId()));
                    String libraryMetadata = getLibraryMetadata(libraryVersionList,pathData);
                    return libraryMetadata;
                }
            }
            logger.info("nuget拉取报错，配置本地库库拉取制品不存在");

            return "404-制品不存在";
        }else{
            //获取索引文件
            String repPath = hostPath + "/nuget/" + repository.getName();
            String indexData = getUploadMetadata(repPath);
            return indexData;
        }
    }


    /**
     * nuget下载->通过代理库下载json文件
     * @param repository  制品库
     * @param pathData 客户端请求地址，不带http的主机地址
     * @param hostPath 客户端请求地址，带http的主机地址
     */
    public String downloadJsonByRemoteRep(Repository repository,String pathData,String hostPath) {
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRepId(repository.getId());
        if (CollectionUtils.isEmpty(remoteProxyList)){
            logger.info("nuget download,not find proxy address");
            return "404-组合未关联仓库";
        }


        String data ;
        if (pathData.contains("/v3/")||pathData.contains("/v3-")){
            //获取包的元数据路径
            String endPath = pathData.substring(pathData.indexOf("/v3"));
            data = registerForwardingRemote(remoteProxyList,endPath);
        }else{
            //获取索引的json
            data = indexForwardingRemote(remoteProxyList, hostPath);
        }

        return data;
    }



    /**
     * nuget下载->通过组合库下载json文件
     * @param repository  制品库
     * @param pathData 客户端请求地址，不带http的主机地址
     * @param hostPath 客户端请求地址，带http的主机地址
     */
    public String downloadJsonByGroupRep(Repository repository,String pathData,String hostPath) {
        //查询组合库关联的仓库
        List<RepositoryGroup> groupList = groupService.findRepositoryGroupList(new RepositoryGroupQuery()
                .setRepositoryGroupId(repository.getId()));

        //组合库未关联仓库
        if (CollectionUtils.isEmpty(groupList)){
            logger.info("pypi拉取-组合未关联仓库");
            return "404-组合未关联仓库";
        }

        String data ;
        if (pathData.contains("/v3/")||pathData.contains("/v3-")){
            //截取制品名字
            String beforeLast = StringUtils.substringBeforeLast(pathData, "/index.json");
            String libraryName = StringUtils.substringAfterLast(beforeLast, "/");

            //查询制品在数据库是否存在
            List<String> repIds = groupList.stream().map(a -> a.getRepository().getId()).toList();
            String[] repIdList = repIds.toArray(new String[repIds.size()]);
            // 因为NuGet 处理包名的标准方式，请求 URL 的制品小写格式。这里查询出当前仓库所有的制品
            List<Library> libraryList = libraryService.findLibraryByCondition(null, "nuget", repIdList);

            //制品在本地库中存在
            if (CollectionUtils.isNotEmpty(libraryList)){
                List<Library> libraries = libraryList.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType())).toList();
                if (CollectionUtils.isNotEmpty(libraries)){
                    List<Library> LastLibraryList = libraries.stream().filter(a -> libraryName.equals(a.getName().toLowerCase())).toList();
                    if (CollectionUtils.isNotEmpty(LastLibraryList)){
                        //通过制品的id查询对应的版本，返回制品元数据
                        List<String> libraryIds = LastLibraryList.stream().map(Library::getId).toList();
                        String[] libraryIdList = libraryIds.toArray(new String[LastLibraryList.size()]);
                        List<LibraryVersion> versions = versionService.findVersionByLibraryIds(libraryIdList);
                        String libraryMetadata = getLibraryMetadata(versions,pathData);

                        return libraryMetadata;
                    }
                }
            }

            //制品不存在、或者存在远程库中。转发获取制品元数据
            List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRpyIds(repIdList);
            String endPath = pathData.substring(pathData.indexOf("/v3"));
            data = registerForwardingRemote(remoteProxyList,endPath);
            return data;
        }else{
            //获取索引文件
            String repPath = hostPath + "/nuget/" + repository.getName();
            String indexData = getUploadMetadata(repPath);
            return indexData;
        }
    }



    /**
     * nuget下载->通过本地下载制品包
     * @param repository  制品库
     * @param pathData 客户端请求地址，不带http的主机地址
     */
    public void  downloadPackageByLocalRep(HttpServletResponse response,Repository repository,String pathData){

        //截取客户端请求地址获取制品和版本
        String endData = StringUtils.substringAfter(pathData, "flatcontainer/");
        String libraryName=StringUtils.substringBefore(endData, "/");
        String fileName = StringUtils.substringAfterLast(endData, "/");

        String path = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName+"/"+fileName ;
        File file = new File(path);

        //本地存在文件，直接拉取
        if (file.exists()){
            try {
                FileUtil.readFileData(file,response);
                return;
            } catch (IOException e) {
                logger.info("nuget拉取，配置本地库拉取package报错");
                NugetResponse.errorToClient(response,500,"拉取package报错");
                e.printStackTrace();
                return;
            }
        }
        logger.info("nuget拉取，配置本地库拉取package报错，文件不存在");
        NugetResponse.errorToClient(response,404,"文件不存在");
    }


    /**
     * nuget下载->通过代理库下载制品包
     * @param repository  制品库
     * @param pathData 客户端请求地址，不带http的主机地址
     */
    public void downloadPackageByRemoteRep(HttpServletResponse response,Repository repository,String pathData){
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRepId(repository.getId());
        if (CollectionUtils.isEmpty(remoteProxyList)){
            logger.info("nuget download,not find proxy address");
            NugetResponse.errorToClient(response,500,"not find proxy address");
            return ;
        }

        //文件名字
        String fileName = StringUtils.substringAfterLast(pathData, "/");

        //截取制品版本
        String beforeLast = StringUtils.substringBeforeLast(pathData, "/");
        String version=StringUtils.substringAfterLast(beforeLast, "/");

        //截取制品名字
        String libraryPath = StringUtils.substringBeforeLast(beforeLast, "/");
        String libraryName=StringUtils.substringAfterLast(libraryPath, "/");

        List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repository.getId(), libraryName, version);
       //制品文件表数据不存在转发远程拉取
        if (CollectionUtils.isEmpty(libraryFileList)){
            packageForwardingRemote(response,remoteProxyList,repository,pathData);
            return;
        }

        //文件在hadess服务中的位置
        String path = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName+"/"+fileName ;
        File file = new File(path);

        //本地存在文件，直接拉取
        if (file.exists()){

            try {
                FileUtil.readFileData(file,response);
                return;
            } catch (IOException e) {
                // 在hadess服务器中拉取代理库的制品报错，转发到远程拉取
                packageForwardingRemote(response,remoteProxyList,repository,pathData);
            }
        }
        //代理库中不存在，直接转发远程拉取
        packageForwardingRemote(response,remoteProxyList,repository,pathData);
    }

    /**
     * nuget下载->通过代理库下载制品包
     * @param repository  制品库
     * @param pathData 客户端请求地址，不带http的主机地址
     */
    public void downloadPackageByGroupRep(HttpServletResponse response,Repository repository,String pathData){
        //查询组合库关联的仓库
        List<RepositoryGroup> groupList = groupService.findRepositoryGroupList(new RepositoryGroupQuery()
                .setRepositoryGroupId(repository.getId()));


        //文件名字
        String fileName = StringUtils.substringAfterLast(pathData, "/");

        //截取制品版本
        String beforeLast = StringUtils.substringBeforeLast(pathData, "/");
        String version=StringUtils.substringAfterLast(beforeLast, "/");

        //截取制品名字
        String libraryPath = StringUtils.substringBeforeLast(beforeLast, "/");
        String libraryName=StringUtils.substringAfterLast(libraryPath, "/");


        List<String> repIds = groupList.stream().map(a -> a.getRepository().getId()).toList();
        String[] repIdList = repIds.toArray(new String[repIds.size()]);

        //查询代理地址
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRpyIds(repIdList);

        //查询制品
        List<LibraryFile> libraryFileList = libraryFileService.findFileByReAndLibraryAndVer(repIdList, null, version);
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> fileName.equals(a.getFileName().toLowerCase())).toList();
            if (CollectionUtils.isNotEmpty(libraryFiles)){
                String repositoryAddress = yamlDataMaService.repositoryAddress();
                for (LibraryFile libraryFile:libraryFiles){
                    String filePath = repositoryAddress + "/" + libraryFile.getFileUrl();
                    File file = new File(filePath);

                    //获取当前制品文件对应的仓库类型
                    String repId = libraryFile.getRepository().getId();
                    List<RepositoryGroup> repositoryGroups = groupList.stream().filter(a -> repId.equals(a.getRepository().getId())).toList();
                    String repositoryType = repositoryGroups.get(0).getRepository().getRepositoryType();
                    //制品存在代理库中
                    if (("remote").equals(repositoryType)){
                        //存在文件，直接拉取
                        if (file.exists()){
                            try {
                                FileUtil.readFileData(file,response);
                                return;
                            } catch (IOException e) {
                                //代理库中的制品拉取失败，转发远程拉取
                                logger.info("nuget拉取，package拉取文件存在，拉取文件报错转发远程拉取");
                                packageForwardingRemote(response,remoteProxyList,repository,pathData);
                                return;
                            }
                        }

                        logger.info("nuget拉取，package拉取表存在，文件不存在转发远程拉取");
                        //代理库中不存在，直接转发远程拉取
                        packageForwardingRemote(response,remoteProxyList,repository,pathData);
                        return;
                    }
                    //制品存在本地库中
                    if (("local").equals(repositoryType)){
                        if (file.exists()){
                            try {
                                FileUtil.readFileData(file,response);
                                return;
                            } catch (IOException e) {
                                logger.info("制品包存在获取失败");
                                NugetResponse.errorToClient(response,500,"Read package fails");
                                throw new RuntimeException(e);
                            }
                        }
                        logger.info("制品包不存在");
                        NugetResponse.errorToClient(response,404,"package not find");
                        return;
                    }
                }
            }
        }

        //制品不存在转发代理拉取
        logger.info("nuget拉取，package拉取表不存在，转发远程拉取");
        packageForwardingRemote(response,remoteProxyList,repository,pathData);

    }



    /**
     * 下载服务索引文件->转发远程
     * @param remoteProxyList  代理地址
     * @param hostPath  hostPath
     */
    public String indexForwardingRemote(List<RepositoryRemoteProxy> remoteProxyList,String hostPath){

        for (RepositoryRemoteProxy remoteProxy:remoteProxyList){

            String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
            try {
                String s = RepositoryUtil.restTemplateJsonGet(agencyUrl);

                //需要替换成的路径
                String path = hostPath + "/nuget/" + remoteProxy.getRepository().getName();
                String data = updateIndexService(s, path);
                return data;
            }catch (Exception e){
                logger.info("pypi拉取元数据失败，地址："+agencyUrl);
                e.printStackTrace();
                continue;
            }
        }
        return "500-调用代理地拉取失败";
    }





    /**
     * 下载注册信息（包的元数据）文件->转发远程
     * @param remoteProxyList  代理地址
     */
    public String registerForwardingRemote(List<RepositoryRemoteProxy> remoteProxyList,String endPath){

        int size=0;
        for (RepositoryRemoteProxy remoteProxy:remoteProxyList){
            size+=1;
            String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
            try {
                URI uri = new URI(agencyUrl);
                String uriHost = uri.getScheme() + "://" + uri.getHost() + (uri.getPort() != -1 ? ":" + uri.getPort() : "");
                String newPath = uriHost + endPath;

                String s = RepositoryUtil.restTemplateJsonGet(newPath);
                return s;
            }catch (Exception e){
                if (size==remoteProxyList.size()){
                    logger.info("pypi拉取元数据失败，地址："+agencyUrl);
                    String message = e.getMessage();
                    if (message.startsWith("404")){
                        return "404-调用代理地拉取未查询到制品";
                    }else {
                        return "500-调用代理地拉取失败";
                    }
                }
            }
        }
        return "500-调用代理地拉取失败";
    }


    /**
     * 下载制品包->转发远程
     * @param remoteProxyList  代理地址
     * @param repository 制品库
     * @param pathData 客户端请求地址
     */
    public void packageForwardingRemote(HttpServletResponse response,List<RepositoryRemoteProxy> remoteProxyList,
                                        Repository repository,String pathData){
        //截取客户端请求地址获取制品和版本
        String endData = StringUtils.substringAfter(pathData, "/flatcontainer/");
        String libraryName=StringUtils.substringBefore(endData, "/");
        String version = endData.substring(libraryName.length()+1, endData.indexOf("/", endData.indexOf("/") + 2));
        String fileName=StringUtils.substringAfterLast(endData, "/");

        //截取地址
        String endPath = pathData.substring(pathData.indexOf("/v3/"));

        for (RepositoryRemoteProxy remoteProxy:remoteProxyList) {
            String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
            try {
                URI uri = new URI(agencyUrl);
                String uriHost = uri.getScheme() + "://" + uri.getHost() + (uri.getPort() != -1 ? ":" + uri.getPort() : "");
                String newPath = uriHost + endPath;

                //存储到Hadess服务器的地址
                String storePath = getStoreFilePathAtRemote(remoteProxy.getRepository(), libraryName, fileName);
                restTemplateGetByte(response,newPath,storePath);

                //添加制品数据到数据表中
                Library library = libraryService.createLibraryData(libraryName, "nuget", repository);
                addLibraryData(library,version,fileName,"central warehouse");
                return;
            } catch (Exception e) {
                logger.info("nuget 拉取package失败，地址：" + agencyUrl);
                e.printStackTrace();
                NugetResponse.errorToClient(response,500,"download package fails");

            }
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
            }
            return null;
        });
    }

    /**
     * 修改服务索引
     * @param indexData  索引数据
     * @param path 修改的路径
     */
    public String updateIndexService(String indexData,String path) throws Exception {
       String updatePath=path;
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(indexData);

        ArrayNode resourcesNode = (ArrayNode) jsonNode.get("resources");
        // 替换每个资源的 id
        for (JsonNode resource : resourcesNode) {

            String type = objectMapper.convertValue(resource.get("@type"), String.class);
            //SearchQueryService（搜索）、RegistrationsBaseUrl（注册信息、包元数据）、PackageBaseAddress（包下载）
            if (type.startsWith("SearchQueryService")||type.startsWith("RegistrationsBaseUrl")||
                    type.startsWith("PackageBaseAddress")){

                //原路径
                String originalPath = objectMapper.convertValue(resource.get("@id"), String.class);
                URI uri = new URI(originalPath);
                String uriPath = uri.getPath();

                //将包下载路径修改为新版本格式/v3
                String newPath = updatePath + uriPath;
                if (type.startsWith("PackageBaseAddress")){
                    newPath=path+"/v3/flatcontainer/";
                }

                ((ObjectNode) resource).put("@id", newPath);
            }
        }
        // 将修改后的对象转换回 JSON 字符串
        String modifiedJson = objectMapper.writeValueAsString(jsonNode);

        return modifiedJson;
    }

    /**
     * nuget远程下载 -> 获取存储到Hadess服务器的地址
     * @param repository 制品库
     * @param  libraryName 制品名字
     * @param fileName 文件名字
     */
    public String getStoreFilePathAtRemote(Repository repository,String libraryName,String fileName) throws IOException {
        String path = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName ;

        // 规范化路径并创建目录（如果不存在）
        Path dirPath = Paths.get(path);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        return dirPath+ "/" +fileName;
    }

    /**
     * nuget下载 -> 获取制品的元数据json
     * @param libraryVersionList 版本list
     */
    public String getLibraryMetadata(List<LibraryVersion>libraryVersionList,String pathData ){
        Map<String, Object> metadataMap = new HashMap<>();
        if (pathData.contains(" v3/registration5")){

        }else {
            List<String> versionList = libraryVersionList.stream().map(LibraryVersion::getVersion).toList();
            metadataMap.put("versions",versionList);
        }





        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(metadataMap);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * nuget上传、下载 -> 获取上传的索引json
     * @param path 路径
     */
    public String getUploadMetadata(String path){
        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("version","3.0.0");

        //添加resources
        List<Map<String,String>> resourceList = new ArrayList<>();


        //需要定义的类型
        String[] types={
                "SearchQueryService","SearchQueryService/3.0.0-rc","SearchQueryService/3.5.0","SearchQueryService/3.0.0-beta",
                "RegistrationsBaseUrl","RegistrationsBaseUrl/3.0.0-rc","RegistrationsBaseUrl/3.0.0-beta",
                "RegistrationsBaseUrl/3.4.0","RegistrationsBaseUrl/3.6.0","RegistrationsBaseUrl/Versioned",
                "PackageBaseAddress/3.0.0","PackagePublish/2.0.0","LegacyGallery/2.0.0"};

        // 打印数组内容
        for (String type : types) {
            Map<String, String> resourceMap = new HashMap<>();
            if (type.startsWith("SearchQueryService")){
                resourceMap.put("@id",path+"/query");
            }
            if (type.startsWith("RegistrationsBaseUrl")){
                if (type.endsWith("3.6.0")||type.endsWith("Versioned")){
                    resourceMap.put("@id",path+"/v3/registration5-gz-semver2/");
                }else {
                    resourceMap.put("@id",path+"/v3/registration5-gz-semver1/");
                }
            }
            if (type.startsWith("PackageBaseAddress")){
                resourceMap.put("@id",path+"/v3/flatcontainer/");
            }
            if (type.startsWith("PackagePublish")||type.startsWith("LegacyGallery")){
                resourceMap.put("@id",path);
            }

            resourceMap.put("@type",type);

            resourceList.add(resourceMap);
        }
        metadataMap.put("resources",resourceList);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(metadataMap);
            return jsonString;
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }

    }

    /**
     * 校验用户信息
     * @param authorization  客户端上传的用户信息
     */
    public Result<String> userCheck(String authorization) {

        //docker第一次访问没有用户信息 为了获取支持的验证机制
        if (ObjectUtils.isEmpty(authorization)){
            logger.info("nuget拉取推送没有用户信息");
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
            logger.info("nuget拉取推送校验用户错误："+e.getMessage());
            return Result.error(401,e.getMessage());
        }
    }

    /**
     * nuget拉取 -> 添加制品表数据
     * @param library 制品
     *  @param version 版本
     * @param fileName 文件名字
     * @param userName 用户名
     */
    public void addLibraryData( Library library,String version,String fileName,String userName){

        Repository repository = library.getRepository();
        String libraryName = library.getName();

        String filePath = yamlDataMaService.repositoryAddress() + "/"+repository.getId()+"/"+libraryName.toLowerCase()+"/"+fileName.toLowerCase();

        //创建版本
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setHash("SHA256 "+RepositoryUtil.getSHA256ByPath(filePath));
        libraryVersion.setPusher(userName);
        libraryVersion.setLibrary(library);
        libraryVersion.setVersion(version);
        libraryVersion.setRepository(repository);
        libraryVersion.setLibraryType("nuget");
        String versionId = versionService.redactLibraryVersion(libraryVersion);
        libraryVersion.setId(versionId);

        //创建文件

        File file = new File(filePath);

        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setRepository(repository);
        libraryFile.setLibraryVersion(libraryVersion);
        libraryFile.setFileName(fileName);
        String size = RepositoryUtil.formatSize(file.length());
        libraryFile.setFileSize(size);
        libraryFile.setSize(file.length());
        libraryFile.setFileUrl(repository.getId() + "/" + libraryName.toLowerCase()+"/"+fileName.toLowerCase());
        libraryFile.setRelativePath(fileName.toLowerCase());
        libraryFileService.redactLibraryFile(libraryFile);
    }
}
