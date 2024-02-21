package io.thoughtware.hadess.scan.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * ScanSet-扫描制品依赖
 */
@Entity
@Table(name="pack_scan_rely")
public class ScanRelyEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;


    //扫描制品id
    @Column(name = "scan_library_id",length = 32)
    private String scanLibraryId;

    //扫描制品结果id
    @Column(name = "scan_record_id",length = 12)
    private String scanRecordId;

    //扫描计划总的id
    @Column(name = "general_record_id",length = 12)
    private String generalRecordId;

    //依赖名字
    @Column(name = "rely_name",length = 64)
    private String relyName;

    //依赖vendor
    @Column(name = "rely_vendor",length = 64)
    private String relyVendor;

    //依赖版本
    @Column(name = "rely_version",length = 64)
    private String relyVersion;

    //依赖语言
    @Column(name = "rely_language",length = 32)
    private String relyLanguage;

    //父级id
    @Column(name = "rely_parent_id",length = 12)
    private String relyParentId;


    //第一层依赖的id
    @Column(name = "rely_one_id",length = 12)
    private String relyOneId;

    //漏洞数量
    @Column(name = "hole_count")
    private Integer holeCount;

    //依赖地址
    @Column(name = "rely_path",length = 428)
    private String relyPath;

    //依赖类型  直接依赖、简介依赖
    @Column(name = "rely_type",length = 32)
    private String relyType;

    //licenses
    @Column(name = "rely_licenses",length = 64)
    private String relyLicenses;


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

    public String getRelyName() {
        return relyName;
    }

    public void setRelyName(String relyName) {
        this.relyName = relyName;
    }

    public String getRelyVendor() {
        return relyVendor;
    }

    public void setRelyVendor(String relyVendor) {
        this.relyVendor = relyVendor;
    }

    public String getRelyVersion() {
        return relyVersion;
    }

    public void setRelyVersion(String relyVersion) {
        this.relyVersion = relyVersion;
    }

    public String getRelyLanguage() {
        return relyLanguage;
    }

    public void setRelyLanguage(String relyLanguage) {
        this.relyLanguage = relyLanguage;
    }

    public String getRelyPath() {
        return relyPath;
    }

    public void setRelyPath(String relyPath) {
        this.relyPath = relyPath;
    }

    public String getRelyType() {
        return relyType;
    }

    public void setRelyType(String relyType) {
        this.relyType = relyType;
    }

    public String getRelyLicenses() {
        return relyLicenses;
    }

    public void setRelyLicenses(String relyLicenses) {
        this.relyLicenses = relyLicenses;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRelyParentId() {
        return relyParentId;
    }

    public void setRelyParentId(String relyParentId) {
        this.relyParentId = relyParentId;
    }

    public Integer getHoleCount() {
        return holeCount;
    }

    public void setHoleCount(Integer holeCount) {
        this.holeCount = holeCount;
    }

    public String getRelyOneId() {
        return relyOneId;
    }

    public void setRelyOneId(String relyOneId) {
        this.relyOneId = relyOneId;
    }

    public String getScanRecordId() {
        return scanRecordId;
    }

    public void setScanRecordId(String scanRecordId) {
        this.scanRecordId = scanRecordId;
    }

    public String getGeneralRecordId() {
        return generalRecordId;
    }

    public void setGeneralRecordId(String generalRecordId) {
        this.generalRecordId = generalRecordId;
    }
}
