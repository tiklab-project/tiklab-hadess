package io.tiklab.xpack.scan.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.scan.model.ScanSet;
import io.tiklab.xpack.scan.model.ScanSetQuery;
import io.tiklab.xpack.scan.service.ScanSetService;
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
 * ScanSetController
 */
@RestController
@RequestMapping("/scanSet")
@Api(name = "ScanSetController",desc = "存储库管理")
public class ScanSetController {

    private static Logger logger = LoggerFactory.getLogger(ScanSetController.class);

    @Autowired
    private ScanSetService scanSetService;

    @RequestMapping(path="/createScanSet",method = RequestMethod.POST)
    @ApiMethod(name = "createScanSet",desc = "创建存储库")
    @ApiParam(name = "scanSet",desc = "scanSet",required = true)
    public Result<String> createScanSet(@RequestBody @NotNull @Valid ScanSet scanSet){
        String id = scanSetService.createScanSet(scanSet);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateScanSet",method = RequestMethod.POST)
    @ApiMethod(name = "updateScanSet",desc = "更新存储库")
    @ApiParam(name = "scanSet",desc = "scanSet",required = true)
    public Result<Void> updateScanSet(@RequestBody @NotNull @Valid ScanSet scanSet){
        scanSetService.updateScanSet(scanSet);

        return Result.ok();
    }

    @RequestMapping(path="/deleteScanSet",method = RequestMethod.POST)
    @ApiMethod(name = "deleteScanSet",desc = "删除存储库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteScanSet(@NotNull String id){
        scanSetService.deleteScanSet(id);

        return Result.ok();
    }

    @RequestMapping(path="/findScanSet",method = RequestMethod.POST)
    @ApiMethod(name = "findScanSet",desc = "通过id查询存储库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ScanSet> findScanSet(@NotNull String id){
        ScanSet scanSet = scanSetService.findScanSet(id);

        return Result.ok(scanSet);
    }

    @RequestMapping(path="/findAllScanSet",method = RequestMethod.POST)
    @ApiMethod(name = "findAllScanSet",desc = "查询所有存储库")
    public Result<List<ScanSet>> findAllScanSet(){
        List<ScanSet> scanSetList = scanSetService.findAllScanSet();

        return Result.ok(scanSetList);
    }

    @RequestMapping(path = "/findScanSetList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanSetList",desc = "条件查询存储库")
    @ApiParam(name = "scanSetQuery",desc = "scanSetQuery",required = true)
    public Result<List<ScanSet>> findScanSetList(@RequestBody @Valid @NotNull ScanSetQuery scanSetQuery){
        List<ScanSet> scanSetList = scanSetService.findScanSetList(scanSetQuery);

        return Result.ok(scanSetList);
    }

    @RequestMapping(path = "/findScanSetPage",method = RequestMethod.POST)
    @ApiMethod(name = "findScanSetPage",desc = "条件分页查询存储库")
    @ApiParam(name = "scanSetQuery",desc = "scanSetQuery",required = true)
    public Result<Pagination<ScanSet>> findScanSetPage(@RequestBody @Valid @NotNull ScanSetQuery scanSetQuery){
        Pagination<ScanSet> pagination = scanSetService.findScanSetPage(scanSetQuery);

        return Result.ok(pagination);
    }

}
