package io.tiklab.xpack.library.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
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
    public List<LibraryFile> findLibraryFileByLibraryId(String[] libraryIds) {
        List<LibraryFileEntity> fileByLibraryId = libraryFileDao.findLibraryFileByLibraryId(libraryIds);
        List<LibraryFile> libraryFileList = BeanMapper.mapList(fileByLibraryId,LibraryFile.class);

        joinTemplate.joinQuery(libraryFileList);

        return libraryFileList;
    }

    @Override
    public List<LibraryFile> findLibraryNewFileList(LibraryFileQuery libraryFileQuery) {
        List<LibraryVersion> libraryVersionList = libraryVersionService.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(libraryFileQuery.getLibraryId()));
        List<LibraryFile> libraryFileList=null;
        if(CollectionUtils.isNotEmpty(libraryVersionList)){
            List<LibraryVersion> libraryVersion = libraryVersionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime)).collect(Collectors.toList());
            libraryFileQuery.setLibraryVersionId(libraryVersion.get(0).getId());
             libraryFileList = this.findLibraryFileList(libraryFileQuery);

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

}