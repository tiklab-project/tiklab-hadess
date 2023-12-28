package io.thoughtware.hadess.library.service;


import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.hadess.library.model.Library;
import io.thoughtware.hadess.library.model.LibraryFile;
import io.thoughtware.hadess.library.model.LibraryQuery;
import io.thoughtware.hadess.library.model.LibraryVersion;

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
     * 更新制品大小
     * @param libraryFile
     */
    void updateMvnLibrarySize(LibraryFile libraryFile, LibraryVersion libraryVersion, Long fileSize);

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
     * 制品匹配仓库
     * @param libraryQuery
     * @return
     */
    List<Library> likeLibraryListNo(LibraryQuery libraryQuery);

    /**
     * 制品匹配仓库
     * @param libraryQuery
     * @return
     */
    List<Library> likeLibraryByName(LibraryQuery libraryQuery);

    /**
     * 通过制品库查询制品数量
     * @param repositoryId 制品库id
     * @param  repositoryType 仓库类型                     
     * @return
     */
    Integer findLibraryNum(String  repositoryId,String repositoryType);

    /**
     * 通过制品名字查询制品和类型查询
     * @param name 制品名字
     * @param type 制品类型  maven、npm
     * @param version  版本, Release 、Snapshot
     * @return
     */
    Library findLibraryByNameAndType(String name,String type);



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

    List<Library> findEqLibraryList(LibraryQuery libraryQuery);

    /**
     * 分页查询扫描制品
     * @param libraryQuery
     * @return
     */
    Pagination<Library> findScanLibraryPage(LibraryQuery libraryQuery);

    /**
     * 查询未添加到扫描列表的制品
     * @param libraryQuery
     * @return
     */
    List<Library> findNotScanLibraryList(LibraryQuery libraryQuery);

    void updateFile();


    /**
     * 通过制品名字查询和groupId 查询mvn制品
     * @param  repository   组合库
     * @param name 制品名字
     * @param groupId 制品groupId
     * @param  type    Snapshot 快照版本、Release 正式版本
     * @return
     */
    Library findMvnLibraryByGroupId(Repository repository,String name,String groupId,String type);


    /**
     * 创建mvn 类型的制品
     * @param repository 制品库
     * @param libraryName 制品名字
     * @param groupId 制品groupId
     * @return
     */
    Library createMvnLibrary(Repository repository,String libraryName,String groupId);
}