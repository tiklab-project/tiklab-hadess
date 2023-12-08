package io.thoughtware.hadess.scan.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;
import io.thoughtware.hadess.scan.model.ScanRecord;
import io.thoughtware.hadess.scan.model.ScanRecordQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanRecordService-扫描制品数据
*/
@JoinProvider(model = ScanRecord.class)
public interface ScanRecordService {

    /**
    * 创建
    * @param scanRecord
    * @return
    */
    String createScanRecord(@NotNull @Valid ScanRecord scanRecord);

    /**
    * 更新
    * @param scanRecord
    */
    void updateScanRecord(@NotNull @Valid ScanRecord scanRecord);

    /**
    * 删除
    * @param id
    */
    void deleteScanRecord(@NotNull String id);

    /**
     * 条件删除
     * @param key
     * @param  value
     */
    void deleteScanRecordByCondition(@NotNull String key,@NotNull String value);

    /**
     * 通过条件组删除
     * @param scanGroup
     */
    void deleteScanRecordByGroup(String scanGroup);

    @FindOne
    ScanRecord findOne(@NotNull String id);

    @FindList
    List<ScanRecord> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    ScanRecord findScanRecord(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanRecord> findAllScanRecord();

    /**
    * 查询列表
    * @param scanRecordQuery
    * @return
    */
    List<ScanRecord> findScanRecordList(ScanRecordQuery scanRecordQuery);

    /**
     * 通过扫描记录查询最新扫描结果
     * @param scanRecordId
     * @return
     */
     ScanRecord findNewScanRecord(String scanRecordId);

    /**
     * 查询列表 不joinTemplate 查询
     * @param scanRecordQuery
     * @return
     */
    List<ScanRecord> findScanRecordListNoJoin(ScanRecordQuery scanRecordQuery);

    /**
    * 按分页查询
    * @param scanRecordQuery
    * @return
    */
    Pagination<ScanRecord> findScanRecordPage(ScanRecordQuery scanRecordQuery);

    /**
     * 查询扫描计划的总报告
     * @param scanRecordQuery
     * @return
     */
    List<ScanRecord> findScanRecordByPlay(ScanRecordQuery scanRecordQuery);

    /**
     * 通过group查询扫描结果
     * @param scanGroup
     */
    ScanRecord findScanRecordByGroup(String scanGroup);

    /**
     * 条件查询有漏洞的扫描依赖树
     * @param scanGroup
     */
    List<ScanRecord> findHaveHoleRelyTreeList(String scanGroup);


}