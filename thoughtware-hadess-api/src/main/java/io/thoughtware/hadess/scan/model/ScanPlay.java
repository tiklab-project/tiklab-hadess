package io.thoughtware.hadess.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.hadess.repository.model.Repository;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * ScanPlay 扫描计划
 */
@ApiModel
@Join
@Mapper
public class ScanPlay extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @NotNull
    @ApiProperty(name="playName",desc="计划名称")
    private String playName;

    @NotNull
    @ApiProperty(name="repository",desc="制品库",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;

    @NotNull
    @ApiProperty(name="scanScheme",desc="扫描方案",required = true)
    @Mappings({
            @Mapping(source = "scanScheme.id",target = "scanSchemeId")
    })
    @JoinQuery(key = "id")
    private ScanScheme scanScheme;

    @ApiProperty(name="scanTime",desc="扫描时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp scanTime;

    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;

    /*--------其他字段--------*/

    @ApiProperty(name="libraryNum",desc="制品数")
    private Integer libraryNum;

    @ApiProperty(name="userName",desc="扫描用户")
    private String userName;

    @ApiProperty(name="result",desc="结果")
    private String result;

    @ApiProperty(name="newScanTime",desc="最新扫描时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp newScanTime;

    @ApiProperty(name="scanGroup",desc="扫描记录code")
    private String scanGroup;

    @ApiProperty(name="newScanRecordId",desc="最新的扫描记录id")
    private String newScanRecordId;

    @ApiProperty(name="scanState",desc="扫描状态  true、false")
    private String scanState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Timestamp getScanTime() {
        return scanTime;
    }

    public void setScanTime(Timestamp scanTime) {
        this.scanTime = scanTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getLibraryNum() {
        return libraryNum;
    }

    public void setLibraryNum(Integer libraryNum) {
        this.libraryNum = libraryNum;
    }

    public ScanScheme getScanScheme() {
        return scanScheme;
    }

    public void setScanScheme(ScanScheme scanScheme) {
        this.scanScheme = scanScheme;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Timestamp getNewScanTime() {
        return newScanTime;
    }

    public void setNewScanTime(Timestamp newScanTime) {
        this.newScanTime = newScanTime;
    }

    public String getScanGroup() {
        return scanGroup;
    }

    public void setScanGroup(String scanGroup) {
        this.scanGroup = scanGroup;
    }

    public String getNewScanRecordId() {
        return newScanRecordId;
    }

    public void setNewScanRecordId(String newScanRecordId) {
        this.newScanRecordId = newScanRecordId;
    }

    public String getScanState() {
        return scanState;
    }

    public void setScanState(String scanState) {
        this.scanState = scanState;
    }
}
