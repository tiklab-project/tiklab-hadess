package com.doublekit.product.statistics.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.product.statis.service.StatisticsService;
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
public class  StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(path = "/statistics",method = RequestMethod.POST)
    @ApiMethod(name = "statistics",desc = "增长统计")
    @ApiParam(name = "month",desc = "月份数",required = true)
    public Result<Map> statistics(@NotNull Date month,String type){
        Map<String, List> statistics =statisticsService .statistics(month,type);
        return Result.ok(statistics);
    }

}
