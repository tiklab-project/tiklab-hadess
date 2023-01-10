package net.tiklab.xpack.repository.dao;

import net.tiklab.core.page.Pagination;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.xpack.repository.entity.StorageEntity;
import net.tiklab.xpack.repository.model.StorageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * StorageDao
 */
@Repository
public class StorageDao{

    private static Logger logger = LoggerFactory.getLogger(StorageDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param storageEntity
     * @return
     */
    public String createStorage(StorageEntity storageEntity) {
        return jpaTemplate.save(storageEntity,String.class);
    }

    /**
     * 更新
     * @param storageEntity
     */
    public void updateStorage(StorageEntity storageEntity){
        jpaTemplate.update(storageEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteStorage(String id){
        jpaTemplate.delete(StorageEntity.class,id);
    }

    public void deleteStorage(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public StorageEntity findStorage(String id){
        return jpaTemplate.findOne(StorageEntity.class,id);
    }

    /**
    * findAllStorage
    * @return
    */
    public List<StorageEntity> findAllStorage() {
        return jpaTemplate.findAll(StorageEntity.class);
    }

    public List<StorageEntity> findStorageList(List<String> idList) {
        return jpaTemplate.findList(StorageEntity.class,idList);
    }

    public List<StorageEntity> findStorageList(StorageQuery storageQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(StorageEntity.class)
                .orders(storageQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,StorageEntity.class);
    }

    public Pagination<StorageEntity> findStoragePage(StorageQuery storageQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(StorageEntity.class)
                .orders(storageQuery.getOrderParams())
                .pagination(storageQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,StorageEntity.class);
    }
}