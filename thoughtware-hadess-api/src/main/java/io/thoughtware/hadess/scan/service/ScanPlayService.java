package io.thoughtware.hadess.scan.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;
import io.thoughtware.hadess.scan.model.ScanPlay;
import io.thoughtware.hadess.scan.model.ScanPlayQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanPlayService-扫描计划
*/
@JoinProvider(model = ScanPlay.class)
public interface ScanPlayService {

    /**
    * 创建
    * @param scanPlay
    * @return
    */
    String createScanPlay(@NotNull @Valid ScanPlay scanPlay);

    /**
    * 更新
    * @param scanPlay
    */
    void updateScanPlay(@NotNull @Valid ScanPlay scanPlay);

    /**
    * 删除
    * @param id
    */
    void deleteScanPlay(@NotNull String id);

    /**
     * 条件删除扫描结果
     * @param  key  删除条件字段
     * @param value
     */
    void deleteScanPlayByCondition(@NotNull String key,@NotNull String value);

    @FindOne
    ScanPlay findOne(@NotNull String id);

    @FindList
    List<ScanPlay> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    ScanPlay findScanPlay(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanPlay> findAllScanPlay();

    /**
    * 查询列表
    * @param scanPlayQuery
    * @return
    */
    List<ScanPlay> findScanPlayList(ScanPlayQuery scanPlayQuery);

    /**
    * 按分页查询
    * @param scanPlayQuery
    * @return
    */
    Pagination<ScanPlay> findScanPlayPage(ScanPlayQuery scanPlayQuery);

}