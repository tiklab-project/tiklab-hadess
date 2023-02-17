package net.tiklab.xpack.library.entity;

import net.tiklab.core.BaseModel;
import net.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pack_library_file")
public class LibraryFileEntity extends BaseModel {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    @Column(name = "library_id",length = 32,notNull = true)
    private String libraryId;

    @Column(name = "library_version_id",length = 32,notNull = true)
    private String libraryVersionId;

    @Column(name = "repository_id",length = 32,notNull = true)
    private String repositoryId;

    @Column(name = "file_size",length = 32,notNull = true)
    private String fileSize;

    @Column(name = "file_name",length = 256,notNull = true)
    private String fileName;

    @Column(name = "file_url",length = 128,notNull = true)
    private String fileUrl;

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
}
