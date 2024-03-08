package io.thoughtware.hadess.library.service;

import com.alibaba.fastjson.JSONObject;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.common.XpackYamlDataMaService;
import io.thoughtware.hadess.library.dao.LibraryFileDao;
import io.thoughtware.hadess.library.entity.LibraryFileEntity;
import io.thoughtware.hadess.library.model.LibraryFile;
import io.thoughtware.hadess.library.model.LibraryFileQuery;
import io.thoughtware.hadess.library.model.LibraryVersion;
import io.thoughtware.hadess.library.model.LibraryVersionQuery;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.toolkit.join.JoinTemplate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    public List<LibraryFile> findLibraryFileByLibraryId(String[] repositoryIds,String fileName) {
        List<LibraryFileEntity> fileByLibraryId = libraryFileDao.findLibraryFileByLibraryId(repositoryIds,fileName);
        List<LibraryFile> libraryFileList = BeanMapper.mapList(fileByLibraryId,LibraryFile.class);


        joinTemplate.joinQuery(libraryFileList);
        return libraryFileList;
    }

    @Override
    public List<LibraryFile> findLibraryNewFileList(LibraryFileQuery libraryFileQuery) {
        LibraryVersion libraryVersion = libraryVersionService.findOne(libraryFileQuery.getLibraryVersionId());
        List<LibraryFile> libraryFileList = findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(libraryVersion.getId()));
       //快照版本
        if (libraryVersion.getVersion().toUpperCase().endsWith("SNAPSHOT")){
            List<LibraryFile> collected = libraryFileList.stream().filter(b -> !b.getFileUrl().contains("maven-metadata.xml")).collect(Collectors.toList());
            List<LibraryFile> libraryFiles = collected.stream().sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
            String snapshotVersion = libraryFiles.get(0).getSnapshotVersion();
            libraryFileList = collected.stream().filter(a -> (snapshotVersion).equals(a.getSnapshotVersion())).collect(Collectors.toList());
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
    public void libraryFileSplice(LibraryFile libraryFile,String versionId){
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

    public static <T> java.util.function.Predicate<T> distinctByKey(java.util.function.Function<? super T, ?> keyExtractor) {
        java.util.Map<Object, Boolean> seen = new java.util.concurrent.ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }



}