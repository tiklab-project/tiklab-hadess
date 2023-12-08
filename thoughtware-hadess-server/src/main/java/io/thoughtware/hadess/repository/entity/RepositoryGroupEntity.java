package io.thoughtware.hadess.repository.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * RepositoryGroupEntity-组合库关联实体
 */
@Entity
@Table(name="pack_repository_group_items")
public class RepositoryGroupEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //组合库id
    @Column(name = "repository_group_id",length = 12,notNull = true)
    private String  repositoryGroupId;

    //关联库id
    @Column(name = "repository_id",length = 12,notNull = true)
    private String  repositoryId;

    //创建时间
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

    public String getRepositoryGroupId() {
        return repositoryGroupId;
    }

    public void setRepositoryGroupId(String repositoryGroupId) {
        this.repositoryGroupId = repositoryGroupId;
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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
