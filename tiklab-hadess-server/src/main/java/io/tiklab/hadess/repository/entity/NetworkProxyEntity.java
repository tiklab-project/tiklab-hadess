package io.tiklab.hadess.repository.entity;

import io.tiklab.core.BaseModel;
import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * RemoteProxyEntity-网络代理地址
 */
@Entity
@Table(name="pack_repository_network_proxy")
public class NetworkProxyEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //地址
    @Column(name = "address",length = 128,notNull = true)
    private String address;

    //端口
    @Column(name = "port",notNull = true)
    private Integer port;

    //描述
    @Column(name = "description",length = 128)
    private String description;


    //启用
    @Column(name = "enable")
    private Integer enable;

    //创建时间
    @Column(name = "create_time")
    private Timestamp createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
