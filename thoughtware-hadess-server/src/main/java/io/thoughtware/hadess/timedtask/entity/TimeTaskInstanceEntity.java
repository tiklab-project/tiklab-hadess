package io.thoughtware.hadess.timedtask.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * TimeTaskInstanceEntity-定时任务的实例
 */
@Entity
@Table(name="pack_time_task_instance")
public class TimeTaskInstanceEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //定时任务id
    @Column(name = "time_task_id",length = 32)
    private String timeTaskId;

    //执行对象的id
    @Column(name = "exec_object_id",length = 32)
    private String execObjectId;


    @Column(name = "cron",length = 255)
    private String cron;

    //执行状态
    @Column(name = "exec_state")
    private Integer execState;

    //任务方式
    @Column(name = "task_way")
    private Integer taskWay;

    //周几
    @Column(name = "week_day")
    private Integer weekDay;


    //执行时间
    @Column(name = "exec_time")
    private String execTime;


    //创建时间
    @Column(name = "create_time")
    private Timestamp createTime;

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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
