package io.tiklab.hadess.setting.service;


import io.tiklab.hadess.setting.model.SystemCount;

public interface SystemCountService {

    /**
     * 系统设置查询汇总数据
     */
    SystemCount collectCount();
}
