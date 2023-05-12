package io.tiklab.xpack.alter.controller;

import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.xpack.alter.service.AlterSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/alterSql")
@Api(name = "AlterSqlController",desc = "修改sql")
public class AlterSqlController {

    @Autowired
    AlterSqlService alterSqlService;

    @RequestMapping(path="/updateId",method = RequestMethod.POST)
    @ApiMethod(name = "updateId",desc = "更新首页-优势")
    public Result<Void> updateId(){
        alterSqlService.updateId();

        return Result.ok();
    }

}
