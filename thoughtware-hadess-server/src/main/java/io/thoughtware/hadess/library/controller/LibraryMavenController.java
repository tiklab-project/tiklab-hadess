package io.thoughtware.hadess.library.controller;

import io.thoughtware.hadess.library.model.LibraryMaven;
import io.thoughtware.hadess.library.model.LibraryMavenQuery;
import io.thoughtware.hadess.library.service.LibraryMavenService;
import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
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
 * LibraryMavenController
 */
@RestController
@RequestMapping("/libraryMaven")
@Api(name = "LibraryMavenController",desc = "制品maven相关字段")
public class LibraryMavenController {

    private static Logger logger = LoggerFactory.getLogger(LibraryMavenController.class);

    @Autowired
    private LibraryMavenService libraryMavenService;

    @RequestMapping(path="/createLibraryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "createLibraryMaven",desc = "创建maven制品")
    @ApiParam(name = "libraryMaven",desc = "libraryMaven",required = true)
    public Result<String> createLibraryMaven(@RequestBody @NotNull @Valid LibraryMaven libraryMaven){
        String id = libraryMavenService.createLibraryMaven(libraryMaven);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateLibraryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "updateLibraryMaven",desc = "更新maven制品")
    @ApiParam(name = "libraryMaven",desc = "libraryMaven",required = true)
    public Result<Void> updateLibraryMaven(@RequestBody @NotNull @Valid LibraryMaven libraryMaven){
        libraryMavenService.updateLibraryMaven(libraryMaven);

        return Result.ok();
    }

    @RequestMapping(path="/deleteLibraryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "deleteLibraryMaven",desc = "删除maven制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteLibraryMaven(@NotNull String id){
        libraryMavenService.deleteLibraryMaven(id);

        return Result.ok();
    }

    @RequestMapping(path="/findLibraryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryMaven",desc = "通过id查询maven制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<LibraryMaven> findLibraryMaven(@NotNull String id){
        LibraryMaven libraryMaven = libraryMavenService.findLibraryMaven(id);

        return Result.ok(libraryMaven);
    }

    @RequestMapping(path="/findAllLibraryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLibraryMaven",desc = "查询所有maven制品")
    public Result<List<LibraryMaven>> findAllLibraryMaven(){
        List<LibraryMaven> libraryMavenList = libraryMavenService.findAllLibraryMaven();

        return Result.ok(libraryMavenList);
    }

    @RequestMapping(path = "/findLibraryMavenList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryMavenList",desc = "条件查询maven制品")
    @ApiParam(name = "libraryMavenQuery",desc = "libraryMavenQuery",required = true)
    public Result<List<LibraryMaven>> findLibraryMavenList(@RequestBody @Valid @NotNull LibraryMavenQuery libraryMavenQuery){
        List<LibraryMaven> libraryMavenList = libraryMavenService.findLibraryMavenList(libraryMavenQuery);

        return Result.ok(libraryMavenList);
    }

    @RequestMapping(path = "/findLibraryMavenPage",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryMavenPage",desc = "条件分页查询maven制品")
    @ApiParam(name = "libraryMavenQuery",desc = "libraryMavenQuery",required = true)
    public Result<Pagination<LibraryMaven>> findLibraryMavenPage(@RequestBody @Valid @NotNull LibraryMavenQuery libraryMavenQuery){
        Pagination<LibraryMaven> pagination = libraryMavenService.findLibraryMavenPage(libraryMavenQuery);

        return Result.ok(pagination);
    }

}
