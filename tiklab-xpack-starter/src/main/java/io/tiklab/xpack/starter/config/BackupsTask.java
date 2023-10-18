package io.tiklab.xpack.starter.config;

import io.tiklab.xpack.repository.model.XpackBackups;
import io.tiklab.xpack.repository.service.XpackBackupsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 *  备份定时任务
 * */
@Component
public class BackupsTask {
    private static Logger logger = LoggerFactory.getLogger(BackupsTask.class);
    @Autowired
    XpackBackupsServer backupsServer;


    //凌晨2点 执行
    @Scheduled(cron = "${backup.time}")
    public void exeBackups(){
        logger.info("准备执行定时备份");
        XpackBackups backups = backupsServer.findBackups();
        //设置了定时备份数据
        if (("true").equals(backups.getTaskState())){
            logger.info("开始执行定时备份");
            backupsServer.backupsExec(backups);
        }
    }
}
