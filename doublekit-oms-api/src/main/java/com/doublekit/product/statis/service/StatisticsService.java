package com.doublekit.product.statis.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticsService {
    /**
     *统计增长数  日统计
     * @param month 传入的月
     * @param type 类型
     * @return
     */
    Map<String, List> statistics(Date month,String type);

    /**
     *统计增长数  周统计
     * @param month 传入的月
     * @param type 类型
     * @return
     */
    Map<String, List> statisticsByWeek(Date month, String type);

    /**
     *统计增长数  月统计
     * @param year 传入年
     * @param type 类型
     * @return
     */
    Map<String, List> statisticsByMonth(String year,String type);
}
