package io.thoughtware.hadess.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.beans.annotation.Mapping;
import io.thoughtware.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.join.annotation.Join;
import io.thoughtware.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;
import io.thoughtware.user.user.model.User;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Library 制品拉取信息模型
 */
@ApiModel
@Join
@Mapper
public class PullInfo extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @NotNull
    @ApiProperty(name="library",desc="制品",required = true)
    @Mappings({
            @Mapping(source = "library.id",target = "libraryId")
    })
    @JoinQuery(key = "id")
    private Library library;

    @NotNull
    @ApiProperty(name="libraryVersion",desc="制品版本",required = true)
    @Mappings({
            @Mapping(source = "libraryVersion.id",target = "libraryVersionId")
    })
    @JoinQuery(key = "id")
    private LibraryVersion libraryVersion;


    @NotNull
    @ApiProperty(name="User",desc="推送人",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name="pullCreate",desc="拉取时间")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp pullCreate;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public LibraryVersion getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(LibraryVersion libraryVersion) {
        this.libraryVersion = libraryVersion;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getPullCreate() {
        return pullCreate;
    }

    public void setPullCreate(Timestamp pullCreate) {
        this.pullCreate = pullCreate;
    }
}
