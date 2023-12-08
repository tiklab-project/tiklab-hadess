package io.thoughtware.hadess.repository.controller;

import io.thoughtware.hadess.repository.model.RepositoryClusterCfg;
import io.thoughtware.hadess.repository.model.RepositoryClusterCfgQuery;
import io.thoughtware.hadess.repository.service.RepositoryClusterCfgService;
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
 * RepositoryClusterCfgController
 */
@RestController
@RequestMapping("/repositoryClusterCfg")
@Api(name = "RepositoryClusterCfgController",desc = "制品库复制信息")
public class RepositoryClusterCfgController {

    private static Logger logger = LoggerFactory.getLogger(RepositoryClusterCfgController.class);

    @Autowired
    private RepositoryClusterCfgService repositoryClusterCfgService;

    @RequestMapping(path="/createRepositoryClusterCfg",method = RequestMethod.POST)
    @ApiMethod(name = "createRepositoryClusterCfg",desc = "创建制品库复制信息")
    @ApiParam(name = "repositoryClusterCfg",desc = "repositoryClusterCfg",required = true)
    public Result<String> createRepositoryClusterCfg(@RequestBody @NotNull @Valid RepositoryClusterCfg repositoryClusterCfg){
        String id = repositoryClusterCfgService.createRepositoryClusterCfg(repositoryClusterCfg);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRepositoryClusterCfg",method = RequestMethod.POST)
    @ApiMethod(name = "updateRepositoryClusterCfg",desc = "更新制品库复制信息")
    @ApiParam(name = "repositoryClusterCfg",desc = "repositoryClusterCfg",required = true)
    public Result<Void> updateRepositoryClusterCfg(@RequestBody @NotNull @Valid RepositoryClusterCfg repositoryClusterCfg){
        repositoryClusterCfgService.updateRepositoryClusterCfg(repositoryClusterCfg);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRepositoryClusterCfg",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRepositoryClusterCfg",desc = "删除制品库复制信息")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRepositoryClusterCfg(@NotNull String id){
        repositoryClusterCfgService.deleteRepositoryClusterCfg(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRepositoryClusterCfg",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryClusterCfg",desc = "通过id查询制品库复制信息")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<RepositoryClusterCfg> findRepositoryClusterCfg(@NotNull String id){
        RepositoryClusterCfg repositoryClusterCfg = repositoryClusterCfgService.findRepositoryClusterCfg(id);

        return Result.ok(repositoryClusterCfg);
    }

    @RequestMapping(path="/findAllRepositoryClusterCfg",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepositoryClusterCfg",desc = "查询所有制品库复制信息")
    public Result<List<RepositoryClusterCfg>> findAllRepositoryClusterCfg(){
        List<RepositoryClusterCfg> repositoryClusterCfgList = repositoryClusterCfgService.findAllRepositoryClusterCfg();

        return Result.ok(repositoryClusterCfgList);
    }

    @RequestMapping(path = "/findRepositoryClusterCfgList",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryClusterCfgList",desc = "条件查询制品库复制信息")
    @ApiParam(name = "repositoryClusterCfgQuery",desc = "repositoryClusterCfgQuery",required = true)
    public Result<List<RepositoryClusterCfg>> findRepositoryClusterCfgList(@RequestBody @Valid @NotNull RepositoryClusterCfgQuery repositoryClusterCfgQuery){
        List<RepositoryClusterCfg> repositoryClusterCfgList = repositoryClusterCfgService.findRepositoryClusterCfgList(repositoryClusterCfgQuery);

        return Result.ok(repositoryClusterCfgList);
    }

    @RequestMapping(path = "/findRepositoryClusterCfgPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryClusterCfgPage",desc = "条件分页查询制品库复制信息")
    @ApiParam(name = "repositoryClusterCfgQuery",desc = "repositoryClusterCfgQuery",required = true)
    public Result<Pagination<RepositoryClusterCfg>> findRepositoryClusterCfgPage(@RequestBody @Valid @NotNull RepositoryClusterCfgQuery repositoryClusterCfgQuery){
        Pagination<RepositoryClusterCfg> pagination = repositoryClusterCfgService.findRepositoryClusterCfgPage(repositoryClusterCfgQuery);

        return Result.ok(pagination);
    }

}
