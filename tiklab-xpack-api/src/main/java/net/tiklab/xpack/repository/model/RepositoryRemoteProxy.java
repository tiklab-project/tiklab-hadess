package net.tiklab.xpack.repository.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.core.BaseModel;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

@ApiModel
@Join
@Mapper(targetAlias = "RepositoryRemoteProxyEntity")
public class RepositoryRemoteProxy extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="repository",desc="制品库",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;

    @NotNull
    @ApiProperty(name="agencyUrl",desc="代理地址",required = true)
    private java.lang.String agencyUrl;

    @ApiProperty(name="agencyName",desc="代理名称")
    private java.lang.String agencyName;

    @ApiProperty(name="userName",desc="账号、用户")
    private java.lang.String userName;

    @ApiProperty(name="password",desc="密码")
    private java.lang.String password;

    @ApiProperty(name="createTime",desc="创建时间")
    private java.sql.Timestamp createTime;

    @ApiProperty(name="updateTime",desc="更新时间")
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

    public java.lang.String getAgencyUrl() {
        return agencyUrl;
    }

    public void setAgencyUrl(java.lang.String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }
    public java.lang.String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(java.lang.String agencyName) {
        this.agencyName = agencyName;
    }
    public java.lang.String getUserName() {
        return userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }
    public java.lang.String getPassword() {
        return password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
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