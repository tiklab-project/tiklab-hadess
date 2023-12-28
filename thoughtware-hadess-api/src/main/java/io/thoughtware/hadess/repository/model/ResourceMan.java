package io.thoughtware.hadess.repository.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.core.BaseModel;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

@ApiModel
public class ResourceMan extends BaseModel {

    @ApiProperty(name="version",desc=" 1 : 社区版 2： 企业版 3： sass版")
    private java.lang.Integer version;


    @ApiProperty(name="usedSpace",desc="使用空间")
    private java.lang.String usedSpace;

    @ApiProperty(name="allSpace",desc="总空间")
    private java.lang.String allSpace;


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(String usedSpace) {
        this.usedSpace = usedSpace;
    }

    public String getAllSpace() {
        return allSpace;
    }

    public void setAllSpace(String allSpace) {
        this.allSpace = allSpace;
    }
}
