package net.tiklab.xpack.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.core.BaseModel;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

@ApiModel
@Mapper(targetAlias = "RepositoryEntity")
public class Repository extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="name",desc="制品库名字",required = true)
    private java.lang.String name;

    @ApiProperty(name="repositoryUrl",desc="制品库路径")
    private java.lang.String repositoryUrl;

    @NotNull
    @ApiProperty(name="type",desc="创建类型 maven、npm等 ",required = true)
    private java.lang.String type;

    @NotNull
    @ApiProperty(name="repositoryType",desc="类型  本地库：local、远程库：remote、组合库：group",required = true)
    private java.lang.String repositoryType;

    @NotNull
    @ApiProperty(name="storage",desc="存储库id",required = true)
    @Mappings({
            @Mapping(source = "storage.id",target = "storageId")
    })
    @JoinQuery(key = "id")
    private Storage storage;


    @ApiProperty(name="description",desc="描述")
    private java.lang.String description;

    @ApiProperty(name="createUser",desc="创建人")
    private java.lang.String createUser;

    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp createTime;

    @ApiProperty(name="updateTime",desc="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp updateTime;

    /*------------其他字段----------*/
    @ApiProperty(name="libraryNum",desc="制品数量")
    private Integer  libraryNum;

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
    public java.lang.String getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(java.lang.String repositoryType) {
        this.repositoryType = repositoryType;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    public java.lang.String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(java.lang.String createUser) {
        this.createUser = createUser;
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


    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLibraryNum() {
        return libraryNum;
    }

    public void setLibraryNum(Integer libraryNum) {
        this.libraryNum = libraryNum;
    }
}
