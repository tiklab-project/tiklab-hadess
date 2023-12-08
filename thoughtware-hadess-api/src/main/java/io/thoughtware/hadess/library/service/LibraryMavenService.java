package io.thoughtware.hadess.library.service;


import io.thoughtware.hadess.library.model.Library;
import io.thoughtware.hadess.library.model.LibraryMaven;
import io.thoughtware.hadess.library.model.LibraryMavenQuery;
import io.thoughtware.core.page.Pagination;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* LibraryMavenService-maven类型制品差异数据接口
*/
public interface LibraryMavenService {

    /**
    * 创建
    * @param libraryMaven
    * @return
    */
    String createLibraryMaven(@NotNull @Valid LibraryMaven libraryMaven);

    /**
    * 更新
    * @param libraryMaven
    */
    void updateLibraryMaven(@NotNull @Valid LibraryMaven libraryMaven);

    /**
    * 删除
    * @param id
    */
    void deleteLibraryMaven(@NotNull String id);


    /**
     * 通过条件删除
     * @param field  删除条件字段
     * @param value 删除字段值
     */
    void deleteLibraryMavenByCondition(@NotNull String field ,@NotNull String value);


    LibraryMaven findOne(@NotNull String id);

    List<LibraryMaven> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    LibraryMaven findLibraryMaven(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<LibraryMaven> findAllLibraryMaven();

    /**
    * 查询列表
    * @param libraryMavenQuery
    * @return
    */
    List<LibraryMaven> findLibraryMavenList(LibraryMavenQuery libraryMavenQuery);

    /**
    * 按分页查询
    * @param libraryMavenQuery
    * @return
    */
    Pagination<LibraryMaven> findLibraryMavenPage(LibraryMavenQuery libraryMavenQuery);

    /**
     *  制品maven创建、修改
     * @param artifactId     artifactId
     * @param groupId  groupId
     * @param library  library
     * @return
     */
    void libraryMavenSplice(String artifactId, String  groupId, Library library );


    /**
     *  通过制品的ids  查询
     * @param libraryIds     libraryIds
     * @return
     */
    List<LibraryMaven> libraryMavenByLibraryIds(String[] libraryIds );

}