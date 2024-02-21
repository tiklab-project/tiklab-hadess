package io.thoughtware.hadess.pushcentral.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.core.BaseModel;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;

import java.sql.Timestamp;

/**
 * PushGroup-推送组模型
 */
@ApiModel
@Join
@Mapper
public class PushGroup extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;


    @ApiProperty(name="groupName",desc="推送组名")
    private String groupName;

    @ApiProperty(name="repositoryId",desc="制品库id")
    private String repositoryId;



    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;

    /*------其他字段------*/
    @ApiProperty(name="pushLibraryNum",desc="推送制品数量")
    private Integer pushLibraryNum;

    @ApiProperty(name="lastPushTime",desc="最后推送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp lastPushTime;

    @ApiProperty(name="lastPushResult",desc="最后推送结果 fail、success")
    private String lastPushResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Timestamp getLastPushTime() {
        return lastPushTime;
    }

    public void setLastPushTime(Timestamp lastPushTime) {
        this.lastPushTime = lastPushTime;
    }

    public String getLastPushResult() {
        return lastPushResult;
    }

    public void setLastPushResult(String lastPushResult) {
        this.lastPushResult = lastPushResult;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getPushLibraryNum() {
        return pushLibraryNum;
    }

    public void setPushLibraryNum(Integer pushLibraryNum) {
        this.pushLibraryNum = pushLibraryNum;
    }
}
