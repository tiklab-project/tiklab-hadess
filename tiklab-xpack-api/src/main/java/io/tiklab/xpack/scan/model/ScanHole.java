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
import io.tiklab.xpack.library.model.LibraryVersion;
import io.tiklab.xpack.repository.model.Repository;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * ScanResult扫描结果
 */
@ApiModel
@Join
@Mapper
public class ScanResult extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @NotNull
    @ApiProperty(name="library",desc="制品",required = true)
    @Mappings({
            @Mapping(source = "library.id",target = "libraryId")
    })
    @JoinQuery(key = "id")
    private Library library;



    @ApiProperty(name="holeName",desc="漏洞名字")
    private String holeName;


    @ApiProperty(name="holeLevel",desc="漏洞等级")
    private String holeLevel;

    @ApiProperty(name="holeNumber",desc="漏洞编号")
    private String holeNumber;


    @ApiProperty(name="releaseTime",desc="发布时间")
    private String releaseTime;



    @ApiProperty(name="holeDesc",desc="漏洞描述")
    private String holeDesc;

    @ApiProperty(name="repairSuggest",desc="修复建议")
    private String repairSuggest;

    @ApiProperty(name="创建时间",desc="creatTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private java.sql.Timestamp creatTime;

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

    public String getHoleName() {
        return holeName;
    }

    public void setHoleName(String holeName) {
        this.holeName = holeName;
    }

    public String getHoleLevel() {
        return holeLevel;
    }

    public void setHoleLevel(String holeLevel) {
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
