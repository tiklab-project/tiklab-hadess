package io.thoughtware.hadess.repository.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.hadess.repository.model.RepositoryMaven;
import io.thoughtware.hadess.repository.model.RepositoryMavenQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* RepositoryMavenService-maven存储库接口
*/
@JoinProvider(model = RepositoryMaven.class)
public interface RepositoryMavenService {

    /**
    * 创建
    * @param repositoryMaven
    * @return
    */
    String createRepositoryMaven(@NotNull @Valid RepositoryMaven repositoryMaven);

    /**
    * 更新
    * @param repositoryMaven
    */
    void updateRepositoryMaven(@NotNull @Valid RepositoryMaven repositoryMaven);

    /**
    * 删除
    * @param id
    */
    void deleteRepositoryMaven(@NotNull String id);

    /**
     * 通过条件删除
     * @param field  删除条件字段
     * @param value 删除字段值
     */
    void deleteRepositoryMavenByCondition(@NotNull String field ,@NotNull String value);

    @FindOne
    RepositoryMaven findOne(@NotNull String id);

    @FindList
    List<RepositoryMaven> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    RepositoryMaven findRepositoryMaven(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<RepositoryMaven> findAllRepositoryMaven();

    /**
    * 查询列表
    * @param repositoryMavenQuery
    * @return
    */
    List<RepositoryMaven> findRepositoryMavenList(RepositoryMavenQuery repositoryMavenQuery);

    /**
    * 按分页查询
    * @param repositoryMavenQuery
    * @return
    */
    Pagination<RepositoryMaven> findRepositoryMavenPage(RepositoryMavenQuery repositoryMavenQuery);

    /**
     * 通过仓库ids 和版本查询
     * @param ids
     * @return
     */
    RepositoryMaven findRepositoryMavenByRpyIds(String[] ids,String version);

}