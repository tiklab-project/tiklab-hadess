package io.tiklab.xpack.library.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.library.dao.LibraryMavenDao;
import io.tiklab.xpack.library.entity.LibraryMavenEntity;
import io.tiklab.xpack.library.model.Library;
import io.tiklab.xpack.library.model.LibraryMaven;
import io.tiklab.xpack.library.model.LibraryMavenQuery;

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
    public void deleteLibraryMavenByLibraryId(String libraryId) {
        DeleteCondition libraryMaven = DeleteBuilders.createDelete(LibraryMavenEntity.class)
                .eq("libraryId",libraryId)
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
        List<LibraryMaven> libraryMavenList = this.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(library.getId())
                .setArtifactId(artifactId).setGroupId(groupId));
        if (CollectionUtils.isEmpty(libraryMavenList)){
            LibraryMaven libraryMaven = new LibraryMaven();
            libraryMaven.setArtifactId(artifactId);
            libraryMaven.setGroupId(groupId);
            libraryMaven.setLibrary(library);
            this.createLibraryMaven(libraryMaven);
        }
    }
}