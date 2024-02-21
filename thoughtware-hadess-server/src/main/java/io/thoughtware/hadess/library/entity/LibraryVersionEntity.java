package io.thoughtware.hadess.library.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * LibraryVersionEntity-制品版本实体
 */
@Entity
@Table(name="pack_library_version")
public class LibraryVersionEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //制品Id
    @Column(name = "library_id",length = 12,notNull = true)
    private String libraryId;

    //制品库Id
    @Column(name = "repository_id",length = 12,notNull = true)
    private String repositoryId;

    //类型 maven、npm
    @Column(name = "library_type",length = 32,notNull = true)
    private String libraryType;

    @Column(name = "size")
    private Integer size;

    //版本
    @Column(name = "version",length = 32,notNull = true)
    private String version;

    //hash
    @Column(name = "hash",length = 320)
    private String hash;

    //推送人
    @Column(name = "pusher",length = 32)
    private String pusher;

    //内容json  mpm用
    @Column(name = "Content_json")
    private String contentJson;

    //推送时间
    @Column(name = "push_time")
    private Timestamp pushTime;

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


    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPusher() {
        return pusher;
    }

    public void setPusher(String pusher) {
        this.pusher = pusher;
    }

    public Timestamp getPushTime() {
        return pushTime;
    }

    public void setPushTime(Timestamp pushTime) {
        this.pushTime = pushTime;
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

    public String getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(String libraryType) {
        this.libraryType = libraryType;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
