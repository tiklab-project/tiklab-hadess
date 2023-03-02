package net.tiklab.xpack.library.entity;

import net.tiklab.core.BaseModel;
import net.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * LibraryEntity 制品实体
 */
@Entity
@Table(name="pack_library")
public class LibraryEntity extends BaseModel {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    //制品名称
    @Column(name = "name",length = 128,notNull = true)
    private String name;

    //类型 maven、npm
    @Column(name = "library_type",length = 32,notNull = true)
    private String libraryType;

    //最新版本
    @Column(name = "new_version",length = 32)
    private String newVersion;

    //制品库id
    @Column(name = "repository_id",length = 32,notNull = true)
    private String repositoryId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(String libraryType) {
        this.libraryType = libraryType;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
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

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }
}
