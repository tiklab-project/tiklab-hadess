package io.thoughtware.hadess.repository.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.hadess.repository.model.RemoteProxy;
import io.thoughtware.hadess.repository.model.RemoteProxyQuery;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RemoteProxyService-代理地址接口
*/
@JoinProvider(model = RemoteProxy.class)
public interface RemoteProxyService {

    /**
    * 创建
    * @param remoteProxy
    * @return
    */
    String createRemoteProxy(@NotNull @Valid RemoteProxy remoteProxy);

    /**
    * 更新
    * @param remoteProxy
    */
    void updateRemoteProxy(@NotNull @Valid RemoteProxy remoteProxy);

    /**
    * 删除
    * @param id
    */
    void deleteRemoteProxy(@NotNull String id);

    @FindOne
    RemoteProxy findOne(@NotNull String id);

    @FindList
    List<RemoteProxy> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    RemoteProxy findRemoteProxy(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<RemoteProxy> findAllRemoteProxy();

    /**
    * 查询列表
    * @param remoteProxyQuery
    * @return
    */
    List<RemoteProxy> findRemoteProxyList(RemoteProxyQuery remoteProxyQuery);

    /**
    * 按分页查询
    * @param remoteProxyQuery
    * @return
    */
    Pagination<RemoteProxy> findRemoteProxyPage(RemoteProxyQuery remoteProxyQuery);

    /**
     * 通过仓库id查询代理
     * @param repositoryId
     * @return
     */
    List<RemoteProxy> findProxyListByRpyId(String repositoryId);
    /**
     * 通过代理地址的id 查询是关联的仓库
     * @param id
     * @return
     */
    List<String> findRepositoryByProxyId(String id);
}