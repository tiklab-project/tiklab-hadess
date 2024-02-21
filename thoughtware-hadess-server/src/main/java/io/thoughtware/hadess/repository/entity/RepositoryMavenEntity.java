package io.thoughtware.hadess.repository.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * RepositoryMavenEntity-maven制品库多余字段实体
 */
@Entity
@Table(name="pack_repository_maven")
public class RepositoryMavenEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //maven制品库版本 类型
    @Column(name = "version",length = 12,notNull = true)
    private String version;

    //制品库id
    @Column(name = "repository_id",length = 12)
    private String repositoryId;

    //是否覆盖
    @Column(name = "cover_state")
    private Integer coverState;


    @Column(name = "create_time")
    private Timestamp createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getCoverState() {
        return coverState;
    }

    public void setCoverState(Integer coverState) {
        this.coverState = coverState;
    }
}
