package io.tiklab.xpack.library.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.xpack.library.model.LibraryVersion;
import io.tiklab.xpack.library.model.LibraryVersionQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* LibraryVersionService-制品版本接口
*/
@JoinProvider(model = LibraryVersion.class)
public interface LibraryVersionService {

    /**
    * 创建
    * @param libraryVersion
    * @return
    */
    String createLibraryVersion(@NotNull @Valid LibraryVersion libraryVersion);

    /**
    * 更新
    * @param libraryVersion
    */
    void updateLibraryVersion(@NotNull @Valid LibraryVersion libraryVersion);

    /**
    * 删除
    * @param id
    */
    void deleteLibraryVersion(@NotNull String id);
    @FindOne
    LibraryVersion findOne(@NotNull String id);
    @FindList
    List<LibraryVersion> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    LibraryVersion findLibraryVersion(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<LibraryVersion> findAllLibraryVersion();

    /**
    * 查询列表
    * @param libraryVersionQuery
    * @return
    */
    List<LibraryVersion> findLibraryVersionList(LibraryVersionQuery libraryVersionQuery);

    /**
    * 按分页查询
    * @param libraryVersionQuery
    * @return
    */
    Pagination<LibraryVersion> findLibraryVersionPage(LibraryVersionQuery libraryVersionQuery);

    /**
     * 查询最新版本
     * @param libraryVersionQuery
     * @return
     */
    LibraryVersion findLibraryNewVersion(LibraryVersionQuery libraryVersionQuery);

    /**
     *  制品版本创建、修改
     * @param libraryVersion    制品版本数据
     * @return
     */
     String libraryVersionSplice( LibraryVersion libraryVersion);
}