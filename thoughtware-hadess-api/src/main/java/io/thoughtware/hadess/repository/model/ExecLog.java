package io.thoughtware.hadess.repository.model;

import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

@ApiModel
public class ExecLog {

    @ApiProperty(name="execState",desc="执行状态")
    private String execState;


    @ApiProperty(name="log",desc="日志")
    private String log;

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
