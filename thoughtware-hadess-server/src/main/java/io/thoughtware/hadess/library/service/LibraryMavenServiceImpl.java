package io.thoughtware.hadess.library.service;

import io.thoughtware.hadess.library.dao.LibraryMavenDao;
import io.thoughtware.hadess.library.entity.LibraryMavenEntity;
import io.thoughtware.hadess.library.model.Library;
import io.thoughtware.hadess.library.model.LibraryMaven;
import io.thoughtware.hadess.library.model.LibraryMavenQuery;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.toolkit.join.JoinTemplate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* LibraryMavenServiceImpl-maven制品差异数据
*/
@Service
public class LibraryMavenServiceImpl implements LibraryMavenService {

    @Autowired
    LibraryMavenDao libraryMavenDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createLibraryMaven(@NotNull @Valid LibraryMaven libraryMaven) {
        LibraryMavenEntity libraryMavenEntity = BeanMapper.map(libraryMaven, LibraryMavenEntity.class);
        libraryMavenEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return libraryMavenDao.createLibraryMaven(libraryMavenEntity);
    }

    @Override
    public void updateLibraryMaven(@NotNull @Valid LibraryMaven libraryMaven) {
        LibraryMavenEntity libraryMavenEntity = BeanMapper.map(libraryMaven, LibraryMavenEntity.class);

        libraryMavenDao.updateLibraryMaven(libraryMavenEntity);
    }

    @Override
    public void deleteLibraryMaven(@NotNull String id) {
        libraryMavenDao.deleteLibraryMaven(id);
    }


    @Override
    public void deleteLibraryMavenByCondition(String field, String value) {
        DeleteCondition libraryMaven = DeleteBuilders.createDelete(LibraryMavenEntity.class)
                .eq(field,value)
                .get();
        libraryMavenDao.deleteLibraryMaven(libraryMaven);
    }

    @Override
    public LibraryMaven findOne(String id) {
        LibraryMavenEntity libraryMavenEntity = libraryMavenDao.findLibraryMaven(id);

        LibraryMaven libraryMaven = BeanMapper.map(libraryMavenEntity, LibraryMaven.class);
        return libraryMaven;
    }

    @Override
    public List<LibraryMaven> findList(List<String> idList) {
        List<LibraryMavenEntity> libraryMavenEntityList =  libraryMavenDao.findLibraryMavenList(idList);

        List<LibraryMaven> libraryMavenList =  BeanMapper.mapList(libraryMavenEntityList,LibraryMaven.class);
        return libraryMavenList;
    }

    @Override
    public LibraryMaven findLibraryMaven(@NotNull String id) {
        LibraryMaven libraryMaven = findOne(id);

        joinTemplate.joinQuery(libraryMaven);

        return libraryMaven;
    }

    @Override
    public List<LibraryMaven> findAllLibraryMaven() {
        List<LibraryMavenEntity> libraryMavenEntityList =  libraryMavenDao.findAllLibraryMaven();

        List<LibraryMaven> libraryMavenList =  BeanMapper.mapList(libraryMavenEntityList,LibraryMaven.class);

        joinTemplate.joinQuery(libraryMavenList);

        return libraryMavenList;
    }

    @Override
    public List<LibraryMaven> findLibraryMavenList(LibraryMavenQuery libraryMavenQuery) {
        List<LibraryMavenEntity> libraryMavenEntityList = libraryMavenDao.findLibraryMavenList(libraryMavenQuery);

        List<LibraryMaven> libraryMavenList = BeanMapper.mapList(libraryMavenEntityList,LibraryMaven.class);

        joinTemplate.joinQuery(libraryMavenList);

        return libraryMavenList;
    }

    @Override
    public Pagination<LibraryMaven> findLibraryMavenPage(LibraryMavenQuery libraryMavenQuery) {
        Pagination<LibraryMavenEntity>  pagination = libraryMavenDao.findLibraryMavenPage(libraryMavenQuery);

        List<LibraryMaven> libraryMavenList = BeanMapper.mapList(pagination.getDataList(),LibraryMaven.class);

        joinTemplate.joinQuery(libraryMavenList);

        return PaginationBuilder.build(pagination,libraryMavenList);
    }

    public void libraryMavenSplice(String artifactId, String  groupId, Library library ){
        List<LibraryMaven> libraryMavenList = this.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(library.getId()));
        if (CollectionUtils.isEmpty(libraryMavenList)){
            LibraryMaven libraryMaven = new LibraryMaven();
            libraryMaven.setArtifactId(artifactId);
            libraryMaven.setGroupId(groupId);
            libraryMaven.setLibrary(library);
            libraryMaven.setRepositoryId(library.getRepository().getId());
            this.createLibraryMaven(libraryMaven);
        }
    }

    @Override
    public List<LibraryMaven> libraryMavenByLibraryIds(String[] libraryIds) {
        List<LibraryMavenEntity> libraryMavenEntityList = libraryMavenDao.libraryMavenByLibraryIds(libraryIds);
        List<LibraryMaven> libraryMavenList = BeanMapper.mapList(libraryMavenEntityList,LibraryMaven.class);
        return libraryMavenList;
    }

}