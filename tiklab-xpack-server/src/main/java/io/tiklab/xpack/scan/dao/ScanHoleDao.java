package io.tiklab.xpack.scan.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.scan.entity.ScanResultEntity;
import io.tiklab.xpack.scan.model.ScanResultQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ScanResultDao-扫描结果数据库访问
 */
@Repository
public class ScanResultDao {

    private static Logger logger = LoggerFactory.getLogger(ScanResultDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param scanResultEntity
     * @return
     */
    public String createScanResult(ScanResultEntity scanResultEntity) {
        return jpaTemplate.save(scanResultEntity,String.class);
    }

    /**
     * 更新
     * @param scanResultEntity
     */
    public void updateScanResult(ScanResultEntity scanResultEntity){
        jpaTemplate.update(scanResultEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteScanResult(String id){
        jpaTemplate.delete(ScanResultEntity.class,id);
    }

    /**
     * 条件删除存储库
     * @param deleteCondition
     */
    public void deleteScanResult(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public ScanResultEntity findScanResult(String id){
        return jpaTemplate.findOne(ScanResultEntity.class,id);
    }

    /**
    * 查询所有存储库
    * @return
    */
    public List<ScanResultEntity> findAllScanResult() {
        return jpaTemplate.findAll(ScanResultEntity.class);
    }

    /**
     * 通过ids查询存储库
     * @param idList
     * @return List <ScanResultEntity>
     */
    public List<ScanResultEntity> findScanResultList(List<String> idList) {
        return jpaTemplate.findList(ScanResultEntity.class,idList);
    }

    /**
     * 条件查询存储库
     * @param scanResultQuery
     * @return List <ScanResultEntity>
     */
    public List<ScanResultEntity> findScanResultList(ScanResultQuery scanResultQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanResultEntity.class)
                .orders(scanResultQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanResultEntity.class);
    }

    /**
     * 条件分页查询存储库
     * @param scanResultQuery
     * @return Pagination <ScanResultEntity>
     */
    public Pagination<ScanResultEntity> findScanResultPage(ScanResultQuery scanResultQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanResultEntity.class)
                .orders(scanResultQuery.getOrderParams())
                .pagination(scanResultQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanResultEntity.class);
    }
}