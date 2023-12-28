package io.thoughtware.hadess.scan.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.hadess.scan.model.ScanResult;
import io.thoughtware.hadess.scan.model.ScanResultQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanResultService-扫描结果
*/
@JoinProvider(model = ScanResult.class)
public interface ScanResultService {

    /**
    * 创建
    * @param scanResult
    * @return
    */
    String createScanResult(@NotNull @Valid ScanResult scanResult);

    /**
    * 更新
    * @param scanResult
    */
    void updateScanResult(@NotNull @Valid ScanResult scanResult);

    /**
    * 删除
    * @param id
    */
    void deleteScanResult(@NotNull String id);

    /**
     * 条件删除扫描结果
     * @param  key  删除条件字段
     * @param value
     */
    void deleteScanResultByCondition(@NotNull String key,@NotNull String value);

    /**
     * 通过扫描记录的ids 删除
     * @param  recordIds
     */
    void deleteScanResultByRecordIds(@NotNull StringBuilder recordIds);

    @FindOne
    ScanResult findOne(@NotNull String id);

    @FindList
    List<ScanResult> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    ScanResult findScanResult(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanResult> findAllScanResult();

    /**
    * 查询列表
    * @param scanResultQuery
    * @return
    */
    List<ScanResult> findScanResultList(ScanResultQuery scanResultQuery);

    /**
    * 按分页查询
    * @param scanResultQuery
    * @return
    */
    Pagination<ScanResult> findScanResultPage(ScanResultQuery scanResultQuery);

    /**
     * 通过recordIds 查询
     * @param scanRecordIds
     * @return
     */
    List<ScanResult> findScanResultByRecordIds(String [] scanRecordIds);


}