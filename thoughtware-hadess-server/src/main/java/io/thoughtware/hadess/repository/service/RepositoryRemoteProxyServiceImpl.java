package io.thoughtware.hadess.repository.service;

import io.thoughtware.hadess.repository.model.RepositoryRemoteProxy;
import io.thoughtware.hadess.repository.model.RepositoryRemoteProxyQuery;
import io.thoughtware.hadess.repository.dao.RepositoryRemoteProxyDao;
import io.thoughtware.hadess.repository.entity.RepositoryRemoteProxyEntity;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.toolkit.join.JoinTemplate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* RepositoryRemoteProxyServiceImpl-远程库代理信息
*/
@Service
public class RepositoryRemoteProxyServiceImpl implements RepositoryRemoteProxyService {

    @Autowired
    RepositoryRemoteProxyDao repositoryRemoteProxyDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createRepositoryRemoteProxy(@NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy) {
        RepositoryRemoteProxyEntity repositoryRemoteProxyEntity = BeanMapper.map(repositoryRemoteProxy, RepositoryRemoteProxyEntity.class);
        repositoryRemoteProxyEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        repositoryRemoteProxyEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return repositoryRemoteProxyDao.createRepositoryRemoteProxy(repositoryRemoteProxyEntity);
    }

    @Override
    public void updateRepositoryRemoteProxy(@NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy) {
        RepositoryRemoteProxyEntity repositoryRemoteProxyEntity = BeanMapper.map(repositoryRemoteProxy, RepositoryRemoteProxyEntity.class);
        repositoryRemoteProxyEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        repositoryRemoteProxyDao.updateRepositoryRemoteProxy(repositoryRemoteProxyEntity);
    }

    @Override
    public void deleteRepositoryRemoteProxy(@NotNull String id) {
        repositoryRemoteProxyDao.deleteRepositoryRemoteProxy(id);
    }

    @Override
    public RepositoryRemoteProxy findOne(String id) {
        RepositoryRemoteProxyEntity repositoryRemoteProxyEntity = repositoryRemoteProxyDao.findRepositoryRemoteProxy(id);

        RepositoryRemoteProxy repositoryRemoteProxy = BeanMapper.map(repositoryRemoteProxyEntity, RepositoryRemoteProxy.class);
        return repositoryRemoteProxy;
    }

    @Override
    public List<RepositoryRemoteProxy> findList(List<String> idList) {
        List<RepositoryRemoteProxyEntity> repositoryRemoteProxyEntityList =  repositoryRemoteProxyDao.findRepositoryRemoteProxyList(idList);

        List<RepositoryRemoteProxy> repositoryRemoteProxyList =  BeanMapper.mapList(repositoryRemoteProxyEntityList,RepositoryRemoteProxy.class);
        return repositoryRemoteProxyList;
    }

    @Override
    public RepositoryRemoteProxy findRepositoryRemoteProxy(@NotNull String id) {
        RepositoryRemoteProxy repositoryRemoteProxy = findOne(id);

        joinTemplate.joinQuery(repositoryRemoteProxy);

        return repositoryRemoteProxy;
    }

    @Override
    public List<RepositoryRemoteProxy> findAllRepositoryRemoteProxy() {
        List<RepositoryRemoteProxyEntity> repositoryRemoteProxyEntityList =  repositoryRemoteProxyDao.findAllRepositoryRemoteProxy();

        List<RepositoryRemoteProxy> repositoryRemoteProxyList =  BeanMapper.mapList(repositoryRemoteProxyEntityList,RepositoryRemoteProxy.class);

        joinTemplate.joinQuery(repositoryRemoteProxyList);

        return repositoryRemoteProxyList;
    }

    @Override
    public List<RepositoryRemoteProxy> findRepositoryRemoteProxyList(RepositoryRemoteProxyQuery repositoryRemoteProxyQuery) {
        List<RepositoryRemoteProxyEntity> repositoryRemoteProxyEntityList = repositoryRemoteProxyDao.findRepositoryRemoteProxyList(repositoryRemoteProxyQuery);

        List<RepositoryRemoteProxy> repositoryRemoteProxyList = BeanMapper.mapList(repositoryRemoteProxyEntityList,RepositoryRemoteProxy.class);

        joinTemplate.joinQuery(repositoryRemoteProxyList);

        return repositoryRemoteProxyList;
    }

    @Override
    public Pagination<RepositoryRemoteProxy> findRepositoryRemoteProxyPage(RepositoryRemoteProxyQuery repositoryRemoteProxyQuery) {
        Pagination<RepositoryRemoteProxyEntity>  pagination = repositoryRemoteProxyDao.findRepositoryRemoteProxyPage(repositoryRemoteProxyQuery);

        List<RepositoryRemoteProxy> repositoryRemoteProxyList = BeanMapper.mapList(pagination.getDataList(),RepositoryRemoteProxy.class);

        joinTemplate.joinQuery(repositoryRemoteProxyList);

        return PaginationBuilder.build(pagination,repositoryRemoteProxyList);
    }

    @Override
    public List<String> findAgencyUrl(String repositoryName) {
        return repositoryRemoteProxyDao.findAgencyUrl(repositoryName);
    }

    @Override
    public RepositoryRemoteProxy findAgencyByRpyIdAndPath(String[] repositoryIds,String path) {
        List<RepositoryRemoteProxyEntity> agencyByRpyIdAndPath = repositoryRemoteProxyDao.findAgencyByRpyIdAndPath(repositoryIds, path);
        List<RepositoryRemoteProxy> repositoryRemoteProxyList = BeanMapper.mapList(agencyByRpyIdAndPath,RepositoryRemoteProxy.class);
       if (CollectionUtils.isNotEmpty(repositoryRemoteProxyList)){
           return repositoryRemoteProxyList.get(0);
       }
        return null;
    }
}