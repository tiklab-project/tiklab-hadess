package io.thoughtware.hadess.repository.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * RepositoryRemoteProxyEntity-远程库代理信息实体
 */
@Entity
@Table(name="pack_repository_remote_proxy")
public class RepositoryRemoteProxyEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //制品库Id
    @Column(name = "repository_id",length = 12,notNull = true)
    private String repositoryId;


    //代理Id
    @Column(name = "remote_proxy_id",length = 12,notNull = true)
    private String remoteProxyId;



    @Column(name = "create_time")
    private Timestamp createTime;


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



    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    public String getRemoteProxyId() {
        return remoteProxyId;
    }

    public void setRemoteProxyId(String remoteProxyId) {
        this.remoteProxyId = remoteProxyId;
    }
}
