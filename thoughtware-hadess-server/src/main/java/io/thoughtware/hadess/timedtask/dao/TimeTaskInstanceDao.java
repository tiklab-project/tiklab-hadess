package io.thoughtware.hadess.timedtask.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.hadess.timedtask.entity.TimeTaskInstanceEntity;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstance;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TimeTaskInstanceDao-定时任务实例数据库访问
 */
@Repository
public class TimeTaskInstanceDao {

    private static Logger logger = LoggerFactory.getLogger(TimeTaskInstanceDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param timeTaskInstanceEntity
     * @return
     */
    public String createTimeTaskInstance(TimeTaskInstanceEntity timeTaskInstanceEntity) {
        return jpaTemplate.save(timeTaskInstanceEntity,String.class);
    }

    /**
     * 更新
     * @param timeTaskInstanceEntity
     */
    public void updateTimeTaskInstance(TimeTaskInstanceEntity timeTaskInstanceEntity){
        jpaTemplate.update(timeTaskInstanceEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteTimeTaskInstance(String id){
        jpaTemplate.delete(TimeTaskInstanceEntity.class,id);
    }

    /**
     * 条件删除定时任务实例
     * @param deleteCondition
     */
    public void deleteTimeTaskInstance(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public TimeTaskInstanceEntity findTimeTaskInstance(String id){
        return jpaTemplate.findOne(TimeTaskInstanceEntity.class,id);
    }

    /**
    * 查询所有定时任务实例
    * @return
    */
    public List<TimeTaskInstanceEntity> findAllTimeTaskInstance() {
        return jpaTemplate.findAll(TimeTaskInstanceEntity.class);
    }

    /**
     * 通过ids查询定时任务实例
     * @param idList
     * @return List <TimeTaskInstanceEntity>
     */
    public List<TimeTaskInstanceEntity> findTimeTaskInstanceList(List<String> idList) {
        return jpaTemplate.findList(TimeTaskInstanceEntity.class,idList);
    }

    /**
     * 条件查询定时任务实例
     * @param timeTaskInstanceQuery
     * @return List <TimeTaskInstanceEntity>
     */
    public List<TimeTaskInstanceEntity> findTimeTaskInstanceList(TimeTaskInstanceQuery timeTaskInstanceQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TimeTaskInstanceEntity.class)
                .eq("timeTaskId",timeTaskInstanceQuery.getTimeTaskId())
                .eq("execObjectId",timeTaskInstanceQuery.getExecObjectId())
                .eq("weekDay",timeTaskInstanceQuery.getWeekDay())
                .eq("execTime", timeTaskInstanceQuery.getExecTime())
                .eq("cron",timeTaskInstanceQuery.getCron())
                .orders(timeTaskInstanceQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,TimeTaskInstanceEntity.class);
    }

    /**
     * 条件分页查询定时任务实例
     * @param timeTaskInstanceQuery
     * @return Pagination <TimeTaskInstanceEntity>
     */
    public Pagination<TimeTaskInstanceEntity> findTimeTaskInstancePage(TimeTaskInstanceQuery timeTaskInstanceQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TimeTaskInstanceEntity.class)
                .eq("timeTaskId",timeTaskInstanceQuery.getTimeTaskId())
                .eq("execObjectId",timeTaskInstanceQuery.getExecObjectId())
                .eq("cron",timeTaskInstanceQuery.getCron())
                .eq("weekDay",timeTaskInstanceQuery.getWeekDay())
                .eq("execTime", timeTaskInstanceQuery.getExecTime())
                .orders(timeTaskInstanceQuery.getOrderParams())
                .pagination(timeTaskInstanceQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,TimeTaskInstanceEntity.class);
    }
}