package io.thoughtware.hadess.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.beans.annotation.Mapping;
import io.thoughtware.beans.annotation.Mappings;
import io.thoughtware.core.BaseModel;
import io.thoughtware.join.annotation.JoinQuery;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;

/**
 *  @pi.model: io.thoughtware.xpack.repository.model.Repository
 */
@ApiModel
@Mapper
public class Repository extends BaseModel {

    /**
     * @pi.name: id
     * @pi.dataType:string
     * @pi.desc: id
     * @pi.value: id
     */
    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    /**
     * @pi.name: name
     * @pi.dataType:string
     * @pi.desc: 制品库名字
     * @pi.value: name
     */
    @NotNull
    @ApiProperty(name="name",desc="制品库名字",required = true)
    private java.lang.String name;

    /**
     * @pi.name: repositoryUrl
     * @pi.dataType:string
     * @pi.desc: 制品库路径
     * @pi.value: repositoryUrl
     */
    @ApiProperty(name="repositoryUrl",desc="制品库路径")
    private java.lang.String repositoryUrl;

    /**
     * @pi.name: type
     * @pi.dataType:string
     * @pi.desc: 创建类型 maven、npm、generic、docker等
     * @pi.value: type
     */
    @NotNull
    @ApiProperty(name="type",desc="创建类型 maven、npm、generic、docker等 ",required = true)
    private java.lang.String type;

    /**
     * @pi.name: repositoryType
     * @pi.dataType:string
     * @pi.desc: 类型  本地库：local、远程库：remote、组合库：group
     * @pi.value: repositoryType
     */
    @NotNull
    @ApiProperty(name="repositoryType",desc="类型  本地库：local、远程库：remote、组合库：group",required = true)
    private java.lang.String repositoryType;

    /**
     * @pi.model: Storage
     * @pi.desc: 存储库
     */
    @ApiProperty(name="storage",desc="存储库id")
    @Mappings({
            @Mapping(source = "storage.id",target = "storageId")
    })
    @JoinQuery(key = "id")
    private Storage storage;


    /**
     * @pi.name: category
     * @pi.dataType:Integer
     * @pi.desc: 1 、演示 ；2、正式仓库
     * @pi.value: category
     */
    @ApiProperty(name="category",desc="1 、演示 ；2、正式仓库")
    private Integer category;

    /**
     * @pi.name: color
     * @pi.dataType:Integer
     * @pi.desc: 制品库图标颜色0-4
     * @pi.value: category
     */
    @ApiProperty(name="color",desc="制品库图标颜色0-4")
    private Integer color;

    /**
     * @pi.name: rules
     * @pi.dataType:String
     * @pi.desc: 权限 public、private
     * @pi.value: rules
     */
    @ApiProperty(name="rules",desc="权限 public、private")
    private java.lang.String rules= "public";

    /**
     * @pi.name: description
     * @pi.dataType:String
     * @pi.desc: 描述
     * @pi.value: description
     */
    @ApiProperty(name="description",desc="描述")
    private java.lang.String description;

    /**
     * @pi.name: createUser
     * @pi.dataType:String
     * @pi.desc: 创建人
     * @pi.value: createUser
     */
    @ApiProperty(name="createUser",desc="创建人")
    private java.lang.String createUser;

    /**
     * @pi.name: createTime
     * @pi.dataType:Timestamp
     * @pi.desc: 创建时间
     * @pi.value: createTime
     */
    @ApiProperty(name="createTime",desc="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.sql.Timestamp createTime;

    /**
     * @pi.name: updateTime
     * @pi.dataType:Timestamp
     * @pi.desc: 更新时间
     * @pi.value: updateTime
     */
    @ApiProperty(name="updateTime",desc="更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.sql.Timestamp updateTime;

    /*------------其他字段----------*/
    /**
     * @pi.name: libraryNum
     * @pi.dataType:Integer
     * @pi.desc: 制品数量
     * @pi.value: libraryNum
     */
    @ApiProperty(name="libraryNum",desc="制品数量")
    private Integer  libraryNum;

    /**
     * @pi.name: prefixPath
     * @pi.dataType:String
     * @pi.desc: 前缀地址
     * @pi.value: prefixPath
     */
    @ApiProperty(name="prefixPath",desc="前缀地址")
    private java.lang.String prefixPath;

    /**
     * @pi.name: versionType
     * @pi.dataType:String
     * @pi.desc: 版本控制
     * @pi.value: versionType
     */
    @ApiProperty(name="versionType",desc="版本控制")
    private String  versionType;

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
}
