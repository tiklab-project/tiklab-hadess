package io.tiklab.hadess.library.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import io.tiklab.core.exception.SystemException;
import io.tiklab.hadess.common.FileUtil;
import io.tiklab.hadess.common.HadessFinal;
import io.tiklab.hadess.common.RepositoryUtil;
import io.tiklab.hadess.common.XpackYamlDataMaService;
import io.tiklab.hadess.library.dao.LibraryFileDao;
import io.tiklab.hadess.library.entity.LibraryFileEntity;
import io.tiklab.hadess.library.model.LibraryFile;
import io.tiklab.hadess.library.model.LibraryFileQuery;
import io.tiklab.hadess.library.model.LibraryVersion;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxy;
import io.tiklab.hadess.repository.service.NetworkProxyService;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.join.JoinTemplate;

import org.apache.commons.collections.CollectionUtils;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* LibraryFileServiceImpl-制品文件
*/
@Service
public class LibraryFileServiceImpl implements LibraryFileService {
    private static Logger logger = LoggerFactory.getLogger(LibraryFileServiceImpl.class);
    @Autowired
    LibraryFileDao libraryFileDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Autowired
    XpackYamlDataMaService xpakYamlDataMaService;

    @Autowired
    NetworkProxyService networkProxyService;

    @Override
    public String createLibraryFile(@NotNull @Valid LibraryFile libraryFile) {
        LibraryFileEntity libraryFileEntity = BeanMapper.map(libraryFile, LibraryFileEntity.class);
        libraryFileEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return libraryFileDao.createLibraryFile(libraryFileEntity);
    }

    @Override
    public void updateLibraryFile(@NotNull @Valid LibraryFile libraryFile) {
        LibraryFileEntity libraryFileEntity = BeanMapper.map(libraryFile, LibraryFileEntity.class);

        libraryFileDao.updateLibraryFile(libraryFileEntity);
    }

    @Override
    public void deleteLibraryFile(@NotNull String id) {
        libraryFileDao.deleteLibraryFile(id);
    }

    @Override
    public void deleteLibraryFile(String versionId, String snapshotVersion) {
        DeleteCondition libraryFile = DeleteBuilders.createDelete(LibraryFileEntity.class)
                .eq("libraryVersionId",versionId)
                .eq("snapshotVersion",snapshotVersion)
                .get();
        libraryFileDao.deleteLibraryFile(libraryFile);

        List<LibraryFile> libraryFiles = this.findLibraryFiles(new LibraryFileQuery().setLibraryVersionId(versionId));
         libraryFiles = libraryFiles.stream().filter(a -> !a.getFileName().contains("maven-metadata.xml")).collect(Collectors.toList());
        //该版本不存在制品文件 删除该版本
        if (CollectionUtils.isEmpty(libraryFiles)){
            libraryVersionService.deleteOnlyVersion(versionId);

            //删除maven-metadata.xml 数据
            DeleteCondition file = DeleteBuilders.createDelete(LibraryFileEntity.class)
                    .eq("libraryVersionId",versionId)
                    .get();
            libraryFileDao.deleteLibraryFile(file);
        }

    }

    @Override
    public void deleteLibraryFileByCondition(String field,String value) {
        List<LibraryFile> libraryFileList=null;
        switch (field){
            case "libraryVersionId":
                 libraryFileList = this.findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(value));
                 break;
            case "libraryId":
                libraryFileList = this.findLibraryFileList(new LibraryFileQuery().setLibraryId(value));
                break;
            case "repositoryId":
                libraryFileList = this.findLibraryFileList(new LibraryFileQuery().setRepositoryId(value));
        }
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            String type = libraryFileList.get(0).getRepository().getType();
            //制品为docker 特殊处理
            if (("docker").equals(type)){
                //通过制品库id 和文件名称查询
                List<LibraryFile> FileList = this.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryFileList.get(0).getLibrary().getId())
                        .setFileName(libraryFileList.get(0).getFileName()));
                if (FileList.size()>1){
                    DeleteCondition libraryFile = DeleteBuilders.createDelete(LibraryFileEntity.class)
                            .eq(field,value)
                            .get();
                    libraryFileDao.deleteLibraryFile(libraryFile);
                    return;
                }
            }

            //删除文件
            for (LibraryFile libraryFile:libraryFileList){
                File file = new File(xpakYamlDataMaService.repositoryAddress()+"/"+libraryFile.getFileUrl());
                file.delete();
            }
            DeleteCondition libraryFile = DeleteBuilders.createDelete(LibraryFileEntity.class)
                    .eq(field,value)
                    .get();
            libraryFileDao.deleteLibraryFile(libraryFile);
        }
    }

    @Override
    public LibraryFile findOne(String id) {
        LibraryFileEntity libraryFileEntity = libraryFileDao.findLibraryFile(id);

        LibraryFile libraryFile = BeanMapper.map(libraryFileEntity, LibraryFile.class);
        return libraryFile;
    }

    @Override
    public List<LibraryFile> findList(List<String> idList) {
        List<LibraryFileEntity> libraryFileEntityList =  libraryFileDao.findLibraryFileList(idList);

        List<LibraryFile> libraryFileList =  BeanMapper.mapList(libraryFileEntityList,LibraryFile.class);
        return libraryFileList;
    }

    @Override
    public LibraryFile findLibraryFile(@NotNull String id) {
        LibraryFile libraryFile = findOne(id);

        joinTemplate.joinQuery(libraryFile);

        return libraryFile;
    }

    @Override
    public List<LibraryFile> findAllLibraryFile() {
        List<LibraryFileEntity> libraryFileEntityList =  libraryFileDao.findAllLibraryFile();

        List<LibraryFile> libraryFileList =  BeanMapper.mapList(libraryFileEntityList,LibraryFile.class);


        joinTemplate.joinQuery(libraryFileList);

        return libraryFileList;
    }

    @Override
    public List<LibraryFile> findLibraryFileList(LibraryFileQuery libraryFileQuery) {
        List<LibraryFileEntity> libraryFileEntityList = libraryFileDao.findLibraryFileList(libraryFileQuery);
        List<LibraryFile> libraryFileList = BeanMapper.mapList(libraryFileEntityList,LibraryFile.class);
        joinTemplate.joinQuery(libraryFileList);

        List<LibraryFile> libraryFiles = libraryFileList.stream().sorted(Comparator.comparing(LibraryFile::getFileName)).collect(Collectors.toList());
        return libraryFiles;
    }

    @Override
    public List<LibraryFile> findLibraryFileByRep(LibraryFileQuery libraryFileQuery) {
        List<LibraryFileEntity> libraryFileEntityList = libraryFileDao.findLibraryFileByRep(libraryFileQuery);
        List<LibraryFile> libraryFileList = BeanMapper.mapList(libraryFileEntityList,LibraryFile.class);

        return libraryFileList;

    }

    @Override
    public List<LibraryFile> findLibraryFiles(LibraryFileQuery libraryFileQuery) {
        List<LibraryFileEntity> libraryFileEntityList = libraryFileDao.findLibraryFileList(libraryFileQuery);
        List<LibraryFile> libraryFileList = BeanMapper.mapList(libraryFileEntityList,LibraryFile.class);
        return libraryFileList;
    }

    @Override
    public List<LibraryFile> findLibraryFileList(String libraryId) {
        List<LibraryFileEntity> libraryFileEntityList = libraryFileDao.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryId));

        List<LibraryFile> libraryFileList = BeanMapper.mapList(libraryFileEntityList,LibraryFile.class);

        return libraryFileList;
    }

    @Override
    public Pagination<LibraryFile> findLibraryFilePage(LibraryFileQuery libraryFileQuery) {
        Pagination<LibraryFileEntity>  pagination = libraryFileDao.findLibraryFilePage(libraryFileQuery);

        List<LibraryFile> libraryFileList = BeanMapper.mapList(pagination.getDataList(),LibraryFile.class);

        joinTemplate.joinQuery(libraryFileList);

        return PaginationBuilder.build(pagination,libraryFileList);
    }



    @Override
    public List<LibraryFile> findLibraryNewFileList(LibraryFileQuery libraryFileQuery) {
        LibraryVersion libraryVersion = libraryVersionService.findOne(libraryFileQuery.getLibraryVersionId());
        List<LibraryFile> libraryFileList = findLibraryFiles(new LibraryFileQuery()
                .setLibraryVersionId(libraryVersion.getId()).setFindNameWay("like").setFileName(libraryFileQuery.getFileName()));
       //快照版本
        if (libraryVersion.getVersion().toUpperCase().endsWith("SNAPSHOT")){
            List<LibraryFile> collected = libraryFileList.stream().filter(b -> !b.getFileUrl().contains("maven-metadata.xml")).collect(Collectors.toList());
            List<LibraryFile> libraryFiles = collected.stream().sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(libraryFiles)){

                //客户端上传了快照版本时间戳
                String snapshotVer = libraryFileQuery.getSnapshotVersion();
                if(StringUtils.isNotEmpty(snapshotVer)){
                   libraryFileList = libraryFiles.stream().filter(a -> (snapshotVer).equals(a.getSnapshotVersion())).collect(Collectors.toList());

                }else {
                    String snapshotVersion = libraryFiles.get(0).getSnapshotVersion();
                    libraryFileList = collected.stream().filter(a -> (snapshotVersion).equals(a.getSnapshotVersion())).collect(Collectors.toList());
                }
            }
       }
        //处理docker 特殊情况
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            LibraryFile libraryFile = libraryFileList.get(0);
            String type = libraryFileList.get(0).getRepository().getType();
            if (("docker").equals(type)){
                List<LibraryFile> libraryFiles = dockerFile(libraryFile);
                libraryFileList = Stream.concat(libraryFileList.stream(), libraryFiles.stream()).collect(Collectors.toList());
                //通过名字过滤相同
                 libraryFileList = libraryFileList.stream()
                        .filter(distinctByKey(LibraryFile::getFileName))
                        .collect(Collectors.toList());
            }
        }

        return libraryFileList;
    }

    /**
     *  制品文件创建、修改
     *  @param libraryFile     制品文件
     * @return
     */
    public String redactLibraryFile(LibraryFile libraryFile){
        String fileId;

        LibraryFileQuery libraryFileQuery = new LibraryFileQuery();
        libraryFileQuery.setLibraryId(libraryFile.getLibrary().getId());
        libraryFileQuery.setFileName(libraryFile.getFileName());
        if (!ObjectUtils.isEmpty(libraryFile.getLibraryVersion())){
            libraryFileQuery.setLibraryVersionId(libraryFile.getLibraryVersion().getId());
        }

        List<LibraryFile> libraryFileList = this.findLibraryFileList(libraryFileQuery);
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            libraryFile.setId(libraryFileList.get(0).getId());
            this.updateLibraryFile(libraryFile);
            fileId=libraryFile.getId();
        }else {
             fileId = this.createLibraryFile(libraryFile);
        }
        return fileId;
    }

    @Override
    public List<LibraryFile> findLibraryLikeFileUrl(String fileUrl) {
        List<LibraryFileEntity> libraryLikeFileUrl = libraryFileDao.findLibraryLikeFileUrl(fileUrl);

        List<LibraryFile> libraryFileList = BeanMapper.mapList(libraryLikeFileUrl,LibraryFile.class);

        return libraryFileList;
    }

    @Override
    public List<LibraryFile> findFileByReAndLibraryAndVer(String repositoryId, String libraryName, String version) {
        List<LibraryFileEntity> libraryFileEntity = libraryFileDao.findFileByReAndLibraryAndVer(repositoryId, libraryName, version);
        List<LibraryFile> libraryFileList = BeanMapper.mapList(libraryFileEntity,LibraryFile.class);

        return libraryFileList;
    }
    @Override
    public List<LibraryFile> findFileByReAndLibraryAndVer(String[] repositoryId, String libraryName, String version) {
        List<LibraryFileEntity> libraryFileEntity = libraryFileDao.findFileByReAndLibraryAndVer(repositoryId, libraryName, version);
        List<LibraryFile> libraryFileList = BeanMapper.mapList(libraryFileEntity,LibraryFile.class);

        return libraryFileList;
    }

    /**
     * 查询docker 文件
     * @param libraryFile  制品库id
     * @return Pagination <LibraryFileEntity>
     */
    public List<LibraryFile> dockerFile(LibraryFile libraryFile){
        List<String> arrayList = new ArrayList<>();

        String address = xpakYamlDataMaService.repositoryAddress();
        String filePath = address + "/" + libraryFile.getFileUrl();
        if (!new File(filePath).exists()){
            throw  new SystemException(HadessFinal.NOT_FOUNT_EXCEPTION,"文件不存在");
        }
        String readFile = RepositoryUtil.readFile(new File(filePath));

        JSONObject allData = JSONObject.parseObject(readFile);
       if (ObjectUtils.isEmpty(allData)){
           return null;
       }
        JSONObject config = (JSONObject) allData.get("config");
        String digest = config.get("digest").toString();

        arrayList.add(digest);
        for (JSONObject layers : (List<JSONObject>) allData.get("layers")) {
            String o = layers.get("digest").toString();
            arrayList.add(o);
        }

        String[] fileName = new String[arrayList.size()];
        String[] strings = arrayList.toArray(fileName);
        List<LibraryFileEntity> listByNames = libraryFileDao.findLibraryFileListByNames(strings);

        List<LibraryFile> libraryFiles = BeanMapper.mapList(listByNames, LibraryFile.class);
        List<LibraryFile> collected = libraryFiles.stream().map(a -> {
            a.setLibraryVersion(libraryFile.getLibraryVersion());
            return a;
        }).collect(Collectors.toList());
        joinTemplate.joinQuery(collected);
        return collected;
    }


    @Override
    public List<LibraryFile> findLibraryFileList(String[] repositoryIds,String relativePath) {
        List<LibraryFileEntity> fileByLibraryId = libraryFileDao.findLibraryFileList(repositoryIds,relativePath);
        List<LibraryFile> libraryFileList = BeanMapper.mapList(fileByLibraryId,LibraryFile.class);

        return libraryFileList;
    }

    @Override
    public String readLibraryFileData(LibraryFileQuery libraryFileQuery) {

        String address = xpakYamlDataMaService.repositoryAddress();
        String filePath = address + "/" + libraryFileQuery.getFileUrl();
        File file = new File(filePath);
        if (!file.exists()){
            String fileName = StringUtils.substringAfterLast(libraryFileQuery.getFileUrl(),"/");
            throw new SystemException(HadessFinal.NOT_FOUNT_EXCEPTION,fileName+"文件不存在");
        }
        String data = RepositoryUtil.readFile(file);
        return data;
    }

    @Override
    public List<String> findDockerLayers(LibraryFileQuery libraryFileQuery) {
        List<String> resultList = new ArrayList<>();
        // 创建 Docker 客户端
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        try {
            String address = xpakYamlDataMaService.repositoryAddress();
            String manifestFilePath = address + "/" + libraryFileQuery.getFileUrl();

            // 读取 manifest.json 文件
            File manifestFile = new File(manifestFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> configData = objectMapper.readValue(manifestFile, Map.class);

            // 获取第一个镜像的配置文件的哈希值
            Map<String,Object> data = (Map) configData.get("config");
            String configDigest = data.get("digest").toString();

            //查询配置文件的路径
            List<LibraryFile> fileList = this.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryFileQuery.getLibraryId()).setFileName(configDigest));
            if (CollectionUtils.isNotEmpty(fileList)){
                LibraryFile libraryFile = fileList.get(0);

                // 读取配置文件
                String confPath = address + "/" + libraryFile.getFileUrl();
                File file = new File(confPath);
                String fileData=null;
                if (!file.exists()){
                    Repository repository = libraryFile.getRepository();

                    if (("remote").equals(repository.getRepositoryType())){
                        List<RepositoryRemoteProxy> remoteProxyList = remoteProxyService.findAgencyByRepId(repository.getId());
                         fileData = readRemoteData(remoteProxyList, libraryFile);
                    }
                }else {
                     fileData = RepositoryUtil.readFile(file);
                }

                if (!ObjectUtils.isEmpty(fileData)){
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(fileData);
                    List<Map> history = objectMapper.convertValue(jsonNode.get("history"), List.class);
                  /*  // 将 JSON 字符串转换为 Map
                    Map<String, Object> config = mapper.readValue(fileData, Map.class);
                    // 获取 history 字段
                    List<Map<String, Object>> history = (List<Map<String, Object>>) config.get("history");*/
                    for (int i = 0; i < history.size(); i++) {
                        Map<String, Object> item = history.get(i);
                        String command = item.get("created_by").toString();
                        if (command.startsWith("/bin/sh -c #(nop)")){
                             command = command.replace("/bin/sh -c #(nop)", "");
                        }
                        resultList.add(command);
                    }
                }
            }
            // 关闭 Docker 客户端
            dockerClient.close();
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
           throw new SystemException(HadessFinal.SYSTEM_EXCEPTION,"读取文件失败") ;
        }
    }

    public static <T> java.util.function.Predicate<T> distinctByKey(java.util.function.Function<? super T, ?> keyExtractor) {
        java.util.Map<Object, Boolean> seen = new java.util.concurrent.ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 转发远程读取数据
     * @param remoteProxyList 代理地址
     * @param libraryFile 文件信息
     */
    public String readRemoteData(List<RepositoryRemoteProxy> remoteProxyList,LibraryFile libraryFile ){
        String fileData=null;

        //存储到本地的地址
        String confPath = xpakYamlDataMaService.repositoryAddress() + "/" + libraryFile.getFileUrl();

        String s1 = StringUtils.substringAfter(libraryFile.getFileUrl(), "/");
        String imageName = StringUtils.substringBefore(s1, "/blobs/");
        //判断镜像是否有仓库名，没有就添加默认的仓库名library
        if (!imageName.contains("/")){
            s1="library/" +s1;
            imageName="library/"+imageName;
        }
        for (int i=0;i<=remoteProxyList.size();i++){
            String path = remoteProxyList.get(i).getRemoteProxy().getAgencyUrl() + "/v2/"+s1;

            //获取token
            String token = getDockerToken(imageName);

            try {
                byte[] bytes = restTemplateGetByte(path, token);
                 fileData = new String(bytes);
                //读取后写入
                InputStream inputStream = new ByteArrayInputStream(bytes);
                String s = StringUtils.substringBeforeLast(confPath, "/");
                FileUtil.copyFileData(inputStream,s,libraryFile.getFileName());

                return fileData;
            } catch (Exception e) {
                if (("写入数据失败").equals(e.getMessage())){
                    throw new SystemException(HadessFinal.WRITE_EXCEPTION,e.getMessage()) ;
                }
                //一直循环到最后一个代理地址仍然报错
                if(i+1==remoteProxyList.size()){
                    throw new SystemException(HadessFinal.READ_REMOTE_EXCEPTION,"访问地址："+path+"失败") ;
                }
            }
        }
        return fileData;

    }

    /**
     * 获取docker的token
     * @param imageName imageName
     */
    public String getDockerToken(String imageName){
        String tokenUrl =HadessFinal.DOCKER_TOKEN+"?service=registry.docker.io&scope=repository:"+imageName+ ":pull";
        //String tokenUrl = "https://auth.docker.io/token?service=registry.docker.io&scope=repository:" +imageName+ ":pull";

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
    public byte[] restTemplateGetByte(String relativeAbsoluteUrl,String token) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(RepositoryUtil.getNetworkProxy(networkProxyService));
        HttpHeaders newHeaders = new HttpHeaders();
        //token不为空的时候为获取manifest数据
        if (!ObjectUtils.isEmpty(token)){
            newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            newHeaders.set("Accept", "application/vnd.docker.distribution.manifest.v2+json");
        }

        ResponseEntity<byte[]> exchange = restTemplate.exchange(relativeAbsoluteUrl, HttpMethod.GET,
                new HttpEntity<>(newHeaders), byte[].class);
        byte[] body = exchange.getBody();
        return body;
    }


}