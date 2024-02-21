package io.thoughtware.hadess.timedtask.service;

import com.alibaba.fastjson.JSON;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.hadess.common.CronUtils;
import io.thoughtware.hadess.common.HadessFinal;
import io.thoughtware.hadess.timedtask.dao.TimeTaskDao;
import io.thoughtware.hadess.timedtask.entity.TimeTaskEntity;
import io.thoughtware.hadess.timedtask.model.TimeTask;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstance;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstanceQuery;
import io.thoughtware.hadess.timedtask.model.TimeTaskQuery;
import io.thoughtware.hadess.timedtask.util.JobManager;
import io.thoughtware.hadess.timedtask.util.RunJob;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.toolkit.join.JoinTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
* TimeTaskServiceImpl-定时任务
*/
@Service
public class TimeTaskServiceImpl implements TimeTaskService {

    @Autowired
    TimeTaskDao timeTaskDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    TimeTaskInstanceService taskInstanceService;

    @Autowired
    JobManager jobManager;

    @Override
    public String createTimeTask(@NotNull @Valid TimeTask timeTask) {

        try {
            TimeTaskEntity timeTaskEntity = BeanMapper.map(timeTask, TimeTaskEntity.class);
            timeTaskEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            timeTaskEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            String timeTaskId = timeTaskDao.createTimeTask(timeTaskEntity);


            String object = JSON.toJSONString(timeTask.getInstanceData());
            TimeTaskInstance timeTaskInstance = JSON.parseObject(object, TimeTaskInstance.class);
            List<Integer> dataList = timeTaskInstance.getDataList();
            for (Integer integer : dataList) {
                timeTaskInstance.setTimeTaskId(timeTaskId);
                timeTaskInstance.setWeekDay(integer);
                timeTaskInstance.setTaskWay(timeTask.getTaskWay());
                timeTaskInstance.setExecObjectId(timeTask.getScanPlayId());
                taskInstanceService.createTimeTaskInstance(timeTaskInstance);
            }
            return timeTaskId;
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
        }


    }

    @Override
    public void updateTimeTask(@NotNull @Valid TimeTask timeTask) {
        TimeTaskEntity timeTaskEntity = BeanMapper.map(timeTask, TimeTaskEntity.class);
        timeTaskDao.updateTimeTask(timeTaskEntity);
    }

    @Override
    public void updateTimeTask(String taskInstanceId) {

        TimeTaskInstance taskInstance = taskInstanceService.findOne(taskInstanceId);

        String timeTaskId = taskInstance.getTimeTaskId();

        TimeTaskEntity timeTask = timeTaskDao.findTimeTask(timeTaskId);

        //循环触发 执行后修改为触发中
        if (taskInstance.getTaskWay()==2){
            taskInstance.setExecState(3);
            timeTask.setExecState(3);

            //修改 表达式重新添加任务
            String cron = CronUtils.getCron(taskInstance.getWeekDay(), taskInstance.getExecTime());
            taskInstance.setCron(cron);
            try {
                jobManager.addJob(taskInstance, RunJob.class, HadessFinal.DEFAULT);
            } catch (SchedulerException e) {
                e.printStackTrace();
                throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
            }

        }else {
            //单次触发  执行后修改为已触发
            taskInstance.setExecState(2);
            timeTask.setExecState(2);
        }

        taskInstanceService.updateTimeTaskInstance(taskInstance);

        timeTaskDao.updateTimeTask(timeTask);

    }

    @Override
    public void deleteTimeTask(@NotNull String id) {
        timeTaskDao.deleteTimeTask(id);

        taskInstanceService.deleteTimeTaskInstance("timeTaskId",id);
    }



    @Override
    public TimeTask findOne(String id) {
        TimeTaskEntity timeTaskEntity = timeTaskDao.findTimeTask(id);

        TimeTask timeTask = BeanMapper.map(timeTaskEntity, TimeTask.class);
        return timeTask;
    }

    @Override
    public List<TimeTask> findList(List<String> idList) {
        List<TimeTaskEntity> timeTaskEntityList =  timeTaskDao.findTimeTaskList(idList);

        List<TimeTask> timeTaskList =  BeanMapper.mapList(timeTaskEntityList,TimeTask.class);
        return timeTaskList;
    }

    @Override
    public TimeTask findTimeTask(@NotNull String id) {
        TimeTask timeTask = findOne(id);

        joinTemplate.joinQuery(timeTask);

        return timeTask;
    }

    @Override
    public List<TimeTask> findAllTimeTask() {
        List<TimeTaskEntity> timeTaskEntityList =  timeTaskDao.findAllTimeTask();

        List<TimeTask> timeTaskList =  BeanMapper.mapList(timeTaskEntityList,TimeTask.class);

        joinTemplate.joinQuery(timeTaskList);

        return timeTaskList;
    }

    @Override
    public List<TimeTask> findTimeTaskList(TimeTaskQuery timeTaskQuery) {
        List<TimeTaskEntity> timeTaskEntityList = timeTaskDao.findTimeTaskList(timeTaskQuery);

        List<TimeTask> timeTaskList = BeanMapper.mapList(timeTaskEntityList,TimeTask.class);

        joinTemplate.joinQuery(timeTaskList);

        for (TimeTask timeTask:timeTaskList){
            List<TimeTaskInstance> timeTaskInstanceList = taskInstanceService.findTimeTaskInstanceList(new TimeTaskInstanceQuery().setTimeTaskId(timeTask.getId()));
            if (CollectionUtils.isNotEmpty(timeTaskInstanceList)){
                List<Integer> integerList = timeTaskInstanceList.stream().map(TimeTaskInstance::getWeekDay).collect(Collectors.toList());
                List<Integer> collected = integerList.stream().sorted().collect(Collectors.toList());
                String replaceWeek = CronUtils.replaceWeek(collected);
                timeTask.setExecWeek(replaceWeek);

                timeTask.setExecTime(timeTaskInstanceList.get(0).getExecTime());
            }
        }
        return timeTaskList;
    }

    @Override
    public Pagination<TimeTask> findTimeTaskPage(TimeTaskQuery timeTaskQuery) {
        Pagination<TimeTaskEntity>  pagination = timeTaskDao.findTimeTaskPage(timeTaskQuery);

        List<TimeTask> timeTaskList = BeanMapper.mapList(pagination.getDataList(),TimeTask.class);

        joinTemplate.joinQuery(timeTaskList);

        return PaginationBuilder.build(pagination,timeTaskList);
    }
}