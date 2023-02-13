package net.tiklab.xpack.library.entity;

import net.tiklab.core.BaseModel;
import net.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pack_library_version")
public class LibraryVersionEntity extends BaseModel {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    @Column(name = "library_id",length = 32,notNull = true)
    private String libraryId;

    @Column(name = "repository_id",length = 32,notNull = true)
    private String repositoryId;

    @Column(name = "library_type",length = 32,notNull = true)
    private String libraryType;

    @Column(name = "version",length = 32,notNull = true)
    private String version;

    @Column(name = "size",length = 12)
    private String size;

    @Column(name = "hash",length = 32)
    private String hash;

    @Column(name = "pusher",length = 32)
    private String pusher;

    @Column(name = "Content_json")
    private String contentJson;

    @Column(name = "push_time")
    private Timestamp pushTime;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

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

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPusher() {
        return pusher;
    }

    public void setPusher(String pusher) {
        this.pusher = pusher;
    }

    public Timestamp getPushTime() {
        return pushTime;
    }

    public void setPushTime(Timestamp pushTime) {
        this.pushTime = pushTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
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
}
