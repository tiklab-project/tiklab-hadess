package io.thoughtware.hadess.timedtask.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstance;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstanceQuery;
import io.thoughtware.hadess.timedtask.service.TimeTaskInstanceService;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
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
 * TimeTaskInstanceController
 */
@RestController
@RequestMapping("/timeTaskInstance")
@Api(name = "TimeTaskInstanceController",desc = "定时任务实例")
public class TimeTaskInstanceController {

    private static Logger logger = LoggerFactory.getLogger(TimeTaskInstanceController.class);

    @Autowired
    private TimeTaskInstanceService timeTaskInstanceService;

    @RequestMapping(path="/createTimeTaskInstance",method = RequestMethod.POST)
    @ApiMethod(name = "createTimeTaskInstance",desc = "添加定时任务实例")
    @ApiParam(name = "timeTaskInstance",desc = "timeTaskInstance",required = true)
    public Result<String> createTimeTaskInstance(@RequestBody @Valid @NotNull TimeTaskInstance timeTaskInstance){
        String timeTaskInstanceId = timeTaskInstanceService.createTimeTaskInstance(timeTaskInstance);

        return Result.ok(timeTaskInstanceId);
    }

    @RequestMapping(path="/updateTimeTaskInstance",method = RequestMethod.POST)
    @ApiMethod(name = "updateTimeTaskInstance",desc = "更新定时任务实例")
    @ApiParam(name = "timeTaskInstance",desc = "timeTaskInstance",required = true)
    public Result<String> updateTimeTaskInstance(@RequestBody @Valid @NotNull TimeTaskInstance timeTaskInstance){
         timeTaskInstanceService.updateTimeTaskInstance(timeTaskInstance);

        return Result.ok();
    }


    @RequestMapping(path="/deleteTimeTaskInstance",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTimeTaskInstance",desc = "删除定时任务实例")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTimeTaskInstance(@NotNull String id){
        timeTaskInstanceService.deleteTimeTaskInstance(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTimeTaskInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findTimeTaskInstance",desc = "通过id查询定时任务实例")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TimeTaskInstance> findTimeTaskInstance(@NotNull String id){
        TimeTaskInstance timeTaskInstance = timeTaskInstanceService.findTimeTaskInstance(id);

        return Result.ok(timeTaskInstance);
    }

    @RequestMapping(path="/findAllTimeTaskInstance",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTimeTaskInstance",desc = "查询所有定时任务实例")
    public Result<List<TimeTaskInstance>> findAllTimeTaskInstance(){
        List<TimeTaskInstance> timeTaskInstanceList = timeTaskInstanceService.findAllTimeTaskInstance();

        return Result.ok(timeTaskInstanceList);
    }

    @RequestMapping(path = "/findTimeTaskInstanceList",method = RequestMethod.POST)
    @ApiMethod(name = "findTimeTaskInstanceList",desc = "条件查询定时任务实例")
    @ApiParam(name = "timeTaskInstanceQuery",desc = "timeTaskInstanceQuery",required = true)
    public Result<List<TimeTaskInstance>> findTimeTaskInstanceList(@RequestBody @Valid @NotNull TimeTaskInstanceQuery timeTaskInstanceQuery){
        List<TimeTaskInstance> timeTaskInstanceList = timeTaskInstanceService.findTimeTaskInstanceList(timeTaskInstanceQuery);

        return Result.ok(timeTaskInstanceList);
    }

    @RequestMapping(path = "/findTimeTaskInstancePage",method = RequestMethod.POST)
    @ApiMethod(name = "findTimeTaskInstancePage",desc = "条件分页查询定时任务实例")
    @ApiParam(name = "timeTaskInstanceQuery",desc = "timeTaskInstanceQuery",required = true)
    public Result<Pagination<TimeTaskInstance>> findTimeTaskInstancePage(@RequestBody @Valid @NotNull TimeTaskInstanceQuery timeTaskInstanceQuery){
        Pagination<TimeTaskInstance> pagination = timeTaskInstanceService.findTimeTaskInstancePage(timeTaskInstanceQuery);

        return Result.ok(pagination);
    }

}
