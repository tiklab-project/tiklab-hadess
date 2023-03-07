package io.tiklab.xpack.library.entity;

import io.tiklab.core.BaseModel;
import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * LibraryMavenEntity-maven制品差异数据实体
 */
@Entity
@Table(name="pack_library_maven")
public class LibraryMavenEntity extends BaseModel {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    //制品Id
    @Column(name = "library_id",length = 32,notNull = true)
    private String libraryId;

    //maven -groupId
    @Column(name = "group_id",length = 12,notNull = true)
    private String groupId;

    //maven -artifactId
    @Column(name = "artifact_id",length = 32)
    private String artifactId;

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
