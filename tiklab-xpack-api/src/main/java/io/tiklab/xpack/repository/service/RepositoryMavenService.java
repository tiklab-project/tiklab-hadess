package io.tiklab.xpack.repository.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.xpack.repository.model.RepositoryMaven;
import io.tiklab.xpack.repository.model.RepositoryMavenQuery;

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

    @FindOne
    RepositoryMaven findOne(@NotNull String id);

    List<RepositoryMaven> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    @FindList
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

}