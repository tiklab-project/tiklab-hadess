package net.tiklab.xpack.repository.entity;

import net.tiklab.core.BaseModel;
import net.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pack_repository_group_items")
public class RepositoryGroupItemsEntity extends BaseModel {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    @Column(name = "repository_group_id",length = 32,notNull = true)
    private String  repositoryGroupId;

    @Column(name = "repository_id",length = 32,notNull = true)
    private String  repositoryId;

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