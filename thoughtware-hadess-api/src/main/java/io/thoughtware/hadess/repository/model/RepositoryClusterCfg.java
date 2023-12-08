package io.thoughtware.hadess.repository.model;


import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.beans.annotation.Mapping;
import io.thoughtware.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.join.annotation.Join;
import io.thoughtware.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;
import io.thoughtware.user.user.model.User;

import javax.validation.constraints.NotNull;

/**
 * RepositoryClusterCfg-制品库复制信息模型
 */
@ApiModel
@Join
@Mapper(targetAlias = "RepositoryClusterCfgEntity")
public class RepositoryClusterCfg extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="repository",desc="制品库",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;

    @ApiProperty(name="createUser",desc="createUser")
    @Mappings({
            @Mapping(source = "user.id",target = "createUser")
    })
    @JoinQuery(key = "id")
    private User user;

    @NotNull
    @ApiProperty(name="url",desc="地址",required = true)
    private java.lang.String url;

    @ApiProperty(name="source",desc="来源")
    private java.lang.String source;

    @NotNull
    @ApiProperty(name="account",desc="账号",required = true)
    private java.lang.String account;

    @NotNull
    @ApiProperty(name="password",desc="密码",required = true)
    private java.lang.String password;

    @ApiProperty(name="timeOut",desc="超时")
    private java.lang.String timeOut;

    @ApiProperty(name="createTime",desc="createTime")
    private java.sql.Timestamp createTime;

    @ApiProperty(name="updateTime",desc="updateTime")
    private java.sql.Timestamp updateTime;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public java.lang.String getUrl() {
        return url;
    }

    public void setUrl(java.lang.String url) {
        this.url = url;
    }
    public java.lang.String getSource() {
        return source;
    }

    public void setSource(java.lang.String source) {
        this.source = source;
    }
    public java.lang.String getAccount() {
        return account;
    }

    public void setAccount(java.lang.String account) {
        this.account = account;
    }
    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }
    public java.lang.String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(java.lang.String timeOut) {
        this.timeOut = timeOut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }
    public java.sql.Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.sql.Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
