package io.tiklab.hadess.library.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.library.model.LibraryPypi;
import io.tiklab.hadess.library.model.LibraryPypiQuery;
import io.tiklab.hadess.library.service.LibraryPypiService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
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
 * LibraryPypiController
 */
@RestController
@RequestMapping("/libraryPypi")
@Api(name = "LibraryPypiController",desc = "制品pypi相关字段")
public class LibraryPypiController {

    private static Logger logger = LoggerFactory.getLogger(LibraryPypiController.class);

    @Autowired
    private LibraryPypiService libraryPypiService;

    @RequestMapping(path="/createLibraryPypi",method = RequestMethod.POST)
    @ApiMethod(name = "createLibraryPypi",desc = "创建pypi制品")
    @ApiParam(name = "libraryPypi",desc = "libraryPypi",required = true)
    public Result<String> createLibraryPypi(@RequestBody @NotNull @Valid LibraryPypi libraryPypi){
        String id = libraryPypiService.createLibraryPypi(libraryPypi);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateLibraryPypi",method = RequestMethod.POST)
    @ApiMethod(name = "updateLibraryPypi",desc = "更新pypi制品")
    @ApiParam(name = "libraryPypi",desc = "libraryPypi",required = true)
    public Result<Void> updateLibraryPypi(@RequestBody @NotNull @Valid LibraryPypi libraryPypi){
        libraryPypiService.updateLibraryPypi(libraryPypi);

        return Result.ok();
    }

    @RequestMapping(path="/deleteLibraryPypi",method = RequestMethod.POST)
    @ApiMethod(name = "deleteLibraryPypi",desc = "删除pypi制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteLibraryPypi(@NotNull String id){
        libraryPypiService.deleteLibraryPypi(id);

        return Result.ok();
    }

    @RequestMapping(path="/findLibraryPypi",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryPypi",desc = "通过id查询pypi制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<LibraryPypi> findLibraryPypi(@NotNull String id){
        LibraryPypi libraryPypi = libraryPypiService.findLibraryPypi(id);

        return Result.ok(libraryPypi);
    }

    @RequestMapping(path="/findAllLibraryPypi",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLibraryPypi",desc = "查询所有pypi制品")
    public Result<List<LibraryPypi>> findAllLibraryPypi(){
        List<LibraryPypi> libraryPypiList = libraryPypiService.findAllLibraryPypi();

        return Result.ok(libraryPypiList);
    }

    @RequestMapping(path = "/findLibraryPypiList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryPypiList",desc = "条件查询pypi制品")
    @ApiParam(name = "libraryPypiQuery",desc = "libraryPypiQuery",required = true)
    public Result<List<LibraryPypi>> findLibraryPypiList(@RequestBody @Valid @NotNull LibraryPypiQuery libraryPypiQuery){
        List<LibraryPypi> libraryPypiList = libraryPypiService.findLibraryPypiList(libraryPypiQuery);

        return Result.ok(libraryPypiList);
    }

    @RequestMapping(path = "/findLibraryPypiPage",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryPypiPage",desc = "条件分页查询pypi制品")
    @ApiParam(name = "libraryPypiQuery",desc = "libraryPypiQuery",required = true)
    public Result<Pagination<LibraryPypi>> findLibraryPypiPage(@RequestBody @Valid @NotNull LibraryPypiQuery libraryPypiQuery){
        Pagination<LibraryPypi> pagination = libraryPypiService.findLibraryPypiPage(libraryPypiQuery);

        return Result.ok(pagination);
    }

}
