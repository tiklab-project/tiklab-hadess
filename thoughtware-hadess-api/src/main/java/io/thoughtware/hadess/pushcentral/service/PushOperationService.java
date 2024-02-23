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
     * 推送制品
     * @param pushOperationQuery
     */
    String pushLibrary(PushOperationQuery pushOperationQuery);

    /**
     * 获取推送结果
     * @param key 获取结果的key
     */
    PushOperation getPushResult(String key);


}
