package com.tiklab.oms.activity.activity.controller;

import com.tiklab.promotion.activity.model.Activity;
import com.tiklab.promotion.activity.model.ActivityQuery;
import com.tiklab.promotion.activity.service.ActivityService;
import com.tiklab.postlink.annotation.Api;
import com.tiklab.postlink.annotation.ApiMethod;
import com.tiklab.postlink.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.rpc.annotation.Reference;
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
 * ActivityController
 */
@RestController
@RequestMapping("/activity")
@Api(name = "ActivityController",desc = "活动主表管理")
public class ActivityController {

    private static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private ActivityService activityService;

    @RequestMapping(path="/createActivity",method = RequestMethod.POST)
    @ApiMethod(name = "createActivity",desc = "createActivity")
    @ApiParam(name = "activity",desc = "activity",required = true)
    public Result<String> createActivity(@RequestBody @NotNull @Valid Activity activity){
        String id = activityService.createActivity(activity);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateActivity",method = RequestMethod.POST)
    @ApiMethod(name = "updateActivity",desc = "updateActivity")
    @ApiParam(name = "activity",desc = "activity",required = true)
    public Result<Void> updateActivity(@RequestBody @NotNull @Valid Activity activity){
        activityService.updateActivity(activity);

        return Result.ok();
    }

    @RequestMapping(path="/deleteActivity",method = RequestMethod.POST)
    @ApiMethod(name = "deleteActivity",desc = "deleteActivity")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteActivity(@NotNull String id){
        activityService.deleteActivity(id);

        return Result.ok();
    }

    @RequestMapping(path="/findActivity",method = RequestMethod.POST)
    @ApiMethod(name = "findActivity",desc = "findActivity")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Activity> findActivity(@NotNull String id){
        Activity activity = activityService.findActivity(id);

        return Result.ok(activity);
    }

    @RequestMapping(path="/findAllActivity",method = RequestMethod.POST)
    @ApiMethod(name = "findAllActivity",desc = "findAllActivity")
    public Result<List<Activity>> findAllActivity(){
        List<Activity> activityList = activityService.findAllActivity();

        return Result.ok(activityList);
    }

    @RequestMapping(path = "/findActivityList",method = RequestMethod.POST)
    @ApiMethod(name = "findActivityList",desc = "findActivityList")
    @ApiParam(name = "activityQuery",desc = "activityQuery",required = true)
    public Result<List<Activity>> findActivityList(@RequestBody @Valid @NotNull ActivityQuery activityQuery){
        List<Activity> activityList = activityService.findActivityList(activityQuery);

        return Result.ok(activityList);
    }

    @RequestMapping(path = "/findActivityPage",method = RequestMethod.POST)
    @ApiMethod(name = "findActivityPage",desc = "findActivityPage")
    @ApiParam(name = "activityQuery",desc = "activityQuery",required = true)
    public Result<Pagination<Activity>> findActivityPage(@RequestBody @Valid @NotNull ActivityQuery activityQuery){
        Pagination<Activity> pagination = activityService.findActivityPage(activityQuery);

        return Result.ok(pagination);
    }

}
