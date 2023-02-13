package net.tiklab.xpack.library.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.core.BaseModel;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.xpack.repository.model.Repository;

import javax.validation.constraints.NotNull;

@ApiModel
@Join
@Mapper(targetAlias = "LibraryEntity")
public class Library extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="name",desc="制品名称",required = true)
    private java.lang.String name;

    @NotNull
    @ApiProperty(name="libraryType",desc="类型 maven、npm",required = true)
    private java.lang.String libraryType;

    @NotNull
    @ApiProperty(name="repository",desc="制品库",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;

    @ApiProperty(name="创建时间",desc="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp createTime;

    @ApiProperty(name="更新时间",desc="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp updateTime;

    @ApiProperty(name="newVersion",desc="最新版本")
    private java.lang.String newVersion;

    /*---------其他字段----------*/
    @ApiProperty(name="groupId",desc="groupId")
    private java.lang.String groupId;

    @ApiProperty(name="artifactId",desc="artifactId")
    private java.lang.String artifactId;


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
    public java.lang.String getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(java.lang.String libraryType) {
        this.libraryType = libraryType;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
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

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }
}
