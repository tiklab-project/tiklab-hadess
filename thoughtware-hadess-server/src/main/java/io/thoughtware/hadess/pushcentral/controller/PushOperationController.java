package io.thoughtware.hadess.pushcentral.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.hadess.pushcentral.model.PushLibrary;
import io.thoughtware.hadess.pushcentral.model.PushLibraryQuery;
import io.thoughtware.hadess.pushcentral.model.PushOperation;
import io.thoughtware.hadess.pushcentral.model.PushOperationQuery;
import io.thoughtware.hadess.pushcentral.service.PushOperationService;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pushOperation")
@Api(name = "PushGroupController",desc = "推送组")
public class PushOperationController {

    @Autowired
    PushOperationService pushOperationService;

    @RequestMapping(path = "/executePushGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findPushLibraryPage",desc = "推送制品组")
    @ApiParam(name = "pushLibraryQuery",desc = "pushLibraryQuery",required = true)
    public Result<String> pushGroup(@RequestBody @Valid @NotNull PushOperationQuery pushOperationQuery){
        String state = pushOperationService.pushGroup(pushOperationQuery);

        return Result.ok(state);
    }

    @RequestMapping(path = "/getPushResult",method = RequestMethod.POST)
    @ApiMethod(name = "findPushLibraryPage",desc = "推送制品组")
    @ApiParam(name = "pushLibraryQuery",desc = "pushLibraryQuery",required = true)
    public Result<PushOperation> getPushResult(@NotNull String pushGroupId){
        PushOperation pushOperation = pushOperationService.getPushResult(pushGroupId);

        return Result.ok(pushOperation);
    }

}
