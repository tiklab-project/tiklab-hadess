package io.tiklab.xpack.scan.model;

import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

/**
 * 扫描的队列
 */
@ApiModel
public class ScanQueue {

    @ApiProperty(name="repositoryId",desc="制品库id")
    private String repositoryId;

    @ApiProperty(name="scanList",desc="扫描list")
    private List<Scan> scanList;

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public List<Scan> getScanList() {
        return scanList;
    }

    public void setScanList(List<Scan> scanList) {
        this.scanList = scanList;
    }
}
