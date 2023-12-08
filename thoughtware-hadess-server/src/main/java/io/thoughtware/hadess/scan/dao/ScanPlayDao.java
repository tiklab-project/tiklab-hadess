package io.thoughtware.hadess.scan.dao;

import io.thoughtware.hadess.scan.entity.ScanPlayEntity;
import io.thoughtware.hadess.scan.model.ScanPlayQuery;
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
 * ScanPlayDao-扫描结果数据库访问
 */
@Repository
public class ScanPlayDao {

    private static Logger logger = LoggerFactory.getLogger(ScanPlayDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param scanPlayEntity
     * @return
     */
    public String createScanPlay(ScanPlayEntity scanPlayEntity) {
        return jpaTemplate.save(scanPlayEntity,String.class);
    }

    /**
     * 更新
     * @param scanPlayEntity
     */
    public void updateScanPlay(ScanPlayEntity scanPlayEntity){
        jpaTemplate.update(scanPlayEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteScanPlay(String id){
        jpaTemplate.delete(ScanPlayEntity.class,id);
    }

    /**
     * 条件删除存储库
     * @param deleteCondition
     */
    public void deleteScanPlay(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public ScanPlayEntity findScanPlay(String id){
        return jpaTemplate.findOne(ScanPlayEntity.class,id);
    }

    /**
    * 查询所有存储库
    * @return
    */
    public List<ScanPlayEntity> findAllScanPlay() {
        return jpaTemplate.findAll(ScanPlayEntity.class);
    }

    /**
     * 通过ids查询存储库
     * @param idList
     * @return List <ScanPlayEntity>
     */
    public List<ScanPlayEntity> findScanPlayList(List<String> idList) {
        return jpaTemplate.findList(ScanPlayEntity.class,idList);
    }

    /**
     * 条件查询存储库
     * @param scanPlayQuery
     * @return List <ScanPlayEntity>
     */
    public List<ScanPlayEntity> findScanPlayList(ScanPlayQuery scanPlayQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanPlayEntity.class)
                .eq("repositoryId",scanPlayQuery.getRepositoryId())
                .orders(scanPlayQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanPlayEntity.class);
    }

    /**
     * 条件分页查询存储库
     * @param scanPlayQuery
     * @return Pagination <ScanPlayEntity>
     */
    public Pagination<ScanPlayEntity> findScanPlayPage(ScanPlayQuery scanPlayQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanPlayEntity.class)
                .eq("repositoryId",scanPlayQuery.getRepositoryId())
                .orders(scanPlayQuery.getOrderParams())
                .pagination(scanPlayQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanPlayEntity.class);
    }
}