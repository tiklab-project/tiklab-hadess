package io.thoughtware.hadess.scan.dao;

import io.thoughtware.hadess.scan.entity.ScanRelyEntity;
import io.thoughtware.hadess.scan.model.ScanRely;
import io.thoughtware.hadess.scan.model.ScanRelyQuery;
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
 * ScanRelyDao-扫描依赖数据库访问
 */
@Repository
public class ScanRelyDao {

    private static Logger logger = LoggerFactory.getLogger(ScanRelyDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param scanRelyEntity
     * @return
     */
    public String createScanRely(ScanRelyEntity scanRelyEntity) {
        return jpaTemplate.save(scanRelyEntity,String.class);
    }

    /**
     * 更新
     * @param scanRelyEntity
     */
    public void updateScanRely(ScanRelyEntity scanRelyEntity){
        jpaTemplate.update(scanRelyEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteScanRely(String id){
        jpaTemplate.delete(ScanRelyEntity.class,id);
    }

    /**
     * 条件删除存储库
     * @param deleteCondition
     */
    public void deleteScanRely(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 通过扫描记录的ids 删除
     * @param recordIds
     */
    public void deleteScanRelyByRecordIds(StringBuilder recordIds) {
        String sql="DELETE FROM pack_scan_rely WHERE scan_record_id IN ("+recordIds+")";
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public ScanRelyEntity findScanRely(String id){
        return jpaTemplate.findOne(ScanRelyEntity.class,id);
    }

    /**
    * 查询所有存储库
    * @return
    */
    public List<ScanRelyEntity> findAllScanRely() {
        return jpaTemplate.findAll(ScanRelyEntity.class);
    }

    /**
     * 通过ids查询存储库
     * @param idList
     * @return List <ScanRelyEntity>
     */
    public List<ScanRelyEntity> findScanRelyList(List<String> idList) {
        return jpaTemplate.findList(ScanRelyEntity.class,idList);
    }

    /**
     * 条件查询存储库
     * @param scanRelyQuery
     * @return List <ScanRelyEntity>
     */
    public List<ScanRelyEntity> findScanRelyList(ScanRelyQuery scanRelyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanRelyEntity.class)
                .eq("scanLibraryId",scanRelyQuery.getScanLibraryId())
                .eq("relyOneId",scanRelyQuery.getRelyOneId())
                .eq("scanRecordId",scanRelyQuery.getScanRecordId())
                .eq("generalRecordId", scanRelyQuery.getGeneralRecordId())
                .orders(scanRelyQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanRelyEntity.class);
    }

    /**
     * 条件分页查询存储库
     * @param scanRelyQuery
     * @return Pagination <ScanRelyEntity>
     */
    public Pagination<ScanRelyEntity> findScanRelyPage(ScanRelyQuery scanRelyQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanRelyEntity.class)
                .eq("scanLibraryId",scanRelyQuery.getScanLibraryId())
                .eq("relyOneId",scanRelyQuery.getRelyOneId())
                .eq("scanRecordId",scanRelyQuery.getScanRecordId())
                .eq("generalRecordId", scanRelyQuery.getGeneralRecordId())
                .orders(scanRelyQuery.getOrderParams())
                .pagination(scanRelyQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanRelyEntity.class);
    }

    /**
     * 条件查询存储库
     * @param scanRecordIds
     * @return List <ScanRelyEntity>
     */
    public List<ScanRelyEntity> findScanRelyListByRecordIds(String [] scanRecordIds) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanRelyEntity.class)
                .in("scanRecordId",scanRecordIds)
                .get();
        return jpaTemplate.findList(queryCondition,ScanRelyEntity.class);
    }
}