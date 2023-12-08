package io.thoughtware.hadess.scan.model;

import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

/**
 * 扫描的队列
 */
@ApiModel
public class ScanQueue {

    @ApiProperty(name="state",desc="状态 start、end")
    private String state;

    @ApiProperty(name="scanPlayId",desc="扫描计划id")
    private String scanPlayId;

    @ApiProperty(name="scanLibraryList",desc="扫描list")
    private List<ScanLibrary> scanLibraryList;

    public String getScanPlayId() {
        return scanPlayId;
    }

    public void setScanPlayId(String scanPlayId) {
        this.scanPlayId = scanPlayId;
    }

    public List<ScanLibrary> getScanLibraryList() {
        return scanLibraryList;
    }

    public void setScanLibraryList(List<ScanLibrary> scanLibraryList) {
        this.scanLibraryList = scanLibraryList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
