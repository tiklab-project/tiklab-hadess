package net.tiklab.xpack.repository.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.xpack.repository.model.RepositoryClusterCfg;
import net.tiklab.xpack.repository.model.RepositoryClusterCfgQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RepositoryClusterCfgService
*/
public interface RepositoryClusterCfgService {

    /**
    * 创建
    * @param repositoryClusterCfg
    * @return
    */
    String createRepositoryClusterCfg(@NotNull @Valid RepositoryClusterCfg repositoryClusterCfg);

    /**
    * 更新
    * @param repositoryClusterCfg
    */
    void updateRepositoryClusterCfg(@NotNull @Valid RepositoryClusterCfg repositoryClusterCfg);

    /**
    * 删除
    * @param id
    */
    void deleteRepositoryClusterCfg(@NotNull String id);

    RepositoryClusterCfg findOne(@NotNull String id);

    List<RepositoryClusterCfg> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    RepositoryClusterCfg findRepositoryClusterCfg(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<RepositoryClusterCfg> findAllRepositoryClusterCfg();

    /**
    * 查询列表
    * @param repositoryClusterCfgQuery
    * @return
    */
    List<RepositoryClusterCfg> findRepositoryClusterCfgList(RepositoryClusterCfgQuery repositoryClusterCfgQuery);

    /**
    * 按分页查询
    * @param repositoryClusterCfgQuery
    * @return
    */
    Pagination<RepositoryClusterCfg> findRepositoryClusterCfgPage(RepositoryClusterCfgQuery repositoryClusterCfgQuery);

}