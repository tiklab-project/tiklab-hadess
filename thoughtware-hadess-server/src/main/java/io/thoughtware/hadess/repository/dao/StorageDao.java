package io.thoughtware.hadess.repository.dao;

import io.thoughtware.hadess.repository.entity.StorageEntity;
import io.thoughtware.hadess.repository.model.StorageQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * StorageDao-存储库数据访问
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

    /**
     * 条件删除存储库
     * @param deleteCondition
     */
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
    * 查询所有存储库
    * @return
    */
    public List<StorageEntity> findAllStorage() {
        return jpaTemplate.findAll(StorageEntity.class);
    }

    /**
     * 通过ids查询存储库
     * @param idList
     * @return List <StorageEntity>
     */
    public List<StorageEntity> findStorageList(List<String> idList) {
        return jpaTemplate.findList(StorageEntity.class,idList);
    }

    /**
     * 条件查询存储库
     * @param storageQuery
     * @return List <StorageEntity>
     */
    public List<StorageEntity> findStorageList(StorageQuery storageQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(StorageEntity.class)
                .orders(storageQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,StorageEntity.class);
    }

    /**
     * 条件分页查询存储库
     * @param storageQuery
     * @return Pagination <StorageEntity>
     */
    public Pagination<StorageEntity> findStoragePage(StorageQuery storageQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(StorageEntity.class)
                .orders(storageQuery.getOrderParams())
                .pagination(storageQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,StorageEntity.class);
    }
}