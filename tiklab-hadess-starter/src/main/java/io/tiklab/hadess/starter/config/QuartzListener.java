package io.tiklab.hadess.starter.config;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.hadess.common.HadessFinal;
import io.tiklab.hadess.timedtask.model.TimeTaskInstance;
import io.tiklab.hadess.timedtask.service.TimeTaskInstanceService;
import io.tiklab.hadess.timedtask.service.TimeTaskService;
import io.tiklab.hadess.timedtask.util.JobManager;
import io.tiklab.hadess.timedtask.util.RunJob;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Scorpio(limingliang)
 * @version 1.0
 * @className QuartzListener
 * @description TODO
 * @date 2021/8/18 10:24
 **/
@Component
public class QuartzListener implements ApplicationRunner {


    @Autowired
    private JobManager jobManager;

    @Autowired
    TimeTaskService timeTaskService;

    @Autowired
    TimeTaskInstanceService taskInstanceService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        run();
    }

    //定时任务执行
    public void run(){
        List<TimeTaskInstance> timeTaskInstance = taskInstanceService.findAllTimeTaskInstance();
        if (CollectionUtils.isNotEmpty(timeTaskInstance)){
            //过滤出触发过后的
            List<TimeTaskInstance> instances = timeTaskInstance.stream().filter(a -> a.getExecState() != 2).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(instances)){
                for (TimeTaskInstance taskInstance:instances){
                    try {
                        jobManager.addJob(taskInstance, RunJob.class, HadessFinal.DEFAULT);
                    } catch (SchedulerException e) {
                        throw new ApplicationException(e);
                    }
                }
            }
        }
    }


}