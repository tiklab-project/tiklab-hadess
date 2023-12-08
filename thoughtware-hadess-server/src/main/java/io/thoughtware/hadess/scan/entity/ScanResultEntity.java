package io.thoughtware.hadess.scan.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * ScanResultEntity-扫描结果
 */
@Entity
@Table(name="pack_scan_result")
public class ScanResultEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //制品id(本地制品和依赖制品)
    @Column(name = "library_id",length = 12)
    private String libraryId;

    //扫描制品id
    @Column(name = "scan_library_id",length = 12)
    private String scanLibraryId;

    //扫描制品结果id
    @Column(name = "scan_record_id",length = 12)
    private String scanRecordId;


    //漏洞名字
    @Column(name = "hole_name",length = 324)
    private String holeName;


    //漏洞等级
    @Column(name = "hole_level")
    private Integer holeLevel;

    //漏洞cve编号
    @Column(name = "hole_cwe",length = 64)
    private String holeCwe;

    //漏洞cve编号
    @Column(name = "hole_cve",length = 64)
    private String holeCve;

    //漏洞Cnnvd编号
    @Column(name = "hole_cnnvd",length = 64)
    private String holeCnnvd;

    //漏洞Cnvd编号
    @Column(name = "hole_cnvd",length = 64)
    private String holeCnvd;

    //漏洞Xmirror编号
    @Column(name = "hole_xmirror",length = 64)
    private String holeXmirror;


    //发布时间
    @Column(name = "release_time",length = 32)
    private String releaseTime;


    //漏洞描述
    @Column(name = "hole_desc")
    private String holeDesc;

    //修复建议
    @Column(name = "repair_suggest")
    private String repairSuggest;

    //漏洞类型
    @Column(name = "hole_type")
    private String holeType;

    @Column(name = "creat_time")
    private Timestamp creatTime;

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

    public String getHoleType() {
        return holeType;
    }

    public void setHoleType(String holeType) {
        this.holeType = holeType;
    }

    public String getHoleName() {
        return holeName;
    }

    public void setHoleName(String holeName) {
        this.holeName = holeName;
    }

    public Integer getHoleLevel() {
        return holeLevel;
    }

    public void setHoleLevel(Integer holeLevel) {
        this.holeLevel = holeLevel;
    }

    public String getHoleCve() {
        return holeCve;
    }

    public void setHoleCve(String holeCve) {
        this.holeCve = holeCve;
    }

    public String getHoleCnnvd() {
        return holeCnnvd;
    }

    public void setHoleCnnvd(String holeCnnvd) {
        this.holeCnnvd = holeCnnvd;
    }

    public String getHoleCnvd() {
        return holeCnvd;
    }

    public void setHoleCnvd(String holeCnvd) {
        this.holeCnvd = holeCnvd;
    }

    public String getHoleXmirror() {
        return holeXmirror;
    }

    public void setHoleXmirror(String holeXmirror) {
        this.holeXmirror = holeXmirror;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getHoleDesc() {
        return holeDesc;
    }

    public void setHoleDesc(String holeDesc) {
        this.holeDesc = holeDesc;
    }

    public String getRepairSuggest() {
        return repairSuggest;
    }

    public void setRepairSuggest(String repairSuggest) {
        this.repairSuggest = repairSuggest;
    }

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public String getHoleCwe() {
        return holeCwe;
    }

    public void setHoleCwe(String holeCwe) {
        this.holeCwe = holeCwe;
    }

    public String getScanLibraryId() {
        return scanLibraryId;
    }

    public void setScanLibraryId(String scanLibraryId) {
        this.scanLibraryId = scanLibraryId;
    }

    public String getScanRecordId() {
        return scanRecordId;
    }

    public void setScanRecordId(String scanRecordId) {
        this.scanRecordId = scanRecordId;
    }
}
