package io.tiklab.xpack.scan.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.xpack.scan.model.ScanScheme;
import io.tiklab.xpack.scan.model.ScanSchemeQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanPlaySchemeService-扫描方案
*/
@JoinProvider(model = ScanScheme.class)
public interface ScanSchemeService {

    /**
    * 创建
    * @param scanScheme
    * @return
    */
    String createScanScheme(@NotNull @Valid ScanScheme scanScheme);

    /**
    * 更新
    * @param scanScheme
    */
    void updateScanScheme(@NotNull @Valid ScanScheme scanScheme);

    /**
    * 删除
    * @param id
    */
    void deleteScanScheme(@NotNull String id);

    /**
     * 条件删除扫描结果
     * @param  key  删除条件字段
     * @param value
     */
    void deleteScanSchemeByCondition(@NotNull String key,@NotNull String value);

    @FindOne
    ScanScheme findOne(@NotNull String id);

    @FindList
    List<ScanScheme> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    ScanScheme findScanScheme(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanScheme> findAllScanScheme();

    /**
    * 查询列表
    * @param scanSchemeQuery
    * @return
    */
    List<ScanScheme> findScanSchemeList(ScanSchemeQuery scanSchemeQuery);

    /**
    * 按分页查询
    * @param scanSchemeQuery
    * @return
    */
    Pagination<ScanScheme> findScanSchemePage(ScanSchemeQuery scanSchemeQuery);

}