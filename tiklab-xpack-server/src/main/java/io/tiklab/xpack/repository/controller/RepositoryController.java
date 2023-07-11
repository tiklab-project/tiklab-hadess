package io.tiklab.xpack.repository.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;

import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import io.tiklab.xpack.repository.model.Repository;
import io.tiklab.xpack.repository.model.RepositoryQuery;
import io.tiklab.xpack.repository.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * RepositoryController
 */
@RestController
@RequestMapping("/repository")
@Api(name = "RepositoryController",desc = "制品库管理")
public class RepositoryController {

    private static Logger logger = LoggerFactory.getLogger(RepositoryController.class);

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(path="/createRepository",method = RequestMethod.POST)
    @ApiMethod(name = "createRepository",desc = "创建制品库")
    @ApiParam(name = "repository",desc = "repository",required = true)
    public Result<String> createRepository(@RequestBody @NotNull @Valid Repository repository){
        String id = repositoryService.createRepository(repository);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRepository",method = RequestMethod.POST)
    @ApiMethod(name = "updateRepository",desc = "更新制品库")
    @ApiParam(name = "repository",desc = "repository",required = true)
    public Result<Void> updateRepository(@RequestBody @NotNull @Valid Repository repository){
        repositoryService.updateRepository(repository);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRepository",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRepository",desc = "删除制品库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRepository(@NotNull String id){
        repositoryService.deleteRepository(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findRepository",desc = "通过id查询制品库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Repository> findRepository(@NotNull String id){
        Repository repository = repositoryService.findRepository(id);

        return Result.ok(repository);
    }

    @RequestMapping(path="/findAllRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepository",desc = "查询所有制品库")
    public Result<List<Repository>> findAllRepository(){
        List<Repository> repositoryList = repositoryService.findAllRepository();

        return Result.ok(repositoryList);
    }

    @RequestMapping(path = "/findRepositoryList",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryList",desc = "通过条件查询制品库")
    @ApiParam(name = "repositoryQuery",desc = "repositoryQuery",required = true)
    public Result<List<Repository>> findRepositoryList(@RequestBody @Valid @NotNull RepositoryQuery repositoryQuery){
        List<Repository> repositoryList = repositoryService.findRepositoryList(repositoryQuery);

        return Result.ok(repositoryList);
    }

    @RequestMapping(path = "/findLocalAndRemoteRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findLocalAndRemoteRepository",desc = "通过条件查询本地库和远程库")
    @ApiParam(name = "type",desc = "库类型",required = true)
    public Result<List<Repository>> findLocalAndRemoteRepository(@NotNull String type){
        List<Repository> repositoryList = repositoryService.findLocalAndRemoteRepository(type);

        return Result.ok(repositoryList);
    }

    @RequestMapping(path = "/findRepositoryPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryPage",desc = "通过条件分页查询")
    @ApiParam(name = "repositoryQuery",desc = "repositoryQuery",required = true)
    public Result<Pagination<Repository>> findRepositoryPage(@RequestBody @Valid @NotNull RepositoryQuery repositoryQuery){
        Pagination<Repository> pagination = repositoryService.findRepositoryPage(repositoryQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findRepositoryByGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryByGroup",desc = "组合库关联的制品库集合")
    @ApiParam(name = "repositoryGroupId",desc = "组合库id",required = true)
    public Result<List<Repository>> findRepositoryByGroup( @NotNull String repositoryGroupId){
        List<Repository> repositoryList = repositoryService.findRepositoryByGroup(repositoryGroupId);

        return Result.ok(repositoryList);
    }
    @RequestMapping(path = "/findUnRelevanceRepository",method = RequestMethod.POST)
    @ApiMethod(name = "findUnRelevanceRepository",desc = "查询未关联组合库的本地和远程库list")
    @ApiParam(name = "repositoryType",desc = "repositoryType",required = true)
    public Result<List<Repository>> findUnRelevanceRepository( @NotNull String repositoryType,@NotNull String repositoryGroupId){
        List<Repository> repositoryList = repositoryService.findUnRelevanceRepository(repositoryType,repositoryGroupId);

        return Result.ok(repositoryList);
    }
}
