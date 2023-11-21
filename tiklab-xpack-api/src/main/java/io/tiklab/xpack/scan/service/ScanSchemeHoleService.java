package io.tiklab.xpack.scan.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.xpack.scan.model.ScanSchemeHole;
import io.tiklab.xpack.scan.model.ScanSchemeHoleQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanPlaySchemeService-扫描方案漏洞关系
*/
@JoinProvider(model = ScanSchemeHole.class)
public interface ScanSchemeHoleService {

    /**
    * 创建
    * @param scanSchemeHole
    * @return
    */
    String createScanSchemeHole(@NotNull @Valid ScanSchemeHole scanSchemeHole);

    /**
    * 更新
    * @param scanSchemeHole
    */
    void updateScanSchemeHole(@NotNull @Valid ScanSchemeHole scanSchemeHole);

    /**
    * 删除
    * @param id
    */
    void deleteScanSchemeHole(@NotNull String id);

    /**
     * 条件删除扫描结果
     * @param  key  删除条件字段
     * @param value
     */
    void deleteScanSchemeHoleByCondition(@NotNull String key,@NotNull String value);

    @FindOne
    ScanSchemeHole findOne(@NotNull String id);

    @FindList
    List<ScanSchemeHole> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    ScanSchemeHole findScanSchemeHole(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanSchemeHole> findAllScanSchemeHole();

    /**
    * 查询列表
    * @param scanSchemeHoleQuery
    * @return
    */
    List<ScanSchemeHole> findScanSchemeHoleList(ScanSchemeHoleQuery scanSchemeHoleQuery);

    /**
    * 按分页查询
    * @param scanSchemeHoleQuery
    * @return
    */
    Pagination<ScanSchemeHole> findScanSchemeHolePage(ScanSchemeHoleQuery scanSchemeHoleQuery);

}