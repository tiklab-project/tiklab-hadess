package io.thoughtware.hadess.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.hadess.library.model.Library;
import io.thoughtware.hadess.library.model.LibraryVersion;
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
 * ScanResult扫描制品
 */
@ApiModel
@Join
@Mapper
public class ScanLibrary extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="scanPlayId",desc="扫描计划id",required = true)
    private String scanPlayId;

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


    @ApiProperty(name="scanState",desc="扫描状态: 未扫描：0  、扫描：1 ")
    private Integer scanState=0;


    @ApiProperty(name="创建时间",desc="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;



    /*--------其他字段----------*/
    @ApiProperty(name="scanRecord",desc="scanRecord")
    private ScanRecord scanRecord;


    @ApiProperty(name="scanResult",desc="扫描结果 success、false")
    private String  scanResult;

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

    public LibraryVersion getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(LibraryVersion libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Integer getScanState() {
        return scanState;
    }

    public void setScanState(Integer scanState) {
        this.scanState = scanState;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public ScanRecord getScanRecord() {
        return scanRecord;
    }

    public void setScanRecord(ScanRecord scanRecord) {
        this.scanRecord = scanRecord;
    }

    public String getScanPlayId() {
        return scanPlayId;
    }

    public void setScanPlayId(String scanPlayId) {
        this.scanPlayId = scanPlayId;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public Integer getRelyNum() {
        return relyNum;
    }

    public void setRelyNum(Integer relyNum) {
        this.relyNum = relyNum;
    }
}
