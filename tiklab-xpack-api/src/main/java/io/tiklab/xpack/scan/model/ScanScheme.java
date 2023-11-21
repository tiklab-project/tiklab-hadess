package io.tiklab.xpack.scan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel
@Join
@Mapper
public class ScanScheme implements Serializable {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="schemeName",desc="方案名称")
    private String schemeName;

    @ApiProperty(name="schemeType",desc="扫描类型 默认:default、自定义:custom")
    private String schemeType;


    @ApiProperty(name="language",desc="语言 Java、JavaScript等")
    private String language;


    @ApiProperty(name="describe",desc="描述")
    private String describe;

    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
}
