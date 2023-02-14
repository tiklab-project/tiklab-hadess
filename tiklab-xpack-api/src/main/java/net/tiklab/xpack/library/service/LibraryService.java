package net.tiklab.xpack.library.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.xpack.library.model.Library;
import net.tiklab.xpack.library.model.LibraryQuery;
import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.repository.model.Storage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
* LibraryService
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
     * 查询maven制品
     * @param libraryQuery
     * @return
     */
    List<Library> findMavenLibraryList(LibraryQuery libraryQuery);

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



}