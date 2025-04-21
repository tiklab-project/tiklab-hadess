package io.tiklab.hadess.library.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.library.model.LibraryComposer;
import io.tiklab.hadess.library.model.LibraryComposerQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* LibraryComposerService-Composer类型制品差异数据接口
*/
public interface LibraryComposerService {

    /**
    * 创建
    * @param libraryComposer
    * @return
    */
    String createLibraryComposer(@NotNull @Valid LibraryComposer libraryComposer);

    /**
    * 更新
    * @param libraryComposer
    */
    void updateLibraryComposer(@NotNull @Valid LibraryComposer libraryComposer);

    /**
    * 删除
    * @param id
    */
    void deleteLibraryComposer(@NotNull String id);


    /**
     * 通过条件删除
     * @param field  删除条件字段
     * @param value 删除字段值
     */
    void deleteLibraryComposerByCondition(@NotNull String field ,@NotNull String value);


    LibraryComposer findOne(@NotNull String id);

    List<LibraryComposer> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    LibraryComposer findLibraryComposer(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<LibraryComposer> findAllLibraryComposer();

    /**
    * 查询列表
    * @param libraryComposerQuery
    * @return
    */
    List<LibraryComposer> findLibraryComposerList(LibraryComposerQuery libraryComposerQuery);

    /**
    * 按分页查询
    * @param libraryComposerQuery
    * @return
    */
    Pagination<LibraryComposer> findLibraryComposerPage(LibraryComposerQuery libraryComposerQuery);

}