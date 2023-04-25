package io.tiklab.xpack.repository.service;


import io.tiklab.xpack.repository.model.RepositoryGroup;
import io.tiklab.core.page.Pagination;
import io.tiklab.xpack.repository.model.RepositoryGroupQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RepositoryGroupService-组合库关联接口
*/
public interface RepositoryGroupService {

    /**
    * 创建
    * @param repositoryGroup
    * @return
    */
    String createRepositoryGroup(@NotNull @Valid RepositoryGroup repositoryGroup);

    /**
    * 更新
    * @param repositoryGroup
    */
    void updateRepositoryGroup(@NotNull @Valid RepositoryGroup repositoryGroup);

    /**
    * 删除
    * @param id
    */
    void deleteRepositoryGroup(@NotNull String id);

    /**
     * 通过制品库id删除
     * @param repositoryId
     */
    void deleteGroupItemsByRepositoryId(@NotNull String repositoryId);

    RepositoryGroup findOne(@NotNull String id);

    List<RepositoryGroup> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    RepositoryGroup findRepositoryGroup(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<RepositoryGroup> findAllRepositoryGroup();

    /**
    * 查询列表
    * @param repositoryGroupQuery
    * @return
    */
    List<RepositoryGroup> findRepositoryGroupList(RepositoryGroupQuery repositoryGroupQuery);

    /**
    * 按分页查询
    * @param repositoryGroupQuery
    * @return
    */
    Pagination<RepositoryGroup> findRepositoryGroupPage(RepositoryGroupQuery repositoryGroupQuery);

    /**
     * 根据条件编辑组合库关联
     * @param repositoryGroupQuery
     * @return
     */
    void compileRepositoryGroup(RepositoryGroupQuery repositoryGroupQuery);
}