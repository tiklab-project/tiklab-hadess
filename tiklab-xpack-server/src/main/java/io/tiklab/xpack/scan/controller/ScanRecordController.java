package io.tiklab.xpack.scan.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.scan.model.ScanLibrary;
import io.tiklab.xpack.scan.model.ScanLibraryQuery;
import io.tiklab.xpack.scan.service.ScanLibraryService;
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
 * ScanLibraryController
 */
@RestController
@RequestMapping("/scanLibrary")
@Api(name = "ScanLibraryController",desc = "扫描制品")
public class ScanLibraryController {

    private static Logger logger = LoggerFactory.getLogger(ScanLibraryController.class);

    @Autowired
    private ScanLibraryService scanLibraryService;

    @RequestMapping(path="/createScanLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "createScanLibrary",desc = "添加扫描制品")
    @ApiParam(name = "scanLibrary",desc = "scanLibrary",required = true)
    public Result<String> createScanLibrary(@RequestBody @Valid @NotNull ScanLibrary scanLibrary){
        String libraryId = scanLibraryService.createScanLibrary(scanLibrary);

        return Result.ok(libraryId);
    }


    @RequestMapping(path="/deleteScanLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "deleteScanLibrary",desc = "删除扫描制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteScanLibrary(@NotNull String id){
        scanLibraryService.deleteScanLibrary(id);

        return Result.ok();
    }

    @RequestMapping(path="/findScanLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findScanLibrary",desc = "通过id查询扫描制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<ScanLibrary> findScanLibrary(@NotNull String id){
        ScanLibrary scanLibrary = scanLibraryService.findScanLibrary(id);

        return Result.ok(scanLibrary);
    }

    @RequestMapping(path="/findAllScanLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findAllScanLibrary",desc = "查询所有扫描制品")
    public Result<List<ScanLibrary>> findAllScanLibrary(){
        List<ScanLibrary> scanLibraryList = scanLibraryService.findAllScanLibrary();

        return Result.ok(scanLibraryList);
    }

    @RequestMapping(path = "/findScanLibraryList",method = RequestMethod.POST)
    @ApiMethod(name = "findScanLibraryList",desc = "条件查询扫描制品")
    @ApiParam(name = "scanLibraryQuery",desc = "scanLibraryQuery",required = true)
    public Result<List<ScanLibrary>> findScanLibraryList(@RequestBody @Valid @NotNull ScanLibraryQuery scanLibraryQuery){
        List<ScanLibrary> scanLibraryList = scanLibraryService.findScanLibraryList(scanLibraryQuery);

        return Result.ok(scanLibraryList);
    }

    @RequestMapping(path = "/findScanLibraryPage",method = RequestMethod.POST)
    @ApiMethod(name = "findScanLibraryPage",desc = "条件分页查询扫描制品")
    @ApiParam(name = "scanLibraryQuery",desc = "scanLibraryQuery",required = true)
    public Result<Pagination<ScanLibrary>> findScanLibraryPage(@RequestBody @Valid @NotNull ScanLibraryQuery scanLibraryQuery){
        Pagination<ScanLibrary> pagination = scanLibraryService.findScanLibraryPage(scanLibraryQuery);

        return Result.ok(pagination);
    }

}
