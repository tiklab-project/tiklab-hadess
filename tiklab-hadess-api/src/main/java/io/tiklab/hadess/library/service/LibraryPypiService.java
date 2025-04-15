package io.tiklab.hadess.library.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.library.model.LibraryPypi;
import io.tiklab.hadess.library.model.LibraryPypiQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* LibraryPypiService-Pypi类型制品差异数据接口
*/
public interface LibraryPypiService {

    /**
    * 创建
    * @param libraryPypi
    * @return
    */
    String createLibraryPypi(@NotNull @Valid LibraryPypi libraryPypi);

    /**
    * 更新
    * @param libraryPypi
    */
    void updateLibraryPypi(@NotNull @Valid LibraryPypi libraryPypi);

    /**
    * 删除
    * @param id
    */
    void deleteLibraryPypi(@NotNull String id);


    /**
     * 通过条件删除
     * @param field  删除条件字段
     * @param value 删除字段值
     */
    void deleteLibraryPypiByCondition(@NotNull String field ,@NotNull String value);


    LibraryPypi findOne(@NotNull String id);

    List<LibraryPypi> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    LibraryPypi findLibraryPypi(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<LibraryPypi> findAllLibraryPypi();

    /**
    * 查询列表
    * @param libraryPypiQuery
    * @return
    */
    List<LibraryPypi> findLibraryPypiList(LibraryPypiQuery libraryPypiQuery);

    /**
    * 按分页查询
    * @param libraryPypiQuery
    * @return
    */
    Pagination<LibraryPypi> findLibraryPypiPage(LibraryPypiQuery libraryPypiQuery);

}