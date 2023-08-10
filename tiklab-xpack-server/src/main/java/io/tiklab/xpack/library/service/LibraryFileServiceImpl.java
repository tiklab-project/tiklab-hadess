package io.tiklab.xpack.library.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.xpack.common.XpakYamlDataMaService;
import io.tiklab.xpack.library.dao.LibraryFileDao;
import io.tiklab.xpack.library.entity.LibraryFileEntity;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.library.model.LibraryFile;
import io.tiklab.xpack.library.model.LibraryFileQuery;

import io.tiklab.xpack.library.model.LibraryVersion;
import io.tiklab.xpack.library.model.LibraryVersionQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* LibraryFileServiceImpl-制品文件
*/
@Service
public class LibraryFileServiceImpl implements LibraryFileService {

    @Autowired
    LibraryFileDao libraryFileDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    LibraryVersionService libraryVersionService;

    @Autowired
    XpakYamlDataMaService xpakYamlDataMaService;

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
        LibraryVersion libraryVersion=null;
        if (StringUtils.isEmpty(libraryFileQuery.getLibraryVersionId())){
            List<LibraryVersion> libraryVersionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(libraryFileQuery.getLibraryId()));

            libraryVersion = libraryVersionList.get(0);
        }else {
            libraryVersion = libraryVersionService.findLibraryVersion(libraryFileQuery.getLibraryVersionId());
        }

        List<LibraryFile> libraryFileList = findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(libraryVersion.getId()));
       //快照版本
        if (libraryVersion.getVersion().endsWith("SNAPSHOT")){
            List<LibraryFile> collected = libraryFileList.stream().filter(b -> !b.getFileUrl().contains("maven-metadata.xml")).collect(Collectors.toList());
            List<LibraryFile> libraryFiles = collected.stream().sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.toList());
            String snapshotVersion = libraryFiles.get(0).getSnapshotVersion();
             libraryFileList = collected.stream().filter(a -> (snapshotVersion).equals(a.getSnapshotVersion())).collect(Collectors.toList());
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

        List<LibraryFile> libraryFileList = this.findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(versionId)
                .setFileName(libraryFile.getFileName()));
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            libraryFile.setId(libraryFileList.get(0).getId());
            this.updateLibraryFile(libraryFile);
        }else {
            this.createLibraryFile(libraryFile);
        }
    }

  /*  @Override
    public void test(){
        List<LibraryFile> allLibraryFile = this.findAllLibraryFile();
        List<LibraryFile> collect = allLibraryFile.stream().filter(a -> StringUtils.isNotEmpty(a.getSnapshotVersion())).collect(Collectors.toList());
        List<LibraryFile> collected = collect.stream().filter(b -> b.getRelativePath().contains(b.getSnapshotVersion())).collect(Collectors.toList());
        for (LibraryFile libraryFile: collected){
            String snapshotVersion = libraryFile.getSnapshotVersion();
            String relativePath = libraryFile.getRelativePath();
            String replace = relativePath.replace( snapshotVersion, "SNAPSHOT");
            libraryFile.setRelativePath(replace);
            this.updateLibraryFile(libraryFile);
        }
    }*/

    @Override
    public void test() {
        List<LibraryFile> allLibraryFile = this.findAllLibraryFile();
        List<LibraryFile> collected = allLibraryFile.stream().filter(b -> b.getFileUrl().contains("///17fcac6b5f0d")).collect(Collectors.toList());
        for (LibraryFile libraryFile : collected) {
            String fileUrl = libraryFile.getFileUrl();
            String replace = fileUrl.replace("///17fcac6b5f0d", "17fcac6b5f0d");
            libraryFile.setFileUrl(replace);
            this.updateLibraryFile(libraryFile);
        }
    }
}