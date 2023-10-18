package io.tiklab.xpack.scan.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.xpack.scan.model.ScanRely;
import io.tiklab.xpack.scan.model.ScanRelyQuery;

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

}