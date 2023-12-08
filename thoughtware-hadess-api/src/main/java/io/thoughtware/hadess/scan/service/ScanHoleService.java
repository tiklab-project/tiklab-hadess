package io.thoughtware.hadess.scan.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;
import io.thoughtware.hadess.scan.model.ScanHole;
import io.thoughtware.hadess.scan.model.ScanHoleQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanHoleService-扫描漏洞
*/
@JoinProvider(model = ScanHole.class)
public interface ScanHoleService {

    /**
    * 创建
    * @param scanHole
    * @return
    */
    String createScanHole(@NotNull @Valid ScanHole scanHole);

    /**
    * 更新
    * @param scanHole
    */
    void updateScanHole(@NotNull @Valid ScanHole scanHole);

    /**
    * 删除
    * @param id
    */
    void deleteScanHole(@NotNull String id);

    /**
     * 条件删除扫描结果
     * @param  key  删除条件字段
     * @param value
     */
    void deleteScanHoleByCondition(@NotNull String key,@NotNull String value);

    @FindOne
    ScanHole findOne(@NotNull String id);

    @FindList
    List<ScanHole> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    ScanHole findScanHole(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanHole> findAllScanHole();

    /**
    * 查询列表
    * @param scanHoleQuery
    * @return
    */
    List<ScanHole> findScanHoleList(ScanHoleQuery scanHoleQuery);

    /**
    * 按分页查询
    * @param scanHoleQuery
    * @return
    */
    Pagination<ScanHole> findScanHolePage(ScanHoleQuery scanHoleQuery);

    /**
     * 条件分页查询没有添加方案的扫描漏洞
     * @param scanHoleQuery
     * @return
     */
    Pagination<ScanHole> findNotScanHolePage(ScanHoleQuery scanHoleQuery);

    /**
     * 条件分页方案下面的漏洞
     * @param scanHoleQuery
     * @return
     */
    Pagination<ScanHole> findSchemeHolePage(ScanHoleQuery scanHoleQuery);



}