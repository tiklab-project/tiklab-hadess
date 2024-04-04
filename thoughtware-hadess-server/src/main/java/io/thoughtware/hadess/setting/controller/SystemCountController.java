package io.thoughtware.hadess.setting.controller;

import io.thoughtware.core.Result;
import io.thoughtware.hadess.setting.model.SystemCount;
import io.thoughtware.hadess.setting.service.SystemCountService;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/systemCount")
@Api(name = "AuthController",desc = "系统设置汇总")
public class SystemCountController {

    @Autowired
    private SystemCountService systemCountService;

    @RequestMapping(path="/collectCount",method = RequestMethod.POST)
    @ApiMethod(name = "createAuth",desc = "系统设置汇总")
    @ApiParam(name = "auth",desc = "auth",required = true)
    public Result<SystemCount> collectCount(){

        SystemCount systemCount = systemCountService.collectCount();

        return Result.ok(systemCount);
    }
}
