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
import net.tiklab.user.user.model.User;
import net.tiklab.xpack.repository.model.Repository;

import javax.validation.constraints.NotNull;

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
    @ApiProperty(name="version",desc="版本",required = true)
    private java.lang.String version;

    @ApiProperty(name="size",desc="大小")
    private java.lang.String size;

    @ApiProperty(name="hash",desc="hash")
    private java.lang.String hash;


    @NotNull
    @ApiProperty(name="User",desc="推送人",required = true)
    @Mappings({
            @Mapping(source = "User.id",target = "pusher")
    })
    @JoinQuery(key = "id")
    private User User;

    @ApiProperty(name="pushTime",desc="推送时间")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp pushTime;

    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp createTime;

    @ApiProperty(name="updateTime",desc="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp updateTime;

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
    public java.lang.String getSize() {
        return size;
    }

    public void setSize(java.lang.String size) {
        this.size = size;
    }
    public java.lang.String getHash() {
        return hash;
    }

    public void setHash(java.lang.String hash) {
        this.hash = hash;
    }

    public net.tiklab.user.user.model.User getUser() {
        return User;
    }

    public void setUser(net.tiklab.user.user.model.User user) {
        User = user;
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
}