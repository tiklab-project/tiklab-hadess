package io.thoughtware.hadess.pushcentral.model;

import io.thoughtware.postin.annotation.ApiProperty;

import java.lang.reflect.Type;

public class PushOperationQuery {

    @ApiProperty(name ="pushGroupIds",desc = "推送组ids")
    private Object pushGroupIds;

    @ApiProperty(name ="pushGroupIds",desc = "推送组ids")
//    private Type pushGroupIds;

    public Object getPushGroupIds() {
        return pushGroupIds;
    }

    public void setPushGroupIds(Object pushGroupIds) {
        this.pushGroupIds = pushGroupIds;
    }
}
