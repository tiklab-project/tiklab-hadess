package net.tiklab.xpack.library.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.xpack.library.model.LibraryVersion;
import net.tiklab.xpack.library.model.LibraryVersionQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* LibraryVersionService
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
}