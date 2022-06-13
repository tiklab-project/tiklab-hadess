package com.doublekit.oms.activity.controller;

import com.doublekit.activity.model.ActivityType;
import com.doublekit.activity.model.ActivityTypeQuery;
import com.doublekit.activity.service.ActivityTypeService;
import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ActivityTypeController
 */
@RestController
@RequestMapping("/activityType")
@Api(name = "ActivityTypeController",desc = "活动类型管理")
public class ActivityTypeController {

    private static Logger logger = LoggerFactory.getLogger(ActivityTypeController.class);

    @Autowired
    private ActivityTypeService activityTypeService;

    @RequestMapping(path="/createActivityType",method = RequestMethod.POST)
    @ApiMethod(name = "createActivityType",desc = "createActivityType")
    @ApiParam(name = "activityType",desc = "activityType",required = true)
    public Result<String> createActivityType(@RequestBody @NotNull @Valid ActivityType activityType){
        String id = activityTypeService.createActivityType(activityType);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateActivityType",method = RequestMethod.POST)
    @ApiMethod(name = "updateActivityType",desc = "updateActivityType")
    @ApiParam(name = "activityType",desc = "activityType",required = true)
    public Result<Void> updateActivityType(@RequestBody @NotNull @Valid ActivityType activityType){
        activityTypeService.updateActivityType(activityType);

        return Result.ok();
    }

    @RequestMapping(path="/deleteActivityType",method = RequestMethod.POST)
    @ApiMethod(name = "deleteActivityType",desc = "deleteActivityType")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteActivityType(@NotNull String id){
        activityTypeService.deleteActivityType(id);

        return Result.ok();
    }

    @RequestMapping(path="/findActivityType",method = RequestMethod.POST)
    @ApiMethod(name = "findActivityType",desc = "findActivityType")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ActivityType> findActivityType(@NotNull String id){
        ActivityType activityType = activityTypeService.findActivityType(id);

        return Result.ok(activityType);
    }

    @RequestMapping(path="/findAllActivityType",method = RequestMethod.POST)
    @ApiMethod(name = "findAllActivityType",desc = "findAllActivityType")
    public Result<List<ActivityType>> findAllActivityType(){
        List<ActivityType> activityTypeList = activityTypeService.findAllActivityType();

        return Result.ok(activityTypeList);
    }

    @RequestMapping(path = "/findActivityTypeList",method = RequestMethod.POST)
    @ApiMethod(name = "findActivityTypeList",desc = "findActivityTypeList")
    @ApiParam(name = "activityTypeQuery",desc = "activityTypeQuery",required = true)
    public Result<List<ActivityType>> findActivityTypeList(@RequestBody @Valid @NotNull ActivityTypeQuery activityTypeQuery){
        List<ActivityType> activityTypeList = activityTypeService.findActivityTypeList(activityTypeQuery);

        return Result.ok(activityTypeList);
    }

    @RequestMapping(path = "/findActivityTypePage",method = RequestMethod.POST)
    @ApiMethod(name = "findActivityTypePage",desc = "findActivityTypePage")
    @ApiParam(name = "activityTypeQuery",desc = "activityTypeQuery",required = true)
    public Result<Pagination<ActivityType>> findActivityTypePage(@RequestBody @Valid @NotNull ActivityTypeQuery activityTypeQuery){
        Pagination<ActivityType> pagination = activityTypeService.findActivityTypePage(activityTypeQuery);

        return Result.ok(pagination);
    }

}
