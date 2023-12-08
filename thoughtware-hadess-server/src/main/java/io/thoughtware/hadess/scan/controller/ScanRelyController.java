package io.thoughtware.hadess.scan.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import io.thoughtware.hadess.scan.model.ScanRely;
import io.thoughtware.hadess.scan.model.ScanRelyQuery;
import io.thoughtware.hadess.scan.service.ScanRelyService;
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
 * ScanRelyController
 */
@RestController
@RequestMapping("/scanRely")
@Api(name = "ScanRelyController",desc = "扫描依赖")
public class ScanRelyController {

    private static Logger logger = LoggerFactory.getLogger(ScanRelyController.class);

    @Autowired
    private ScanRelyService scanRelyService;

    @RequestMapping(path="/deleteScanRely",method = RequestMethod.POST)
    @ApiMethod(name = "deleteScanRely",desc = "删除扫描依赖")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteScanRely(@NotNull String id){
        scanRelyService.deleteScanRely(id);

        return Result.ok();
    }

    @RequestMapping(path="/findScanRely",method = RequestMethod.POST)
    @ApiMethod(name = "findScanRely",desc = "通过id查询扫描依赖")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ScanRely> findScanRely(@NotNull String id){
        ScanRely scanRely = scanRelyService.findScanRely(id);

        return Result.ok(scanRely);
    }

    @RequestMapping(path="/findAllScanRely",method = RequestMethod.POST)
    @ApiMethod(name = "findAllScanRely",desc = "查询所有扫描依赖")
    public Result<List<ScanRely>> findAllScanRely(){
        List<ScanRely> scanRelyList = scanRelyService.findAllScanRely();

        return Result.ok(scanRelyList);
    }

    @RequestMapping(path = "/findScanRelyList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanRelyList",desc = "条件查询扫描依赖")
    @ApiParam(name = "scanRelyQuery",desc = "scanRelyQuery",required = true)
    public Result<List<ScanRely>> findScanRelyList(@RequestBody @Valid @NotNull ScanRelyQuery scanRelyQuery){
        List<ScanRely> scanRelyList = scanRelyService.findScanRelyList(scanRelyQuery);

        return Result.ok(scanRelyList);
    }

    @RequestMapping(path = "/findScanRelyPage",method = RequestMethod.POST)
    @ApiMethod(name = "findScanRelyPage",desc = "条件分页查询扫描依赖")
    @ApiParam(name = "scanRelyQuery",desc = "scanRelyQuery",required = true)
    public Result<Pagination<ScanRely>> findScanRelyPage(@RequestBody @Valid @NotNull ScanRelyQuery scanRelyQuery){
        Pagination<ScanRely> pagination = scanRelyService.findScanRelyPage(scanRelyQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findScanRelyTreeList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanRelyList",desc = "条件查询扫描依赖树")
    @ApiParam(name = "scanRelyQuery",desc = "scanRelyQuery",required = true)
    public Result<List<ScanRely>> findScanRelyTreeList(@RequestBody @Valid @NotNull ScanRelyQuery scanRelyQuery){
        List<ScanRely> scanRelyList = scanRelyService.findScanRelyTreeList(scanRelyQuery);

        return Result.ok(scanRelyList);
    }

    @RequestMapping(path = "/findHaveHoleRelyTreeList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanRelyList",desc = "条件查询有漏洞的扫描依赖树")
    @ApiParam(name = "scanRelyQuery",desc = "scanRelyQuery",required = true)
    public Result<List<ScanRely>> findHaveHoleRelyTreeList(@NotNull String scanGroup){
        List<ScanRely> scanRelyList = scanRelyService.findHaveHoleRelyTreeList(scanGroup);

        return Result.ok(scanRelyList);
    }

}
