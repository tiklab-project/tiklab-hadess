package io.tiklab.xpack.repository.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.join.JoinTemplate;
import io.tiklab.xpack.repository.dao.RepositoryMavenDao;
import io.tiklab.xpack.repository.entity.RepositoryMavenEntity;
import io.tiklab.xpack.repository.model.RepositoryMaven;
import io.tiklab.xpack.repository.model.RepositoryMavenQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public Pagination<RepositoryMaven> findRepositoryMavenPage(RepositoryMavenQuery repositoryMavenQuery) {
        Pagination<RepositoryMavenEntity>  pagination = repositoryMavenDao.findRepositoryMavenPage(repositoryMavenQuery);

        List<RepositoryMaven> repositoryMavenList = BeanMapper.mapList(pagination.getDataList(),RepositoryMaven.class);

        joinTemplate.joinQuery(repositoryMavenList);

        return PaginationBuilder.build(pagination,repositoryMavenList);
    }
}