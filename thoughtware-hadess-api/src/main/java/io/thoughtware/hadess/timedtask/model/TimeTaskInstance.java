package io.thoughtware.hadess.timedtask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.core.BaseModel;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

import java.sql.Timestamp;
import java.util.List;

@ApiModel
@Join
@Mapper
public class TimeTaskInstance extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="timeTaskId",desc="定时任务id")
    private String timeTaskId;

    @ApiProperty(name="execObjectId",desc="执行对象的id")
    private String execObjectId;


    @ApiProperty(name="cron",desc="cron表达式")
    private String cron;


    @ApiProperty(name="weekDay",desc="周几")
    private Integer weekDay;

    @ApiProperty(name="execState",desc="1:未触发 2:已触发 3:触发中 （循环触发在触发后状态为触发中）")
    private Integer execState=1;

    @ApiProperty(name="execTime",desc="执行时间")
    private String execTime;

    @ApiProperty(name="taskWay",desc="任务方式 1：单次触发、2：循环触发")
    private Integer taskWay;

    @ApiProperty(name="creatTime",desc="creatTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private java.sql.Timestamp creatTime;


    /*-------其他字段---------*/
    @ApiProperty(name="dataList",desc="天数,周几")
    private List<Integer> dataList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeTaskId() {
        return timeTaskId;
    }

    public void setTimeTaskId(String timeTaskId) {
        this.timeTaskId = timeTaskId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getExecState() {
        return execState;
    }

    public void setExecState(Integer execState) {
        this.execState = execState;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public List<Integer> getDataList() {
        return dataList;
    }

    public void setDataList(List<Integer> dataList) {
        this.dataList = dataList;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getTaskWay() {
        return taskWay;
    }

    public void setTaskWay(Integer taskWay) {
        this.taskWay = taskWay;
    }



    public String getExecObjectId() {
        return execObjectId;
    }

    public void setExecObjectId(String execObjectId) {
        this.execObjectId = execObjectId;
    }
}
