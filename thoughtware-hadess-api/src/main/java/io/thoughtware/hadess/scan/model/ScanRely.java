package io.thoughtware.hadess.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.beans.annotation.Mapping;
import io.thoughtware.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.join.annotation.Join;
import io.thoughtware.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * ScanResult扫描依赖
 */
@ApiModel
@Join
@Mapper
public class ScanRely extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;


    @NotNull
    @ApiProperty(name="scanLibrary",desc="扫描制品",required = true)
    @Mappings({
            @Mapping(source = "scanLibrary.id",target = "scanLibraryId")
    })
    @JoinQuery(key = "id")
    private ScanLibrary scanLibrary;


    @ApiProperty(name="scanRecordId",desc="扫描结果的id")
    private String scanRecordId;

    @ApiProperty(name="relyName",desc="依赖名字")
    private String relyName;

    @ApiProperty(name="relyVendor",desc="依赖vendor")
    private String relyVendor;

    @ApiProperty(name="relyVersion",desc="依赖版本")
    private String relyVersion;

    @ApiProperty(name="relyLanguage",desc="依赖语言")
    private String relyLanguage;

    @ApiProperty(name="relyPath",desc="依赖地址")
    private String relyPath;

    @ApiProperty(name="relyType",desc="依赖类型 direct： 直接依赖、indirect：间接依赖")
    private String relyType;

    @ApiProperty(name="relyLicenses",desc="licenses")
    private String relyLicenses;

    @ApiProperty(name="relyParentId",desc="依赖父级id")
    private String relyParentId;

    @ApiProperty(name="relyOneId",desc="第一层 依赖的id")
    private String relyOneId;



    @ApiProperty(name="holeCount",desc="漏洞数量")
    private Integer holeCount;

    @ApiProperty(name="创建时间",desc="creatTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp creatTime;

    /*--- 其他字段---*/

    private List<ScanRely> scanRelyList;

    private List<ScanResult> scanResultList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public ScanLibrary getScanLibrary() {
        return scanLibrary;
    }

    public void setScanLibrary(ScanLibrary scanLibrary) {
        this.scanLibrary = scanLibrary;
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

    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
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

    public List<ScanRely> getScanRelyList() {
        return scanRelyList;
    }

    public void setScanRelyList(List<ScanRely> scanRelyList) {
        this.scanRelyList = scanRelyList;
    }

    public String getRelyOneId() {
        return relyOneId;
    }

    public void setRelyOneId(String relyOneId) {
        this.relyOneId = relyOneId;
    }

    public List<ScanResult> getScanResultList() {
        return scanResultList;
    }

    public void setScanResultList(List<ScanResult> scanResultList) {
        this.scanResultList = scanResultList;
    }

    public String getScanRecordId() {
        return scanRecordId;
    }

    public void setScanRecordId(String scanRecordId) {
        this.scanRecordId = scanRecordId;
    }
}
