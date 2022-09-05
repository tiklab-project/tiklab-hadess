package com.tiklab.oms.role.model;

import com.tiklab.beans.annotation.Mapper;
import com.tiklab.core.BaseModel;
import com.tiklab.postin.annotation.ApiModel;
import com.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

@ApiModel
@Mapper(targetAlias = "RoleUserEntity")
public class RoleUser extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @ApiProperty(name="roleId",desc="roleId")
    private java.lang.String roleId;

    @ApiProperty(name="userId",desc="userId")
    private java.lang.String userId;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }
    public java.lang.String getRoleId() {
        return roleId;
    }

    public void setRoleId(java.lang.String roleId) {
        this.roleId = roleId;
    }
    public java.lang.String getUserId() {
        return userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }
}
