package io.thoughtware.hadess.repository.service;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.hadess.repository.dao.RemoteProxyDao;
import io.thoughtware.hadess.repository.entity.RemoteProxyEntity;
import io.thoughtware.hadess.repository.entity.RepositoryMavenEntity;
import io.thoughtware.hadess.repository.model.RemoteProxy;
import io.thoughtware.hadess.repository.model.RemoteProxyQuery;
import io.thoughtware.hadess.repository.model.RepositoryRemoteProxy;
import io.thoughtware.hadess.repository.model.RepositoryRemoteProxyQuery;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.toolkit.join.JoinTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* RemoteProxyServiceImpl-代理地址
*/
@Service
public class RemoteProxyServiceImpl implements RemoteProxyService {

    @Autowired
    RemoteProxyDao remoteProxyDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    RepositoryRemoteProxyService remoteProxyService;

    @Override
    public String createRemoteProxy(@NotNull @Valid RemoteProxy remoteProxy) {
        RemoteProxyEntity remoteProxyEntity = BeanMapper.map(remoteProxy, RemoteProxyEntity.class);

        remoteProxyEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return remoteProxyDao.createRemoteProxy(remoteProxyEntity);
    }

    @Override
    public void updateRemoteProxy(@NotNull @Valid RemoteProxy remoteProxy) {
        RemoteProxyEntity remoteProxyEntity = BeanMapper.map(remoteProxy, RemoteProxyEntity.class);
        remoteProxyDao.updateRemoteProxy(remoteProxyEntity);
    }

    @Override
    public void deleteRemoteProxy(@NotNull String id) {
        remoteProxyDao.deleteRemoteProxy(id);
    }

    @Override
    public RemoteProxy findOne(String id) {
        RemoteProxyEntity remoteProxyEntity = remoteProxyDao.findRemoteProxy(id);

        RemoteProxy remoteProxy = BeanMapper.map(remoteProxyEntity, RemoteProxy.class);
        return remoteProxy;
    }

    @Override
    public List<RemoteProxy> findList(List<String> idList) {
        List<RemoteProxyEntity> remoteProxyEntityList =  remoteProxyDao.findRemoteProxyList(idList);

        List<RemoteProxy> remoteProxyList =  BeanMapper.mapList(remoteProxyEntityList,RemoteProxy.class);
        return remoteProxyList;
    }

    @Override
    public RemoteProxy findRemoteProxy(@NotNull String id) {
        RemoteProxy remoteProxy = findOne(id);

        joinTemplate.joinQuery(remoteProxy);

        return remoteProxy;
    }

    @Override
    public List<RemoteProxy> findAllRemoteProxy() {
        List<RemoteProxyEntity> remoteProxyEntityList =  remoteProxyDao.findAllRemoteProxy();

        List<RemoteProxy> remoteProxyList =  BeanMapper.mapList(remoteProxyEntityList,RemoteProxy.class);

        joinTemplate.joinQuery(remoteProxyList);

        return remoteProxyList;
    }

    @Override
    public List<RemoteProxy> findRemoteProxyList(RemoteProxyQuery remoteProxyQuery) {
        List<RemoteProxyEntity> remoteProxyEntityList = remoteProxyDao.findRemoteProxyList(remoteProxyQuery);

        List<RemoteProxy> remoteProxyList = BeanMapper.mapList(remoteProxyEntityList,RemoteProxy.class);

        joinTemplate.joinQuery(remoteProxyList);

        return remoteProxyList;
    }

    @Override
    public Pagination<RemoteProxy> findRemoteProxyPage(RemoteProxyQuery remoteProxyQuery) {
        Pagination<RemoteProxyEntity>  pagination = remoteProxyDao.findRemoteProxyPage(remoteProxyQuery);

        List<RemoteProxy> remoteProxyList = BeanMapper.mapList(pagination.getDataList(),RemoteProxy.class);

        joinTemplate.joinQuery(remoteProxyList);

        return PaginationBuilder.build(pagination,remoteProxyList);
    }

    @Override
    public List<RemoteProxy> findProxyListByRpyId(String repositoryId) {
        List<RepositoryRemoteProxy> repositoryRemoteProxyList = remoteProxyService.findRepositoryRemoteProxyList(new RepositoryRemoteProxyQuery().setRepositoryId(repositoryId));
        if (CollectionUtils.isEmpty(repositoryRemoteProxyList)){
            return Collections.emptyList();
        }
        List<RemoteProxy> remoteProxies = repositoryRemoteProxyList.stream().map(RepositoryRemoteProxy::getRemoteProxy).collect(Collectors.toList());
        return remoteProxies;
    }

    @Override
    public List<String> findRepositoryByProxyId(String id) {
        List<RepositoryRemoteProxy> repositoryRemoteProxyList = remoteProxyService.findRepositoryRemoteProxyList(new RepositoryRemoteProxyQuery().setRemoteProxyId(id));
        if (CollectionUtils.isEmpty(repositoryRemoteProxyList)){
            return Collections.emptyList();
        }
        List<String> stringList = repositoryRemoteProxyList.stream().map(a -> a.getRepository().getName()).collect(Collectors.toList());


        return stringList;
    }
}