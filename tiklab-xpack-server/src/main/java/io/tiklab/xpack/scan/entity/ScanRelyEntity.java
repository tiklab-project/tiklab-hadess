package io.tiklab.xpack.scan.entity;

import io.tiklab.core.BaseModel;
import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * ScanSet-扫描制品
 */
@Entity
@Table(name="pack_scan_library")
public class ScanLibraryEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //地址
    @Column(name = "library_id",length = 128)
    private String libraryId;

    //扫描时常
    @Column(name = "scan_time_long",length = 32)
    private String scanTimeLong;


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

    public String getScanTimeLong() {
        return scanTimeLong;
    }

    public void setScanTimeLong(String scanTimeLong) {
        this.scanTimeLong = scanTimeLong;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
