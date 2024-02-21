package io.thoughtware.hadess.scan.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * ScanRecordEntity-扫描制品结果
 */
@Entity
@Table(name="pack_scan_record")
public class ScanRecordEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //扫描制品的id
    @Column(name = "scan_library_id",length = 12)
    private String scanLibraryId;

    //制品的id
    @Column(name = "library_id",length = 12)
    private String libraryId;


    //扫描计划的id
    @Column(name = "scan_play_id",length = 12)
    private String scanPlayId;



    //扫描group
    @Column(name = "scan_group",length = 12)
    private String scanGroup;


    //制品的版本
    @Column(name = "library_version",length = 64)
    private String libraryVersion;


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


    //日志
    @Column(name = "log",length = 32)
    private String log;

    //扫描方式
    @Column(name = "scan_way",length = 32)
    private String scanWay;
    //扫描结果
    @Column(name = "scan_result",length = 32)
    private String scanResult;

    @Column(name = "scan_user_id")
    private String scanUserId;

    @Column(name = "record_type")
    private String recordType;


    @Column(name = "create_time")
    private Timestamp createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScanLibraryId() {
        return scanLibraryId;
    }

    public void setScanLibraryId(String scanLibraryId) {
        this.scanLibraryId = scanLibraryId;
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

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(String libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public String getScanPlayId() {
        return scanPlayId;
    }

    public void setScanPlayId(String scanPlayId) {
        this.scanPlayId = scanPlayId;
    }

    public String getScanGroup() {
        return scanGroup;
    }

    public void setScanGroup(String scanGroup) {
        this.scanGroup = scanGroup;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getScanWay() {
        return scanWay;
    }

    public void setScanWay(String scanWay) {
        this.scanWay = scanWay;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getScanUserId() {
        return scanUserId;
    }

    public void setScanUserId(String scanUserId) {
        this.scanUserId = scanUserId;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

}
