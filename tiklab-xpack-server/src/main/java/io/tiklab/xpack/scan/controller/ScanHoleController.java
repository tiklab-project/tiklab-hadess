package io.tiklab.xpack.scan.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.scan.model.ScanHole;
import io.tiklab.xpack.scan.model.ScanHoleQuery;
import io.tiklab.xpack.scan.service.ScanHoleService;
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
 * ScanHoleController
 */
@RestController
@RequestMapping("/scanHole")
@Api(name = "ScanHoleController",desc = "扫描结果")
public class ScanHoleController {

    private static Logger logger = LoggerFactory.getLogger(ScanHoleController.class);

    @Autowired
    private ScanHoleService scanHoleService;

    @RequestMapping(path="/deleteScanHole",method = RequestMethod.POST)
    @ApiMethod(name = "deleteScanHole",desc = "删除扫描结果")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteScanHole(@NotNull String id){
        scanHoleService.deleteScanHole(id);

        return Result.ok();
    }

    @RequestMapping(path="/findScanHole",method = RequestMethod.POST)
    @ApiMethod(name = "findScanHole",desc = "通过id查询扫描结果")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ScanHole> findScanHole(@NotNull String id){
        ScanHole scanHole = scanHoleService.findScanHole(id);

        return Result.ok(scanHole);
    }

    @RequestMapping(path="/findAllScanHole",method = RequestMethod.POST)
    @ApiMethod(name = "findAllScanHole",desc = "查询所有扫描结果")
    public Result<List<ScanHole>> findAllScanHole(){
        List<ScanHole> scanHoleList = scanHoleService.findAllScanHole();

        return Result.ok(scanHoleList);
    }

    @RequestMapping(path = "/findScanHoleList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanHoleList",desc = "条件查询扫描结果")
    @ApiParam(name = "scanHoleQuery",desc = "scanHoleQuery",required = true)
    public Result<List<ScanHole>> findScanHoleList(@RequestBody @Valid @NotNull ScanHoleQuery scanHoleQuery){
        List<ScanHole> scanHoleList = scanHoleService.findScanHoleList(scanHoleQuery);

        return Result.ok(scanHoleList);
    }

    @RequestMapping(path = "/findScanHolePage",method = RequestMethod.POST)
    @ApiMethod(name = "findScanHolePage",desc = "条件分页查询扫描结果")
    @ApiParam(name = "scanHoleQuery",desc = "scanHoleQuery",required = true)
    public Result<Pagination<ScanHole>> findScanHolePage(@RequestBody @Valid @NotNull ScanHoleQuery scanHoleQuery){
        Pagination<ScanHole> pagination = scanHoleService.findScanHolePage(scanHoleQuery);

        return Result.ok(pagination);
    }

}
