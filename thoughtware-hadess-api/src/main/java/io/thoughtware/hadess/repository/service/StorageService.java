package io.thoughtware.hadess.repository.service;


import io.thoughtware.hadess.repository.model.Storage;
import io.thoughtware.hadess.repository.model.StorageQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* StorageService-存储库接口
*/
@JoinProvider(model = Storage.class)
public interface StorageService {

    /**
    * 创建
    * @param storage
    * @return
    */
    String createStorage(@NotNull @Valid Storage storage);

    /**
    * 更新
    * @param storage
    */
    void updateStorage(@NotNull @Valid Storage storage);

    /**
    * 删除
    * @param id
    */
    void deleteStorage(@NotNull String id);

    @FindOne
    Storage findOne(@NotNull String id);

    @FindList
    List<Storage> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    Storage findStorage(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<Storage> findAllStorage();

    /**
    * 查询列表
    * @param storageQuery
    * @return
    */
    List<Storage> findStorageList(StorageQuery storageQuery);

    /**
    * 按分页查询
    * @param storageQuery
    * @return
    */
    Pagination<Storage> findStoragePage(StorageQuery storageQuery);

}