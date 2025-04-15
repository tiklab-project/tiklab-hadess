package io.tiklab.hadess.library.controller;

import io.tiklab.hadess.library.model.LibraryFile;
import io.tiklab.hadess.library.model.LibraryFileQuery;
import io.tiklab.hadess.library.service.LibraryFileService;
import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
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
 * LibraryFileController
 */
@RestController
@RequestMapping("/libraryFile")
@Api(name = "LibraryFileController",desc = "制品文件")
public class LibraryFileController  {

    private static Logger logger = LoggerFactory.getLogger(LibraryFileController.class);

    @Autowired
    private LibraryFileService libraryFileService;


    @RequestMapping(path="/createLibraryFile",method = RequestMethod.POST)
    @ApiMethod(name = "createLibraryFile",desc = "创建制品文件")
    @ApiParam(name = "libraryFile",desc = "libraryFile",required = true)
    public Result<String> createLibraryFile(@RequestBody @NotNull @Valid LibraryFile libraryFile){
        String id = libraryFileService.createLibraryFile(libraryFile);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateLibraryFile",method = RequestMethod.POST)
    @ApiMethod(name = "updateLibraryFile",desc = "更新制品文件")
    @ApiParam(name = "libraryFile",desc = "libraryFile",required = true)
    public Result<Void> updateLibraryFile(@RequestBody @NotNull @Valid LibraryFile libraryFile){
        libraryFileService.updateLibraryFile(libraryFile);

        return Result.ok();
    }

    @RequestMapping(path="/deleteLibraryFile",method = RequestMethod.POST)
    @ApiMethod(name = "deleteLibraryFile",desc = "删除制品文件")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteLibraryFile(@NotNull String id){
        libraryFileService.deleteLibraryFile(id);

        return Result.ok();
    }

    @RequestMapping(path="/deleteSnapshotFile",method = RequestMethod.POST)
    @ApiMethod(name = "deleteSnapshotFile",desc = "通过快照版本和版本id删除")
    @ApiParam(name = "snapshotVersion",desc = "versionId、snapshotVersion",required = true)
    public Result<Void> deleteSnapshotFile(@NotNull String versionId,@NotNull String snapshotVersion){
        libraryFileService.deleteLibraryFile(versionId,snapshotVersion);

        return Result.ok();
    }

    @RequestMapping(path="/findLibraryFile",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryFile",desc = "通过id查询制品文件")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<LibraryFile> findLibraryFile(@NotNull String id){
        LibraryFile libraryFile = libraryFileService.findLibraryFile(id);

        return Result.ok(libraryFile);
    }

    @RequestMapping(path="/findAllLibraryFile",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLibraryFile",desc = "查询所有制品文件")
    public Result<List<LibraryFile>> findAllLibraryFile(){
        List<LibraryFile> libraryFileList = libraryFileService.findAllLibraryFile();

        return Result.ok(libraryFileList);
    }

    @RequestMapping(path = "/findLibraryFileList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryFileList",desc = "条件查询制品文件")
    @ApiParam(name = "libraryFileQuery",desc = "libraryFileQuery",required = true)
    public Result<List<LibraryFile>> findLibraryFileList(@RequestBody @Valid @NotNull LibraryFileQuery libraryFileQuery){
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(libraryFileQuery);

        return Result.ok(libraryFileList);
    }

    @RequestMapping(path = "/findLibraryFilePage",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryFilePage",desc = "条件分页查询制品文件")
    @ApiParam(name = "libraryFileQuery",desc = "libraryFileQuery",required = true)
    public Result<Pagination<LibraryFile>> findLibraryFilePage(@RequestBody @Valid @NotNull LibraryFileQuery libraryFileQuery){
        Pagination<LibraryFile> pagination = libraryFileService.findLibraryFilePage(libraryFileQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findLibraryNewFileList",method = RequestMethod.POST)
    @ApiMethod(name = "findLibraryNewFileList",desc = "查询最新版本的文件list")
    @ApiParam(name = "libraryFileQuery",desc = "libraryFileQuery",required = true)
    public Result<List<LibraryFile>> findLibraryNewFileList(@RequestBody @Valid @NotNull LibraryFileQuery libraryFileQuery){
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryNewFileList(libraryFileQuery);

        return Result.ok(libraryFileList);
    }

    @RequestMapping(path = "/readLibraryFileData",method = RequestMethod.POST)
    @ApiMethod(name = "readLibraryFileData",desc = "读取制品文件的内容")
    @ApiParam(name = "libraryFileQuery",desc = "libraryFileQuery",required = true)
    public Result<String> readLibraryFileData(@RequestBody @Valid @NotNull LibraryFileQuery libraryFileQuery){
        String data = libraryFileService.readLibraryFileData(libraryFileQuery);

        return Result.ok(data);
    }

    @RequestMapping(path = "/findDockerLayers",method = RequestMethod.POST)
    @ApiMethod(name = "findDockerLayers",desc = "读取docker镜像历史列表")
    @ApiParam(name = "libraryFileQuery",desc = "findDockerLayers",required = true)
    public Result<List<String>> findDockerLayers(@RequestBody @Valid @NotNull LibraryFileQuery libraryFileQuery){
        List<String> data = libraryFileService.findDockerLayers(libraryFileQuery);

        return Result.ok(data);
    }
}
