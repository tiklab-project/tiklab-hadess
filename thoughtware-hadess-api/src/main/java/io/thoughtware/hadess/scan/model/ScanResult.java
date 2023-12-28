package io.thoughtware.hadess.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.sql.Timestamp;

/**
 * ScanResult扫描结果
 */
@ApiModel
@Join
@Mapper
public class ScanResult extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="holeType",desc="漏洞类型 本地制品：scanLibrary、依赖：relyLibrary")
    private String holeType;

    @ApiProperty(name="libraryId",desc="制品ID 本地制品和依赖制品")
    private String libraryId;

    @ApiProperty(name="scanRecordId",desc="扫描结果的id")
    private String scanRecordId;


    @ApiProperty(name="scanLibraryId",desc="扫描制品id")
    private String scanLibraryId;


    @ApiProperty(name="holeName",desc="漏洞名字")
    private String holeName;


    @ApiProperty(name="holeLevel",desc="漏洞等级 1-4递减")
    private Integer holeLevel;

    @ApiProperty(name="holeCwe",desc="漏洞holeCwe编号")
    private String holeCwe;

    @ApiProperty(name="holeCve",desc="漏洞cve编号")
    private String holeCve;


    @ApiProperty(name="holeCnnvd",desc="漏洞cnnvd 编号")
    private String holeCnnvd;

    @ApiProperty(name="holeCnvd",desc="漏洞Cnvd编号")
    private String holeCnvd;

    @ApiProperty(name="holeXmirror",desc="漏洞Xmirror编号")
    private String holeXmirror;


    @ApiProperty(name="releaseTime",desc="发布时间")
    private String releaseTime;

    @ApiProperty(name="holeDesc",desc="漏洞描述")
    private String holeDesc;

    @ApiProperty(name="repairSuggest",desc="修复建议")
    private String repairSuggest;

    @ApiProperty(name="creatTime",desc="creatTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private java.sql.Timestamp creatTime;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHoleType() {
        return holeType;
    }

    public void setHoleType(String holeType) {
        this.holeType = holeType;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
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
