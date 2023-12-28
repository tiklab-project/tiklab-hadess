package io.thoughtware.hadess.library.model;

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
import java.util.List;

/**
 * Library 制品模型
 */
@ApiModel
@Join
@Mapper
public class Library extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="name",desc="制品名称",required = true)
    private java.lang.String name;

    @NotNull
    @ApiProperty(name="libraryType",desc="类型 maven、npm",required = true)
    private java.lang.String libraryType;

    @NotNull
    @ApiProperty(name="repository",desc="制品库",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;

    @ApiProperty(name="创建时间",desc="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp createTime;

    @ApiProperty(name="更新时间",desc="updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+8")
    private java.sql.Timestamp updateTime;

    @ApiProperty(name="newVersion",desc="最新版本")
    private java.lang.String newVersion;

    @ApiProperty(name="size",desc="最新版本大小")
    private java.lang.Long size;

    /*---------其他字段----------*/

    @ApiProperty(name="repositorySize",desc="制品大小")
    private java.lang.String repositorySize;

    @ApiProperty(name="groupId",desc="maven制品-groupId")
    private java.lang.String groupId;

    @ApiProperty(name="artifactId",desc="maven制品-artifactId")
    private java.lang.String artifactId;

    @ApiProperty(name="children",desc="版本list")
    private List<LibraryVersion> children;


    @ApiProperty(name="holeSeverity",desc="严重漏洞")
    private Integer holeSeverity;

    @ApiProperty(name="holeHigh",desc="高级漏洞")
    private Integer holeHigh;

    @ApiProperty(name="holeMiddle",desc="中级漏洞")
    private Integer holeMiddle;

    @ApiProperty(name="holeLow",desc="低级漏洞")
    private Integer holeLow;

    @ApiProperty(name="scanLibraryId",desc="扫描id")
    private String scanLibraryId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @ApiProperty(name="scanDate",desc="扫描时间")
    private Timestamp scanDate;

    @ApiProperty(name="version",desc="制品版本")
    private String version;

    @ApiProperty(name="oldVersion",desc="原版本")
    private String oldVersion;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getLibraryType() {
        return libraryType;
    }

    public void setLibraryType(java.lang.String libraryType) {
        this.libraryType = libraryType;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }
    public java.sql.Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.sql.Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public List<LibraryVersion> getChildren() {
        return children;
    }

    public void setChildren(List<LibraryVersion> children) {
        this.children = children;
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

    public Timestamp getScanDate() {
        return scanDate;
    }

    public void setScanDate(Timestamp scanDate) {
        this.scanDate = scanDate;
    }

    public String getScanLibraryId() {
        return scanLibraryId;
    }

    public void setScanLibraryId(String scanLibraryId) {
        this.scanLibraryId = scanLibraryId;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getRepositorySize() {
        return repositorySize;
    }

    public void setRepositorySize(String repositorySize) {
        this.repositorySize = repositorySize;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOldVersion() {
        return oldVersion;
    }

    public void setOldVersion(String oldVersion) {
        this.oldVersion = oldVersion;
    }
}
