package io.thoughtware.hadess.library.service;

import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.library.dao.LibraryVersionDao;
import io.thoughtware.hadess.library.entity.LibraryVersionEntity;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.toolkit.join.JoinTemplate;

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
import java.util.stream.Stream;

/**
* LibraryVersionServiceImpl-制品版本
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

    @Autowired
    PullInfoService pullInfoService;

    @Override
    public String createLibraryVersion(@NotNull @Valid LibraryVersion libraryVersion) {
        LibraryVersionEntity libraryVersionEntity = BeanMapper.map(libraryVersion, LibraryVersionEntity.class);
        libraryVersionEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        libraryVersionEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        libraryVersionEntity.setPushTime(new Timestamp(System.currentTimeMillis()));
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

        libraryFileService.deleteLibraryFileByCondition("libraryVersionId",id);
    }



    @Override
    public LibraryVersion findOne(String id) {
        LibraryVersionEntity libraryVersionEntity = libraryVersionDao.findLibraryVersion(id);

        LibraryVersion libraryVersion = BeanMapper.map(libraryVersionEntity, LibraryVersion.class);



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

        List<LibraryMaven> libraryMavenList = libraryMavenService.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(libraryVersion.getLibrary().getId()));
        if (CollectionUtils.isNotEmpty(libraryMavenList)){
            libraryVersion.setArtifactId(libraryMavenList.get(0).getArtifactId());
            libraryVersion.setGroupId(libraryMavenList.get(0).getGroupId());

            List<PullInfo> pullInfoList = pullInfoService.findPullInfoList(new PullInfoQuery().setLibraryVersionId(libraryMavenList.get(0).getId()));
            if (CollectionUtils.isNotEmpty(pullInfoList)){
                PullInfo pullInfo = pullInfoList.get(0);
                libraryVersion.setPullUser(pullInfo.getUser().getName());
                libraryVersion.setPullTime(pullInfo.getPullCreate());
                libraryVersion.setPullNum(pullInfoList.size());
            }
        }
        //制品的大小
        String librarySize = librarySize(libraryVersion.getId());
        libraryVersion.setSize(librarySize);

        joinTemplate.joinQuery(libraryVersion);

        return libraryVersion;
    }

    @Override
    public LibraryVersion findLibraryVersionById(String versionId) {
        LibraryVersion libraryVersion = findOne(versionId);
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
    public List<LibraryVersion> findLibraryVersionList(String repositoryId) {
        List<LibraryVersionEntity> libraryVersionEntityList = libraryVersionDao.findLibraryVersionList(new LibraryVersionQuery().setRepositoryId(repositoryId));

        List<LibraryVersion> libraryVersionList = BeanMapper.mapList(libraryVersionEntityList,LibraryVersion.class);

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
    public Pagination<LibraryVersion> findHistoryVersionPage(LibraryVersionQuery libraryVersionQuery) {
        Pagination<LibraryVersionEntity>  pagination = libraryVersionDao.findLibraryVersionPage(libraryVersionQuery);
        List<LibraryVersion> libraryVersionList=null;
        if (CollectionUtils.isNotEmpty(pagination.getDataList())){
             libraryVersionList = BeanMapper.mapList(pagination.getDataList(),LibraryVersion.class);
            joinTemplate.joinQuery(libraryVersionList);
            for (LibraryVersion libraryVersion:libraryVersionList){
                //制品的大小
                String librarySize = librarySize(libraryVersion.getId());
                libraryVersion.setSize(librarySize);
            }
        }
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

    @Override
    public void deleteVersionAndLibrary(String id) {
        LibraryVersion libraryVersion = this.findLibraryVersion(id);

        //删除该版本下面的制品文件
        libraryFileService.deleteLibraryFileByCondition("libraryVersionId",id);

        //删除该版本对应的制品
        libraryService.deleteLibrary(libraryVersion.getLibrary().getId());

        if ("maven".equals(libraryVersion.getLibrary().getLibraryType())){
            libraryMavenService.deleteLibraryMavenByCondition("libraryId",libraryVersion.getLibrary().getId());
        }
        this.deleteLibraryVersion(id);
    }

    @Override
    public void deleteVersionByCondition(String field, String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(LibraryVersionEntity.class)
                .eq(field, value)
                .get();
        libraryVersionDao.deleteLibraryVersion(deleteCondition);
    }

    @Override
    public LibraryVersion findVersionByNameAndVer(String libraryName, String version) {
        LibraryVersionEntity versionByNameAndVer = libraryVersionDao.findVersionByNameAndVer(libraryName, version);
        LibraryVersion libraryVersion = BeanMapper.map(versionByNameAndVer, LibraryVersion.class);

        return libraryVersion;
    }



    /**
     *  制品版本大小
     * @param libraryVersionId    制品版本id
     * @return
     */
    public String librarySize( String libraryVersionId){
        String librarySize=null;
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(libraryVersionId));

        if (!CollectionUtils.isEmpty(libraryFileList)){
            LibraryFile libraryFile = libraryFileList.get(0);
            if (("docker").equals(libraryFile.getRepository().getType())){
                List<LibraryFile> libraryFiles = libraryFileService.dockerFile(libraryFile);
                libraryFileList = Stream.concat(libraryFileList.stream(), libraryFiles.stream()).collect(Collectors.toList());

            }

            List<String> sizeList = libraryFileList.stream().map(LibraryFile::getFileSize).collect(Collectors.toList());
            librarySize = RepositoryUtil.formatSizeSum(sizeList);

        }
        return librarySize;
    }
}