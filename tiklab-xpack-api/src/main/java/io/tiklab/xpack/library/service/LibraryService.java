package io.tiklab.xpack.library.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.xpack.library.model.Library;
import io.tiklab.xpack.library.model.LibraryQuery;
import io.tiklab.xpack.repository.model.Repository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* LibraryService-制品接口
*/
@JoinProvider(model = Library.class)
public interface LibraryService {

    /**
    * 创建
    * @param library
    * @return
    */
    String createLibrary(@NotNull @Valid Library library);

    /**
    * 更新
    * @param library
    */
    void updateLibrary(@NotNull @Valid Library library);

    /**
    * 删除
    * @param id
    */
    void deleteLibrary(@NotNull String id);

    /**
     * 通过制品库删除
     * @param repositoryId 制品库id
     */
    void deleteLibraryByRepository(@NotNull String repositoryId);


    @FindOne
    Library findOne(@NotNull String id);

    @FindList
    List<Library> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    Library findLibrary(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<Library> findAllLibrary();

    /**
    * 查询列表
    * @param libraryQuery
    * @return
    */
    List<Library> findLibraryList(LibraryQuery libraryQuery);

    /**
     * 通过制品库查询制品数量
     * @param repositoryId 制品库id
     * @return
     */
    Integer findLibraryNum(String  repositoryId);

    /**
     * 通过制品名字查询制品
     * @param name 制品名字
     * @param type 制品类型  maven、npm
     * @param version  版本, Release 、Snapshot
     * @return
     */
    Library findLibraryByName(String name,String type,String version);


    /**
     * 查询制品库下面的制品
     * @param libraryQuery
     * @return
     */
    Pagination<Library> findLibraryListByRepository(LibraryQuery libraryQuery);

    /**
    * 按分页查询
    * @param libraryQuery
    * @return
    */
    Pagination<Library> findLibraryPage(LibraryQuery libraryQuery);


    /**
     *  制品创建
     * @param libraryName     制品文件
     * @param libraryType  制品类型
     * @param repository  制品库信息
     * @return
     */
     Library createLibraryData(String libraryName, String libraryType, Repository repository);

    /**
     * 条件查询制品文件
     * @param libraryQuery
     * @return
     */
    Pagination<Library> findLibraryListByCondition(LibraryQuery libraryQuery);

    /**
     * 查询未添加到推送中央仓库的记录的制品列表
     * @param libraryQuery
     * @return
     */
    List<Library> findNotPushLibraryList(LibraryQuery libraryQuery);


}