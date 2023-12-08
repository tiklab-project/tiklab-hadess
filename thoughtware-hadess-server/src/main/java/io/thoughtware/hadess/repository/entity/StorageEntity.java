package io.thoughtware.hadess.repository.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * StorageEntity-存储库实体
 */
@Entity
@Table(name="pack_storage")
public class StorageEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //存储库名称
    @Column(name = "name",length = 128,notNull = true)
    private String name;

    //总大小
    @Column(name = "all_size",length = 12)
    private String allSize;

    //剩余大小
    @Column(name = "residue_size",length = 12)
    private String residueSize;

    //创建时间
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

    public String getAllSize() {
        return allSize;
    }

    public void setAllSize(String allSize) {
        this.allSize = allSize;
    }

    public String getResidueSize() {
        return residueSize;
    }

    public void setResidueSize(String residueSize) {
        this.residueSize = residueSize;
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
}
