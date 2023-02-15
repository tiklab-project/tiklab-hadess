package net.tiklab.xpack.repository.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.xpack.repository.model.RepositoryGroupItems;
import net.tiklab.xpack.repository.model.RepositoryGroupItemsQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RepositoryGroupItemsService
*/
public interface RepositoryGroupItemsService {

    /**
    * 创建
    * @param repositoryGroupItems
    * @return
    */
    String createRepositoryGroupItems(@NotNull @Valid RepositoryGroupItems repositoryGroupItems);

    /**
    * 更新
    * @param repositoryGroupItems
    */
    void updateRepositoryGroupItems(@NotNull @Valid RepositoryGroupItems repositoryGroupItems);

    /**
    * 删除
    * @param id
    */
    void deleteRepositoryGroupItems(@NotNull String id);

    /**
     * 通过制品库id删除
     * @param repositoryId
     */
    void deleteGroupItemsByRepositoryId(@NotNull String repositoryId);

    RepositoryGroupItems findOne(@NotNull String id);

    List<RepositoryGroupItems> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    RepositoryGroupItems findRepositoryGroupItems(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<RepositoryGroupItems> findAllRepositoryGroupItems();

    /**
    * 查询列表
    * @param repositoryGroupItemsQuery
    * @return
    */
    List<RepositoryGroupItems> findRepositoryGroupItemsList(RepositoryGroupItemsQuery repositoryGroupItemsQuery);

    /**
    * 按分页查询
    * @param repositoryGroupItemsQuery
    * @return
    */
    Pagination<RepositoryGroupItems> findRepositoryGroupItemsPage(RepositoryGroupItemsQuery repositoryGroupItemsQuery);

    /**
     * 根据条件编辑组合库关联
     * @param repositoryGroupItems
     * @return
     */
    String compileRepositoryGroupItems(RepositoryGroupItems repositoryGroupItems);
}