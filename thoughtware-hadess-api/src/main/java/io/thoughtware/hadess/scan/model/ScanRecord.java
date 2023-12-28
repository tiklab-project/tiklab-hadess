package io.thoughtware.hadess.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.hadess.library.model.Library;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;
import io.thoughtware.user.user.model.User;

import java.sql.Timestamp;
import java.util.List;

/**
 * ScanRecord 扫描记录
 */
@ApiModel
@Join
@Mapper
public class ScanRecord extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="scanLibraryId",desc="扫描制品Id")
    private String scanLibraryId;

    @ApiProperty(name="library",desc="制品")
    @Mappings({
            @Mapping(source = "library.id",target = "libraryId")
    })
    @JoinQuery(key = "id")
    private Library library;

    @ApiProperty(name="scanUser",desc="扫描用户")
    @Mappings({
            @Mapping(source = "scanUser.id",target = "scanUserId")
    })
    @JoinQuery(key = "id")
    private User scanUser;

    @ApiProperty(name="libraryVersion",desc="制品版本")
    private String libraryVersion;

    @ApiProperty(name="scanPlayId",desc="扫描计划id")
    private String scanPlayId;

    @ApiProperty(name="scanGroup",desc="扫描组")
    private String scanGroup;


    @ApiProperty(name="scanType",desc="扫描类型 ")
    private Timestamp scanType;

    @ApiProperty(name="holeSeverity",desc="严重漏洞")
    private Integer holeSeverity;

    @ApiProperty(name="holeHigh",desc="高级漏洞")
    private Integer holeHigh;

    @ApiProperty(name="holeMiddle",desc="中级漏洞")
    private Integer holeMiddle;

    @ApiProperty(name="holeLow",desc="低级漏洞")
    private Integer holeLow;

    @ApiProperty(name="scanTimeLong",desc="扫描时常")
    private String scanTimeLong;

    @ApiProperty(name="recordType",desc="扫描类型 general、each")
    private String recordType;



    @ApiProperty(name="log",desc="日志")
    private String log;

    @ApiProperty(name="scanWay",desc="扫描方式  手动：hand")
    private String scanWay;

    @ApiProperty(name="scanResult",desc="扫描结果 success、fail")
    private String scanResult;

    @ApiProperty(name="创建时间",desc="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;



    /*--------依赖数量----------*/
    @ApiProperty(name="relyNum",desc="依赖数量")
    private Integer relyNum;

    /*--- 其他字段---*/

    private List<ScanRely> scanRelyList;

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

    public Timestamp getScanType() {
        return scanType;
    }

    public void setScanType(Timestamp scanType) {
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

    public Integer getRelyNum() {
        return relyNum;
    }

    public void setRelyNum(Integer relyNum) {
        this.relyNum = relyNum;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public String getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(String libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public String getScanPlayId() {
        return scanPlayId;
    }

    public void setScanPlayId(String scanPlayId) {
        this.scanPlayId = scanPlayId;
    }

    public String getScanGroup() {
        return scanGroup;
    }

    public void setScanGroup(String scanGroup) {
        this.scanGroup = scanGroup;
    }

    public List<ScanRely> getScanRelyList() {
        return scanRelyList;
    }

    public void setScanRelyList(List<ScanRely> scanRelyList) {
        this.scanRelyList = scanRelyList;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getScanWay() {
        return scanWay;
    }

    public void setScanWay(String scanWay) {
        this.scanWay = scanWay;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public User getScanUser() {
        return scanUser;
    }

    public void setScanUser(User scanUser) {
        this.scanUser = scanUser;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
