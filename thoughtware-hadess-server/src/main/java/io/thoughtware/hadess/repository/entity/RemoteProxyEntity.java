package io.thoughtware.hadess.repository.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

/**
 * RemoteProxyEntity-代理地址
 */
@Entity
@Table(name="pack_remote_proxy")
public class RemoteProxyEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //代理地址
    @Column(name = "agency_url",length = 128,notNull = true)
    private String agencyUrl;

    //代理名字
    @Column(name = "agency_name",length = 32)
    private String agencyName;

    //代理类型
    @Column(name = "agency_type",length = 32)
    private String agencyType;


    //类型
    @Column(name = "type")
    private Integer type;

    //账号
    @Column(name = "account",length = 16)
    private String account;

    //密码
    @Column(name = "password",length = 16)
    private String password;

    //创建时间
    @Column(name = "create_time")
    private Timestamp createTime;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public void setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(String agencyType) {
        this.agencyType = agencyType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
