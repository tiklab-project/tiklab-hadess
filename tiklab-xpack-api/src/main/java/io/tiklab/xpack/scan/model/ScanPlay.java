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
}
