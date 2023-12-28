package io.thoughtware.hadess.scan.dao;

import io.thoughtware.hadess.scan.entity.ScanResultEntity;
import io.thoughtware.hadess.scan.model.ScanResultQuery;
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
     * 通过扫描记录的ids 删除
     * @param recordIds
     */
    public void deleteScanResultByRecordIds(StringBuilder recordIds) {
        String sql="DELETE FROM pack_scan_result WHERE scan_record_id IN ("+recordIds+")";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
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
                .eq("libraryId",scanResultQuery.getLibraryId())
                .eq("holeType",scanResultQuery.getHoleType())
                .eq("scanLibraryId",scanResultQuery.getScanLibraryId())
                .eq("holeLevel",scanResultQuery.getHoleLevel())
                .eq("scanRecordId",scanResultQuery.getScanRecordId())
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
                .eq("libraryId",scanResultQuery.getLibraryId())
                .eq("holeType",scanResultQuery.getHoleType())
                .eq("scanLibraryId",scanResultQuery.getScanLibraryId())
                .eq("holeLevel",scanResultQuery.getHoleLevel())
                .eq("scanRecordId",scanResultQuery.getScanRecordId())
                .orders(scanResultQuery.getOrderParams())
                .pagination(scanResultQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanResultEntity.class);
    }

    public List<ScanResultEntity> findScanResultByRecordIds(String [] scanRecordIds) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanResultEntity.class)
                .in("scanRecordId",scanRecordIds)
                .get();
        return jpaTemplate.findList(queryCondition,ScanResultEntity.class);
    }


}