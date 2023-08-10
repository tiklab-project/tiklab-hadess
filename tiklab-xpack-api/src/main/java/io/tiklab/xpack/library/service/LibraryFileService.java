package io.tiklab.xpack.library.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.xpack.library.model.LibraryFile;
import io.tiklab.xpack.library.model.LibraryFileQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* LibraryFileService-制品文件接口
*/
public interface LibraryFileService {

    /**
    * 创建
    * @param libraryFile
    * @return
    */
    String createLibraryFile(@NotNull @Valid LibraryFile libraryFile);

    /**
    * 更新
    * @param libraryFile
    */
    void updateLibraryFile(@NotNull @Valid LibraryFile libraryFile);

    /**
    * 删除
    * @param id
    */
    void deleteLibraryFile(@NotNull String id);

    /**
     * 通过条件删除
     * @param field  删除条件字段
     * @param value 删除字段值
     */
    void deleteLibraryFileByCondition(@NotNull String field ,@NotNull String value);

    LibraryFile findOne(@NotNull String id);

    List<LibraryFile> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    LibraryFile findLibraryFile(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    List<LibraryFile> findAllLibraryFile();

    /**
    * 查询列表
    * @param libraryFileQuery
    * @return
    */
    List<LibraryFile> findLibraryFileList(LibraryFileQuery libraryFileQuery);

    /**
    * 按分页查询
    * @param libraryFileQuery
    * @return
    */
    Pagination<LibraryFile> findLibraryFilePage(LibraryFileQuery libraryFileQuery);

    /**
     * 通过制品库id查询
     * @param repositoryIds 制品库ids
     * @param  fileName 文件名称
     * @return
     */
    List<LibraryFile> findLibraryFileByLibraryId(String[] repositoryIds ,String fileName);

    /**
     * 查询最新版本的文件列表
     * @param libraryFileQuery
     * @return
     */
    List<LibraryFile> findLibraryNewFileList(LibraryFileQuery libraryFileQuery);

    /**
     * 查询最新版本的文件列表
     * @param libraryFile
     * @param versionId
     * @return
     */
     void libraryFileSplice(LibraryFile libraryFile,String versionId);

    public void test();

}