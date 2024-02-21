package io.thoughtware.hadess.repository.model;

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
import java.util.ArrayList;
import java.util.List;

/**
 * RepositoryRemoteProxy-远程库代理信息模型
 */
@ApiModel
@Join
@Mapper
public class RepositoryRemoteProxy extends BaseModel {

    @ApiProperty(name="id",desc="id")
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="repository",desc="制品库",required = true)
    @Mappings({
            @Mapping(source = "repository.id",target = "repositoryId")
    })
    @JoinQuery(key = "id")
    private Repository repository;


    @ApiProperty(name="remoteProxy",desc="代理")
    @Mappings({
            @Mapping(source = "remoteProxy.id",target = "remoteProxyId")
    })
    @JoinQuery(key = "id")
    private RemoteProxy remoteProxy;


    @ApiProperty(name="createTime",desc="创建时间")
    private java.sql.Timestamp createTime;


    /*----其他字段------*/

    @ApiProperty(name="execType",desc="执行类型 add、update")
    private java.lang.String  execType;

    @ApiProperty(name="remoteProxyIds",desc="remoteProxyIds")
    private List<String>  remoteProxyIds;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }


    public RemoteProxy getRemoteProxy() {
        return remoteProxy;
    }

    public void setRemoteProxy(RemoteProxy remoteProxy) {
        this.remoteProxy = remoteProxy;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getExecType() {
        return execType;
    }

    public void setExecType(String execType) {
        this.execType = execType;
    }

    public List getRemoteProxyIds() {
        return remoteProxyIds;
    }

    public void setRemoteProxyIds(List remoteProxyIds) {
        this.remoteProxyIds = remoteProxyIds;
    }
}
