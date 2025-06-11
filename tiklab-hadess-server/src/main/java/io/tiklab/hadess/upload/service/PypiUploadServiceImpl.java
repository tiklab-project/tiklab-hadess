package io.tiklab.hadess.upload.service;

import io.tiklab.core.Result;
import io.tiklab.hadess.common.FileUtil;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.UserCheckService;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.model.*;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.hadess.library.service.LibraryPypiService;
import io.tiklab.hadess.library.service.LibraryService;
import io.tiklab.hadess.library.service.LibraryVersionService;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.model.RepositoryGroup;
import io.tiklab.hadess.repository.model.RepositoryGroupQuery;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxy;
import io.tiklab.hadess.repository.service.RepositoryGroupService;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
import io.tiklab.hadess.repository.service.RepositoryService;
import io.tiklab.hadess.upload.common.response.PypiResponse;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Exporter
public class PypiUploadServiceImpl implements PypiUploadService{
    private static Logger logger = LoggerFactory.getLogger(PypiUploadServiceImpl.class);

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
    LibraryPypiService libraryPypiService;

    @Autowired
    LibraryVersionService versionService;

    @Autowired
    LibraryFileService libraryFileService;


    @Autowired
    UserCheckService userCheckService;

    @Override
    public void downloadMetadata(HttpServletRequest request,HttpServletResponse response) {
        //获取推送用户信息进行校验
        String authorization = request.getHeader("Authorization");
        Result<String> userCheckResult = userCheck(authorization);
        if (userCheckResult.getCode()==401){
            PypiResponse.errorToClient(response,401,userCheckResult.getMsg());
            return;
        }

        String contextPath = request.getRequestURI();
        String pathData = yamlDataMaService.getUploadRepositoryUrl(contextPath,"pypi");
       //制品库名字
        String repName = StringUtils.substringBefore(pathData, "/");
        Repository repository = repositoryService.findRepository(repName, "pypi");

        //制品名字
        String libraryName = pathData.substring(pathData.indexOf("/") + 1, pathData.lastIndexOf("/"));
        String result=null;
        if (!ObjectUtils.isEmpty(repository)){
            //客户端请求拉取元数据仓库地址为本地库
            if (("local").equals(repository.getRepositoryType())){
                result = downloadMetadataByLocalRep(repository, libraryName);
            }
            //客户端请求拉取元数据仓库地址为远程库
            if (("remote").equals(repository.getRepositoryType())){
                result = downloadMetadataByRemoteRep(repository, libraryName);
            }
            //客户端请求拉取元数据仓库地址为组合库
            if (("group").equals(repository.getRepositoryType())){
                result = downloadMetadataByGroupRep(repository, libraryName);
            }
        }

        //获取结果返回给客户端
        if (!ObjectUtils.isEmpty(result)){
            if (result.startsWith("404")||result.startsWith("500")){
                String[] split = result.split("-");
                PypiResponse.errorToClient(response,Integer.parseInt(split[0]),split[1]);
                return;
            }
            PypiResponse.correctToClient(response,result);
            return;
        }

        logger.info("pypi拉取，配置的仓库不存在");
        PypiResponse.errorToClient(response,404,"配置的仓库不存在");
    }


    @Override
    public void downloadPackages(String contextPath, HttpServletResponse response) {
        String pathData = yamlDataMaService.getUploadRepositoryUrl(contextPath,"pypi");
        //仓库名字
        String repName = StringUtils.substringBefore(pathData, "/");
        Repository repository = repositoryService.findRepository(repName, "pypi");

        String path = StringUtils.substringAfter(pathData, "/");

        //客户端请求拉取包文件仓库地址为本地库
        if (("local").equals(repository.getRepositoryType())){
              downloadPackagesByLocalRep(repository,path,response);
        }else {
            //客户端请求拉取包文件仓库地址为远程库
            downloadPackagesByRemoteRep(repository,path,response);
        }

    }

    @Override
    public void UploadData(HttpServletRequest request,HttpServletResponse resp) {
        //获取推送用户信息进行校验
        String authorization = request.getHeader("Authorization");
        Result<String> userCheckResult = userCheck(authorization);
        if (userCheckResult.getCode()==401){
            PypiResponse.errorToClient(resp,401,userCheckResult.getMsg());
            return;
        }
        //用户名
        String userName = userCheckResult.getData();

        String contextPath = request.getRequestURI();
        Enumeration<String> headerNames = request.getHeaderNames();
        //仓库名称
        String repName = yamlDataMaService.getUploadRepositoryUrl(contextPath,"pypi");
        if (repName.endsWith("/")){
            repName=StringUtils.substringBefore(repName,"/");
        }
        Repository repository = repositoryService.findRepository(repName, "pypi");
        if (!ObjectUtils.isEmpty(repository)){
            if (("local").equals(repository.getRepositoryType())){
                uploadByLocalRep(request,repository,userName);
            }
            if (("group").equals(repository.getRepositoryType())){
                String result = uploadByGroupRep(request, repository, userName);
                if (result.startsWith("500")){
                    String[] split = result.split("-");
                    PypiResponse.errorToClient(resp,Integer.valueOf(split[0]),split[1]);
                    return;
                }
            }
            if (("remote").equals(repository.getRepositoryType())){
                logger.info("pypi上传制品，不能上传到代理库");
                PypiResponse.errorToClient(resp,500,"不能上传到代理库");
                return;
            }
        }else {
            logger.info("pypi推送，配置的仓库不存在");
            PypiResponse.errorToClient(resp,404,"仓库不存在");
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
            logger.info("pypi拉取推送错误："+e.getMessage());
            return Result.error(401,e.getMessage());
        }
    }

    /**
     * 下载元数据->通过本地库
     * @param repository  仓库
     * @param libraryName 制品名称
     */
    public String downloadMetadataByLocalRep(Repository repository,String libraryName){

        List<Library> libraryList = libraryService.findLibraryList(repository.getId(), libraryName);
        if (CollectionUtils.isEmpty(libraryList)){
            logger.info("pypi拉取，客户端配置为本地库且制品表数据不存在");
            return "404-客户端配置为本地库且制品表数据不存在";
        }

        List<String> libraryIds = libraryList.stream().map(Library::getId).collect(Collectors.toList());
        String[] localLibraryIdList = libraryIds.toArray(new String[libraryIds.size()]);
        List<LibraryPypi> libraryPypiList = libraryPypiService.findLibraryPypiList(new LibraryPypiQuery().setLibraryIds(localLibraryIdList));
        for (LibraryPypi libraryPypi:libraryPypiList){
            String metadataPath = yamlDataMaService.repositoryAddress() + "/" + libraryPypi.getMetadataPath();
            File file = new File(metadataPath);
            if (!file.exists()){
                continue;
            }
            String metadata = RepositoryUtil.readFile(file);
            return metadata;
        }
        logger.info("pypi拉取，客户端配置为本地库且制品文件file不存在");
        return "404-客户端配置为本地库且制品文件file不存在";
    }

    /**
     * 下载元数据->通过代理库
     * @param repository  仓库
     * @param libraryName 制品名称
     */
    public String downloadMetadataByRemoteRep(Repository repository,String libraryName){

        //拉取元数据不携带版本所以，防止拉取不到最新版本，所以即使远程库中存在元数据也需要转发到代理地址拉取
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRepId(repository.getId());
        if (CollectionUtils.isEmpty(remoteProxyList)){
            logger.info("pypi拉取，代理库没有关联代理地址");
            return "500-代理库没有关联代理地址";
        }

        String s = metadataForwardingRemote(remoteProxyList,libraryName);
        return s;
    }

    /**
     * 下载元数据->通过组合库
     * @param repository  仓库
     * @param libraryName 制品名称
     */
    public String downloadMetadataByGroupRep(Repository repository,String libraryName){
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
        List<Library> libraryList = libraryService.findLibraryByCondition(libraryName, "pypi", repIdList);

        //客户端拉取的制品不为空
        if (CollectionUtils.isNotEmpty(libraryList)){
            //客户端拉取的制品不为空且为本地库的
            List<String> localLibraryIds = libraryList.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType()))
                    .map(Library::getId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(localLibraryIds)){
                String[] localLibraryIdList = localLibraryIds.toArray(new String[localLibraryIds.size()]);
                List<LibraryPypi> libraryPypiList = libraryPypiService.findLibraryPypiList(new LibraryPypiQuery().setLibraryIds(localLibraryIdList));
                for (LibraryPypi libraryPypi:libraryPypiList){
                    String metadataPath = yamlDataMaService.repositoryAddress() + "/" + libraryPypi.getMetadataPath();
                    File file = new File(metadataPath);
                    if (!file.exists()){
                        continue;
                    }
                    String metadata = RepositoryUtil.readFile(file);
                    return metadata;
                }
            }
        }
        logger.info("pypi拉取，代理拉取元数据");
        //制品不存在或者存在远程库中。拉取元数据不携带版本所以，防止拉取不到最新版本，所以即使远程库中存在元数据也需要转发到代理地址拉取
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRpyIds(repIdList);
        if (CollectionUtils.isEmpty(remoteProxyList)){
            logger.info("pypi拉取，代理库没有关联代理地址");
            return "500-代理库没有关联代理地址";
        }

        //转发远程
        String s = metadataForwardingRemote(remoteProxyList,libraryName);
        return s;
    }



    /**
     * 下载包文件->通过本地库
     * @param repository  仓库
     * @param pathData 客户端请求报文件路径
     */
    public void downloadPackagesByLocalRep(Repository repository,String pathData,HttpServletResponse response){

        String fileName = StringUtils.substringAfterLast(pathData, "/");
        List<LibraryFile> libraryFiles = libraryFileService.findLibraryFiles(new LibraryFileQuery().setRepositoryId(repository.getId())
                .setFileName(fileName));

        if (CollectionUtils.isEmpty(libraryFiles)){
            logger.info("pypi拉取包文件，本地库文件表数据不存在");
            return;
        }
        String packagePath = yamlDataMaService.repositoryAddress() + "/" + libraryFiles.get(0).getFileUrl();
        File packageFile = new File(packagePath);
        if (!packageFile.exists()){
            logger.info("pypi拉取包文件，本地库文件不存在");
            return;
        }
        try {
            FileUtil.readFileData(packageFile,response);
            //byte[]  bytes = Files.readAllBytes(Paths.get(packagePath));
        } catch (IOException e) {
            logger.info("pypi拉取包文件，本地库读取文件失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载包文件->通过远程库
     * @param repository  仓库
     * @param pathData 客户端请求报文件路径
     */
    public void downloadPackagesByRemoteRep(Repository repository,String pathData,HttpServletResponse response){
        String fileName = StringUtils.substringAfterLast(pathData, "/");

        //通过仓库id、文件名字查询制品文件
        List<LibraryFile> libraryFiles = libraryFileService.findLibraryFiles(new LibraryFileQuery()
                .setFileName(fileName)
                .setRepositoryId(repository.getId()));

        if (CollectionUtils.isNotEmpty(libraryFiles)){
            String fileUrl = libraryFiles.get(0).getFileUrl();
            String filePath = yamlDataMaService.repositoryAddress() + "/" + fileUrl;
            File packageFile = new File(filePath);
            if (packageFile.exists()){
                try {
                     //Files.readAllBytes(Paths.get(filePath));
                     FileUtil.readFileData(packageFile,response);
                     return;
                } catch (IOException e) {
                    logger.info("pypi包文件拉取，配置远程库读取本地文件失败，走转发远程");
                }
            }
        }
        logger.info("pypi包文件拉取，走转发远程");
        //本地不存在去远程代理拉取
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRepId(repository.getId());
         packagesForwardingRemote(remoteProxyList, pathData,response);

         //写入表数据
        try {
            addDownloadLibraryData(repository,fileName);
        } catch (FileNotFoundException e) {
            logger.info("pypi远程拉取，写入表数据失败");
            e.printStackTrace();
        }
    }

    /**
     * 下载元数据数据->转发远程
     * @param remoteProxyList  代理地址
     */
    public String metadataForwardingRemote(List<RepositoryRemoteProxy> remoteProxyList,String libraryName){

        for (RepositoryRemoteProxy remoteProxy:remoteProxyList){
            String repoName = remoteProxy.getRepository().getName();
            String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
            try {
                if (!agencyUrl.endsWith("/")){
                    agencyUrl=agencyUrl+"/";
                }
                String path = agencyUrl + libraryName + "/";
                String data = restTemplateGet(path);
                // 替换 href 中的 /packages,携带制品库信息。方便拉取包文件的时候读取仓库数据
                String modifiedContent = data.replaceAll("href=\"([^\"]*)/packages", "href=\"$1/"+repoName+"/packages");

                //创建制品和pypi的元数据
                addDownloadMetadataLibraryData(remoteProxy.getRepository(),libraryName,modifiedContent);
                return modifiedContent;
            }catch (Exception e){
                logger.info("pypi拉取元数据失败，地址："+agencyUrl);
                e.printStackTrace();
                continue;
            }
        }
        return "500-调用代理地拉取失败";
    }

    /**
     * 下载包文件->转发远程
     * @param remoteProxyList  代理地址
     * @param pathData
     */
    public void packagesForwardingRemote(List<RepositoryRemoteProxy> remoteProxyList,String pathData,HttpServletResponse response){

        for (RepositoryRemoteProxy remoteProxy:remoteProxyList){
            String agencyUrl = remoteProxy.getRemoteProxy().getAgencyUrl();
            try {
                if (!agencyUrl.endsWith("/")){
                    agencyUrl=agencyUrl+"/";
                }
                if (agencyUrl.endsWith("/simple/")){
                    agencyUrl=agencyUrl.replace("/simple/","/");
                }
                //获取文件本地存储的位置
                String storePath = getStoreFilePathAtRemote(remoteProxy.getRepository(), pathData);
                String path = agencyUrl + pathData;

                //远程获取
                restTemplateGetByte(response,path,storePath);
                return;
            }catch (Exception e){
                e.printStackTrace();
                logger.info("pypi拉取报文件失败，地址："+agencyUrl);
                continue;
            }
        }

    }


    /**
     * 上传pypi->通过组合库
     * @param request  客户端端请求数据
     * @param repository 仓库
     * @param userName 用户
     */
    public String uploadByGroupRep(HttpServletRequest request,Repository repository,String userName){
        //查询组合库关联的仓库
        List<RepositoryGroup> groupList = groupService.findRepositoryGroupList(new RepositoryGroupQuery()
                .setRepositoryGroupId(repository.getId()));
        List<RepositoryGroup> localRepList = groupList.stream().filter(a -> ("local").equals(a.getRepository().getRepositoryType())).toList();

        //组合库未关联本地仓库。上传只能上传到本地制品库
        if (CollectionUtils.isEmpty(localRepList)){
            logger.info("pypi上传制品，未关联本地库");
            return "500-未关联本地库";
        }

        //上传到本地库
        return uploadByLocalRep(request,localRepList.get(0).getRepository(),userName);

    }

    /**
     * 上传pypi->通过本地库
     * @param request  客户端端请求数据
     * @param repository 仓库
     */
    public String uploadByLocalRep(HttpServletRequest request,Repository repository,String userName){
        //判断是否是multipart/form-data 类型
        if (ServletFileUpload.isMultipartContent(request)) {
            try {

                //创建解析器，结合 DiskFileItemFactory，用于配置文件存储的临时位置。
                // 调用 parseRequest(request) 方法，该方法会读取整个请求体，自动处理边界分隔符，提取各个部分的数据
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);


                //获取制品名字
                List<FileItem> nameList = items.stream().filter(item -> ("name").equals(item.getFieldName())).toList();
                String libraryName = nameList.get(0).getString();

                //获取流文件
                List<FileItem> fileItems = items.stream().filter(item -> !item.isFormField()).toList();
                String fileName = fileItems.get(0).getName();
                InputStream fileContent = fileItems.get(0).getInputStream();

                //写入文件
                String folderPath = yamlDataMaService.repositoryAddress() + "/"+repository.getId()+"/"+libraryName;
                FileUtil.copyFileData(fileContent,folderPath,fileName);

                //添加表数据
                addUploadLibraryData(items,repository,userName);
                return "200";
            } catch (Exception e) {
                e.printStackTrace();
                return "500-"+e.getMessage();
            }
        }
        return "500-客户端上传的文件类型不支持multipart/form-data";
    }


    /**
     * pypi上传 -> 添加制品表数据
     * @param items  客户端请求数据
     * @param repository 仓库
     * @param userName 推送用户
     */
    public void addUploadLibraryData(List<FileItem> items,Repository repository,String userName) throws Exception {
        //创建制品
        List<FileItem> nameList = items.stream().filter(item -> ("name").equals(item.getFieldName())).toList();
        String libraryName = nameList.get(0).getString();
        Library library = libraryService.createLibraryData(libraryName, "pypi", repository);

        //创建版本
        List<FileItem> versionList = items.stream().filter(item -> ("version").equals(item.getFieldName())).toList();
        String version = versionList.get(0).getString();
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setPusher(userName);
        libraryVersion.setLibrary(library);
        libraryVersion.setVersion(version);
        libraryVersion.setRepository(repository);
        libraryVersion.setLibraryType("pypi");
        String versionId = versionService.redactLibraryVersion(libraryVersion);
        libraryVersion.setId(versionId);

        //创建文件
        List<FileItem> fileItems = items.stream().filter(item -> !item.isFormField()).toList();
        String fileName = fileItems.get(0).getName();
        String filePath = yamlDataMaService.repositoryAddress() + "/"+repository.getId()+"/"+libraryName+"/"+fileName;
        File file = new File(filePath);
        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setRepository(repository);
        libraryFile.setLibraryVersion(libraryVersion);
        libraryFile.setFileName(fileName);
        String size = RepositoryUtil.formatSize(file.length());
        libraryFile.setFileSize(size);
        libraryFile.setSize(file.length());
        libraryFile.setFileUrl(repository.getId() + "/" + libraryName+"/"+fileName);
        libraryFile.setRelativePath(fileName);
        libraryFileService.redactLibraryFile(libraryFile);



        //写入元数据
        writeMetadata(repository ,libraryName,version,fileName);

        //添加元数据表
        LibraryPypi libraryPypi = new LibraryPypi();
        libraryPypi.setLibraryId(library.getId());
        libraryPypi.setMetadataPath(repository.getId()+"/"+libraryName+"/"+libraryName+".html");
        libraryPypiService.createLibraryPypi(libraryPypi);
    }

    /**
     * pypi远程下载 -> 添加制品表数据、元数据。防止拉不到新版本远程的元数据始终走代理拉取
     * @param repository 仓库
     * @param libraryName 制品名字
     */
    public void addDownloadMetadataLibraryData(Repository repository,String libraryName,String metadataData) throws FileNotFoundException {

        //创建制品
       libraryService.createLibraryData(libraryName, "pypi", repository);

        //拉取代理的制品元数据json没有写到本地hadess服务中
   /*     //创建pypi制品扩展数据
        LibraryPypi libraryPypi = new LibraryPypi();
        libraryPypi.setLibraryId(library.getId());
        libraryPypi.setMetadataPath(repository.getId() + "/" + libraryName+"/"+libraryName);
        libraryPypiService.createLibraryPypi(libraryPypi);

        //写入元数据文件
        String folderPath = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName;
        InputStream inputStream = new ByteArrayInputStream(metadataData.getBytes());
        RepositoryUtil.copyFileData(inputStream,folderPath,libraryName);*/
    }


    /**
     * pypi远程下载 -> 添加制品表数据
     * @param repository 仓库
     * @param fileName 文件名
     */
    public void addDownloadLibraryData(Repository repository,String fileName) throws FileNotFoundException {

        String libraryName;
        String version;
        if (fileName.endsWith(".tar.gz")){
            String before = StringUtils.substringBefore(fileName, ".tar.gz");
            version=StringUtils.substringAfterLast(before, "-");
            libraryName=StringUtils.substringBeforeLast(before, "-");
        }else {
            //这是以 .whl结尾的
             libraryName = StringUtils.substringBefore(fileName, "-");
            String after = StringUtils.substringAfter(fileName, "-");
            version = StringUtils.substringBefore(after, "-");
        }

        //创建制品
        Library library = libraryService.createLibraryData(libraryName, "pypi", repository);

        //创建版本
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setPusher("central warehouse");
        libraryVersion.setLibrary(library);
        libraryVersion.setVersion(version);
        libraryVersion.setRepository(repository);
        libraryVersion.setLibraryType("pypi");
        String versionId = versionService.redactLibraryVersion(libraryVersion);
        libraryVersion.setId(versionId);

        //创建文件
        String filePath = yamlDataMaService.repositoryAddress() + "/"+repository.getId()+"/"+libraryName+"/"+fileName;
        File file = new File(filePath);

        LibraryFile libraryFile = new LibraryFile();
        libraryFile.setLibrary(library);
        libraryFile.setRepository(repository);
        libraryFile.setLibraryVersion(libraryVersion);
        libraryFile.setFileName(fileName);
        String size = RepositoryUtil.formatSize(file.length());
        libraryFile.setFileSize(size);
        libraryFile.setSize(file.length());
        libraryFile.setFileUrl(repository.getId() + "/" + libraryName+"/"+fileName);
        libraryFile.setRelativePath(fileName);
        libraryFileService.redactLibraryFile(libraryFile);

        //写入文件
        InputStream inputStream =new FileInputStream(file);
        String folderPath = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName;
        FileUtil.copyFileData(inputStream,folderPath,fileName);
    }



    /**
     * pypi远程下载 -> 获取文件本地存储地址
     * @param pathData 客户端请求路径
     */
   public String getStoreFilePathAtRemote(Repository repository,String pathData) throws IOException {
       String fileName = StringUtils.substringAfterLast(pathData, "/");
       String libraryName;
       if (fileName.endsWith(".tar.gz")){
           String before = StringUtils.substringBefore(fileName, ".tar.gz");
           libraryName=StringUtils.substringBeforeLast(before, "-");
       }else {
           //这是以 .whl结尾的
           libraryName = StringUtils.substringBefore(fileName, "-");
       }
       String path = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + libraryName ;

       // 规范化路径并创建目录（如果不存在）
       Path dirPath = Paths.get(path);
       if (!Files.exists(dirPath)) {
           Files.createDirectories(dirPath);
       }
       return dirPath+ "/" +fileName;
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
     * 转发远程获取
     * @param relativeAbsoluteUrl 地址
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
     * 上传pypi，解析html并写入元数据
     * @param repository 仓库
     * @param  libraryName 制品名字
     * @param version 版本
     * @param fileName 文件名字
     */
    public void writeMetadata(Repository repository,String libraryName,
                              String version,String fileName) throws Exception {

        String folderPath=yamlDataMaService.repositoryAddress() + "/"+repository.getId()+"/"+libraryName;

        String filePath = folderPath + "/" + libraryName+".html";
        File file = new File(filePath);
        Document document;
        if (file.exists()){
             document = Jsoup.parse(file, "UTF-8");
        }else {
            //InputStream inputStream = new ByteArrayInputStream(getHtml().getBytes());
            document = Jsoup.parse(getHtml());
        }
        // 修改 <title> 内容
        Element title = document.selectFirst("title");
        title.text("Links for "+libraryName);


        // 修改 <h1> 内容
        Element h1 = document.selectFirst("h1");
        h1.text("Links for "+libraryName);

        // 添加新的 <a> 标签到 <body>
        Element body = document.body();


        // 检查是否已经存在相同内容的 <a> 标签
        boolean linkExists = document.select("a:contains(" + fileName + ")").size() > 0;
        if (!linkExists) {
            body.appendChild(new Element("br")); // 添加换行
            String href="../../"+repository.getName()+"/packages/"+libraryName+"/"+version+"/"+fileName;
            Element newLink = new Element("a").attr("href", href).text(fileName);
            body.appendChild(newLink);
        }

        // 将 ByteArrayOutputStream 的内容写入文件
        String html = document.html();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes());
        FileUtil.copyFileData(inputStream,folderPath,libraryName+".html");
    }
    public static String getHtml(){
        return  """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <title>Links for tensorflow</title>
                </head>
                <body>
                    <h1>Links for hadess</h1>
                </body>
                </html>
                """;
    }


}
