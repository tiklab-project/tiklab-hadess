package io.thoughtware.hadess.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.core.BaseModel;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;
import io.thoughtware.toolkit.beans.annotation.Mapper;

import java.sql.Timestamp;

@ApiModel
@Mapper
public class RemoteProxy extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @ApiProperty(name="agencyUrl",desc="代理地址")
    private java.lang.String agencyUrl;

    @ApiProperty(name="agencyName",desc="代理名字")
    private java.lang.String agencyName;

    @ApiProperty(name="agencyType",desc="代理类型 maven、npm、docker")
    private java.lang.String agencyType;

    @ApiProperty(name="type",desc="类型 0 默认、 1")
    private java.lang.Integer type;

    @ApiProperty(name="account",desc="账号")
    private java.lang.String account;

    @ApiProperty(name="password",desc="密码")
    private java.lang.String password;

    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.sql.Timestamp createTime;

    /*------其他字段-------*/

    @ApiProperty(name="execType",desc="执行类型 add、update")
    private java.lang.String  execType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public void setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(String agencyType) {
        this.agencyType = agencyType;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExecType() {
        return execType;
    }

    public void setExecType(String execType) {
        this.execType = execType;
    }
}
