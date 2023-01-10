package net.tiklab.xpack.library.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.xpack.library.model.LibraryVersion;
import net.tiklab.xpack.library.model.LibraryVersionQuery;
import net.tiklab.xpack.library.service.LibraryVersionService;
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
    @ApiMethod(name = "createLibraryVersion",desc = "createLibraryVersion")
    @ApiParam(name = "libraryVersion",desc = "libraryVersion",required = true)
    public Result<String> createLibraryVersion(@RequestBody @NotNull @Valid LibraryVersion libraryVersion){
        String id = libraryVersionService.createLibraryVersion(libraryVersion);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "updateLibraryVersion",desc = "updateLibraryVersion")
    @ApiParam(name = "libraryVersion",desc = "libraryVersion",required = true)
    public Result<Void> updateLibraryVersion(@RequestBody @NotNull @Valid LibraryVersion libraryVersion){
        libraryVersionService.updateLibraryVersion(libraryVersion);

        return Result.ok();
    }

    @RequestMapping(path="/deleteLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "deleteLibraryVersion",desc = "deleteLibraryVersion")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteLibraryVersion(@NotNull String id){
        libraryVersionService.deleteLibraryVersion(id);

        return Result.ok();
    }

    @RequestMapping(path="/findLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryVersion",desc = "findLibraryVersion")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<LibraryVersion> findLibraryVersion(@NotNull String id){
        LibraryVersion libraryVersion = libraryVersionService.findLibraryVersion(id);

        return Result.ok(libraryVersion);
    }

    @RequestMapping(path="/findAllLibraryVersion",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLibraryVersion",desc = "findAllLibraryVersion")
    public Result<List<LibraryVersion>> findAllLibraryVersion(){
        List<LibraryVersion> libraryVersionList = libraryVersionService.findAllLibraryVersion();

        return Result.ok(libraryVersionList);
    }

    @RequestMapping(path = "/findLibraryVersionList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryVersionList",desc = "findLibraryVersionList")
    @ApiParam(name = "libraryVersionQuery",desc = "libraryVersionQuery",required = true)
    public Result<List<LibraryVersion>> findLibraryVersionList(@RequestBody @Valid @NotNull LibraryVersionQuery libraryVersionQuery){
        List<LibraryVersion> libraryVersionList = libraryVersionService.findLibraryVersionList(libraryVersionQuery);

        return Result.ok(libraryVersionList);
    }

    @RequestMapping(path = "/findLibraryVersionPage",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryVersionPage",desc = "findLibraryVersionPage")
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
