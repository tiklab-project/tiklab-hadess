package io.thoughtware.hadess.repository.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name="pack_backups")
public class XpackBackupsEntity {

    @Id
    @GeneratorValue(length=12)
    @Column(name = "id")
    private String id;

    //定时状态 开启open 关闭:close
    @Column(name = "task_state")
    private String taskState;


    //最后执行的结果
    @Column(name = "exec_result")
    private String execResult;

    //最近执行时间
    @Column(name = "exec_time")
    private String execTime;

    //执行状态
    @Column(name = "exec_state")
    private String execState;


    //日志
    @Column(name = "log")
    private String log;



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

    public String getExecResult() {
        return execResult;
    }

    public void setExecResult(String execResult) {
        this.execResult = execResult;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
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
