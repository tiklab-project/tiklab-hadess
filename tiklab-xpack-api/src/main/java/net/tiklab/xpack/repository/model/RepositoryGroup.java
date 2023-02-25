package net.tiklab.xpack.repository.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.core.BaseModel;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@ApiModel
@Mapper(targetAlias = "RepositoryGroupEntity")
@Join
public class RepositoryGroup extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="repositoryGroup",desc="组合库id",required = true)
    @Mappings({
            @Mapping(source = "repositoryGroup.id",target = "repositoryGroupId")
    })
    @JoinQuery(key = "id")
    private Repository repositoryGroup;

    @NotNull
    @ApiProperty(name="repository",desc="关联的库id",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;

    @ApiProperty(name="createTime",desc="createTime")
    private java.sql.Timestamp createTime;

    @ApiProperty(name="updateTime",desc="updateTime")
    private java.sql.Timestamp updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Repository getRepositoryGroup() {
        return repositoryGroup;
    }

    public void setRepositoryGroup(Repository repositoryGroup) {
        this.repositoryGroup = repositoryGroup;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
