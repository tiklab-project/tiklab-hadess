package io.thoughtware.hadess.repository.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * RepositoryClusterCfgEntity-制品库复制信息实体
 */
@Entity
@Table(name="pack_repository_cluster_cfg")
public class RepositoryClusterCfgEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //制品库id
    @Column(name = "repository_id",length = 12,notNull = true)
    private String repositoryId;

    //地址
    @Column(name = "url",length = 32,notNull = true)
    private String url;

    //来源
    @Column(name = "source",length = 32)
    private String source;

    //账号
    @Column(name = "account",length = 32,notNull = true)
    private String account;

    //密码
    @Column(name = "password",length = 32,notNull = true)
    private String password;

    //超时
    @Column(name = "time_out",length = 18)
    private String timeOut;

    //创建人
    @Column(name = "create_user",length = 12)
    private String createUser;

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

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
