package io.tiklab.xpack.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.core.BaseModel;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.xpack.library.model.Library;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

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
    @ApiProperty(name="library",desc="制品",required = true)
    @Mappings({
            @Mapping(source = "library.id",target = "libraryId")
    })
    @JoinQuery(key = "id")
    private Library library;


    @NotNull
    @ApiProperty(name="scanLibrary",desc="扫描制品",required = true)
    @Mappings({
            @Mapping(source = "scanLibrary.id",target = "scanLibraryId")
    })
    @JoinQuery(key = "id")
    private ScanLibrary scanLibrary;

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

    @ApiProperty(name="relyType",desc="依赖类型  直接依赖、简介依赖")
    private String relyType;

    @ApiProperty(name="relyLicenses",desc="licenses")
    private String relyLicenses;


    @ApiProperty(name="创建时间",desc="creatTime")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private Timestamp creatTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
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
}
