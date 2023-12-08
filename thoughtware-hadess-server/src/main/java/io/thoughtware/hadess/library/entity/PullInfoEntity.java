package io.thoughtware.hadess.library.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * LibraryEntity 制品拉取信息实体
 */
@Entity
@Table(name="pack_pull_info")
public class PullInfoEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //制品id
    @Column(name = "library_id",length = 12,notNull = true)
    private String libraryId;

    //制品版本id
    @Column(name = "library_version_id",length = 12,notNull = true)
    private String libraryVersionId;

    //拉取人id
    @Column(name = "user_id",length = 12)
    private String userId;

    @Column(name = "pull_create")
    private Timestamp pullCreate;


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

    public String getLibraryVersionId() {
        return libraryVersionId;
    }

    public void setLibraryVersionId(String libraryVersionId) {
        this.libraryVersionId = libraryVersionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getPullCreate() {
        return pullCreate;
    }

    public void setPullCreate(Timestamp pullCreate) {
        this.pullCreate = pullCreate;
    }
}
