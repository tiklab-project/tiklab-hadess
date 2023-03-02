package net.tiklab.xpack.repository.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.xpack.repository.model.Storage;
import net.tiklab.xpack.repository.model.StorageQuery;

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

    List<Storage> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    @FindList
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