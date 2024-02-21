package io.thoughtware.hadess.pushcentral.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.core.BaseModel;
import io.thoughtware.hadess.library.model.Library;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;

import java.sql.Timestamp;

/**
 * PushOperation-推送
 */
@ApiModel
public class PushOperation extends BaseModel {

    @ApiProperty(name="log",desc="日志")
    private String log;

    @ApiProperty(name="pushResult",desc="推送结果 success、fail")
    private String pushResult;

    @ApiProperty(name="resultKey",desc="存储结果的key pushGroupId、loginId")
    private String resultKey;

    @ApiProperty(name="pushTimeLong",desc="推送时长")
    private String pushTimeLong;

    @ApiProperty(name="startTime",desc="开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp startTime;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getPushResult() {
        return pushResult;
    }

    public void setPushResult(String pushResult) {
        this.pushResult = pushResult;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public String getPushTimeLong() {
        return pushTimeLong;
    }

    public void setPushTimeLong(String pushTimeLong) {
        this.pushTimeLong = pushTimeLong;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
}
