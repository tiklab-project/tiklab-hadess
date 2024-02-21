package io.thoughtware.hadess.timedtask.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * TimeTaskEntity-定时任务
 */
@Entity
@Table(name="pack_time_task")
public class TimeTaskEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //扫描计划的id
    @Column(name = "scan_play_id",length = 32)
    private String scanPlayId;

    @Column(name = "task_name",length = 524)
    private String taskName;

    //任务类型
    @Column(name = "task_type",length = 32)
    private String taskType;

    //任务方式
    @Column(name = "task_way")
    private Integer taskWay;


    //执行状态
    @Column(name = "exec_state")
    private Integer execState;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

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

    public void setTaskWay(Integer taskWay) {
        this.taskWay = taskWay;
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

    public Integer getExecState() {
        return execState;
    }

    public void setExecState(Integer execState) {
        this.execState = execState;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
