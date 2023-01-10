package net.tiklab.xpack.repository.entity;

import net.tiklab.core.BaseModel;
import net.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pack_storage")
public class StorageEntity extends BaseModel {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    @Column(name = "name",length = 128,notNull = true)
    private String name;

    @Column(name = "all_size",length = 12)
    private String allSize;

    @Column(name = "residue_size",length = 12)
    private String residueSize;

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
