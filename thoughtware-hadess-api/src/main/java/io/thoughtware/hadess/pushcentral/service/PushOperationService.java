package io.thoughtware.hadess.pushcentral.service;

import io.thoughtware.hadess.pushcentral.model.PushOperation;
import io.thoughtware.hadess.pushcentral.model.PushOperationQuery;

public interface PushOperationService {


    /**
     * 推送组
     * @param pushOperationQuery
     */
    String pushGroup(PushOperationQuery pushOperationQuery);

    /**
     * 获取推送结果
     * @param pushGroupId
     */
    PushOperation getPushResult(String pushGroupId);
}
