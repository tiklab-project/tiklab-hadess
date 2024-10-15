package io.tiklab.hadess.pushcentral.service;

import io.tiklab.hadess.pushcentral.model.PushOperation;
import io.tiklab.hadess.pushcentral.model.PushOperationQuery;

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
