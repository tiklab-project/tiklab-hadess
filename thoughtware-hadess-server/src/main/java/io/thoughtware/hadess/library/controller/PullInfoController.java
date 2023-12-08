package io.thoughtware.hadess.library.controller;

import io.thoughtware.hadess.library.model.PullInfo;
import io.thoughtware.hadess.library.model.PullInfoQuery;
import io.thoughtware.hadess.library.service.PullInfoService;
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
 * PullInfoController
 */
@RestController
@RequestMapping("/pullInfo")
@Api(name = "PullInfoController",desc = "制品拉取信息")
public class PullInfoController {

    private static Logger logger = LoggerFactory.getLogger(PullInfoController.class);

    @Autowired
    private PullInfoService pullInfoService;

    @RequestMapping(path="/createPullInfo",method = RequestMethod.POST)
    @ApiMethod(name = "createPullInfo",desc = "创建制品拉取信息")
    @ApiParam(name = "pullInfo",desc = "pullInfo",required = true)
    public Result<String> createPullInfo(@RequestBody @NotNull @Valid PullInfo pullInfo){
        String id = pullInfoService.createPullInfo(pullInfo);

        return Result.ok(id);
    }

    @RequestMapping(path="/updatePullInfo",method = RequestMethod.POST)
    @ApiMethod(name = "updatePullInfo",desc = "更新制品拉取信息")
    @ApiParam(name = "pullInfo",desc = "pullInfo",required = true)
    public Result<Void> updatePullInfo(@RequestBody @NotNull @Valid PullInfo pullInfo){
        pullInfoService.updatePullInfo(pullInfo);

        return Result.ok();
    }

    @RequestMapping(path="/deletePullInfo",method = RequestMethod.POST)
    @ApiMethod(name = "deletePullInfo",desc = "删除制品拉取信息")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deletePullInfo(@NotNull String id){
        pullInfoService.deletePullInfo(id);

        return Result.ok();
    }

    @RequestMapping(path="/findPullInfo",method = RequestMethod.POST)
    @ApiMethod(name = "findPullInfo",desc = "通过id查询制品文件")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<PullInfo> findPullInfo(@NotNull String id){
        PullInfo pullInfo = pullInfoService.findPullInfo(id);

        return Result.ok(pullInfo);
    }

    @RequestMapping(path="/findAllPullInfo",method = RequestMethod.POST)
    @ApiMethod(name = "findAllPullInfo",desc = "查询所有制品拉取信息")
    public Result<List<PullInfo>> findAllPullInfo(){
        List<PullInfo> pullInfoList = pullInfoService.findAllPullInfo();

        return Result.ok(pullInfoList);
    }

    @RequestMapping(path = "/findPullInfoList",method = RequestMethod.POST)
    @ApiMethod(name = "findPullInfoList",desc = "条件查询制品拉取信息")
    @ApiParam(name = "pullInfoQuery",desc = "pullInfoQuery",required = true)
    public Result<List<PullInfo>> findPullInfoList(@RequestBody @Valid @NotNull PullInfoQuery pullInfoQuery){
        List<PullInfo> pullInfoList = pullInfoService.findPullInfoList(pullInfoQuery);

        return Result.ok(pullInfoList);
    }

    @RequestMapping(path = "/findPullInfoPage",method = RequestMethod.POST)
    @ApiMethod(name = "findPullInfoPage",desc = "条件分页查询制品拉取信息")
    @ApiParam(name = "pullInfoQuery",desc = "pullInfoQuery",required = true)
    public Result<Pagination<PullInfo>> findPullInfoPage(@RequestBody @Valid @NotNull PullInfoQuery pullInfoQuery){
        Pagination<PullInfo> pagination = pullInfoService.findPullInfoPage(pullInfoQuery);

        return Result.ok(pagination);
    }
}
