package io.thoughtware.hadess.scan.controller;

import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
import io.thoughtware.hadess.scan.model.ScanSchemeHole;
import io.thoughtware.hadess.scan.model.ScanSchemeHoleQuery;
import io.thoughtware.hadess.scan.service.ScanSchemeHoleService;
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
 * ScanSchemeHoleController
 */
@RestController
@RequestMapping("/scanSchemeHole")
@Api(name = "ScanSchemeHoleController",desc = "扫描方案漏洞关系")
public class ScanSchemeHoleController {

    private static Logger logger = LoggerFactory.getLogger(ScanSchemeHoleController.class);

    @Autowired
    private ScanSchemeHoleService scanSchemeHoleService;

    @RequestMapping(path="/createScanSchemeHole",method = RequestMethod.POST)
    @ApiMethod(name = "createScanSchemeHole",desc = "添加扫描扫描方案漏洞关系")
    @ApiParam(name = "scanSchemeHole",desc = "scanSchemeHole",required = true)
    public Result<String> createScanSchemeHole(@RequestBody @Valid @NotNull ScanSchemeHole scanSchemeHole){
        String scanSchemeHoleId = scanSchemeHoleService.createScanSchemeHole(scanSchemeHole);

        return Result.ok(scanSchemeHoleId);
    }

    @RequestMapping(path="/updateScanSchemeHole",method = RequestMethod.POST)
    @ApiMethod(name = "updateScanSchemeHole",desc = "更新扫描方案漏洞关系")
    @ApiParam(name = "scanSchemeHole",desc = "scanSchemeHole",required = true)
    public Result<String> updateScanSchemeHole(@RequestBody @Valid @NotNull ScanSchemeHole scanSchemeHole){
         scanSchemeHoleService.updateScanSchemeHole(scanSchemeHole);

        return Result.ok();
    }


    @RequestMapping(path="/deleteScanSchemeHole",method = RequestMethod.POST)
    @ApiMethod(name = "deleteScanSchemeHole",desc = "删除扫描方案漏洞关系")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteScanSchemeHole(@NotNull String id){
        scanSchemeHoleService.deleteScanSchemeHole(id);

        return Result.ok();
    }

    @RequestMapping(path="/findScanSchemeHole",method = RequestMethod.POST)
    @ApiMethod(name = "findScanSchemeHole",desc = "通过id查询扫描方案漏洞关系")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ScanSchemeHole> findScanSchemeHole(@NotNull String id){
        ScanSchemeHole scanSchemeHole = scanSchemeHoleService.findScanSchemeHole(id);

        return Result.ok(scanSchemeHole);
    }

    @RequestMapping(path="/findAllScanSchemeHole",method = RequestMethod.POST)
    @ApiMethod(name = "findAllScanSchemeHole",desc = "查询所有扫描方案漏洞关系")
    public Result<List<ScanSchemeHole>> findAllScanSchemeHole(){
        List<ScanSchemeHole> scanSchemeHoleList = scanSchemeHoleService.findAllScanSchemeHole();

        return Result.ok(scanSchemeHoleList);
    }

    @RequestMapping(path = "/findScanSchemeHoleList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanSchemeHoleList",desc = "条件查询扫描方案漏洞关系")
    @ApiParam(name = "scanSchemeHoleQuery",desc = "scanSchemeHoleQuery",required = true)
    public Result<List<ScanSchemeHole>> findScanSchemeHoleList(@RequestBody @Valid @NotNull ScanSchemeHoleQuery scanSchemeHoleQuery){
        List<ScanSchemeHole> scanSchemeHoleList = scanSchemeHoleService.findScanSchemeHoleList(scanSchemeHoleQuery);

        return Result.ok(scanSchemeHoleList);
    }

    @RequestMapping(path = "/findScanSchemeHolePage",method = RequestMethod.POST)
    @ApiMethod(name = "findScanSchemeHolePage",desc = "条件分页查询扫描方案漏洞关系")
    @ApiParam(name = "scanSchemeHoleQuery",desc = "scanSchemeHoleQuery",required = true)
    public Result<Pagination<ScanSchemeHole>> findScanSchemeHolePage(@RequestBody @Valid @NotNull ScanSchemeHoleQuery scanSchemeHoleQuery){
        Pagination<ScanSchemeHole> pagination = scanSchemeHoleService.findScanSchemeHolePage(scanSchemeHoleQuery);

        return Result.ok(pagination);
    }

}
