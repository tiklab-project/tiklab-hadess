package io.thoughtware.hadess.repository.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

@ApiModel
@Mapper
public class XpackBackups {

    @ApiProperty(name="id",desc="备份id")
    private String id;

    @ApiProperty(name="taskState",desc="定时任务状态")
    private String taskState;


    @ApiProperty(name="log",desc="日志")
    private String log;

    @ApiProperty(name="execResult",desc="最后执行结果 未执行：non 失败：fail 成功：success")
    private String execResult;

    @ApiProperty(name="execTime",desc="执行时间")
    private String execTime;

    @ApiProperty(name="execState",desc="执行状态")
    private String execState;


    @ApiProperty(name="backupsAddress",desc="备份地址")
    private String backupsAddress;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public String getExecResult() {
        return execResult;
    }

    public void setExecResult(String execResult) {
        this.execResult = execResult;
    }


    public String getBackupsAddress() {
        return backupsAddress;
    }

    public void setBackupsAddress(String backupsAddress) {
        this.backupsAddress = backupsAddress;
    }

    public String getExecState() {
        return execState;
    }

    public void setExecState(String execState) {
        this.execState = execState;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
