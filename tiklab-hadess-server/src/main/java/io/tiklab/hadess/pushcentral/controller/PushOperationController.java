package io.tiklab.hadess.pushcentral.controller;

import io.tiklab.core.Result;
import io.tiklab.hadess.pushcentral.model.PushOperation;
import io.tiklab.hadess.pushcentral.model.PushOperationQuery;
import io.tiklab.hadess.pushcentral.service.PushOperationService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pushOperation")
//@Api(name = "PushGroupController",desc = "推送组")
public class PushOperationController {

    @Autowired
    PushOperationService pushOperationService;

    @RequestMapping(path = "/executePushGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findPushLibraryPage",desc = "推送制品组")
    @ApiParam(name = "pushOperationQuery",desc = "pushOperationQuery",required = true)
    public Result<String> pushGroup(@RequestBody @Valid @NotNull PushOperationQuery pushOperationQuery){
        String state = pushOperationService.pushGroup(pushOperationQuery);

        return Result.ok(state);
    }

    @RequestMapping(path = "/executePushLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findPushLibraryPage",desc = "推送制品")
    @ApiParam(name = "pushOperationQuery",desc = "pushOperationQuery",required = true)
    public Result<String> pushLibrary(@RequestBody @Valid @NotNull PushOperationQuery pushOperationQuery){
        String state = pushOperationService.pushLibrary(pushOperationQuery);

        return Result.ok(state);
    }

    @RequestMapping(path = "/getPushResult",method = RequestMethod.POST)
    @ApiMethod(name = "findPushLibraryPage",desc = "推送制品组")
    @ApiParam(name = "key",desc = "获取推送结果的key",required = true)
    public Result<PushOperation> getPushResult(@NotNull String key){
        PushOperation pushOperation = pushOperationService.getPushResult(key);

        return Result.ok(pushOperation);
    }

}
