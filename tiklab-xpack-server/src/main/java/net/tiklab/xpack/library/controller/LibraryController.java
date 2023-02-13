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
    @ApiMethod(name = "createLibrary",desc = "createLibrary")
    @ApiParam(name = "library",desc = "library",required = true)
    public Result<String> createLibrary(@RequestBody @NotNull @Valid Library library){
        String id = libraryService.createLibrary(library);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "updateLibrary",desc = "updateLibrary")
    @ApiParam(name = "library",desc = "library",required = true)
    public Result<Void> updateLibrary(@RequestBody @NotNull @Valid Library library){
        libraryService.updateLibrary(library);

        return Result.ok();
    }

    @RequestMapping(path="/deleteLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "deleteLibrary",desc = "deleteLibrary")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteLibrary(@NotNull String id){
        libraryService.deleteLibrary(id);

        return Result.ok();
    }

    @RequestMapping(path="/findLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findLibrary",desc = "findLibrary")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Library> findLibrary(@NotNull String id){
        Library library = libraryService.findLibrary(id);

        return Result.ok(library);
    }

    @RequestMapping(path="/findAllLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLibrary",desc = "findAllLibrary")
    public Result<List<Library>> findAllLibrary(){
        List<Library> libraryList = libraryService.findAllLibrary();

        return Result.ok(libraryList);
    }

    @RequestMapping(path = "/findLibraryList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryList",desc = "findLibraryList")
    @ApiParam(name = "libraryQuery",desc = "libraryQuery",required = true)
    public Result<List<Library>> findLibraryList(@RequestBody @Valid @NotNull LibraryQuery libraryQuery){
        List<Library> libraryList = libraryService.findLibraryList(libraryQuery);
        return Result.ok(libraryList);
    }

    @RequestMapping(path = "/findLibraryPage",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryPage",desc = "findLibraryPage")
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


    @RequestMapping(path = "/mavenSubmit/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne制品提交")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public Result<Repository> mavenSubmit(HttpServletRequest request, HttpServletResponse response){

        String contextPath = request.getRequestURI();
        OutputStream outputStream=null;
        InputStream inputStream=null;
        try {
            inputStream = request.getInputStream();
            libraryService.mavenSubmit(contextPath,inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @RequestMapping(path = "/maven-install/**",method = {RequestMethod.PUT,RequestMethod.GET})
    @ApiMethod(name = "mavenSubmit",desc = "mavne制品拉取")
    @ApiParam(name = "requestParam",desc = "requestParam")
    public void mavenInstall(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        String contextPath = request.getRequestURI();
        byte[] fileData = libraryService.mavenInstall(contextPath);
        response.setCharacterEncoding("UTF-8");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(fileData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}


