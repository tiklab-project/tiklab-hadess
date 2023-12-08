package io.thoughtware.hadess.library.controller;

import io.thoughtware.hadess.library.model.PushLibrary;
import io.thoughtware.hadess.library.model.PushLibraryQuery;
import io.thoughtware.hadess.library.service.PushLibraryService;
import io.thoughtware.core.Result;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.postin.annotation.Api;
import io.thoughtware.postin.annotation.ApiMethod;
import io.thoughtware.postin.annotation.ApiParam;
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
 * PushLibraryController
 */
@RestController
@RequestMapping("/pushLibrary")
@Api(name = "PushLibraryController",desc = "推送中央仓库的制品")
public class PushLibraryController {

    private static Logger logger = LoggerFactory.getLogger(PushLibraryController.class);

    @Autowired
    private PushLibraryService pushLibraryService;

    @RequestMapping(path="/createPushLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "createPushLibrary",desc = "创建推送制品")
    @ApiParam(name = "pushLibrary",desc = "pushLibrary",required = true)
    public Result<String> createPushLibrary(@RequestBody @NotNull @Valid PushLibrary pushLibrary){
        String id = pushLibraryService.createPushLibrary(pushLibrary);

        return Result.ok(id);
    }

    @RequestMapping(path="/updatePushLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "updatePushLibrary",desc = "更新推送制品")
    @ApiParam(name = "pushLibrary",desc = "pushLibrary",required = true)
    public Result<Void> updatePushLibrary(@RequestBody @NotNull @Valid PushLibrary pushLibrary){
        pushLibraryService.updatePushLibrary(pushLibrary);

        return Result.ok();
    }

    @RequestMapping(path="/deletePushLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "deletePushLibrary",desc = "删除推送制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deletePushLibrary(@NotNull String id){
        pushLibraryService.deletePushLibrary(id);

        return Result.ok();
    }

    @RequestMapping(path="/findPushLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findPushLibrary",desc = "通过id查询推送制品")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<PushLibrary> findPushLibrary(@NotNull String id){
        PushLibrary pushLibrary = pushLibraryService.findPushLibrary(id);

        return Result.ok(pushLibrary);
    }

    @RequestMapping(path="/findAllPushLibrary",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPushLibrary",desc = "查询所有推送制品")
    public Result<List<PushLibrary>> findAllPushLibrary(){
        List<PushLibrary> pushLibraryList = pushLibraryService.findAllPushLibrary();

        return Result.ok(pushLibraryList);
    }

    @RequestMapping(path = "/findPushLibraryList",method = RequestMethod.POST)
    @ApiMethod(name = "findPushLibraryList",desc = "条件查询推送制品")
    @ApiParam(name = "pushLibraryQuery",desc = "pushLibraryQuery",required = true)
    public Result<List<PushLibrary>> findPushLibraryList(@RequestBody @Valid @NotNull PushLibraryQuery pushLibraryQuery){
        List<PushLibrary> pushLibraryList = pushLibraryService.findPushLibraryList(pushLibraryQuery);
        return Result.ok(pushLibraryList);
    }

    @RequestMapping(path = "/findPushLibraryPage",method = RequestMethod.POST)
    @ApiMethod(name = "findPushLibraryPage",desc = "条件分页查询推送制品")
    @ApiParam(name = "pushLibraryQuery",desc = "pushLibraryQuery",required = true)
    public Result<Pagination<PushLibrary>> findPushLibraryPage(@RequestBody @Valid @NotNull PushLibraryQuery pushLibraryQuery){
        Pagination<PushLibrary> pagination = pushLibraryService.findPushLibraryPage(pushLibraryQuery);

        return Result.ok(pagination);
    }


    @RequestMapping(path="/pushCentralWare",method = RequestMethod.POST)
    @ApiMethod(name = "pushCentralWare",desc = "推送中央仓库")
    @ApiParam(name = "pushLibraryId",desc = "pushLibraryId",required = true)
    public Result<String> pushCentralWare(@NotNull String  pushLibraryId){
       String result = pushLibraryService.pushCentralWare(pushLibraryId);

        return Result.ok(result);
    }

    @RequestMapping(path="/pushResult",method = RequestMethod.POST)
    @ApiMethod(name = "pushResult",desc = "获取推送结果")
    @ApiParam(name = "repositoryId",desc = "制品库id ",required = true)
    public Result<PushLibrary> pushResult(String repositoryId){
        List result= pushLibraryService.pushResult(repositoryId);

        return Result.ok(result);
    }
}


