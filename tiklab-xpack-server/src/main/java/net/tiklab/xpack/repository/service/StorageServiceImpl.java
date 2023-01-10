package net.tiklab.xpack.repository.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.xpack.repository.dao.StorageDao;
import net.tiklab.xpack.repository.entity.StorageEntity;
import net.tiklab.xpack.repository.model.Storage;
import net.tiklab.xpack.repository.model.StorageQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
* StorageServiceImpl
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