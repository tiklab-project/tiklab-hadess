package io.thoughtware.hadess.pushcentral.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.hadess.pushcentral.entity.PushGroupEntity;
import io.thoughtware.hadess.pushcentral.model.PushGroupQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PushGroupDao-推送组 数据访问
 */
@Repository
public class PushGroupDao {

    private static Logger logger = LoggerFactory.getLogger(PushGroupDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pushGroupEntity
     * @return
     */
    public String createPushGroup(PushGroupEntity pushGroupEntity) {
        return jpaTemplate.save(pushGroupEntity,String.class);
    }

    /**
     * 更新
     * @param pushGroupEntity
     */
    public void updatePushGroup(PushGroupEntity pushGroupEntity){
        jpaTemplate.update(pushGroupEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deletePushGroup(String id){
        jpaTemplate.delete(PushGroupEntity.class,id);
    }

    /**
     * 条件删除
     * @param deleteCondition
     * @return
     */
    public void deletePushGroup(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public PushGroupEntity findPushGroup(String id){
        return jpaTemplate.findOne(PushGroupEntity.class,id);
    }

    /**
    * 查询所有制品
    * @return
    */
    public List<PushGroupEntity> findAllPushGroup() {
        return jpaTemplate.findAll(PushGroupEntity.class);
    }

    /**
     * 通过ids查询推送组
     * @param idList
     * @return List <PushGroupEntity>
     */
    public List<PushGroupEntity> findPushGroupList(List<String> idList) {
        return jpaTemplate.findList(PushGroupEntity.class,idList);
    }

    /**
     * 条件查询推送组
     * @param pushGroupQuery
     * @return List <PushGroupEntity>
     */
    public List<PushGroupEntity> findPushGroupList(PushGroupQuery pushGroupQuery) {
        QueryCondition queryCondition =QueryBuilders.createQuery(PushGroupEntity.class)
                .eq("repositoryId", pushGroupQuery.getRepositoryId())
                .orders(pushGroupQuery.getOrderParams())
                .get();

        return jpaTemplate.findList(queryCondition,PushGroupEntity.class);
    }

    /**
     * 条件分页查询推送组
     * @param pushGroupQuery
     * @return Pagination <PushGroupEntity>
     */
    public Pagination<PushGroupEntity> findPushGroupPage(PushGroupQuery pushGroupQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(PushGroupEntity.class)
                .eq("repositoryId", pushGroupQuery.getRepositoryId())
                .orders(pushGroupQuery.getOrderParams())
                .pagination(pushGroupQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,PushGroupEntity.class);
    }



}