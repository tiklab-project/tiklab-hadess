package io.tiklab.hadess.library.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.library.model.LibraryComposer;
import io.tiklab.hadess.library.model.LibraryComposerQuery;
import io.tiklab.hadess.library.service.LibraryComposerService;
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
 * LibraryComposerController  制品composer相关字段
 */
@RestController
@RequestMapping("/libraryComposer")
public class LibraryComposerController {

    private static Logger logger = LoggerFactory.getLogger(LibraryComposerController.class);

    @Autowired
    private LibraryComposerService libraryComposerService;

    @RequestMapping(path="/createLibraryComposer",method = RequestMethod.POST)
    public Result<String> createLibraryComposer(@RequestBody @NotNull @Valid LibraryComposer libraryComposer){
        String id = libraryComposerService.createLibraryComposer(libraryComposer);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateLibraryComposer",method = RequestMethod.POST)
    public Result<Void> updateLibraryComposer(@RequestBody @NotNull @Valid LibraryComposer libraryComposer){
        libraryComposerService.updateLibraryComposer(libraryComposer);

        return Result.ok();
    }


    @RequestMapping(path="/deleteLibraryComposer",method = RequestMethod.POST)
    public Result<Void> deleteLibraryComposer(@NotNull String id){
        libraryComposerService.deleteLibraryComposer(id);

        return Result.ok();
    }

    @RequestMapping(path="/findLibraryComposer",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryComposer",desc = "通过id查询composer制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<LibraryComposer> findLibraryComposer(@NotNull String id){
        LibraryComposer libraryComposer = libraryComposerService.findLibraryComposer(id);

        return Result.ok(libraryComposer);
    }

    @RequestMapping(path="/findAllLibraryComposer",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLibraryComposer",desc = "查询所有composer制品")
    public Result<List<LibraryComposer>> findAllLibraryComposer(){
        List<LibraryComposer> libraryComposerList = libraryComposerService.findAllLibraryComposer();

        return Result.ok(libraryComposerList);
    }

    @RequestMapping(path = "/findLibraryComposerList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryComposerList",desc = "条件查询composer制品")
    @ApiParam(name = "libraryComposerQuery",desc = "libraryComposerQuery",required = true)
    public Result<List<LibraryComposer>> findLibraryComposerList(@RequestBody @Valid @NotNull LibraryComposerQuery libraryComposerQuery){
        List<LibraryComposer> libraryComposerList = libraryComposerService.findLibraryComposerList(libraryComposerQuery);

        return Result.ok(libraryComposerList);
    }

    @RequestMapping(path = "/findLibraryComposerPage",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryComposerPage",desc = "条件分页查询composer制品")
    @ApiParam(name = "libraryComposerQuery",desc = "libraryComposerQuery",required = true)
    public Result<Pagination<LibraryComposer>> findLibraryComposerPage(@RequestBody @Valid @NotNull LibraryComposerQuery libraryComposerQuery){
        Pagination<LibraryComposer> pagination = libraryComposerService.findLibraryComposerPage(libraryComposerQuery);

        return Result.ok(pagination);
    }

}
