package io.thoughtware.hadess.pushcentral.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.hadess.pushcentral.model.PushGroup;
import io.thoughtware.hadess.pushcentral.model.PushGroupQuery;
import io.thoughtware.hadess.pushcentral.service.PushGroupService;
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
 * PushGroupController
 */
@RestController
@RequestMapping("/pushGroup")
@Api(name = "PushGroupController",desc = "推送组")
public class PushGroupController {

    private static Logger logger = LoggerFactory.getLogger(PushGroupController.class);

    @Autowired
    private PushGroupService pushGroupService;

    @RequestMapping(path="/createPushGroup",method = RequestMethod.POST)
    @ApiMethod(name = "createPushGroup",desc = "创建推送组")
    @ApiParam(name = "pushGroup",desc = "pushGroup",required = true)
    public Result<String> createPushGroup(@RequestBody @NotNull @Valid PushGroup pushGroup){
        String id = pushGroupService.createPushGroup(pushGroup);

        return Result.ok(id);
    }

    @RequestMapping(path="/updatePushGroup",method = RequestMethod.POST)
    @ApiMethod(name = "updatePushGroup",desc = "更新推送组")
    @ApiParam(name = "pushGroup",desc = "pushGroup",required = true)
    public Result<Void> updatePushGroup(@RequestBody @NotNull @Valid PushGroup pushGroup){
        pushGroupService.updatePushGroup(pushGroup);

        return Result.ok();
    }

    @RequestMapping(path="/deletePushGroup",method = RequestMethod.POST)
    @ApiMethod(name = "deletePushGroup",desc = "删除推送组")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deletePushGroup(@NotNull String id){
        pushGroupService.deletePushGroup(id);

        return Result.ok();
    }

    @RequestMapping(path="/findPushGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findPushGroup",desc = "通过id查询制品文件")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<PushGroup> findPushGroup(@NotNull String id){
        PushGroup pushGroup = pushGroupService.findPushGroup(id);

        return Result.ok(pushGroup);
    }

    @RequestMapping(path="/findAllPushGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPushGroup",desc = "查询所有推送组")
    public Result<List<PushGroup>> findAllPushGroup(){
        List<PushGroup> pushGroupList = pushGroupService.findAllPushGroup();

        return Result.ok(pushGroupList);
    }

    @RequestMapping(path = "/findPushGroupList",method = RequestMethod.POST)
    @ApiMethod(name = "findPushGroupList",desc = "条件查询推送组")
    @ApiParam(name = "pushGroupQuery",desc = "pushGroupQuery",required = true)
    public Result<List<PushGroup>> findPushGroupList(@RequestBody @Valid @NotNull PushGroupQuery pushGroupQuery){
        List<PushGroup> pushGroupList = pushGroupService.findPushGroupList(pushGroupQuery);

        return Result.ok(pushGroupList);
    }

    @RequestMapping(path = "/findPushGroupPage",method = RequestMethod.POST)
    @ApiMethod(name = "findPushGroupPage",desc = "条件分页查询推送组")
    @ApiParam(name = "pushGroupQuery",desc = "pushGroupQuery",required = true)
    public Result<Pagination<PushGroup>> findPushGroupPage(@RequestBody @Valid @NotNull PushGroupQuery pushGroupQuery){
        Pagination<PushGroup> pagination = pushGroupService.findPushGroupPage(pushGroupQuery);

        return Result.ok(pagination);
    }
}
