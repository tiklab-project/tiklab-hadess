package io.tiklab.hadess.library.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.library.model.LibraryFile;
import io.tiklab.hadess.library.model.LibraryFileQuery;

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
     * 通过快照版本和版本id删除
     * @param versionId 版本id
     * @param snapshotVersion 时间搓版本
     */
    void deleteLibraryFile(@NotNull String versionId,@NotNull String snapshotVersion);


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
     * 查询列表
     * @param libraryFileQuery
     * @return
     */
    List<LibraryFile> findLibraryFiles(LibraryFileQuery libraryFileQuery);

    /**
     * 通过制品id 查询
     * @param libraryId  制品id
     * @return
     */
    List<LibraryFile> findLibraryFileList(String libraryId);

    /**
    * 按分页查询
    * @param libraryFileQuery
    * @return
    */
    Pagination<LibraryFile> findLibraryFilePage(LibraryFileQuery libraryFileQuery);


    /**
     * 查询最新版本的文件列表
     * @param libraryFileQuery
     * @return
     */
    List<LibraryFile> findLibraryNewFileList(LibraryFileQuery libraryFileQuery);

    /**
     * 编辑制品文件
     * @param libraryFile
     */
    String redactLibraryFile(LibraryFile libraryFile);

    /**
     * 通过地址like 查询
     * @param fileUrl 地址
     * @return
     */
    List<LibraryFile> findLibraryLikeFileUrl(String fileUrl);

    /**
     * 通过制品库和制品以及版本查询
     * @param repositoryId  制品库id
     * @param libraryName   制品名称
     * @param version       版本
     * @return Pagination <LibraryFileEntity>
     */
    List<LibraryFile> findFileByReAndLibraryAndVer(String repositoryId,String libraryName,String version);

    /**
     * 通过制品库和制品以及版本查询
     * @param repositoryId  制品库id
     * @param libraryName   制品名称
     * @param version       版本
     * @return Pagination <LibraryFileEntity>
     */
    List<LibraryFile> findFileByReAndLibraryAndVer(String[] repositoryId,String libraryName,String version);

    /**
     * 查询docker 文件的镜像文件
     * @return Pagination <LibraryFileEntity>
     */
    List<LibraryFile> dockerFile(LibraryFile libraryFile);


    /**
     * 通过仓库ids、路径查询
     * @param repositoryIds 制品库ids
     * @param  relativePath 文件相对的路径
     */
    List<LibraryFile> findLibraryFileList(String[] repositoryIds ,String relativePath);

    /**
     * 读取制品文件内容
     * @param libraryFileQuery libraryFileQuery
     */
    String readLibraryFileData(LibraryFileQuery libraryFileQuery);

    /**
     * 查询docker镜像的历史层
     * @param libraryFileQuery libraryFileQuery
     */
    List<String> findDockerLayers(LibraryFileQuery libraryFileQuery);
}