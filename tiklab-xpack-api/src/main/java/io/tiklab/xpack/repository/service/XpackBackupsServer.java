package io.tiklab.xpack.repository.service;


import io.tiklab.xpack.repository.model.Backups;
import io.tiklab.xpack.repository.model.ExecLog;

import java.io.InputStream;

public interface XpackBackupsServer {

    /**
     * 查询备份相关数据
     */
    Backups findBackups();

    /**
     * 修改备份相关数据
     */
    void updateBackups(Backups backups);

    /**
     * 执行备份
     */
    String backupsExec(Backups backups);


    /**
     * 数据恢复
     * @param fileName 文件名称
     */
    String recoveryData(String fileName);

    /**
     * 获取备份、恢复执行结果
     * @param type :backups、recovery
     */
    ExecLog gainBackupsRes(String type);


    /**
     * 备份数据上传
     * @param  inputStream inputStream
     * @param fileName 文件名称
     */
    void uploadBackups(InputStream inputStream, String fileName);

}
