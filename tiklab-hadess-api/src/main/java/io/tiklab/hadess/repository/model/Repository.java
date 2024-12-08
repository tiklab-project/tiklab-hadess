package io.tiklab.hadess.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.core.BaseModel;
import io.tiklab.toolkit.join.annotation.JoinQuery;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;
import java.util.List;


@ApiModel
@Mapper
public class Repository extends BaseModel {


    @ApiProperty(name="id",desc="id")
    private java.lang.String id;


    @NotNull
    @ApiProperty(name="name",desc="制品库名字",required = true)
    private java.lang.String name;


    @ApiProperty(name="repositoryUrl",desc="制品库路径")
    private java.lang.String repositoryUrl;


    @NotNull
    @ApiProperty(name="type",desc="创建类型 maven、npm、generic、docker等 ",required = true)
    private java.lang.String type;


    @NotNull
    @ApiProperty(name="repositoryType",desc="类型  本地库：local、远程库：remote、组合库：group",required = true)
    private java.lang.String repositoryType;


    @ApiProperty(name="storage",desc="存储库id")
    @Mappings({
            @Mapping(source = "storage.id",target = "storageId")
    })
    @JoinQuery(key = "id")
    private Storage storage;


    @ApiProperty(name="category",desc="1 、演示 ；2、正式仓库;3、公共库")
    private Integer category;

    @ApiProperty(name="color",desc="制品库图标颜色0-4")
    private Integer color;


    @ApiProperty(name="rules",desc="权限 public、private")
    private java.lang.String rules= "public";


    @ApiProperty(name="description",desc="描述")
    private java.lang.String description;


    @ApiProperty(name="createUser",desc="创建人")
    private java.lang.String createUser;


    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.sql.Timestamp createTime;


    @ApiProperty(name="updateTime",desc="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.sql.Timestamp updateTime;

    /*------------其他字段----------*/

    @ApiProperty(name="libraryNum",desc="制品数量")
    private Integer  libraryNum;


    @ApiProperty(name="prefixPath",desc="前缀地址")
    private java.lang.String prefixPath;


    @ApiProperty(name="versionType",desc="版本控制")
    private String  versionType;


    @ApiProperty(name="proxyDataList",desc="代理数据")
    private List<Object> proxyDataList;

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
    public java.lang.String getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(java.lang.String repositoryType) {
        this.repositoryType = repositoryType;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    public java.lang.String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(java.lang.String createUser) {
        this.createUser = createUser;
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


    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLibraryNum() {
        return libraryNum;
    }

    public void setLibraryNum(Integer libraryNum) {
        this.libraryNum = libraryNum;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getPrefixPath() {
        return prefixPath;
    }

    public void setPrefixPath(String prefixPath) {
        this.prefixPath = prefixPath;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public List<Object> getProxyDataList() {
        return proxyDataList;
    }

    public void setProxyDataList(List<Object> proxyDataList) {
        this.proxyDataList = proxyDataList;
    }
}
