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
    @Column(name = "library_id",length = 32)
    private String libraryId;

    //制品版本id
    @Column(name = "library_version_id",length = 32)
    private String libraryVersionId;


    //制品库id
    @Column(name = "repository_id",length = 32)
    private String repositoryId;


    //类型
    @Column(name = "scan_type",length = 32)
    private String scanType;

    //严重漏洞
    @Column(name = "hole_severity")
    private Integer holeSeverity;

    //高级漏洞
    @Column(name = "hole_high")
    private Integer holeHigh;

    //中级漏洞
    @Column(name = "hole_middle")
    private Integer holeMiddle;

    //低级漏洞
    @Column(name = "hole_low")
    private Integer holeLow;

    //扫描时常
    @Column(name = "scan_time_long",length = 32)
    private String scanTimeLong;

    //扫描状态 0 未扫描 1
    @Column(name = "scan_state")
    private Integer scanState=0;

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

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public Integer getHoleSeverity() {
        return holeSeverity;
    }

    public void setHoleSeverity(Integer holeSeverity) {
        this.holeSeverity = holeSeverity;
    }

    public Integer getHoleHigh() {
        return holeHigh;
    }

    public void setHoleHigh(Integer holeHigh) {
        this.holeHigh = holeHigh;
    }

    public Integer getHoleMiddle() {
        return holeMiddle;
    }

    public void setHoleMiddle(Integer holeMiddle) {
        this.holeMiddle = holeMiddle;
    }

    public Integer getHoleLow() {
        return holeLow;
    }

    public void setHoleLow(Integer holeLow) {
        this.holeLow = holeLow;
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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getScanState() {
        return scanState;
    }

    public void setScanState(Integer scanState) {
        this.scanState = scanState;
    }
}
