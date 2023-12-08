package io.thoughtware.hadess.scan.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * ScanHoleEntity-扫描制品漏洞
 */
@Entity
@Table(name="pack_scan_hole")
public class ScanHoleEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //vendor  （groupId）
    @Column(name = "vendor",length = 64)
    private String vendor;

    //仓配
    @Column(name = "product",length = 32)
    private String product;

    @Column(name = "version")
    private String version;

    @Column(name = "language")
    private String language;

    @Column(name = "hole_name")
    private String holeName;

    @Column(name = "hole_number")
    private String holeNumber;

    @Column(name = "hole_level")
    private Integer holeLevel;

    @Column(name = "suggestion")
    private String suggestion;

    @Column(name = "describe")
    private String describe;

    @Column(name = "create_time")
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
