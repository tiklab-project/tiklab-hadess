package io.thoughtware.hadess.repository.service;


import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.hadess.repository.model.RepositoryQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RepositoryService-制品库接口
*/
@JoinProvider(model = Repository.class)
public interface RepositoryService {

    /**
    * 创建
    * @param repository
    * @return
    */
    String createRepository(@NotNull @Valid Repository repository);

    /**
    * 更新
    * @param repository
    */
    void updateRepository(@NotNull @Valid Repository repository);

    /**
    * 删除
    * @param id
    */
    void deleteRepository(@NotNull String id);

     Repository findOneRepository(String id);

    @FindOne
    Repository findOne(@NotNull String id);

    @FindList
    List<Repository> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    Repository findRepository(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<Repository> findAllRepository();

    /**
    * 条件查询仓库查询列表
    * @param repositoryQuery
    * @return
    */
    List<Repository> findRepositoryList(RepositoryQuery repositoryQuery);


    /**
     * 查询公共库
     * @param type 制品库类型 npm、maven
     * @param  repositoryType 制品库种类 远程库、本地库
     * @return
     */
    List<Repository> findPublicRepositoryList(String type,String repositoryType);

    /**
     * 根据仓库名字查询
     * @param repositoryName 仓库名字
     * @return
     */
    Repository findRepositoryByName(String repositoryName);


    /**
     * 条件查询仓库
     * @param repositoryName 仓库名字
     * @param  type 仓库类型 maven、npm、docker
     * @return
     */
    Repository findRepository(String repositoryName,String type);

    /**
     * 查询本地和远程库
     * @param
     * @return
     */
    List<Repository> findLocalAndRemoteRepository(String type);


    /**
    * 按分页查询
    * @param repositoryQuery
    * @return
    */
    Pagination<Repository> findRepositoryPage(RepositoryQuery repositoryQuery);

    /**
     * 根据组合库id查询相关联的制品库
     * @param repositoryGroupId 组合库id
     * @return
     */
    List<Repository> findRepositoryByGroup(String repositoryGroupId);

    /**
     * 通过类型查询未关联组合库的本地和远程库list
     * @param repositoryType 类型
     * @return
     */
    List<Repository> findUnRelevanceRepository(String repositoryType,String repositoryGroupId);


}