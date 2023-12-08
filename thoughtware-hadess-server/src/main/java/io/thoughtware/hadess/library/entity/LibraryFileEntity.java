package io.thoughtware.hadess.library.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * LibraryFileEntity-制品文件实体
 */
@Entity
@Table(name="pack_library_file")
public class LibraryFileEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //制品Id
    @Column(name = "library_id",length = 12,notNull = true)
    private String libraryId;

    //制品版本id
    @Column(name = "library_version_id",length = 12)
    private String libraryVersionId;

    //制品库Id
    @Column(name = "repository_id",length = 12,notNull = true)
    private String repositoryId;

    //文件大小
    @Column(name = "file_size",length = 32,notNull = true)
    private String fileSize;

    //文件名称
    @Column(name = "file_name",length = 256,notNull = true)
    private String fileName;

    //文件地址
    @Column(name = "file_url",length = 256,notNull = true)
    private String fileUrl;


    //文件相对路径
    @Column(name = "relative_path",length = 256)
    private String relativePath;


    //快照版本
    @Column(name = "snapshot_version",length = 255)
    private String snapshotVersion;

    @Column(name = "create_time")
    private Timestamp createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getLibraryVersionId() {
        return libraryVersionId;
    }

    public void setLibraryVersionId(String libraryVersionId) {
        this.libraryVersionId = libraryVersionId;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
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
}
