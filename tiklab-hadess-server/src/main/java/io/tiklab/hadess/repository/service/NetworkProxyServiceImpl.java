package io.tiklab.hadess.repository.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.hadess.repository.dao.NetworkProxyDao;
import io.tiklab.hadess.repository.entity.NetworkProxyEntity;
import io.tiklab.hadess.repository.model.NetworkProxy;
import io.tiklab.hadess.repository.model.NetworkProxyQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* NetworkProxyServiceImpl-网络代理地址
*/
@Service
public class NetworkProxyServiceImpl implements NetworkProxyService {

    @Autowired
    NetworkProxyDao networkProxyDao;

    @Autowired
    JoinTemplate joinTemplate;


    @Override
    public String createNetworkProxy(@NotNull @Valid NetworkProxy networkProxy) {
        NetworkProxyEntity networkProxyEntity = BeanMapper.map(networkProxy, NetworkProxyEntity.class);

        networkProxyEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return networkProxyDao.createNetworkProxy(networkProxyEntity);
    }

    @Override
    public void updateNetworkProxy(@NotNull @Valid NetworkProxy networkProxy) {
        NetworkProxyEntity networkProxyEntity = BeanMapper.map(networkProxy, NetworkProxyEntity.class);
        if (networkProxy.getEnable()==1){

        }
        networkProxyDao.updateNetworkProxy(networkProxyEntity);
    }

    @Override
    public void deleteNetworkProxy(@NotNull String id) {
        networkProxyDao.deleteNetworkProxy(id);
    }

    @Override
    public NetworkProxy findOne(String id) {
        NetworkProxyEntity networkProxyEntity = networkProxyDao.findNetworkProxy(id);

        NetworkProxy networkProxy = BeanMapper.map(networkProxyEntity, NetworkProxy.class);
        return networkProxy;
    }

    @Override
    public List<NetworkProxy> findList(List<String> idList) {
        List<NetworkProxyEntity> networkProxyEntityList =  networkProxyDao.findNetworkProxyList(idList);

        List<NetworkProxy> networkProxyList =  BeanMapper.mapList(networkProxyEntityList,NetworkProxy.class);
        return networkProxyList;
    }

    @Override
    public NetworkProxy findNetworkProxy(@NotNull String id) {
        NetworkProxy networkProxy = findOne(id);

        joinTemplate.joinQuery(networkProxy);

        return networkProxy;
    }

    @Override
    public List<NetworkProxy> findAllNetworkProxy() {
        List<NetworkProxyEntity> networkProxyEntityList =  networkProxyDao.findAllNetworkProxy();

        List<NetworkProxy> networkProxyList =  BeanMapper.mapList(networkProxyEntityList,NetworkProxy.class);

        joinTemplate.joinQuery(networkProxyList);

        return networkProxyList;
    }

    @Override
    public List<NetworkProxy> findNetworkProxyList(NetworkProxyQuery networkProxyQuery) {
        List<NetworkProxyEntity> networkProxyEntityList = networkProxyDao.findNetworkProxyList(networkProxyQuery);

        List<NetworkProxy> networkProxyList = BeanMapper.mapList(networkProxyEntityList,NetworkProxy.class);

        joinTemplate.joinQuery(networkProxyList);

        return networkProxyList;
    }

    @Override
    public Pagination<NetworkProxy> findNetworkProxyPage(NetworkProxyQuery networkProxyQuery) {
        Pagination<NetworkProxyEntity>  pagination = networkProxyDao.findNetworkProxyPage(networkProxyQuery);

        List<NetworkProxy> networkProxyList = BeanMapper.mapList(pagination.getDataList(),NetworkProxy.class);

        joinTemplate.joinQuery(networkProxyList);

        return PaginationBuilder.build(pagination,networkProxyList);
    }

}