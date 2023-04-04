package io.tiklab.xpack.repository.service;


import io.tiklab.xpack.repository.model.RepositoryRemoteProxy;
import io.tiklab.xpack.repository.model.RepositoryRemoteProxyQuery;
import io.tiklab.core.page.Pagination;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RepositoryRemoteProxyService-远程库代理信息
*/
public interface RepositoryRemoteProxyService {

    /**
    * 创建
    * @param repositoryRemoteProxy
    * @return
    */
    String createRepositoryRemoteProxy(@NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy);

    /**
    * 更新
    * @param repositoryRemoteProxy
    */
    void updateRepositoryRemoteProxy(@NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy);

    /**
    * 删除
    * @param id
    */
    void deleteRepositoryRemoteProxy(@NotNull String id);

    RepositoryRemoteProxy findOne(@NotNull String id);

    List<RepositoryRemoteProxy> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    RepositoryRemoteProxy findRepositoryRemoteProxy(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<RepositoryRemoteProxy> findAllRepositoryRemoteProxy();

    /**
    * 查询列表
    * @param repositoryRemoteProxyQuery
    * @return
    */
    List<RepositoryRemoteProxy> findRepositoryRemoteProxyList(RepositoryRemoteProxyQuery repositoryRemoteProxyQuery);



    /**
    * 按分页查询
    * @param repositoryRemoteProxyQuery
    * @return
    */
    Pagination<RepositoryRemoteProxy> findRepositoryRemoteProxyPage(RepositoryRemoteProxyQuery repositoryRemoteProxyQuery);

}