package net.tiklab.xpack.library.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.xpack.library.model.Library;
import net.tiklab.xpack.library.model.LibraryMaven;
import net.tiklab.xpack.library.model.LibraryMavenQuery;

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
     * @param single  single
     * @param library  library
     * @return
     */
    void libraryMavenSplice(String artifactId, String[]  single, Library library );

}