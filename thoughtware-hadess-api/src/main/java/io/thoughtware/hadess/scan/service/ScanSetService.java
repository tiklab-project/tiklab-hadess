package io.thoughtware.hadess.scan.service;


import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;
import io.thoughtware.hadess.scan.model.ScanSet;
import io.thoughtware.hadess.scan.model.ScanSetQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ScanSetService-扫描设置
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

    @FindList
    List<ScanSet> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
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



}