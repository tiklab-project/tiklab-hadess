package io.thoughtware.hadess.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.core.BaseModel;
import io.thoughtware.join.annotation.Join;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.sql.Timestamp;

/*
* 扫码漏洞
* */
@ApiModel
@Join
@Mapper
public class ScanHole extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="vendor",desc="vendor （groupId）")
    private String vendor;

    @ApiProperty(name="product",desc="产品名字")
    private String product;

    @ApiProperty(name="version",desc="版本")
    private String version;

    @ApiProperty(name="language",desc="语言")
    private String language;

    @ApiProperty(name="holeName",desc="漏洞名称")
    private String holeName;

    @ApiProperty(name="hole_number",desc="漏洞编号")
    private String holeNumber;

    @ApiProperty(name="holeLevel",desc="漏洞等级 1-4递减")
    private Integer holeLevel;

    @ApiProperty(name="suggestion",desc="建议")
    private String suggestion;

    @ApiProperty(name="describe",desc="描述")
    private String describe;


    @ApiProperty(name="创建时间",desc="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHoleName() {
        return holeName;
    }

    public void setHoleName(String holeName) {
        this.holeName = holeName;
    }

    public String getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(String holeNumber) {
        this.holeNumber = holeNumber;
    }

    public Integer getHoleLevel() {
        return holeLevel;
    }

    public void setHoleLevel(Integer holeLevel) {
        this.holeLevel = holeLevel;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
