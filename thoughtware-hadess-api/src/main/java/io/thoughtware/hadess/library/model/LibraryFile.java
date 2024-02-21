package io.thoughtware.hadess.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

/**
 * LibraryFile-制品文件模型
 */
@ApiModel
@Join
@Mapper
public class LibraryFile extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="library",desc="制品",required = true)
    @Mappings({
            @Mapping(source = "library.id",target = "libraryId")
    })
    @JoinQuery(key = "id")
    private Library library;

    @ApiProperty(name="libraryVersion",desc="制品版本",required = true)
    @Mappings({
            @Mapping(source = "libraryVersion.id",target = "libraryVersionId")
    })
    @JoinQuery(key = "id")
    private LibraryVersion libraryVersion;

    @NotNull
    @ApiProperty(name="repository",desc="制品库",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;

    @NotNull
    @ApiProperty(name="fileSize",desc="文件大小",required = true)
    private java.lang.String fileSize;

    @ApiProperty(name="size",desc="文件大小")
    private java.lang.Long size;


    @NotNull
    @ApiProperty(name="fileName",desc="文件名称",required = true)
    private java.lang.String fileName;

    @NotNull
    @ApiProperty(name="fileUrl",desc="文件地址",required = true)
    private java.lang.String fileUrl;

    @ApiProperty(name="relativePath",desc="文件相对路径",required = true)
    private java.lang.String relativePath;

    @ApiProperty(name="snapshotVersion",desc="快照版本")
    private java.lang.String snapshotVersion;

    @ApiProperty(name="createTime",desc="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private java.sql.Timestamp createTime;


    /*---------其他字段-------*/
    @ApiProperty(name="repositoryName",desc="制品库名字")
    private java.lang.String repositoryName;



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

    public java.lang.String getFileSize() {
        return fileSize;
    }

    public void setFileSize(java.lang.String fileSize) {
        this.fileSize = fileSize;
    }
    public java.lang.String getFileName() {
        return fileName;
    }

    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }
    public java.lang.String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(java.lang.String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public LibraryVersion getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(LibraryVersion libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getSnapshotVersion() {
        return snapshotVersion;
    }

    public void setSnapshotVersion(String snapshotVersion) {
        this.snapshotVersion = snapshotVersion;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
