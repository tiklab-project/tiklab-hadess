package io.thoughtware.hadess.scan.dao;

import io.thoughtware.hadess.scan.entity.ScanRecordEntity;
import io.thoughtware.hadess.scan.model.ScanRecordQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jdbc.JdbcTemplate;
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
 * ScanRecordDao-扫描制品数据数据库访问
 */
@Repository
public class ScanRecordDao {

    private static Logger logger = LoggerFactory.getLogger(ScanRecordDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param scanRecordEntity
     * @return
     */
    public String createScanRecord(ScanRecordEntity scanRecordEntity) {
        return jpaTemplate.save(scanRecordEntity,String.class);
    }

    /**
     * 更新
     * @param scanRecordEntity
     */
    public void updateScanRecord(ScanRecordEntity scanRecordEntity){
        jpaTemplate.update(scanRecordEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteScanRecord(String id){
        jpaTemplate.delete(ScanRecordEntity.class,id);
    }

    /**
     * 条件删除扫描制品结果
     * @param deleteCondition
     */
    public void deleteScanRecord(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }


    /**
     * 查找
     * @param id
     * @return
     */
    public ScanRecordEntity findScanRecord(String id){
        return jpaTemplate.findOne(ScanRecordEntity.class,id);
    }

    /**
    * 查询所有扫描制品结果
    * @return
    */
    public List<ScanRecordEntity> findAllScanRecord() {
        return jpaTemplate.findAll(ScanRecordEntity.class);
    }

    /**
     * 通过ids查询扫描制品结果
     * @param idList
     * @return List <ScanRecordEntity>
     */
    public List<ScanRecordEntity> findScanRecordList(List<String> idList) {
        return jpaTemplate.findList(ScanRecordEntity.class,idList);
    }

    /**
     * 条件查询扫描制品结果
     * @param scanRecordQuery
     * @return List <ScanRecordEntity>
     */
    public List<ScanRecordEntity> findScanRecordList(ScanRecordQuery scanRecordQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanRecordEntity.class)
                .eq("scanLibraryId",scanRecordQuery.getScanLibraryId())
                .eq("scanPlayId",scanRecordQuery.getScanPlayId())
                .eq("scanGroup",scanRecordQuery.getScanGroup())
                .eq("recordType",scanRecordQuery.getRecordType())
                .orders(scanRecordQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanRecordEntity.class);
    }

    /**
     * 条件分页查询扫描制品结果
     * @param scanRecordQuery
     * @return Pagination <ScanRecordEntity>
     */
    public Pagination<ScanRecordEntity> findScanRecordPage(ScanRecordQuery scanRecordQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanRecordEntity.class)
                .eq("scanLibraryId",scanRecordQuery.getScanLibraryId())
                .eq("scanPlayId",scanRecordQuery.getScanPlayId())
                .eq("scanGroup",scanRecordQuery.getScanGroup())
                .orders(scanRecordQuery.getOrderParams())
                .pagination(scanRecordQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanRecordEntity.class);
    }
}