package net.tiklab.xpack.repository.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.repository.model.RepositoryQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    * 查询列表
    * @param repositoryQuery
    * @return
    */
    List<Repository> findRepositoryList(RepositoryQuery repositoryQuery);


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