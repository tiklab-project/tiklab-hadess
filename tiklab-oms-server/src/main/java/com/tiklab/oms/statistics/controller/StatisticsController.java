package com.tiklab.oms.statistics.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.rpc.annotation.Reference;
import com.tiklab.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 统计
 * Created by limingliang on 2017/9/25.
 */
@RestController
@RequestMapping("/statistics")
@Api(name = "MemberController",desc = "统计")
public class StatisticsController {
    @Autowired
    @Reference(address = "${homes.address}")
    StatisticsService statisticsService;

    @RequestMapping(path = "/statisticsByDate",method = RequestMethod.POST)
    @ApiMethod(name = "statisticsByDate",desc = "增长统计 日统计")
    @ApiParam(name = "time",desc = "月份数",required = true)
    public Result<Map> statisticsByDate(@NotNull Date time,String type){
        Map<String, List> statistics =statisticsService .statistics(time,type);
        return Result.ok(statistics);
    }

    @RequestMapping(path = "/statisticsByWeek",method = RequestMethod.POST)
    @ApiMethod(name = "statisticsByWeek",desc = "增长统计 周统计")
    @ApiParam(name = "time",desc = "月份数",required = true)
    public Result<Map> statisticsByWeek(@NotNull Date time,String type){
        Map<String, List> statistics =statisticsService .statisticsByWeek(time,type);
        return Result.ok(statistics);
    }

    @RequestMapping(path = "/statisticsByMonth",method = RequestMethod.POST)
    @ApiMethod(name = "statisticsByMonth",desc = "增长统计 月统计")
    @ApiParam(name = "time",desc = "年",required = true)
    public Result<Map> statisticsByMonth(@NotNull String time,String type){
        Map<String, List> statistics =statisticsService .statisticsByMonth(time,type);
        return Result.ok(statistics);
    }


}
