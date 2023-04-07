package io.tiklab.xpack.library.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.library.model.LibraryVersion;
import io.tiklab.xpack.library.model.LibraryVersionQuery;
import io.tiklab.xpack.library.service.LibraryVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * LibraryVersionController
 */
@RestController
@RequestMapping("/libraryVersion")
@Api(name = "LibraryVersionController",desc = "制品版本信息管理")
public class LibraryVersionController {

    private static Logger logger = LoggerFactory.getLogger(LibraryVersionController.class);

    @Autowired
    private LibraryVersionService libraryVersionService;

    @RequestMapping(path="/createLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "createLibraryVersion",desc = "创建制品版本")
    @ApiParam(name = "libraryVersion",desc = "libraryVersion",required = true)
    public Result<String> createLibraryVersion(@RequestBody @NotNull @Valid LibraryVersion libraryVersion){
        String id = libraryVersionService.createLibraryVersion(libraryVersion);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "updateLibraryVersion",desc = "更新制品版本")
    @ApiParam(name = "libraryVersion",desc = "libraryVersion",required = true)
    public Result<Void> updateLibraryVersion(@RequestBody @NotNull @Valid LibraryVersion libraryVersion){
        libraryVersionService.updateLibraryVersion(libraryVersion);

        return Result.ok();
    }

    @RequestMapping(path="/deleteLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "deleteLibraryVersion",desc = "删除制品版本")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteLibraryVersion(@NotNull String id){
        libraryVersionService.deleteLibraryVersion(id);

        return Result.ok();
    }

    @RequestMapping(path="/findLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryVersion",desc = "通过id查询制品版本")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<LibraryVersion> findLibraryVersion(@NotNull String id){
        LibraryVersion libraryVersion = libraryVersionService.findLibraryVersion(id);

        return Result.ok(libraryVersion);
    }

    @RequestMapping(path="/findAllLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLibraryVersion",desc = "查询所有制品版本")
    public Result<List<LibraryVersion>> findAllLibraryVersion(){
        List<LibraryVersion> libraryVersionList = libraryVersionService.findAllLibraryVersion();

        return Result.ok(libraryVersionList);
    }

    @RequestMapping(path = "/findLibraryVersionList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryVersionList",desc = "条件查询制品版本")
    @ApiParam(name = "libraryVersionQuery",desc = "libraryVersionQuery",required = true)
    public Result<List<LibraryVersion>> findLibraryVersionList(@RequestBody @Valid @NotNull LibraryVersionQuery libraryVersionQuery){
        List<LibraryVersion> libraryVersionList = libraryVersionService.findLibraryVersionList(libraryVersionQuery);

        return Result.ok(libraryVersionList);
    }

    @RequestMapping(path = "/findLibraryVersionPage",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryVersionPage",desc = "条件分页查询制品版本")
    @ApiParam(name = "libraryVersionQuery",desc = "libraryVersionQuery",required = true)
    public Result<Pagination<LibraryVersion>> findLibraryVersionPage(@RequestBody @Valid @NotNull LibraryVersionQuery libraryVersionQuery){
        Pagination<LibraryVersion> pagination = libraryVersionService.findLibraryVersionPage(libraryVersionQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findLibraryNewVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryNewVersion",desc = "查询最新制品版本")
    @ApiParam(name = "libraryVersionQuery",desc = "libraryVersionQuery",required = true)
    public Result<List<LibraryVersion>> findLibraryNewVersion(@RequestBody @Valid @NotNull LibraryVersionQuery libraryVersionQuery){
        LibraryVersion libraryVersion = libraryVersionService.findLibraryNewVersion(libraryVersionQuery);

        return Result.ok(libraryVersion);
    }

}