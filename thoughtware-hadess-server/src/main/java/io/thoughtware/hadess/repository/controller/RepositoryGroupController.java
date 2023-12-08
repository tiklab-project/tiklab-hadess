package io.thoughtware.hadess.repository.controller;

import io.thoughtware.hadess.repository.model.RepositoryGroup;
import io.thoughtware.hadess.repository.model.RepositoryGroupQuery;
import io.thoughtware.hadess.repository.service.RepositoryGroupService;
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
 * RepositoryGroupController
 */
@RestController
@RequestMapping("/repositoryGroup")
@Api(name = "RepositoryGroupController",desc = "组合库和本地远程库关联管理")
public class RepositoryGroupController {

    private static Logger logger = LoggerFactory.getLogger(RepositoryGroupController.class);

    @Autowired
    private RepositoryGroupService repositoryGroupService;

    @RequestMapping(path="/createRepositoryGroup",method = RequestMethod.POST)
    @ApiMethod(name = "createRepositoryGroup",desc = "创建组合库关联")
    @ApiParam(name = "repositoryGroup",desc = "repositoryGroup",required = true)
    public Result<String> createRepositoryGroup(@RequestBody @NotNull @Valid RepositoryGroup repositoryGroup){
        String id = repositoryGroupService.createRepositoryGroup(repositoryGroup);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRepositoryGroup",method = RequestMethod.POST)
    @ApiMethod(name = "updateRepositoryGroup",desc = "更新组合库关联")
    @ApiParam(name = "repositoryGroup",desc = "repositoryGroup",required = true)
    public Result<Void> updateRepositoryGroup(@RequestBody @NotNull @Valid RepositoryGroup repositoryGroup){
        repositoryGroupService.updateRepositoryGroup(repositoryGroup);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRepositoryGroup",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRepositoryGroup",desc = "删除组合库关联")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRepositoryGroup(@NotNull String id){
        repositoryGroupService.deleteRepositoryGroup(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRepositoryGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryGroup",desc = "通过id查询组合库关联")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<RepositoryGroup> findRepositoryGroup(@NotNull String id){
        RepositoryGroup repositoryGroup = repositoryGroupService.findRepositoryGroup(id);

        return Result.ok(repositoryGroup);
    }

    @RequestMapping(path="/findAllRepositoryGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepositoryGroup",desc = "查询所有组合库关联")
    public Result<List<RepositoryGroup>> findAllRepositoryGroup(){
        List<RepositoryGroup> repositoryGroupList = repositoryGroupService.findAllRepositoryGroup();

        return Result.ok(repositoryGroupList);
    }

    @RequestMapping(path = "/findRepositoryGroupList",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryGroupList",desc = "条件查询组合库关联")
    @ApiParam(name = "repositoryGroupQuery",desc = "repositoryGroupQuery",required = true)
    public Result<List<RepositoryGroup>> findRepositoryGroupList(@RequestBody @Valid @NotNull RepositoryGroupQuery repositoryGroupQuery){
        List<RepositoryGroup> repositoryGroupList = repositoryGroupService.findRepositoryGroupList(repositoryGroupQuery);

        return Result.ok(repositoryGroupList);
    }

    @RequestMapping(path = "/findRepositoryGroupPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryGroupPage",desc = "条件分页查询组合库关联")
    @ApiParam(name = "repositoryGroupQuery",desc = "repositoryGroupQuery",required = true)
    public Result<Pagination<RepositoryGroup>> findRepositoryGroupPage(@RequestBody @Valid @NotNull RepositoryGroupQuery repositoryGroupQuery){
        Pagination<RepositoryGroup> pagination = repositoryGroupService.findRepositoryGroupPage(repositoryGroupQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path="/compileRepositoryGroup",method = RequestMethod.POST)
    @ApiMethod(name = "compileRepositoryGroup",desc = "根据条件编辑组合库关联")
    @ApiParam(name = "repositoryGroup",desc = "repositoryGroup",required = true)
    public Result<String> compileRepositoryGroup(@RequestBody @NotNull RepositoryGroupQuery repositoryGroupQuery){

         repositoryGroupService.compileRepositoryGroup(repositoryGroupQuery);

        return Result.ok();
    }


}
