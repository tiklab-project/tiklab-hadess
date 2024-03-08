package io.thoughtware.hadess.timedtask.util;

import io.thoughtware.hadess.common.CronUtils;
import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstance;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Scope("singleton")
public class JobManager {

    private static final Logger logger = LoggerFactory.getLogger(JobManager.class);

    private static final SchedulerFactory schedulerFactory =  new StdSchedulerFactory();

    private Scheduler scheduler = null;

    /**
     * @param jobClass  执行不同的任务
     * @param taskInstance   时间设置，参考quartz说明文档
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addJob(TimeTaskInstance taskInstance, Class jobClass,  String group) throws SchedulerException {
        String cron = taskInstance.getCron();

        //执行对象的id
        String execObjectId = taskInstance.getExecObjectId();

        //获取执行周的具体时间
        Date execTime = CronUtils.weekTime(cron);

        String weekTime = CronUtils.dateChangeString(execTime);

        if (execTime.getTime() <= new Date().getTime()){
            logger.warn("定时任务时间已过，跳过添加：{}，执行对象id：{}，执行时间：{}，cron表达式：{}",group,execObjectId, weekTime,cron);
            return;
        }

        logger.warn("添加定时任务，定时任务组：{}，执行对象id：{}，执行时间：{}，cron表达式：{}",group,execObjectId, weekTime,cron);

        // 任务名，任务组，任务执行类
        Scheduler scheduler = schedulerFactory.getScheduler();

        Boolean isNewTrigger = false;

        JobKey jobKey = JobKey.jobKey(group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

        int size = scheduler.getTriggersOfJob(jobKey).size();
        logger.warn("触发器长度：{}",size);

        if (Objects.isNull(jobDetail)){
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            //添加pipelineId执行信息
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("group",group);
            jobDataMap.put("objectId",execObjectId);
            jobDataMap.put("cron",cron);
            jobDataMap.put("weekTime",weekTime);
            jobBuilder.setJobData(jobDataMap);

            jobDetail = jobBuilder.withIdentity(group).build();

            isNewTrigger = true;

        }

        String jobName=taskInstance.getId()+"_"+execObjectId;
        // 添加触发器
        addTrigger(scheduler,jobDetail,group,cron,isNewTrigger,jobName);

    }

    /**
     *  添加触发器
     * @param scheduler 定时器
     * @param jobDetail JobDetail
     * @param state 是否为新建trigger
     * @param cron 时间
     * @throws SchedulerException  添加失败
     */
    private void addTrigger( Scheduler scheduler,JobDetail jobDetail,String group,String cron,Boolean state,String jobName) throws SchedulerException {


        // 触发器
        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(jobName, group) // 触发器名,触发器组
                .withSchedule(CronScheduleBuilder.cronSchedule(cron));// 触发器时间设定

        // Job存在则指定job
        if (!state){
            triggerBuilder.forJob(jobDetail);
        }

        CronTrigger trigger = triggerBuilder.build();

        // 调度容器设置JobDetail和Trigger
        if (state){
            scheduler.scheduleJob(jobDetail, trigger);
        }else {
            scheduler.scheduleJob(trigger);
        }

        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }

    }


    /**
     * @Description: 修改一个任务的触发时间
     * @param jobName
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param cron   时间设置，参考quartz说明文档
     */
    public  void modifyJobTime(String jobName,String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                logger.warn("任务："+jobName+"被修改");

                /** 方式二：先删除，然后在创建一个新的Job  */
                JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                Class<? extends org.quartz.Job> jobClass = jobDetail.getJobClass();
                removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                // addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass,cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void removeJob(String group,String triggerName){

        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(group);

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            logger.warn("移除触发器，jobName:"+ triggerName);

            int size = scheduler.getTriggersOfJob(jobKey).size();

            logger.warn("触发器长度：{}",size);
            if (size <= 1){
                scheduler.deleteJob(jobKey);// 删除任务
                logger.warn("Job触发完成：" + jobKey + "，移除Job" );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void removeJob(String jobName, String jobGroupName,String triggerName, String triggerGroupName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     */
    public   void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前正在执行的任务
     * @return
     */
    public  boolean getCurrentJobs(String name){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            List<JobExecutionContext> jobContexts = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext context : jobContexts) {
                if (name.equals(context.getTrigger().getJobKey().getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


}
