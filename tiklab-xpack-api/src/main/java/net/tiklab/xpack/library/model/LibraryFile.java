package net.tiklab.xpack.library.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.core.BaseModel;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

@ApiModel
@Join
@Mapper(targetAlias = "LibraryFileEntity")
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

    @NotNull
    @ApiProperty(name="libraryVersion",desc="制品版本",required = true)
    @Mappings({
            @Mapping(source = "libraryVersion.id",target = "libraryVersionId")
    })
    @JoinQuery(key = "id")
    private LibraryVersion libraryVersion;

    @NotNull
    @ApiProperty(name="fileSize",desc="文件大小",required = true)
    private java.lang.String fileSize;

    @NotNull
    @ApiProperty(name="fileName",desc="文件名称",required = true)
    private java.lang.String fileName;

    @NotNull
    @ApiProperty(name="fileUrl",desc="文件地址",required = true)
    private java.lang.String fileUrl;

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
}
