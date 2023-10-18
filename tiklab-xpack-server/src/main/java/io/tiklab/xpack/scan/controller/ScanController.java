package io.tiklab.xpack.scan.controller;

import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
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


    @RequestMapping(path="/excOneScanLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "excOneScanLibrary",desc = "执行单个制品扫描")
    public Result<String> excOneScanLibrary(@RequestBody ScanQueue scanQueue){

        String result = scanService.excOneScanLibrary(scanQueue);

        return Result.ok(result);
    }

    @RequestMapping(path="/excMultiScanLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "excMultiScanLibrary",desc = "扫描多个制品")
    public Result<String> excMultiScanLibrary(@RequestBody ScanQueue scanQueue){

        String result = scanService.excMultiScanLibrary(scanQueue);
        return Result.ok(result);
    }

    @RequestMapping(path="/findScanQueue",method = RequestMethod.POST)
    @ApiMethod(name = "findScanQueue",desc = "查询扫描队列")
    public Result<List> findScanQueue(@NotNull String repositoryId){

        List result= scanService.findScanQueue(repositoryId);

        return Result.ok(result);
    }

    @RequestMapping(path="/findOneScanResult",method = RequestMethod.POST)
    @ApiMethod(name = "findOneScanResult",desc = "查询单个扫描结果")
    public Result<Map> findOneScanResult(@NotNull  String scanLibraryId){

        Map result= scanService.findOneScanResult(scanLibraryId);

        return Result.ok(result);
    }



}
