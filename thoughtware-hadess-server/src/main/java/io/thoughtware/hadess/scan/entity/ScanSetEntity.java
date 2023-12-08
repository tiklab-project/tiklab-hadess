package io.thoughtware.hadess.scan.entity;

import io.thoughtware.core.BaseModel;
import io.thoughtware.dal.jpa.annotation.*;

/**
 * ScanSet-扫描设置
 */
@Entity
@Table(name="pack_scan_set")
public class ScanSetEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;

    //地址
    @Column(name = "address",length = 128)
    private String address;

    //token
    @Column(name = "token",length = 128)
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
