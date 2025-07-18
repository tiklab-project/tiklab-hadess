package io.tiklab.hadess.timedtask.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.hadess.common.CronUtils;
import io.tiklab.hadess.common.HadessFinal;
import io.tiklab.hadess.timedtask.dao.TimeTaskInstanceDao;
import io.tiklab.hadess.timedtask.entity.TimeTaskInstanceEntity;
import io.tiklab.hadess.timedtask.model.TimeTaskInstance;
import io.tiklab.hadess.timedtask.model.TimeTaskInstanceQuery;
import io.tiklab.hadess.timedtask.util.JobManager;
import io.tiklab.hadess.timedtask.util.RunJob;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* TimeTaskInstanceServiceImpl-定时任务实例
*/
@Service
public class TimeTaskInstanceServiceImpl implements TimeTaskInstanceService {

    @Autowired
    TimeTaskInstanceDao timeTaskInstanceDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    JobManager jobManager;

    @Override
    public String createTimeTaskInstance(@NotNull @Valid TimeTaskInstance timeTaskInstance) {

        List<TimeTaskInstanceEntity> timeTaskInstanceList = timeTaskInstanceDao.findTimeTaskInstanceList(new TimeTaskInstanceQuery().setWeekDay(timeTaskInstance.getWeekDay())
                .setExecTime(timeTaskInstance.getExecTime()));

        if (CollectionUtils.isNotEmpty(timeTaskInstanceList)){
            throw new ApplicationException(HadessFinal.SYSTEM_EXCEPTION,"当前时间已经添加过，无需重复添加。");
        }

        String cron = CronUtils.getCron(timeTaskInstance.getWeekDay(), timeTaskInstance.getExecTime());

        timeTaskInstance.setCron(cron);
        TimeTaskInstanceEntity timeTaskInstanceEntity = BeanMapper.map(timeTaskInstance, TimeTaskInstanceEntity.class);
        timeTaskInstanceEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        String timeTaskInstanceId = timeTaskInstanceDao.createTimeTaskInstance(timeTaskInstanceEntity);
        timeTaskInstance.setId(timeTaskInstanceId);
        try {
            jobManager.addJob(timeTaskInstance, RunJob.class,timeTaskInstance.getTaskType());
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new ApplicationException(HadessFinal.SYSTEM_EXCEPTION,"当前时间已经添加过，无需重复添加。");
        }

        return timeTaskInstanceId;
    }

    @Override
    public void updateTimeTaskInstance(@NotNull @Valid TimeTaskInstance timeTaskInstance) {
        TimeTaskInstanceEntity timeTaskInstanceEntity = BeanMapper.map(timeTaskInstance, TimeTaskInstanceEntity.class);
        timeTaskInstanceDao.updateTimeTaskInstance(timeTaskInstanceEntity);
    }


    @Override
    public void deleteTimeTaskInstance(@NotNull String id) {
        timeTaskInstanceDao.deleteTimeTaskInstance(id);
    }

    @Override
    public void deleteTimeTaskInstance(String fieldType, String value) {
        if (("timeTaskId").equals(fieldType)){
            List<TimeTaskInstance> timeTaskInstanceList = this.findTimeTaskInstanceList(new TimeTaskInstanceQuery().setTimeTaskId(value));
            if (CollectionUtils.isNotEmpty(timeTaskInstanceList)){
                for (TimeTaskInstance taskInstance:timeTaskInstanceList){
                    jobManager.removeJob(taskInstance.getTimeTask().getTaskType(),taskInstance.getId()+"_"+taskInstance.getExecObjectId());
                }
            }
        }

        DeleteCondition deleteCondition = DeleteBuilders.createDelete(TimeTaskInstanceEntity.class)
                .eq(fieldType,value)
                .get();
        timeTaskInstanceDao.deleteTimeTaskInstance(deleteCondition);
    }

    @Override
    public TimeTaskInstance findOne(String id) {
        TimeTaskInstanceEntity timeTaskInstanceEntity = timeTaskInstanceDao.findTimeTaskInstance(id);

        TimeTaskInstance timeTaskInstance = BeanMapper.map(timeTaskInstanceEntity, TimeTaskInstance.class);
        return timeTaskInstance;
    }

    @Override
    public List<TimeTaskInstance> findList(List<String> idList) {
        List<TimeTaskInstanceEntity> timeTaskInstanceEntityList =  timeTaskInstanceDao.findTimeTaskInstanceList(idList);

        List<TimeTaskInstance> timeTaskInstanceList =  BeanMapper.mapList(timeTaskInstanceEntityList,TimeTaskInstance.class);
        return timeTaskInstanceList;
    }

    @Override
    public TimeTaskInstance findTimeTaskInstance(@NotNull String id) {
        TimeTaskInstance timeTaskInstance = findOne(id);

        joinTemplate.joinQuery(timeTaskInstance);

        return timeTaskInstance;
    }

    @Override
    public List<TimeTaskInstance> findAllTimeTaskInstance() {
        List<TimeTaskInstanceEntity> timeTaskInstanceEntityList =  timeTaskInstanceDao.findAllTimeTaskInstance();

        List<TimeTaskInstance> timeTaskInstanceList =  BeanMapper.mapList(timeTaskInstanceEntityList,TimeTaskInstance.class);

        joinTemplate.joinQuery(timeTaskInstanceList);

        return timeTaskInstanceList;
    }

    @Override
    public List<TimeTaskInstance> findTimeTaskInstanceList(TimeTaskInstanceQuery timeTaskInstanceQuery) {
        List<TimeTaskInstanceEntity> timeTaskInstanceEntityList = timeTaskInstanceDao.findTimeTaskInstanceList(timeTaskInstanceQuery);

        List<TimeTaskInstance> timeTaskInstanceList = BeanMapper.mapList(timeTaskInstanceEntityList,TimeTaskInstance.class);


        joinTemplate.joinQuery(timeTaskInstanceList);

        return timeTaskInstanceList;
    }

    @Override
    public Pagination<TimeTaskInstance> findTimeTaskInstancePage(TimeTaskInstanceQuery timeTaskInstanceQuery) {
        Pagination<TimeTaskInstanceEntity>  pagination = timeTaskInstanceDao.findTimeTaskInstancePage(timeTaskInstanceQuery);

        List<TimeTaskInstance> timeTaskInstanceList = BeanMapper.mapList(pagination.getDataList(),TimeTaskInstance.class);

        joinTemplate.joinQuery(timeTaskInstanceList);

        return PaginationBuilder.build(pagination,timeTaskInstanceList);
    }


}