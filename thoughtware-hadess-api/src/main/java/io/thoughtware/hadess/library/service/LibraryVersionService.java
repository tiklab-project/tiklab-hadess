package io.thoughtware.hadess.library.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.hadess.library.model.LibraryVersion;
import io.thoughtware.hadess.library.model.LibraryVersionQuery;

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

    /**
     * 带制品id 删除  先查询该版本是否为最后一个版本、最后一个版本把该制品也删除掉
     * @param id
     */
    void deleteLibraryVersion(@NotNull String id,String libraryId);

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
     * 查询列表
     * @param repositoryId
     * @return
     */
    List<LibraryVersion> findLibraryVersionList(String repositoryId);

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
     * @param  fileName 制品文件名字
     * @return
     */
     String libraryVersionSplice( LibraryVersion libraryVersion,String fileName);


    /**
     * 通过条件删除版本
     * @param field  删除条件字段
     * @param value 删除字段值
     */
    void deleteVersionByCondition(String field,String value);

    /**
     * 通过版本好和制品名字查询
     * @param libraryName  制品名字
     * @param version 版本好
     */
    LibraryVersion findVersionByNameAndVer(String libraryName,String version);

    /**
     * 通过id 查询
     * @param versionId
     */
    LibraryVersion findLibraryVersionById(String versionId);


    /**
     * 分页查询历史版本
     * @param libraryVersionQuery
     */
    Pagination<LibraryVersion> findHistoryVersionPage(LibraryVersionQuery libraryVersionQuery);

     String librarySize( String libraryVersionId);

    /**
     * 通过制品ids 查询
     * @param libraryIds
     */
    List<LibraryVersion> findVersionByLibraryIds(String[] libraryIds);
}