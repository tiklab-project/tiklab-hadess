package io.tiklab.xpack.scan.controller;

import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.scan.model.ScanQueue;
import io.tiklab.xpack.scan.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scan")
@Api(name = "Controller",desc = "扫描管理")
public class ScanController {

    @Autowired
    ScanService scanService;


    @RequestMapping(path="/execScan",method = RequestMethod.POST)
    @ApiMethod(name = "execScan",desc = "执行扫描")
    @ApiParam(name = "scanPlayId",desc = "扫描计划的id",required = true)
    public Result<String> execScan(@NotNull String scanPlayId){

        String result = scanService.execScan(scanPlayId);
        return Result.ok(result);
    }

    @RequestMapping(path="/findExecResult",method = RequestMethod.POST)
    @ApiMethod(name = "findExecResult",desc = "查询扫描结果")
    @ApiParam(name = "scanPlayId",desc = "扫描计划的id",required = true)
    public Result<ScanQueue> findExecResult(@NotNull String scanPlayId){

        ScanQueue execResult = scanService.findExecResult(scanPlayId);
        return Result.ok(execResult);
    }

}
