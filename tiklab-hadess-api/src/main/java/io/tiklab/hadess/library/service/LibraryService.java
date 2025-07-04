package io.tiklab.hadess.library.service;


import io.tiklab.hadess.library.model.*;
import io.tiklab.hadess.repository.model.Repository;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

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
     * 通过仓库ids查询
     * @param repIds repIds
     */
    List<Library> findLibraryByRepIds(String[] repIds);


    /**
     * 通过仓库id查询
     * @param repId repId
     */
    List<Library> findLibraryByRepId(String repId);

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
     * @param type 制品类型  maven、npm、docker
     * @param  repId 仓库id
     * @return
     */
    Library findLibraryByCondition(String name,String type,String repId);

    /**
     * 通过制品名字、类型、制品ids查询
     * @param name 制品名字
     * @param type 制品类型  maven、npm
     * @param rpyIds 仓库ids
     */
    List<Library> findLibraryByCondition(String name,String type,String[] rpyIds);


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
     * @param libraryIds 制品ids
     * @param repositoryId 制品库id
     * @param LibraryName 制品名字
     */
    List<Library> findNotInLibraryList(String[] libraryIds,String repositoryId,String LibraryName);



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

    /**
     * 通过仓库id、制品名字查询制品
     * @param repositoryId 仓库id
     * @param name 制品名字 (包名)
     */
    List<Library> findLibraryList(String repositoryId, String name);

    /**通过类型、搜索信息分页查询制品文件
     * @param libraryQuery libraryQuery
     */
    Pagination<Library> findLibraryListByCond(LibraryQuery libraryQuery);
}