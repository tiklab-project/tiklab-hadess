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

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * ScanResult扫描制品
 */
@ApiModel
@Join
@Mapper
public class ScanLibrary extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="library",desc="制品",required = true)
    @Mappings({
            @Mapping(source = "library.id",target = "libraryId")
    })
    @JoinQuery(key = "id")
    private Library library;

    @ApiProperty(name="libraryVersion",desc="制品版本",required = true)
    @Mappings({
            @Mapping(source = "libraryVersion.id",target = "libraryVersionId")
    })
    @JoinQuery(key = "id")
    private LibraryVersion libraryVersion;

    @ApiProperty(name="repositoryId",desc="制品库id")
    private String repositoryId;

    @ApiProperty(name="holeSeverity",desc="严重漏洞")
    private Integer holeSeverity;

    @ApiProperty(name="holeHigh",desc="高级漏洞")
    private Integer holeHigh;

    @ApiProperty(name="holeMiddle",desc="中级漏洞")
    private Integer holeMiddle;

    @ApiProperty(name="holeLow",desc="低级漏洞")
    private Integer holeLow;

    @ApiProperty(name="scanType",desc="类型 library:制品列表扫描、")
    private String scanType;

    @ApiProperty(name="scanState",desc="扫描状态: 未扫描：0  、扫描：1 ")
    private Integer scanState=0;



    @ApiProperty(name="scanTimeLong",desc="扫描时常")
    private String scanTimeLong;

    @ApiProperty(name="创建时间",desc="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;

    @ApiProperty(name="更新时间",desc="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp updateTime;



    /*--------依赖数量----------*/
    @ApiProperty(name="relyNum",desc="依赖数量")
    private Integer relyNum;

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

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
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

    public LibraryVersion getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(LibraryVersion libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public Integer getRelyNum() {
        return relyNum;
    }

    public void setRelyNum(Integer relyNum) {
        this.relyNum = relyNum;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getScanState() {
        return scanState;
    }

    public void setScanState(Integer scanState) {
        this.scanState = scanState;
    }
}
