package com.tiklab.oms.role.model;


import com.tiklab.beans.annotation.Mapper;
import com.tiklab.core.BaseModel;
import com.tiklab.postin.annotation.ApiModel;
import com.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

@ApiModel
@Mapper(targetAlias = "RoleEntity")
public class Role extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @ApiProperty(name="name",desc="name")
    private java.lang.String name;

    @ApiProperty(name="desc",desc="desc")
    private java.lang.String desc;

    @ApiProperty(name="grouper",desc="grouper")
    private java.lang.String grouper;

    @ApiProperty(name="type",desc="type")
    private java.lang.String type;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getDesc() {
        return desc;
    }

    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }
    public java.lang.String getGrouper() {
        return grouper;
    }

    public void setGrouper(java.lang.String grouper) {
        this.grouper = grouper;
    }
    public java.lang.String getType() {
        return type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }
}
