package io.thoughtware.hadess.scan.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import io.thoughtware.hadess.scan.model.ScanResult;
import io.thoughtware.hadess.scan.model.ScanResultQuery;
import io.thoughtware.hadess.scan.service.ScanResultService;
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
 * ScanResultController
 */
@RestController
@RequestMapping("/scanResult")
@Api(name = "ScanResultController",desc = "扫描结果")
public class ScanResultController {

    private static Logger logger = LoggerFactory.getLogger(ScanResultController.class);

    @Autowired
    private ScanResultService scanResultService;

    @RequestMapping(path="/deleteScanResult",method = RequestMethod.POST)
    @ApiMethod(name = "deleteScanResult",desc = "删除扫描结果")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteScanResult(@NotNull String id){
        scanResultService.deleteScanResult(id);

        return Result.ok();
    }

    @RequestMapping(path="/findScanResult",method = RequestMethod.POST)
    @ApiMethod(name = "findScanResult",desc = "通过id查询扫描结果")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ScanResult> findScanResult(@NotNull String id){
        ScanResult scanResult = scanResultService.findScanResult(id);

        return Result.ok(scanResult);
    }

    @RequestMapping(path="/findAllScanResult",method = RequestMethod.POST)
    @ApiMethod(name = "findAllScanResult",desc = "查询所有扫描结果")
    public Result<List<ScanResult>> findAllScanResult(){
        List<ScanResult> scanResultList = scanResultService.findAllScanResult();

        return Result.ok(scanResultList);
    }

    @RequestMapping(path = "/findScanResultList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanResultList",desc = "条件查询扫描结果")
    @ApiParam(name = "scanResultQuery",desc = "scanResultQuery",required = true)
    public Result<List<ScanResult>> findScanResultList(@RequestBody @Valid @NotNull ScanResultQuery scanResultQuery){
        List<ScanResult> scanResultList = scanResultService.findScanResultList(scanResultQuery);

        return Result.ok(scanResultList);
    }

    @RequestMapping(path = "/findScanResultPage",method = RequestMethod.POST)
    @ApiMethod(name = "findScanResultPage",desc = "条件分页查询扫描结果")
    @ApiParam(name = "scanResultQuery",desc = "scanResultQuery",required = true)
    public Result<Pagination<ScanResult>> findScanResultPage(@RequestBody @Valid @NotNull ScanResultQuery scanResultQuery){
        Pagination<ScanResult> pagination = scanResultService.findScanResultPage(scanResultQuery);

        return Result.ok(pagination);
    }

}
