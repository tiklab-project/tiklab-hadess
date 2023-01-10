package net.tiklab.xpack.repository.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.core.BaseModel;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

@ApiModel
@Mapper(targetAlias = "StorageEntity")
public class Storage extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="name",desc="存储库名称",required = true)
    private java.lang.String name;

    @ApiProperty(name="allSize",desc="总大小")
    private java.lang.String allSize;

    @ApiProperty(name="residueSize",desc="剩余大小")
    private java.lang.String residueSize;

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
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getAllSize() {
        return allSize;
    }

    public void setAllSize(java.lang.String allSize) {
        this.allSize = allSize;
    }
    public java.lang.String getResidueSize() {
        return residueSize;
    }

    public void setResidueSize(java.lang.String residueSize) {
        this.residueSize = residueSize;
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
