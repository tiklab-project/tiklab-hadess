package io.thoughtware.hadess.scan.entity;

import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pack_scan_scheme_hole")
public class ScanSchemeHoleEntity {
    @Id
    @GeneratorValue(length=12)
    @Column(name = "id")
    private String id;

    @Column(name = "scan_scheme_id" ,notNull = true)
    private String scanSchemeId;

    @Column(name = "scan_hole_id",notNull = true)
    private String scanHoleId;

    @Column(name = "create_time")
    private Timestamp createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScanSchemeId() {
        return scanSchemeId;
    }

    public void setScanSchemeId(String scanSchemeId) {
        this.scanSchemeId = scanSchemeId;
    }

    public String getScanHoleId() {
        return scanHoleId;
    }

    public void setScanHoleId(String scanHoleId) {
        this.scanHoleId = scanHoleId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
