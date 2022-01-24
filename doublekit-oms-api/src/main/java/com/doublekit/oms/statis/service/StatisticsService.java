package com.doublekit.oms.statis.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticsService {
    /**
     *统计增长数
     * @param month 传入的月
     * @param type 类型
     * @return
     */
    Map<String, List> statistics(Date month,String type);
}
