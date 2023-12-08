package io.thoughtware.hadess.scan.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.join.annotation.Join;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

@ApiModel
@Mapper
public class ScanSet {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="address",desc="地址")
    private String address;

    @ApiProperty(name="token",desc="token")
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
