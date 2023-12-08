package io.thoughtware.hadess.scan.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import io.thoughtware.hadess.scan.model.ScanHole;
import io.thoughtware.hadess.scan.model.ScanHoleQuery;
import io.thoughtware.hadess.scan.service.ScanHoleService;
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
@Api(name = "ScanHoleController",desc = "扫描漏洞")
public class ScanHoleController {

    private static Logger logger = LoggerFactory.getLogger(ScanHoleController.class);

    @Autowired
    private ScanHoleService scanHoleService;

    @RequestMapping(path="/createScanHole",method = RequestMethod.POST)
    @ApiMethod(name = "createScanHole",desc = "添加扫描漏洞")
    @ApiParam(name = "scanHole",desc = "scanHole",required = true)
    public Result<String> createScanHole(@RequestBody @Valid @NotNull ScanHole scanHole){
        String scanHoleId = scanHoleService.createScanHole(scanHole);

        return Result.ok(scanHoleId);
    }

    @RequestMapping(path="/updateScanHole",method = RequestMethod.POST)
    @ApiMethod(name = "updateScanHole",desc = "更新扫描漏洞")
    @ApiParam(name = "scanHole",desc = "scanHole",required = true)
    public Result<String> updateScanHole(@RequestBody @Valid @NotNull ScanHole scanHole){
         scanHoleService.updateScanHole(scanHole);

        return Result.ok();
    }


    @RequestMapping(path="/deleteScanHole",method = RequestMethod.POST)
    @ApiMethod(name = "deleteScanHole",desc = "删除扫描漏洞")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteScanHole(@NotNull String id){
        scanHoleService.deleteScanHole(id);

        return Result.ok();
    }

    @RequestMapping(path="/findScanHole",method = RequestMethod.POST)
    @ApiMethod(name = "findScanHole",desc = "通过id查询扫描漏洞")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ScanHole> findScanHole(@NotNull String id){
        ScanHole scanHole = scanHoleService.findScanHole(id);

        return Result.ok(scanHole);
    }

    @RequestMapping(path="/findAllScanHole",method = RequestMethod.POST)
    @ApiMethod(name = "findAllScanHole",desc = "查询所有扫描漏洞")
    public Result<List<ScanHole>> findAllScanHole(){
        List<ScanHole> scanHoleList = scanHoleService.findAllScanHole();

        return Result.ok(scanHoleList);
    }

    @RequestMapping(path = "/findScanHoleList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanHoleList",desc = "条件查询扫描漏洞")
    @ApiParam(name = "scanHoleQuery",desc = "scanHoleQuery",required = true)
    public Result<List<ScanHole>> findScanHoleList(@RequestBody @Valid @NotNull ScanHoleQuery scanHoleQuery){
        List<ScanHole> scanHoleList = scanHoleService.findScanHoleList(scanHoleQuery);

        return Result.ok(scanHoleList);
    }

    @RequestMapping(path = "/findScanHolePage",method = RequestMethod.POST)
    @ApiMethod(name = "findScanHolePage",desc = "条件分页查询扫描漏洞")
    @ApiParam(name = "scanHoleQuery",desc = "scanHoleQuery",required = true)
    public Result<Pagination<ScanHole>> findScanHolePage(@RequestBody @Valid @NotNull ScanHoleQuery scanHoleQuery){
        Pagination<ScanHole> pagination = scanHoleService.findScanHolePage(scanHoleQuery);

        return Result.ok(pagination);
    }
    @RequestMapping(path = "/findSchemeHolePage",method = RequestMethod.POST)
    @ApiMethod(name = "findSchemeHolePage",desc = "条件分页方案下面的漏洞")
    @ApiParam(name = "scanHoleQuery",desc = "scanHoleQuery",required = true)
    public Result<Pagination<ScanHole>> findSchemeHolePage(@RequestBody @Valid @NotNull ScanHoleQuery scanHoleQuery){
        Pagination<ScanHole> pagination = scanHoleService.findSchemeHolePage(scanHoleQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findNotScanHolePage",method = RequestMethod.POST)
    @ApiMethod(name = "findNotScanHolePage",desc = "条件分页查询没有添加方案的扫描漏洞")
    @ApiParam(name = "scanHoleQuery",desc = "scanHoleQuery",required = true)
    public Result<Pagination<ScanHole>> findNotScanHolePage(@RequestBody @Valid @NotNull ScanHoleQuery scanHoleQuery){
        Pagination<ScanHole> pagination = scanHoleService.findNotScanHolePage(scanHoleQuery);

        return Result.ok(pagination);
    }
}
