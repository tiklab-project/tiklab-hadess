package io.tiklab.hadess.repository.service;

import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.hadess.repository.model.RemoteProxy;
import io.tiklab.hadess.repository.model.RemoteProxyQuery;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxy;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxyQuery;
import io.tiklab.hadess.repository.dao.RepositoryRemoteProxyDao;
import io.tiklab.hadess.repository.entity.RepositoryRemoteProxyEntity;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.join.JoinTemplate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
* RepositoryRemoteProxyServiceImpl-远程库代理信息
*/
@Service
public class RepositoryRemoteProxyServiceImpl implements RepositoryRemoteProxyService {

    @Autowired
    RepositoryRemoteProxyDao repositoryRemoteProxyDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    RemoteProxyServiceImpl remoteProxyService;

    @Override
    public String createRepositoryRemoteProxy(@NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy) {
        RepositoryRemoteProxyEntity repositoryRemoteProxyEntity = BeanMapper.map(repositoryRemoteProxy, RepositoryRemoteProxyEntity.class);
        repositoryRemoteProxyEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

        //更新
        if (("update").equals(repositoryRemoteProxy.getExecType())){
            DeleteCondition deleteCondition = DeleteBuilders.createDelete(RepositoryRemoteProxyEntity.class)
                    .eq("repositoryId", repositoryRemoteProxy.getRepository().getId())
                    .get();
            repositoryRemoteProxyDao.deleteRepositoryRemoteProxy(deleteCondition);
            List<String> remoteProxyIds = repositoryRemoteProxy.getRemoteProxyIds();
            for (String proxyId:remoteProxyIds){
                repositoryRemoteProxyEntity.setRemoteProxyId(proxyId);
                repositoryRemoteProxyDao.createRepositoryRemoteProxy(repositoryRemoteProxyEntity);
            }
            return null;
        }

        return repositoryRemoteProxyDao.createRepositoryRemoteProxy(repositoryRemoteProxyEntity);
    }

    @Override
    public void updateRepositoryRemoteProxy(@NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy) {
        RepositoryRemoteProxyEntity repositoryRemoteProxyEntity = BeanMapper.map(repositoryRemoteProxy, RepositoryRemoteProxyEntity.class);
        repositoryRemoteProxyDao.updateRepositoryRemoteProxy(repositoryRemoteProxyEntity);
    }

    @Override
    public void deleteRepositoryRemoteProxy(@NotNull String id) {
        repositoryRemoteProxyDao.deleteRepositoryRemoteProxy(id);
    }

    @Override
    public void deleteRepositoryRemoteProxy(String type, String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(RepositoryRemoteProxyEntity.class)
                .eq(type, value)
                .get();
        repositoryRemoteProxyDao.deleteRepositoryRemoteProxy(deleteCondition);
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



        return PaginationBuilder.build(pagination,repositoryRemoteProxyList);
    }

    @Override
    public List<RepositoryRemoteProxy> findAgencyByRepId(String repositoryId) {
        List<RepositoryRemoteProxyEntity> remoteProxyEntities = repositoryRemoteProxyDao.findRemoteProxyRepId(repositoryId);

        List<RepositoryRemoteProxy> repositoryRemoteProxyList = BeanMapper.mapList(remoteProxyEntities,RepositoryRemoteProxy.class);
        joinTemplate.joinQuery(repositoryRemoteProxyList);

        return repositoryRemoteProxyList;
    }

    @Override
    public RepositoryRemoteProxy findAgencyByRpyIdAndPath(String[] repositoryIds,String path) {
        List<RemoteProxy> remoteProxyList = remoteProxyService.findRemoteProxyList(new RemoteProxyQuery().setAgencyUrl(path));
        if (CollectionUtils.isNotEmpty(remoteProxyList)){
            String id = remoteProxyList.get(0).getId();
            List<RepositoryRemoteProxyEntity> agencyByRpyIdAndPath = repositoryRemoteProxyDao.findAgencyByRpyIdAndPath(repositoryIds, id);
            List<RepositoryRemoteProxy> repositoryRemoteProxyList = BeanMapper.mapList(agencyByRpyIdAndPath,RepositoryRemoteProxy.class);
            if (CollectionUtils.isNotEmpty(repositoryRemoteProxyList)){
                RepositoryRemoteProxy repositoryRemoteProxy = repositoryRemoteProxyList.get(0);
                joinTemplate.joinQuery(repositoryRemoteProxyList);
                return repositoryRemoteProxy;
            }
        }

        return null;
    }

    @Override
    public List<RepositoryRemoteProxy> findAgencyByRpyIds(String[] repositoryIds) {
        List<RepositoryRemoteProxyEntity> agencyList = repositoryRemoteProxyDao.findAgencyByRpyIds(repositoryIds);

        List<RepositoryRemoteProxy> proxyList = BeanMapper.mapList(agencyList,RepositoryRemoteProxy.class);

        joinTemplate.joinQuery(proxyList);


        return proxyList;
    }
}