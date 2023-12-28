package io.thoughtware.hadess.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.user.user.model.User;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * LibraryVersion-制品版本模型
 */
@ApiModel
@Join
@Mapper
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


    @ApiProperty(name="User",desc="推送人",required = true)
    private String pusher;


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

    @ApiProperty(name="pullUser",desc="最近拉取人")
    private java.lang.String pullUser;

    @ApiProperty(name="pullTime",desc="最近拉取时间")
    private java.sql.Timestamp pullTime;

    @ApiProperty(name="pullNum",desc="拉取次数")
    private java.lang.Integer pullNum;

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

    public String getPusher() {
        return pusher;
    }

    public void setPusher(String pusher) {
        this.pusher = pusher;
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

    public String getPullUser() {
        return pullUser;
    }

    public void setPullUser(String pullUser) {
        this.pullUser = pullUser;
    }

    public Timestamp getPullTime() {
        return pullTime;
    }

    public void setPullTime(Timestamp pullTime) {
        this.pullTime = pullTime;
    }

    public Integer getPullNum() {
        return pullNum;
    }

    public void setPullNum(Integer pullNum) {
        this.pullNum = pullNum;
    }
}
