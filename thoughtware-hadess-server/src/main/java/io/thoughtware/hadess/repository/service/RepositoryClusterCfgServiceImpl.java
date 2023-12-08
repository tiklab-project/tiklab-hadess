package io.thoughtware.hadess.repository.service;

import io.thoughtware.hadess.repository.model.RepositoryClusterCfg;
import io.thoughtware.hadess.repository.model.RepositoryClusterCfgQuery;
import io.thoughtware.hadess.repository.dao.RepositoryClusterCfgDao;
import io.thoughtware.hadess.repository.entity.RepositoryClusterCfgEntity;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.join.JoinTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RepositoryClusterCfgServiceImpl-制品库复制信息
*/
@Service
public class RepositoryClusterCfgServiceImpl implements RepositoryClusterCfgService {

    @Autowired
    RepositoryClusterCfgDao repositoryClusterCfgDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createRepositoryClusterCfg(@NotNull @Valid RepositoryClusterCfg repositoryClusterCfg) {
        RepositoryClusterCfgEntity repositoryClusterCfgEntity = BeanMapper.map(repositoryClusterCfg, RepositoryClusterCfgEntity.class);

        return repositoryClusterCfgDao.createRepositoryClusterCfg(repositoryClusterCfgEntity);
    }

    @Override
    public void updateRepositoryClusterCfg(@NotNull @Valid RepositoryClusterCfg repositoryClusterCfg) {
        RepositoryClusterCfgEntity repositoryClusterCfgEntity = BeanMapper.map(repositoryClusterCfg, RepositoryClusterCfgEntity.class);

        repositoryClusterCfgDao.updateRepositoryClusterCfg(repositoryClusterCfgEntity);
    }

    @Override
    public void deleteRepositoryClusterCfg(@NotNull String id) {
        repositoryClusterCfgDao.deleteRepositoryClusterCfg(id);
    }

    @Override
    public RepositoryClusterCfg findOne(String id) {
        RepositoryClusterCfgEntity repositoryClusterCfgEntity = repositoryClusterCfgDao.findRepositoryClusterCfg(id);

        RepositoryClusterCfg repositoryClusterCfg = BeanMapper.map(repositoryClusterCfgEntity, RepositoryClusterCfg.class);
        return repositoryClusterCfg;
    }

    @Override
    public List<RepositoryClusterCfg> findList(List<String> idList) {
        List<RepositoryClusterCfgEntity> repositoryClusterCfgEntityList =  repositoryClusterCfgDao.findRepositoryClusterCfgList(idList);

        List<RepositoryClusterCfg> repositoryClusterCfgList =  BeanMapper.mapList(repositoryClusterCfgEntityList,RepositoryClusterCfg.class);
        return repositoryClusterCfgList;
    }

    @Override
    public RepositoryClusterCfg findRepositoryClusterCfg(@NotNull String id) {
        RepositoryClusterCfg repositoryClusterCfg = findOne(id);

        joinTemplate.joinQuery(repositoryClusterCfg);

        return repositoryClusterCfg;
    }

    @Override
    public List<RepositoryClusterCfg> findAllRepositoryClusterCfg() {
        List<RepositoryClusterCfgEntity> repositoryClusterCfgEntityList =  repositoryClusterCfgDao.findAllRepositoryClusterCfg();

        List<RepositoryClusterCfg> repositoryClusterCfgList =  BeanMapper.mapList(repositoryClusterCfgEntityList,RepositoryClusterCfg.class);

        joinTemplate.joinQuery(repositoryClusterCfgList);

        return repositoryClusterCfgList;
    }

    @Override
    public List<RepositoryClusterCfg> findRepositoryClusterCfgList(RepositoryClusterCfgQuery repositoryClusterCfgQuery) {
        List<RepositoryClusterCfgEntity> repositoryClusterCfgEntityList = repositoryClusterCfgDao.findRepositoryClusterCfgList(repositoryClusterCfgQuery);

        List<RepositoryClusterCfg> repositoryClusterCfgList = BeanMapper.mapList(repositoryClusterCfgEntityList,RepositoryClusterCfg.class);

        joinTemplate.joinQuery(repositoryClusterCfgList);

        return repositoryClusterCfgList;
    }

    @Override
    public Pagination<RepositoryClusterCfg> findRepositoryClusterCfgPage(RepositoryClusterCfgQuery repositoryClusterCfgQuery) {
        Pagination<RepositoryClusterCfgEntity>  pagination = repositoryClusterCfgDao.findRepositoryClusterCfgPage(repositoryClusterCfgQuery);

        List<RepositoryClusterCfg> repositoryClusterCfgList = BeanMapper.mapList(pagination.getDataList(),RepositoryClusterCfg.class);

        joinTemplate.joinQuery(repositoryClusterCfgList);

        return PaginationBuilder.build(pagination,repositoryClusterCfgList);
    }
}