package io.tiklab.xpack.scan.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.xpack.scan.model.ScanSet;
import io.tiklab.xpack.scan.model.ScanSetQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanSetService-存储库接口
*/
@JoinProvider(model = ScanSet.class)
public interface ScanSetService {

    /**
    * 创建
    * @param scanSet
    * @return
    */
    String createScanSet(@NotNull @Valid ScanSet scanSet);

    /**
    * 更新
    * @param scanSet
    */
    void updateScanSet(@NotNull @Valid ScanSet scanSet);

    /**
    * 删除
    * @param id
    */
    void deleteScanSet(@NotNull String id);

    @FindOne
    ScanSet findOne(@NotNull String id);

    List<ScanSet> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    @FindList
    ScanSet findScanSet(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<ScanSet> findAllScanSet();

    /**
    * 查询列表
    * @param scanSetQuery
    * @return
    */
    List<ScanSet> findScanSetList(ScanSetQuery scanSetQuery);

    /**
    * 按分页查询
    * @param scanSetQuery
    * @return
    */
    Pagination<ScanSet> findScanSetPage(ScanSetQuery scanSetQuery);

}