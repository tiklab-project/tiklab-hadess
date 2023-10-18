package io.tiklab.xpack.scan.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.xpack.scan.entity.ScanHoleEntity;
import io.tiklab.xpack.scan.model.ScanHoleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ScanHoleDao-扫描结果数据库访问
 */
@Repository
public class ScanHoleDao {

    private static Logger logger = LoggerFactory.getLogger(ScanHoleDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param scanHoleEntity
     * @return
     */
    public String createScanHole(ScanHoleEntity scanHoleEntity) {
        return jpaTemplate.save(scanHoleEntity,String.class);
    }

    /**
     * 更新
     * @param scanHoleEntity
     */
    public void updateScanHole(ScanHoleEntity scanHoleEntity){
        jpaTemplate.update(scanHoleEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteScanHole(String id){
        jpaTemplate.delete(ScanHoleEntity.class,id);
    }

    /**
     * 条件删除存储库
     * @param deleteCondition
     */
    public void deleteScanHole(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public ScanHoleEntity findScanHole(String id){
        return jpaTemplate.findOne(ScanHoleEntity.class,id);
    }

    /**
    * 查询所有存储库
    * @return
    */
    public List<ScanHoleEntity> findAllScanHole() {
        return jpaTemplate.findAll(ScanHoleEntity.class);
    }

    /**
     * 通过ids查询存储库
     * @param idList
     * @return List <ScanHoleEntity>
     */
    public List<ScanHoleEntity> findScanHoleList(List<String> idList) {
        return jpaTemplate.findList(ScanHoleEntity.class,idList);
    }

    /**
     * 条件查询存储库
     * @param scanHoleQuery
     * @return List <ScanHoleEntity>
     */
    public List<ScanHoleEntity> findScanHoleList(ScanHoleQuery scanHoleQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanHoleEntity.class)
                .eq("libraryId",scanHoleQuery.getLibraryId())
                .eq("holeType",scanHoleQuery.getHoleType())
                .eq("scanLibraryId",scanHoleQuery.getScanLibraryId())
                .eq("holeLevel",scanHoleQuery.getHoleLevel())
                .eq("scanRecordId",scanHoleQuery.getScanRecordId())
                .orders(scanHoleQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanHoleEntity.class);
    }

    /**
     * 条件分页查询存储库
     * @param scanHoleQuery
     * @return Pagination <ScanHoleEntity>
     */
    public Pagination<ScanHoleEntity> findScanHolePage(ScanHoleQuery scanHoleQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanHoleEntity.class)
                .eq("libraryId",scanHoleQuery.getLibraryId())
                .eq("holeType",scanHoleQuery.getHoleType())
                .eq("scanLibraryId",scanHoleQuery.getScanLibraryId())
                .eq("holeLevel",scanHoleQuery.getHoleLevel())
                .eq("scanRecordId",scanHoleQuery.getScanRecordId())
                .orders(scanHoleQuery.getOrderParams())
                .pagination(scanHoleQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanHoleEntity.class);
    }
}