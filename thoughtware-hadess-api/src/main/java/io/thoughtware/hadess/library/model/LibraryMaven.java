package io.thoughtware.hadess.library.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.beans.annotation.Mapping;
import io.thoughtware.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.join.annotation.Join;
import io.thoughtware.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

/**
 * LibraryMaven-maven制品差异数据模型
 */
@ApiModel
@Mapper
@Join
public class LibraryMaven extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @ApiProperty(name="repositoryId",desc="repositoryId")
    private String repositoryId;

    @NotNull
    @ApiProperty(name="library",desc="制品",required = true)
    @Mappings({
            @Mapping(source = "library.id",target = "libraryId")
    })
    @JoinQuery(key = "id")
    private Library library;

    @NotNull
    @ApiProperty(name="groupId",desc="groupId",required = true)
    private java.lang.String groupId;

    @ApiProperty(name="artifactId",desc="artifactId")
    private java.lang.String artifactId;

    @ApiProperty(name="createTime",desc="createTime")
    private java.sql.Timestamp createTime;

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

    public java.lang.String getGroupId() {
        return groupId;
    }

    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
    }
    public java.lang.String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(java.lang.String artifactId) {
        this.artifactId = artifactId;
    }
    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }
}
