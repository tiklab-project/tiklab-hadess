package io.thoughtware.hadess.scan.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.hadess.scan.model.ScanLibrary;
import io.thoughtware.hadess.scan.model.ScanLibraryQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanLibraryService-扫描制品
*/
@JoinProvider(model = ScanLibrary.class)
public interface ScanLibraryService {

    /**
    * 创建
    * @param scanLibrary
    * @return
    */
    String createScanLibrary(@NotNull @Valid ScanLibrary scanLibrary);

    /**
    * 更新
    * @param scanLibrary
    */
    void updateScanLibrary(@NotNull @Valid ScanLibrary scanLibrary);

    /**
    * 删除
    * @param id
    */
    void deleteScanLibrary(@NotNull String id);


    @FindOne
    ScanLibrary findOne(@NotNull String id);

    @FindList
    List<ScanLibrary> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    ScanLibrary findScanLibrary(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanLibrary> findAllScanLibrary();

    /**
    * 查询列表
    * @param scanLibraryQuery
    * @return
    */
    List<ScanLibrary> findScanLibraryList(ScanLibraryQuery scanLibraryQuery);

    /**
     * 查询列表 不joinTemplate 查询
     * @param scanLibraryQuery
     * @return
     */
    List<ScanLibrary> findScanLibraryListNoJoin(ScanLibraryQuery scanLibraryQuery);

    /**
    * 按分页查询
    * @param scanLibraryQuery
    * @return
    */
    Pagination<ScanLibrary> findScanLibraryPage(ScanLibraryQuery scanLibraryQuery);

}