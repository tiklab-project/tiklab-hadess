package io.thoughtware.hadess.pushcentral.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * PushRepositoryEntity-推送制品列表
 */
@Entity
@Table(name="pack_push_library")
public class PushLibraryEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;


    //推送组id
    @Column(name = "push_group_id",length = 12,notNull = true)
    private String pushGroupId;

    //制品库id
    @Column(name = "repository_id",length = 12,notNull = true)
    private String repositoryId;

    //制品id
    @Column(name = "library_id",length = 12,notNull = true)
    private String libraryId;


    //制品版本
    @Column(name = "library_version",length = 32)
    private String libraryVersion;

    //最后推送时间
    @Column(name = "last_push_time")
    private Timestamp lastPushTime;

    //最后推送结果
    @Column(name = "last_push_result",length = 12)
    private String lastPushResult;


    //推送人
    @Column(name = "user_id",length = 12)
    private String useId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
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

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getPushGroupId() {
        return pushGroupId;
    }

    public void setPushGroupId(String pushGroupId) {
        this.pushGroupId = pushGroupId;
    }

    public String getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(String libraryVersion) {
        this.libraryVersion = libraryVersion;
    }
}
