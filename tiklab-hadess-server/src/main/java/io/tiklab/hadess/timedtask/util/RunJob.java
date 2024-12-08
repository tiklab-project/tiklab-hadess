package io.tiklab.hadess.timedtask.util;

import io.tiklab.eam.common.context.LoginContext;


import io.tiklab.hadess.timedtask.service.TimeTaskService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public  class RunJob implements org.quartz.Job {
    private static final Logger logger = LoggerFactory.getLogger(RunJob.class);


    public static  TimeTaskService taskService;

    public static   JobManager jobManager;

    @Autowired
    public void setTimeTaskService(TimeTaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setJobManager(JobManager jobManager) {
        this.jobManager = jobManager;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobName = jobExecutionContext.getTrigger().getKey().getName();
        logger.info("定时任务JobName:"+jobName);
        String[] split = jobName.split("_");
        String taskInstanceId = split[0];
        String execObjectId = split[1];

        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String group = (String)map.get("group");
        logger.info("定时任务group:"+group);

        String loginId = LoginContext.getLoginId();
      /*  //执行扫描计划
        scanService.execScan(execObjectId);
        taskService.updateTimeTask(taskInstanceId);

        logger.warn("定时任务触发完成:"+jobName);
        jobManager.removeJob(group,jobName);*/
    }

}



























