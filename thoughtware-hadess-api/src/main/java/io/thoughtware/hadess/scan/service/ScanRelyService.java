package io.thoughtware.hadess.scan.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.hadess.scan.model.ScanRely;
import io.thoughtware.hadess.scan.model.ScanRelyQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanRelyService-扫描结果
*/
@JoinProvider(model = ScanRely.class)
public interface ScanRelyService {

    /**
    * 创建
    * @param scanRely
    * @return
    */
    String createScanRely(@NotNull @Valid ScanRely scanRely);

    /**
    * 更新
    * @param scanRely
    */
    void updateScanRely(@NotNull @Valid ScanRely scanRely);

    /**
    * 删除
    * @param id
    */
    void deleteScanRely(@NotNull String id);

    /**
     * 条件删除扫描依赖
     * @param  key  删除条件字段
     * @param value
     */
    void deleteScanRelyByCondition(@NotNull String key,@NotNull String value);

    /**
     * 通过扫描记录的ids 删除
     * @param  recordIds
     */
    void deleteScanRelyByRecordIds(@NotNull StringBuilder recordIds);

    @FindOne
    ScanRely findOne(@NotNull String id);

    @FindList
    List<ScanRely> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    ScanRely findScanRely(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanRely> findAllScanRely();

    /**
    * 查询列表
    * @param scanRelyQuery
    * @return
    */
    List<ScanRely> findScanRelyList(ScanRelyQuery scanRelyQuery);

    /**
    * 按分页查询
    * @param scanRelyQuery
    * @return
    */
    Pagination<ScanRely> findScanRelyPage(ScanRelyQuery scanRelyQuery);

    /**
     * 按扫描制品依赖树
     * @param scanRelyQuery
     * @return
     */
    List<ScanRely> findScanRelyTreeList(ScanRelyQuery scanRelyQuery);

    /**
     * 条件查询有漏洞的扫描依赖树
     * @param scanRelyQuery
     * @return
     */
    List<ScanRely> findHaveHoleRelyTreeList(ScanRelyQuery scanRelyQuery);

    /**
     * 条件查询有漏洞的扫描依赖树
     * @param scanGroup
     * @return
     */
    List<ScanRely> findHaveHoleRelyTreeList(String scanGroup);

    /**
     * 通过recordIds 查询
     * @param scanRecordIds
     * @return
     */
    List<ScanRely> findScanRelyListByRecordIds(String [] scanRecordIds);

}