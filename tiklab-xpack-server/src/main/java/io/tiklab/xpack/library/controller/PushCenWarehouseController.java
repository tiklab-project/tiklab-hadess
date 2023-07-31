package io.tiklab.xpack.library.controller;

import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.library.service.PushCenWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/pushCenWarehouse")
@Api(name = "PushCenWarehouseController",desc = "推送")
public class PushCenWarehouseController {

    @Autowired
    private PushCenWarehouse pushCenWarehouse;

    @RequestMapping(path="/pushCentralWare",method = RequestMethod.POST)
    @ApiMethod(name = "pushCentralWare",desc = "推送中央仓库")
    @ApiParam(name = "libraryId",desc = "制品id",required = true)
    public Result<String> pushCentralWare(@NotNull String libraryId,@NotNull String type){
         pushCenWarehouse.pushCentralWare(libraryId,type);

        return Result.ok();
    }

    @RequestMapping(path="/pushResult",method = RequestMethod.POST)
    @ApiMethod(name = "pushResult",desc = "推送结果")
    @ApiParam(name = "versionId",desc = "制品id ",required = true)
    public Result<String> pushResult(@NotNull String libraryId){
       String result= pushCenWarehouse.pushResult(libraryId);

        return Result.ok(result);
    }

}
