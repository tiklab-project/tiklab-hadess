package io.thoughtware.hadess.scan.dao;

import io.thoughtware.core.order.Order;
import io.thoughtware.hadess.scan.entity.ScanHoleEntity;
import io.thoughtware.hadess.scan.model.ScanHoleQuery;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ScanHoleDao-扫描漏洞数据库访问
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
     * 条件删除漏洞
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
    * 查询所有漏洞
    * @return
    */
    public List<ScanHoleEntity> findAllScanHole() {
        return jpaTemplate.findAll(ScanHoleEntity.class);
    }

    /**
     * 通过ids查询漏洞
     * @param idList
     * @return List <ScanHoleEntity>
     */
    public List<ScanHoleEntity> findScanHoleList(List<String> idList) {
        return jpaTemplate.findList(ScanHoleEntity.class,idList);
    }

    /**
     * 条件查询漏洞
     * @param scanHoleQuery
     * @return List <ScanHoleEntity>
     */
    public List<ScanHoleEntity> findScanHoleList(ScanHoleQuery scanHoleQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanHoleEntity.class)
                .eq("holeLevel",scanHoleQuery.getHoleLevel())
                .eq("language",scanHoleQuery.getLanguage())
                .orders(scanHoleQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ScanHoleEntity.class);
    }

    /**
     * 条件分页查询漏洞
     * @param scanHoleQuery
     * @return Pagination <ScanHoleEntity>
     */
    public Pagination<ScanHoleEntity> findScanHolePage(ScanHoleQuery scanHoleQuery) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(ScanHoleEntity.class)
                .eq("language", scanHoleQuery.getLanguage())
                .like("holeName",scanHoleQuery.getHoleName());


        if (CollectionUtils.isNotEmpty(scanHoleQuery.getHoleLevelList())){
            queryBuilders.in("holeLevel",scanHoleQuery.getHoleLevelList().toArray(new Integer[]{scanHoleQuery.getHoleLevelList().size()}));
        }else {
            queryBuilders.eq("holeLevel", scanHoleQuery.getHoleLevel());
        }
        QueryCondition queryCondition = queryBuilders.orders(scanHoleQuery.getOrderParams())
                .pagination(scanHoleQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanHoleEntity.class);
    }

    /**
     * 条件分页查询漏洞
     * @param holeIds
     * @return Pagination <ScanHoleEntity>
     */
    public List<ScanHoleEntity> findScanHoleByIds(String[] holeIds, List<Order> orderParams ) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ScanHoleEntity.class)
                .in("id",holeIds)
                .orders(orderParams)
                .get();
        return jpaTemplate.findList(queryCondition,ScanHoleEntity.class);
    }

    /**
     * 条件分页查询漏洞
     * @param holeIds
     * @return Pagination <ScanHoleEntity>
     */
    public Pagination<ScanHoleEntity> findNotInScanHoleList(ScanHoleQuery scanHoleQuery,String[] holeIds) {
        QueryBuilders queryBuilders = QueryBuilders.createQuery(ScanHoleEntity.class)
                .eq("language", scanHoleQuery.getLanguage())
                .notIn("id",holeIds)
                .like("holeName",scanHoleQuery.getHoleName());


        if (CollectionUtils.isNotEmpty(scanHoleQuery.getHoleLevelList())){
            queryBuilders.in("holeLevel",scanHoleQuery.getHoleLevelList().toArray(new Integer[]{scanHoleQuery.getHoleLevelList().size()}));
        }else {
            queryBuilders.eq("holeLevel", scanHoleQuery.getHoleLevel());
        }
        QueryCondition queryCondition = queryBuilders
                .orders(scanHoleQuery.getOrderParams())
                .pagination(scanHoleQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ScanHoleEntity.class);
    }
}