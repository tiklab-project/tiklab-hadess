package io.tiklab.hadess.library.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import io.tiklab.core.exception.SystemException;
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
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.join.JoinTemplate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
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
    XpackYamlDataMaService xpakYamlDataMaService;

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
     *  @param versionId   制品版本id
     * @return
     */
    public void redactLibraryFile(LibraryFile libraryFile,String versionId){
        LibraryVersion libraryVersion = new LibraryVersion();
        libraryVersion.setId(versionId);
        libraryFile.setLibraryVersion(libraryVersion);

        List<LibraryFile> libraryFileList = this.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryFile.getLibrary().getId()).setLibraryVersionId(versionId)
                .setFileName(libraryFile.getFileName()));
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            libraryFile.setId(libraryFileList.get(0).getId());
            this.updateLibraryFile(libraryFile);
        }else {
            this.createLibraryFile(libraryFile);
        }
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
                String s = RepositoryUtil.readFile(new File(confPath));
                if (!ObjectUtils.isEmpty(s)){
                    ObjectMapper mapper = new ObjectMapper();

                    // 将 JSON 字符串转换为 Map
                    Map<String, Object> config = mapper.readValue(s, Map.class);
                    // 获取 history 字段
                    List<Map<String, Object>> history = (List<Map<String, Object>>) config.get("history");
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
           throw new SystemException(HadessFinal.SYSTEM_EXCEPTION,e.getMessage()) ;
        }
    }

    public static <T> java.util.function.Predicate<T> distinctByKey(java.util.function.Function<? super T, ?> keyExtractor) {
        java.util.Map<Object, Boolean> seen = new java.util.concurrent.ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }



}