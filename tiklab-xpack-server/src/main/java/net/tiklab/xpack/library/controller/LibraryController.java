package net.tiklab.xpack.library.controller;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.xdevapi.JsonString;
import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.xpack.library.model.Library;
import net.tiklab.xpack.library.model.LibraryQuery;
import net.tiklab.xpack.library.service.LibraryService;
import net.tiklab.xpack.repository.model.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

/**
 * LibraryController
 */
@RestController
@RequestMapping("/library")
@Api(name = "LibraryController",desc = "制品管理")
public class LibraryController {

    private static Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private LibraryService libraryService;

    @RequestMapping(path="/createLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "createLibrary",desc = "创建制品库")
    @ApiParam(name = "library",desc = "library",required = true)
    public Result<String> createLibrary(@RequestBody @NotNull @Valid Library library){
        String id = libraryService.createLibrary(library);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "updateLibrary",desc = "更新制品库")
    @ApiParam(name = "library",desc = "library",required = true)
    public Result<Void> updateLibrary(@RequestBody @NotNull @Valid Library library){
        libraryService.updateLibrary(library);

        return Result.ok();
    }

    @RequestMapping(path="/deleteLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "deleteLibrary",desc = "删除制品库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteLibrary(@NotNull String id){
        libraryService.deleteLibrary(id);

        return Result.ok();
    }

    @RequestMapping(path="/findLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findLibrary",desc = "通过id查询制品库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Library> findLibrary(@NotNull String id){
        Library library = libraryService.findLibrary(id);

        return Result.ok(library);
    }

    @RequestMapping(path="/findAllLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLibrary",desc = "查询所有制品库")
    public Result<List<Library>> findAllLibrary(){
        List<Library> libraryList = libraryService.findAllLibrary();

        return Result.ok(libraryList);
    }

    @RequestMapping(path = "/findLibraryList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryList",desc = "条件查询制品库")
    @ApiParam(name = "libraryQuery",desc = "libraryQuery",required = true)
    public Result<List<Library>> findLibraryList(@RequestBody @Valid @NotNull LibraryQuery libraryQuery){
        List<Library> libraryList = libraryService.findLibraryList(libraryQuery);
        return Result.ok(libraryList);
    }

    @RequestMapping(path = "/findLibraryPage",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryPage",desc = "条件分页查询制品库")
    @ApiParam(name = "libraryQuery",desc = "libraryQuery",required = true)
    public Result<Pagination<Library>> findLibraryPage(@RequestBody @Valid @NotNull LibraryQuery libraryQuery){
        Pagination<Library> pagination = libraryService.findLibraryPage(libraryQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findMavenLibraryList",method = RequestMethod.POST)
    @ApiMethod(name = "findMavenLibraryList",desc = "查询maven制品列表")
    @ApiParam(name = "libraryQuery",desc = "libraryQuery",required = true)
    public Result<List<Library>> findMavenLibraryList(@RequestBody @Valid @NotNull LibraryQuery libraryQuery){
        List<Library> libraryList = libraryService.findMavenLibraryList(libraryQuery);
        return Result.ok(libraryList);
    }


}


