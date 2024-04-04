package io.thoughtware.hadess.setting.service;


import io.thoughtware.hadess.setting.model.SystemCount;

public interface SystemCountService {

    /**
     * 系统设置查询汇总数据
     */
    SystemCount collectCount();
}
