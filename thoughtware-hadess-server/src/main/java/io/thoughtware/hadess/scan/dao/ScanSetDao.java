package io.thoughtware.hadess.scan.dao;

import io.thoughtware.hadess.scan.entity.ScanSetEntity;
import io.thoughtware.hadess.scan.model.ScanSetQuery;
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
 * ScanSetDao-扫描设置数据访问
 */
@Repository
public class ScanSetDao {

    private static Logger logger = LoggerFactory.getLogger(ScanSetDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param scanSetEntity
     * @return
     */
    public String createScanSet(ScanSetEntity scanSetEntity) {
        return jpaTemplate.save(scanSetEntity,String.class);
    }

    /**
     * 更新
     * @param scanSetEntity
     */
    public void updateScanSet(ScanSetEntity scanSetEntity){
        jpaTemplate.update(scanSetEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteScanSet(String id){
        jpaTemplate.delete(ScanSetEntity.class,id);
    }

    /**
     * 条件删除存储库
     * @param deleteCondition
     */
    public void deleteScanSet(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public ScanSetEntity findScanSet(String id){
        return jpaTemplate.findOne(ScanSetEntity.class,id);
    }

    /**
    * 查询所有存储库
    * @return
    */
    public List<ScanSetEntity> findAllScanSet() {
        return jpaTemplate.findAll(ScanSetEntity.class);
    }

    /**
     * 通过ids查询存储库
     * @param idList
     * @return List <ScanSetEntity>
     */
    public List<ScanSetEntity> findScanSetList(List<String> idList) {
        return jpaTemplate.findList(ScanSetEntity.class,idList);
    }

    /**
     * 条件查询存储库
     * @param scanSetQuery
     * @return List <ScanSetEntity>
     */
    public List<ScanSetEntity> findScanSetList(ScanSetQuery scanSetQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanSetEntity.class)
                .orders(scanSetQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanSetEntity.class);
    }


}