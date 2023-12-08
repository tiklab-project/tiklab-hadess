package io.thoughtware.hadess.scan.model;

import io.thoughtware.core.BaseModel;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class Scan extends BaseModel {

    @ApiProperty(name="scanLibraryId",desc="扫描制品id")
    private String scanLibraryId;

    @ApiProperty(name="scanLibraryIds",desc="制品库id")
    private String repositoryId;

    @ApiProperty(name="scanLibraryIds",desc="版本")
    private String version;

    @ApiProperty(name="scanLibraryIds",desc="制品id")
    private String libraryId;


    @ApiProperty(name="scanLibraryIds",desc="扫描制品list")
    private List<String> scanLibraryIds;

    public List<String> getScanLibraryIds() {
        return scanLibraryIds;
    }

    public void setScanLibraryIds(List<String> scanLibraryIds) {
        this.scanLibraryIds = scanLibraryIds;
    }

    public String getScanLibraryId() {
        return scanLibraryId;
    }

    public void setScanLibraryId(String scanLibraryId) {
        this.scanLibraryId = scanLibraryId;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }
}
