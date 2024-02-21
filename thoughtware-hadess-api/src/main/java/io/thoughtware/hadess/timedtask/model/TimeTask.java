package io.thoughtware.hadess.timedtask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.core.BaseModel;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

import java.sql.Timestamp;

@ApiModel
@Join
@Mapper
public class TimeTask extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="scanPlayId",desc="扫描计划id")
    private String scanPlayId;

    @ApiProperty(name="taskName",desc="定时任务名字")
    private String taskName;

    @ApiProperty(name="taskType",desc="定时任务类型 scan")
    private String taskType;

    @ApiProperty(name="taskWay",desc="任务方式 1：单次触发、2：循环触发")
    private Integer taskWay;

    @ApiProperty(name="execState",desc="执行状态 1:未触发 2:已触发 3：触发中（循环触发在触发后状态为触发中）")
    private Integer execState=1;

    @ApiProperty(name="creatTime",desc="creatTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private java.sql.Timestamp creatTime;

    @ApiProperty(name="updateTime",desc="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private java.sql.Timestamp updateTime;


    /*------其他字段------------*/
    @ApiProperty(name="instanceData",desc="定时任务实例数据")
    private Object instanceData;

    @ApiProperty(name="execWeek",desc="执行周")
    private String execWeek;

    @ApiProperty(name="taskType",desc="执行时间")
    private String execTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScanPlayId() {
        return scanPlayId;
    }

    public void setScanPlayId(String scanPlayId) {
        this.scanPlayId = scanPlayId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskWay() {
        return taskWay;
    }

    public void setTaskWay(Integer taskWay) {
        this.taskWay = taskWay;
    }

    public Integer getExecState() {
        return execState;
    }

    public void setExecState(Integer execState) {
        this.execState = execState;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Object getInstanceData() {
        return instanceData;
    }

    public void setInstanceData(Object instanceData) {
        this.instanceData = instanceData;
    }

    public String getExecWeek() {
        return execWeek;
    }

    public void setExecWeek(String execWeek) {
        this.execWeek = execWeek;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }
}
