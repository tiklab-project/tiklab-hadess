package io.thoughtware.hadess.scan.controller;

import io.thoughtware.core.Result;
import io.thoughtware.hadess.scan.model.ScanRecord;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import io.thoughtware.hadess.scan.model.ScanQueue;
import io.thoughtware.hadess.scan.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    public Result<ScanRecord> findExecResult(@NotNull String scanPlayId){

        //ScanQueue execResult = scanService.findExecResult(scanPlayId);

        ScanRecord execState = scanService.findExecState(scanPlayId);
        return Result.ok(execState);
    }

}
