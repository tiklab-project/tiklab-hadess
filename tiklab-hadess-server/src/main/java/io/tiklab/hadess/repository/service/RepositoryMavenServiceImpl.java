package io.tiklab.hadess.repository.service;

import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.repository.model.RepositoryMaven;
import io.tiklab.hadess.repository.model.RepositoryMavenQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.hadess.repository.dao.RepositoryMavenDao;
import io.tiklab.hadess.repository.entity.RepositoryMavenEntity;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* RepositoryMavenServiceImpl-存储库
*/
@Service
public class RepositoryMavenServiceImpl implements RepositoryMavenService {

    @Autowired
    RepositoryMavenDao repositoryMavenDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createRepositoryMaven(@NotNull @Valid RepositoryMaven repositoryMaven) {
        RepositoryMavenEntity repositoryMavenEntity = BeanMapper.map(repositoryMaven, RepositoryMavenEntity.class);
        repositoryMavenEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return repositoryMavenDao.createRepositoryMaven(repositoryMavenEntity);
    }

    @Override
    public void updateRepositoryMaven(@NotNull @Valid RepositoryMaven repositoryMaven) {
        RepositoryMavenEntity repositoryMavenEntity = BeanMapper.map(repositoryMaven, RepositoryMavenEntity.class);

        repositoryMavenDao.updateRepositoryMaven(repositoryMavenEntity);
    }

    @Override
    public void deleteRepositoryMaven(@NotNull String id) {
        repositoryMavenDao.deleteRepositoryMaven(id);
    }

    @Override
    public void deleteRepositoryMavenByCondition(String field, String value) {
        DeleteCondition libraryFile = DeleteBuilders.createDelete(RepositoryMavenEntity.class)
                .eq(field,value)
                .get();
        repositoryMavenDao.deleteRepositoryMaven(libraryFile);
    }

    @Override
    public RepositoryMaven findOne(String id) {
        RepositoryMavenEntity repositoryMavenEntity = repositoryMavenDao.findRepositoryMaven(id);

        RepositoryMaven repositoryMaven = BeanMapper.map(repositoryMavenEntity, RepositoryMaven.class);
        return repositoryMaven;
    }

    @Override
    public List<RepositoryMaven> findList(List<String> idList) {
        List<RepositoryMavenEntity> repositoryMavenEntityList =  repositoryMavenDao.findRepositoryMavenList(idList);

        List<RepositoryMaven> repositoryMavenList =  BeanMapper.mapList(repositoryMavenEntityList,RepositoryMaven.class);
        return repositoryMavenList;
    }

    @Override
    public RepositoryMaven findRepositoryMaven(@NotNull String id) {
        RepositoryMaven repositoryMaven = findOne(id);

        joinTemplate.joinQuery(repositoryMaven);

        return repositoryMaven;
    }

    @Override
    public List<RepositoryMaven> findAllRepositoryMaven() {
        List<RepositoryMavenEntity> repositoryMavenEntityList =  repositoryMavenDao.findAllRepositoryMaven();

        List<RepositoryMaven> repositoryMavenList =  BeanMapper.mapList(repositoryMavenEntityList,RepositoryMaven.class);

        joinTemplate.joinQuery(repositoryMavenList);

        return repositoryMavenList;
    }

    @Override
    public List<RepositoryMaven> findRepositoryMavenList(RepositoryMavenQuery repositoryMavenQuery) {
        List<RepositoryMavenEntity> repositoryMavenEntityList = repositoryMavenDao.findRepositoryMavenList(repositoryMavenQuery);

        List<RepositoryMaven> repositoryMavenList = BeanMapper.mapList(repositoryMavenEntityList,RepositoryMaven.class);

        joinTemplate.joinQuery(repositoryMavenList);

        return repositoryMavenList;
    }

    @Override
    public List<RepositoryMaven> findRepositoryMavenByRep(RepositoryMavenQuery repositoryMavenQuery) {
        List<RepositoryMavenEntity> repositoryMavenEntityList = repositoryMavenDao.findRepositoryMavenByRep(repositoryMavenQuery);

        List<RepositoryMaven> repositoryMavenList = BeanMapper.mapList(repositoryMavenEntityList,RepositoryMaven.class);


        return repositoryMavenList;
    }


    @Override
    public Pagination<RepositoryMaven> findRepositoryMavenPage(RepositoryMavenQuery repositoryMavenQuery) {
        Pagination<RepositoryMavenEntity>  pagination = repositoryMavenDao.findRepositoryMavenPage(repositoryMavenQuery);

        List<RepositoryMaven> repositoryMavenList = BeanMapper.mapList(pagination.getDataList(),RepositoryMaven.class);

        joinTemplate.joinQuery(repositoryMavenList);

        return PaginationBuilder.build(pagination,repositoryMavenList);
    }

    @Override
    public RepositoryMaven findRepositoryMavenByRpyIds(String[] ids, String version) {
        RepositoryMaven repositoryMaven=null;

        List<RepositoryMavenEntity> repositoryMavenEntityList = repositoryMavenDao.findRepositoryMavenByRpyIds(ids,version);

        List<RepositoryMaven> repositoryMavenList = BeanMapper.mapList(repositoryMavenEntityList,RepositoryMaven.class);
        if (CollectionUtils.isNotEmpty(repositoryMavenList)){
             repositoryMaven = repositoryMavenList.get(0);
        }
        return repositoryMaven;
    }
}