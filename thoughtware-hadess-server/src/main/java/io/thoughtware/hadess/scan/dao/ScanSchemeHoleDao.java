package io.thoughtware.hadess.scan.dao;

import io.thoughtware.hadess.scan.entity.ScanSchemeHoleEntity;
import io.thoughtware.hadess.scan.model.ScanSchemeHoleQuery;
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
 * ScanSchemeHoleDao-扫描方案漏洞关系数据库访问
 */
@Repository
public class ScanSchemeHoleDao {

    private static Logger logger = LoggerFactory.getLogger(ScanSchemeHoleDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param scanSchemeHoleEntity
     * @return
     */
    public String createScanSchemeHole(ScanSchemeHoleEntity scanSchemeHoleEntity) {
        return jpaTemplate.save(scanSchemeHoleEntity,String.class);
    }

    /**
     * 更新
     * @param scanSchemeHoleEntity
     */
    public void updateScanSchemeHole(ScanSchemeHoleEntity scanSchemeHoleEntity){
        jpaTemplate.update(scanSchemeHoleEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteScanSchemeHole(String id){
        jpaTemplate.delete(ScanSchemeHoleEntity.class,id);
    }

    /**
     * 条件删除扫描方案漏洞关系
     * @param deleteCondition
     */
    public void deleteScanSchemeHole(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public ScanSchemeHoleEntity findScanSchemeHole(String id){
        return jpaTemplate.findOne(ScanSchemeHoleEntity.class,id);
    }

    /**
    * 查询所有扫描方案漏洞关系
    * @return
    */
    public List<ScanSchemeHoleEntity> findAllScanSchemeHole() {
        return jpaTemplate.findAll(ScanSchemeHoleEntity.class);
    }

    /**
     * 通过ids查询扫描方案漏洞关系
     * @param idList
     * @return List <ScanSchemeHoleEntity>
     */
    public List<ScanSchemeHoleEntity> findScanSchemeHoleList(List<String> idList) {
        return jpaTemplate.findList(ScanSchemeHoleEntity.class,idList);
    }

    /**
     * 条件查询扫描方案漏洞关系
     * @param scanSchemeHoleQuery
     * @return List <ScanSchemeHoleEntity>
     */
    public List<ScanSchemeHoleEntity> findScanSchemeHoleList(ScanSchemeHoleQuery scanSchemeHoleQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanSchemeHoleEntity.class)
                .eq("scanSchemeId",scanSchemeHoleQuery.getScanSchemeId())
                .orders(scanSchemeHoleQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanSchemeHoleEntity.class);
    }

    /**
     * 条件分页查询扫描方案漏洞关系
     * @param scanSchemeHoleQuery
     * @return Pagination <ScanSchemeHoleEntity>
     */
    public Pagination<ScanSchemeHoleEntity> findScanSchemeHolePage(ScanSchemeHoleQuery scanSchemeHoleQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanSchemeHoleEntity.class)
                .eq("scanSchemeId",scanSchemeHoleQuery.getScanSchemeId())
                .orders(scanSchemeHoleQuery.getOrderParams())
                .pagination(scanSchemeHoleQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanSchemeHoleEntity.class);
    }
}