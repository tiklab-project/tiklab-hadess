package io.tiklab.xpack.scan.entity;

import io.tiklab.core.BaseModel;
import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * ScanResultEntity-扫描结果
 */
@Entity
@Table(name="pack_scan_hole")
public class ScanHoleEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //制品id
    @Column(name = "scan_rely_id",length = 12)
    private String scanRelyId;

    //漏洞名字
    @Column(name = "hole_name",length = 324)
    private String holeName;


    //漏洞等级
    @Column(name = "hole_level")
    private Integer holeLevel;

    //漏洞编号
    @Column(name = "hole_number",length = 524)
    private String holeNumber;


    //发布时间
    @Column(name = "release_time",length = 32)
    private String releaseTime;


    //漏洞描述
    @Column(name = "hole_desc")
    private String holeDesc;

    //修复建议
    @Column(name = "repair_suggest")
    private String repairSuggest;

    @Column(name = "creat_time")
    private Timestamp creatTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScanRelyId() {
        return scanRelyId;
    }

    public void setScanRelyId(String scanRelyId) {
        this.scanRelyId = scanRelyId;
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

    public String getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(String holeNumber) {
        this.holeNumber = holeNumber;
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
}
