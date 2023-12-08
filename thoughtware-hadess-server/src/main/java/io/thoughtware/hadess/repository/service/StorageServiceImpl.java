package io.thoughtware.hadess.repository.service;

import io.thoughtware.hadess.repository.model.Storage;
import io.thoughtware.hadess.repository.model.StorageQuery;
import io.thoughtware.hadess.repository.dao.StorageDao;
import io.thoughtware.hadess.repository.entity.StorageEntity;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.join.JoinTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* StorageServiceImpl-存储库
*/
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageDao storageDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createStorage(@NotNull @Valid Storage storage) {
        StorageEntity storageEntity = BeanMapper.map(storage, StorageEntity.class);

        return storageDao.createStorage(storageEntity);
    }

    @Override
    public void updateStorage(@NotNull @Valid Storage storage) {
        StorageEntity storageEntity = BeanMapper.map(storage, StorageEntity.class);

        storageDao.updateStorage(storageEntity);
    }

    @Override
    public void deleteStorage(@NotNull String id) {
        storageDao.deleteStorage(id);
    }

    @Override
    public Storage findOne(String id) {
        StorageEntity storageEntity = storageDao.findStorage(id);

        Storage storage = BeanMapper.map(storageEntity, Storage.class);
        return storage;
    }

    @Override
    public List<Storage> findList(List<String> idList) {
        List<StorageEntity> storageEntityList =  storageDao.findStorageList(idList);

        List<Storage> storageList =  BeanMapper.mapList(storageEntityList,Storage.class);
        return storageList;
    }

    @Override
    public Storage findStorage(@NotNull String id) {
        Storage storage = findOne(id);

        joinTemplate.joinQuery(storage);

        return storage;
    }

    @Override
    public List<Storage> findAllStorage() {
        List<StorageEntity> storageEntityList =  storageDao.findAllStorage();

        List<Storage> storageList =  BeanMapper.mapList(storageEntityList,Storage.class);

        joinTemplate.joinQuery(storageList);

        return storageList;
    }

    @Override
    public List<Storage> findStorageList(StorageQuery storageQuery) {
        List<StorageEntity> storageEntityList = storageDao.findStorageList(storageQuery);

        List<Storage> storageList = BeanMapper.mapList(storageEntityList,Storage.class);

        joinTemplate.joinQuery(storageList);

        return storageList;
    }

    @Override
    public Pagination<Storage> findStoragePage(StorageQuery storageQuery) {
        Pagination<StorageEntity>  pagination = storageDao.findStoragePage(storageQuery);

        List<Storage> storageList = BeanMapper.mapList(pagination.getDataList(),Storage.class);

        joinTemplate.joinQuery(storageList);

        return PaginationBuilder.build(pagination,storageList);
    }
}