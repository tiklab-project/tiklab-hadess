package io.tiklab.hadess.upload.service;

import io.tiklab.core.Result;
import io.tiklab.core.exception.SystemException;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.model.*;
import io.tiklab.hadess.library.service.*;
import io.tiklab.hadess.repository.model.*;
import io.tiklab.hadess.repository.service.RepositoryGroupService;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
import io.tiklab.hadess.repository.service.RepositoryService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoUploadServiceImpl implements GoUploadService{
    private static Logger logger = LoggerFactory.getLogger(GoUploadServiceImpl.class);

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RepositoryGroupService groupService;
    
    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Autowired
    XpackYamlDataMaService yamlDataMaService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryVersionService versionService;

    @Autowired
    LibraryFileService libraryFileService ;



    private Object lock = new Object(); // 创建一个锁对象
    @Override
    public Result<byte[]> pullData(String pathData) {

        //制品
        String repositoryName = StringUtils.substringBefore(pathData, "/");
        Repository repository = repositoryService.findRepositoryByName(repositoryName);
        if (ObjectUtils.isEmpty(repository)){
            return Result.error(404,"Repository not found");
        }

        String path = StringUtils.substringAfter(pathData, "/");

        //客户端配置的源为本地库
        if (("local").equals(repository.getRepositoryType())){
            return Result.error(404,"配置的仓库源不能为local库，请修改为远程库或者组合库");
        }


        //客户端配置的拉取源为远程库
        if (("remote").equals(repository.getRepositoryType())){
            return this.analysisRemote(repository,path);
        }

        //客户端配置的源为组合库
        if (("group").equals(repository.getRepositoryType())){
            return this.analysisGroup(repository,path);
        }

        return Result.error(404,"Repository not found");
    }



    /**
     * 转发到远程的代理
     * @param repository 远程库
     * @param path  转发的路径
     */
    public Result<byte[]> analysisRemote(Repository repository,String path) {
        //获取go包文件版本信息。直接走代理
        if (!path.endsWith("/list")){
            String[] rpyIds = {repository.getId()};
            List<LibraryFile> fileList = libraryFileService.findLibraryFileList(rpyIds, path);
            //文件存在直接去本地拉取返回
            if (CollectionUtils.isNotEmpty(fileList)){
                Result result = this.localPull(fileList.get(0), path);
                if (result.getCode()==0){
                    return result;
                }
            }
        }

        logger.info("go转发远程拉取地址："+path);
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findRepositoryRemoteProxyList(new RepositoryRemoteProxyQuery().setRepositoryId(repository.getId()));
        if (CollectionUtils.isEmpty(remoteProxyList))  {
            return Result.error(400,"没有配置代理地址");
        }
        List<String> proxyPathList = remoteProxyList.stream().map(a -> a.getRemoteProxy().getAgencyUrl())
                .distinct().collect(Collectors.toList());
        Result<byte[]> result = restTemplateMethod(proxyPathList, path, 0);

        // 写入数据
        if (result.getCode()==0){
            writeFile(repository,path,result.getData());

            //创建文件记录
            createLibraryInfo(repository,path);
        }
        return result;
    }

    /**
     * 解析组合库
     * @param repository 组合库
     * @param path  转发的路径
     */
    public Result analysisGroup(Repository repository,String path){
        List<RepositoryGroup> groupList = groupService.findRepositoryGroupList(new RepositoryGroupQuery().setRepositoryGroupId(repository.getId()));
        if (CollectionUtils.isEmpty(groupList)){
            return Result.error(404,"组合库未关联库");
        }

        //获取go包文件版本信息。直接走代理
        if (path.endsWith("/list")){
            return joinGroupProxy(groupList,repository,path);
        }

        //查询制品文件是否存在
        List<String> stringList = groupList.stream().map(a -> a.getRepository().getId()).collect(Collectors.toList());
        String[] rpyIds = stringList.toArray(new String[stringList.size()]);
        List<LibraryFile> fileList = libraryFileService.findLibraryFileList(rpyIds, path);

        //本地不存在走代理拉取
        if (CollectionUtils.isEmpty(fileList)){
            return joinGroupProxy(groupList,repository,path);
        }

        //存在直接本地拉取
        Result result = localPull(fileList.get(0), path);

        //存在记录，不存在文件直接走代理
        if (result.getCode()==404){
            return joinGroupProxy(groupList,repository,path);
        }
        return result;
    }

    /**
     * 本地拉取
     * @param libraryFile 制品文件
     * @param path  路径
     */
    public Result localPull(LibraryFile libraryFile,String path){
        String filePath = yamlDataMaService.repositoryAddress() + "/" + libraryFile.getRepository().getId() + "/" + path;
        try {
            File file = new File(filePath);
            if (!file.exists()){
                logger.info("读取go文件"+path+"存在记录不存在文件，方式：本地拉取");
                return Result.error(404,"文件不存在");
            }
            logger.info("读取go文件"+path+"方式：本地拉取");
            byte[] bytes = RepositoryUtil.readFileByte(file);
            return  Result.ok(bytes);
        } catch (IOException e) {
            logger.info("读取go文件："+filePath+"失败");
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * joinProxy  拼接代理地址 转发远程拉取
     * @param groupList 代理地址
     * @param repository  转发的路径
     */
    public Result joinGroupProxy(List<RepositoryGroup> groupList,Repository repository,String path ){
        logger.info("读取go文件"+path+"方式：代理拉取");
        //通过组合库中所有代理库id的集合查询代理地址
        List<String> remoteRpyIds = groupList.stream().map(RepositoryGroup::getRepository)
                .filter(a -> ("remote").equals(a.getRepositoryType()))
                .map(Repository::getId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(remoteRpyIds)){
            logger.info("组合库："+repository.getName()+"  没有关联代理库");
            return Result.error(400,repository.getName()+"  没有关联代理库");
        }

        //远程仓库ids
        String[] repositoryIds = remoteRpyIds.toArray(new String[remoteRpyIds.size()]);
        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRpyIds(repositoryIds);
        if (CollectionUtils.isEmpty(remoteProxyList)){
            logger.info("组合库："+repository.getName()+"  关联的代理库没有配置代理地址");
            return Result.error(400,"组合库："+repository.getName()+"  关联的代理库没有配置代理地址");
        }

        //转发远程拉取
        List<String> proxyPathList = remoteProxyList.stream().map(a -> a.getRemoteProxy().getAgencyUrl())
                .distinct().collect(Collectors.toList());
        Result<byte[]> result = restTemplateMethod(proxyPathList, path, 0);

        // 写入数据
        if (result.getCode()==0){
            //获取走的是哪代理的仓库
            List<RepositoryRemoteProxy> collected = remoteProxyList.stream().filter(a -> (result.getMsg()).equals(a.getRemoteProxy().getAgencyUrl())).collect(Collectors.toList());
            Repository rpy = collected.get(0).getRepository();
            writeFile(rpy,path,result.getData());
            //创建文件记录
            createLibraryInfo(rpy,path);
        }
        return result;
    }


    /**
     * template  拉取
     * @param proxyPathList 代理地址
     * @param path  转发的路径
     */
    public Result<byte[]> restTemplateMethod(List<String> proxyPathList,String path, Integer index) {
        String agencyUrl = proxyPathList.get(index);
        if (!agencyUrl.endsWith("/")){
            agencyUrl=agencyUrl+"/";
        }

        //完整路径
        String fullPath = agencyUrl + path;
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> entity = restTemplate.getForEntity(fullPath, byte[].class);
            int codeValue = entity.getStatusCodeValue();
            if (codeValue==200){
                return Result.ok(entity.getBody(),agencyUrl);
            }
            return Result.error(400,"拉取错误");
        } catch (Exception e) {
            if (index+1<proxyPathList.size()){
                String s = proxyPathList.get(index + 1);
                logger.info("go转发远程拉取失败："+fullPath+"。执行其他代理拉取："+s);
                restTemplateMethod(proxyPathList,path,index + 1);
            }else {
                logger.info("go转发远程拉取："+path+" ；失败："+e.getMessage());
                return Result.error(404,"制品不存在");
            }
        }
        return Result.error(400,"go远程拉取失败");
    }

    /**
     * 写入文件信息到服务器
     * @param repository repository
     * @param path  转发的路径
     * @param data 内容
     */
    public void writeFile(Repository repository,String path,byte[] data) {
        try {
            //创建文件
            String filePath = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + path;
            File file = new File(filePath);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }

            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(data);
     /*       BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(data);*/
            fos.close();
        }catch (IOException e){
            logger.info("获取go文件后创建文件： "+path+" 失败："+e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }
    /**
     * 创建go制品信息
     * @param repository repository
     * @param path  转发的路径
     */
    public void createLibraryInfo(Repository repository,String path) {
        String packageName  = StringUtils.substringBefore(path, "/@v");


        synchronized (lock) {
            try {

                //创建制品
                Library library = libraryService.createLibraryData(packageName,"go",repository);


                String filePath = yamlDataMaService.repositoryAddress() + "/" + repository.getId() + "/" + path;
                File file = new File(filePath);
                //以.info、.zip、.mod 结尾的请求 是拉取版本数据
                if ((path.endsWith(".info")||path.endsWith(".zip")||path.endsWith(".mod"))){
                    String endPath  = StringUtils.substringAfterLast(path, "@v/");
                    String version = StringUtils.substringBeforeLast(endPath, ".");
                    LibraryVersion libraryVersion = new LibraryVersion();
                    libraryVersion.setRepository(repository);
                    libraryVersion.setLibrary(library);
                    libraryVersion.setPusher("系统");
                    libraryVersion.setVersion(version);
                    libraryVersion.setHash(RepositoryUtil.getSHA256ByPath(filePath));
                    //libraryVersion.setSize(file.length());
                    libraryVersion.setLibraryType("go");
                    String versionId = versionService.redactLibraryVersion(libraryVersion);
                    libraryVersion.setId(versionId);

                    //创建制品文件
                    LibraryFile libraryFile = new LibraryFile();
                    libraryFile.setRepository(repository);
                    libraryFile.setLibrary(library);
                    libraryFile.setLibraryVersion(libraryVersion);
                    libraryFile.setFileName(endPath);
                    libraryFile.setFileUrl(repository.getId()+"/"+path);
                    libraryFile.setRelativePath(path);
                    libraryFile.setFileSize(RepositoryUtil.formatSize(file.length()));
                    libraryFile.setSize(file.length());
                    libraryFileService.redactLibraryFile(libraryFile);
                }
            }finally {
                lock.notify();
            }
        }
    }
}
