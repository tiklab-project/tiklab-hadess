package io.tiklab.xpack.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.user.user.model.User;
import io.tiklab.xpack.repository.model.Repository;
import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.core.BaseModel;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

/**
 * LibraryVersion-制品版本模型
 */
@ApiModel
@Join
@Mapper(targetAlias = "LibraryVersionEntity")
public class LibraryVersion extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="library",desc="制品",required = true)
    @Mappings({
            @Mapping(source = "library.id",target = "libraryId")
    })
    @JoinQuery(key = "id")
    private Library library;

    @NotNull
    @ApiProperty(name="repository",desc="制品库",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;

    @NotNull
    @ApiProperty(name="libraryType",desc="类型 maven、npm",required = true)
    private java.lang.String libraryType;

    @NotNull
    @ApiProperty(name="version",desc="版本",required = true)
    private java.lang.String version;

    @ApiProperty(name="hash",desc="hash")
    private java.lang.String hash;

    @NotNull
    @ApiProperty(name="User",desc="推送人",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "pusher")
    })
    @JoinQuery(key = "id")
    private User user;


    @ApiProperty(name="contentJson",desc="内容json  mpm用")
    private java.lang.String contentJson;

    @ApiProperty(name="pushTime",desc="推送时间")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp pushTime;

    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private java.sql.Timestamp createTime;

    @ApiProperty(name="updateTime",desc="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private java.sql.Timestamp updateTime;


    /*--------------其他字段-------------*/
    @ApiProperty(name="groupId",desc="groupId")
    private java.lang.String groupId;

    @ApiProperty(name="artifactId",desc="artifactId")
    private java.lang.String artifactId;

    @ApiProperty(name="size",desc="size 大小")
    private java.lang.String size;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public java.lang.String getVersion() {
        return version;
    }

    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    public java.lang.String getHash() {
        return hash;
    }

    public void setHash(java.lang.String hash) {
        this.hash = hash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.sql.Timestamp getPushTime() {
        return pushTime;
    }

    public void setPushTime(java.sql.Timestamp pushTime) {
        this.pushTime = pushTime;
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

    public String getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(String libraryType) {
        this.libraryType = libraryType;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}