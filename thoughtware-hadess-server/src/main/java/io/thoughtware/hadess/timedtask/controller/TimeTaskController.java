package io.thoughtware.hadess.timedtask.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.hadess.timedtask.model.TimeTask;
import io.thoughtware.hadess.timedtask.model.TimeTaskQuery;
import io.thoughtware.hadess.timedtask.service.TimeTaskService;
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
 * TimeTaskController
 */
@RestController
@RequestMapping("/timeTask")
@Api(name = "TimeTaskController",desc = "定时任务")
public class TimeTaskController {

    private static Logger logger = LoggerFactory.getLogger(TimeTaskController.class);

    @Autowired
    private TimeTaskService timeTaskService;

    @RequestMapping(path="/createTimeTask",method = RequestMethod.POST)
    @ApiMethod(name = "createTimeTask",desc = "添加定时任务")
    @ApiParam(name = "timeTask",desc = "timeTask",required = true)
    public Result<String> createTimeTask(@RequestBody @Valid @NotNull TimeTask timeTask){
        String timeTaskId = timeTaskService.createTimeTask(timeTask);

        return Result.ok(timeTaskId);
    }

    @RequestMapping(path="/updateTimeTask",method = RequestMethod.POST)
    @ApiMethod(name = "updateTimeTask",desc = "更新定时任务")
    @ApiParam(name = "timeTask",desc = "timeTask",required = true)
    public Result<String> updateTimeTask(@RequestBody @Valid @NotNull TimeTask timeTask){
         timeTaskService.updateTimeTask(timeTask);

        return Result.ok();
    }


    @RequestMapping(path="/deleteTimeTask",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTimeTask",desc = "删除定时任务")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTimeTask(@NotNull String id){
        timeTaskService.deleteTimeTask(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTimeTask",method = RequestMethod.POST)
    @ApiMethod(name = "findTimeTask",desc = "通过id查询定时任务")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TimeTask> findTimeTask(@NotNull String id){
        TimeTask timeTask = timeTaskService.findTimeTask(id);

        return Result.ok(timeTask);
    }

    @RequestMapping(path="/findAllTimeTask",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTimeTask",desc = "查询所有定时任务")
    public Result<List<TimeTask>> findAllTimeTask(){
        List<TimeTask> timeTaskList = timeTaskService.findAllTimeTask();

        return Result.ok(timeTaskList);
    }

    @RequestMapping(path = "/findTimeTaskList",method = RequestMethod.POST)
    @ApiMethod(name = "findTimeTaskList",desc = "条件查询定时任务")
    @ApiParam(name = "timeTaskQuery",desc = "timeTaskQuery",required = true)
    public Result<List<TimeTask>> findTimeTaskList(@RequestBody @Valid @NotNull TimeTaskQuery timeTaskQuery){
        List<TimeTask> timeTaskList = timeTaskService.findTimeTaskList(timeTaskQuery);

        return Result.ok(timeTaskList);
    }

    @RequestMapping(path = "/findTimeTaskPage",method = RequestMethod.POST)
    @ApiMethod(name = "findTimeTaskPage",desc = "条件分页查询定时任务")
    @ApiParam(name = "timeTaskQuery",desc = "timeTaskQuery",required = true)
    public Result<Pagination<TimeTask>> findTimeTaskPage(@RequestBody @Valid @NotNull TimeTaskQuery timeTaskQuery){
        Pagination<TimeTask> pagination = timeTaskService.findTimeTaskPage(timeTaskQuery);

        return Result.ok(pagination);
    }

}
