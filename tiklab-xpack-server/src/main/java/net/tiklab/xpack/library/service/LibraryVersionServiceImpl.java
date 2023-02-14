package net.tiklab.xpack.library.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.user.user.model.User;
import net.tiklab.xpack.library.dao.LibraryVersionDao;
import net.tiklab.xpack.library.entity.LibraryVersionEntity;
import net.tiklab.xpack.library.model.*;

import net.tiklab.xpack.repository.model.Repository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

/**
* LibraryVersionServiceImpl
*/
@Service
public class LibraryVersionServiceImpl implements LibraryVersionService {

    @Autowired
    LibraryVersionDao libraryVersionDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    LibraryMavenService libraryMavenService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryFileService libraryFileService;

    @Override
    public String createLibraryVersion(@NotNull @Valid LibraryVersion libraryVersion) {
        LibraryVersionEntity libraryVersionEntity = BeanMapper.map(libraryVersion, LibraryVersionEntity.class);
        libraryVersionEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        libraryVersionEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return libraryVersionDao.createLibraryVersion(libraryVersionEntity);
    }

    @Override
    public void updateLibraryVersion(@NotNull @Valid LibraryVersion libraryVersion) {
        LibraryVersionEntity libraryVersionEntity = BeanMapper.map(libraryVersion, LibraryVersionEntity.class);
        libraryVersionEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        libraryVersionDao.updateLibraryVersion(libraryVersionEntity);
    }

    @Override
    public void deleteLibraryVersion(@NotNull String id) {
        libraryVersionDao.deleteLibraryVersion(id);
    }

    @Override
    public LibraryVersion findOne(String id) {
        LibraryVersionEntity libraryVersionEntity = libraryVersionDao.findLibraryVersion(id);

        LibraryVersion libraryVersion = BeanMapper.map(libraryVersionEntity, LibraryVersion.class);

        List<LibraryMaven> libraryMavenList = libraryMavenService.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(libraryVersion.getLibrary().getId()));
        if (CollectionUtils.isNotEmpty(libraryMavenList)){
            libraryVersion.setArtifactId(libraryMavenList.get(0).getArtifactId());
            libraryVersion.setGroupId(libraryMavenList.get(0).getGroupId());
        }
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(libraryVersion.getId()));
        if (!CollectionUtils.isEmpty(libraryFileList)){
            int allKb = libraryFileList.stream().mapToInt(version -> Integer.valueOf(StringUtils.substringBeforeLast(version.getFileSize(), "kb"))).sum();
            libraryVersion.setSize(allKb+"kb");
        }
        return libraryVersion;
    }

    @Override
    public List<LibraryVersion> findList(List<String> idList) {
        List<LibraryVersionEntity> libraryVersionEntityList =  libraryVersionDao.findLibraryVersionList(idList);

        List<LibraryVersion> libraryVersionList =  BeanMapper.mapList(libraryVersionEntityList,LibraryVersion.class);
        return libraryVersionList;
    }

    @Override
    public LibraryVersion findLibraryVersion(@NotNull String id) {
        LibraryVersion libraryVersion = findOne(id);

        joinTemplate.joinQuery(libraryVersion);

        return libraryVersion;
    }

    @Override
    public List<LibraryVersion> findAllLibraryVersion() {
        List<LibraryVersionEntity> libraryVersionEntityList =  libraryVersionDao.findAllLibraryVersion();

        List<LibraryVersion> libraryVersionList =  BeanMapper.mapList(libraryVersionEntityList,LibraryVersion.class);

        joinTemplate.joinQuery(libraryVersionList);

        return libraryVersionList;
    }

    @Override
    public List<LibraryVersion> findLibraryVersionList(LibraryVersionQuery libraryVersionQuery) {
        List<LibraryVersionEntity> libraryVersionEntityList = libraryVersionDao.findLibraryVersionList(libraryVersionQuery);

        List<LibraryVersion> libraryVersionList = BeanMapper.mapList(libraryVersionEntityList,LibraryVersion.class);

        joinTemplate.joinQuery(libraryVersionList);

        return libraryVersionList;
    }

    @Override
    public Pagination<LibraryVersion> findLibraryVersionPage(LibraryVersionQuery libraryVersionQuery) {
        Pagination<LibraryVersionEntity>  pagination = libraryVersionDao.findLibraryVersionPage(libraryVersionQuery);

        List<LibraryVersion> libraryVersionList = BeanMapper.mapList(pagination.getDataList(),LibraryVersion.class);

        joinTemplate.joinQuery(libraryVersionList);

        return PaginationBuilder.build(pagination,libraryVersionList);
    }

    @Override
    public LibraryVersion findLibraryNewVersion(LibraryVersionQuery libraryVersionQuery) {
        LibraryVersion libraryVersion=null;
        List<LibraryVersion> libraryVersionList = this.findLibraryVersionList(libraryVersionQuery);
        if (CollectionUtils.isNotEmpty(libraryVersionList)){
            List<LibraryVersion> versions = libraryVersionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime).reversed()).collect(Collectors.toList());
             libraryVersion = versions.get(0);
        }
        return libraryVersion;
    }

    /**
     *  制品版本创建、修改
     * @param libraryVersion    libraryVersion
     * @return
     */
    public String libraryVersionSplice( LibraryVersion libraryVersion){

        libraryVersion.setPushTime(new Timestamp(System.currentTimeMillis()));
        String libraryVersionId=null;

        List<LibraryVersion> libraryVersionList = this.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(libraryVersion.getLibrary().getId()).
                setRepositoryId(libraryVersion.getRepository().getId()).setVersion(libraryVersion.getVersion()));
        if (CollectionUtils.isNotEmpty(libraryVersionList)){
            libraryVersionId = libraryVersionList.get(0).getId();
            if (StringUtils.isEmpty(libraryVersion.getHash())){
                libraryVersion.setHash(libraryVersionList.get(0).getHash());
            }
            libraryVersion.setId(libraryVersionId);
            this.updateLibraryVersion(libraryVersion);
        }else {
            libraryVersionId = this.createLibraryVersion(libraryVersion);

            //更新最新版本
            libraryVersion.getLibrary().setNewVersion(libraryVersion.getVersion());
            libraryService.updateLibrary(libraryVersion.getLibrary());
        }
        return libraryVersionId;
    }
}