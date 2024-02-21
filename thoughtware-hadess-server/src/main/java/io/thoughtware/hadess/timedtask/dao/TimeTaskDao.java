package io.thoughtware.hadess.timedtask.dao;

import io.thoughtware.core.page.Pagination;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.hadess.timedtask.entity.TimeTaskEntity;
import io.thoughtware.hadess.timedtask.model.TimeTaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TimeTaskDao-定时任务数据库访问
 */
@Repository
public class TimeTaskDao {

    private static Logger logger = LoggerFactory.getLogger(TimeTaskDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param timeTaskEntity
     * @return
     */
    public String createTimeTask(TimeTaskEntity timeTaskEntity) {
        return jpaTemplate.save(timeTaskEntity,String.class);
    }

    /**
     * 更新
     * @param timeTaskEntity
     */
    public void updateTimeTask(TimeTaskEntity timeTaskEntity){
        jpaTemplate.update(timeTaskEntity);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteTimeTask(String id){
        jpaTemplate.delete(TimeTaskEntity.class,id);
    }

    /**
     * 条件删除定时任务实例
     * @param deleteCondition
     */
    public void deleteTimeTask(DeleteCondition deleteCondition){
        jpaTemplate.delete(deleteCondition);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public TimeTaskEntity findTimeTask(String id){
        return jpaTemplate.findOne(TimeTaskEntity.class,id);
    }

    /**
    * 查询所有定时任务实例
    * @return
    */
    public List<TimeTaskEntity> findAllTimeTask() {
        return jpaTemplate.findAll(TimeTaskEntity.class);
    }

    /**
     * 通过ids查询定时任务实例
     * @param idList
     * @return List <TimeTaskEntity>
     */
    public List<TimeTaskEntity> findTimeTaskList(List<String> idList) {
        return jpaTemplate.findList(TimeTaskEntity.class,idList);
    }

    /**
     * 条件查询定时任务实例
     * @param timeTaskQuery
     * @return List <TimeTaskEntity>
     */
    public List<TimeTaskEntity> findTimeTaskList(TimeTaskQuery timeTaskQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TimeTaskEntity.class)
                .eq("scanPlayId",timeTaskQuery.getScanPlayId())
                .orders(timeTaskQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,TimeTaskEntity.class);
    }

    /**
     * 条件分页查询定时任务实例
     * @param timeTaskQuery
     * @return Pagination <TimeTaskEntity>
     */
    public Pagination<TimeTaskEntity> findTimeTaskPage(TimeTaskQuery timeTaskQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TimeTaskEntity.class)
                .eq("scanPlayId",timeTaskQuery.getScanPlayId())
                .orders(timeTaskQuery.getOrderParams())
                .pagination(timeTaskQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,TimeTaskEntity.class);
    }
}