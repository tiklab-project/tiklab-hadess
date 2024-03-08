package io.thoughtware.hadess.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@ApiModel
@Join
@Mapper
public class ScanSchemeHole implements Serializable {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="scanSchemeId",desc="扫描方案id")
    private String scanSchemeId;

    @ApiProperty(name="scanHoleId",desc="扫描漏洞id")
    private String scanHoleId;


    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;

    /*-------其他字段---------*/
    @ApiProperty(name="scanHoleIdList",desc="扫描漏洞id")
    private List<String> scanHoleIdList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScanSchemeId() {
        return scanSchemeId;
    }

    public void setScanSchemeId(String scanSchemeId) {
        this.scanSchemeId = scanSchemeId;
    }

    public String getScanHoleId() {
        return scanHoleId;
    }

    public void setScanHoleId(String scanHoleId) {
        this.scanHoleId = scanHoleId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List<String> getScanHoleIdList() {
        return scanHoleIdList;
    }

    public void setScanHoleIdList(List<String> scanHoleIdList) {
        this.scanHoleIdList = scanHoleIdList;
    }
}
