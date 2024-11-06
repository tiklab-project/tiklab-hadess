package io.tiklab.hadess.pushcentral.model;

import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

public class PushOperationQuery {

    @ApiProperty(name ="pushGroupIds",desc = "推送组ids")
    private List<String> pushGroupIds;

    @ApiProperty(name ="pushLibraryIds",desc = "推送制品ids")
    private Object pushLibraryIds;

    @ApiProperty(name ="pushType",desc = "推送类型 hadess、center")
    private String pushType;

    public List<String> getPushGroupIds() {
        return pushGroupIds;
    }

    public void setPushGroupIds(List<String> pushGroupIds) {
        this.pushGroupIds = pushGroupIds;
    }

    public Object getPushLibraryIds() {
        return pushLibraryIds;
    }

    public void setPushLibraryIds(Object pushLibraryIds) {
        this.pushLibraryIds = pushLibraryIds;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }
}
