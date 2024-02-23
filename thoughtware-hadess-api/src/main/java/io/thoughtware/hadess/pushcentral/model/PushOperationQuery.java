package io.thoughtware.hadess.pushcentral.model;

import io.thoughtware.postin.annotation.ApiProperty;

import java.lang.reflect.Type;

public class PushOperationQuery {

    @ApiProperty(name ="pushGroupIds",desc = "推送组ids")
    private Object pushGroupIds;

    @ApiProperty(name ="pushLibraryIds",desc = "推送制品ids")
    private Object pushLibraryIds;


    public Object getPushGroupIds() {
        return pushGroupIds;
    }

    public void setPushGroupIds(Object pushGroupIds) {
        this.pushGroupIds = pushGroupIds;
    }

    public Object getPushLibraryIds() {
        return pushLibraryIds;
    }

    public void setPushLibraryIds(Object pushLibraryIds) {
        this.pushLibraryIds = pushLibraryIds;
    }

}
