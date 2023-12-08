package io.thoughtware.hadess.repository.controller;

import io.thoughtware.hadess.repository.model.RepositoryMaven;
import io.thoughtware.hadess.repository.model.RepositoryMavenQuery;
import io.thoughtware.hadess.repository.service.RepositoryMavenService;
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
 * RepositoryMavenController
 */
@RestController
@RequestMapping("/repositoryMaven")
@Api(name = "RepositoryMavenController",desc = "maven 存储库管理")
public class RepositoryMavenController {

    private static Logger logger = LoggerFactory.getLogger(RepositoryMavenController.class);

    @Autowired
    private RepositoryMavenService repositoryMavenService;

    @RequestMapping(path="/createRepositoryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "createRepositoryMaven",desc = "创建maven 存储库")
    @ApiParam(name = "repositoryMaven",desc = "repositoryMaven",required = true)
    public Result<String> createRepositoryMaven(@RequestBody @NotNull @Valid RepositoryMaven repositoryMaven){
        String id = repositoryMavenService.createRepositoryMaven(repositoryMaven);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRepositoryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "updateRepositoryMaven",desc = "更新maven 存储库")
    @ApiParam(name = "repositoryMaven",desc = "repositoryMaven",required = true)
    public Result<Void> updateRepositoryMaven(@RequestBody @NotNull @Valid RepositoryMaven repositoryMaven){
        repositoryMavenService.updateRepositoryMaven(repositoryMaven);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRepositoryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRepositoryMaven",desc = "删除maven 存储库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRepositoryMaven(@NotNull String id){
        repositoryMavenService.deleteRepositoryMaven(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRepositoryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryMaven",desc = "通过id查询maven 存储库")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<RepositoryMaven> findRepositoryMaven(@NotNull String id){
        RepositoryMaven repositoryMaven = repositoryMavenService.findRepositoryMaven(id);

        return Result.ok(repositoryMaven);
    }

    @RequestMapping(path="/findAllRepositoryMaven",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepositoryMaven",desc = "查询所有maven 存储库")
    public Result<List<RepositoryMaven>> findAllRepositoryMaven(){
        List<RepositoryMaven> repositoryMavenList = repositoryMavenService.findAllRepositoryMaven();

        return Result.ok(repositoryMavenList);
    }

    @RequestMapping(path = "/findRepositoryMavenList",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryMavenList",desc = "条件查询maven 存储库")
    @ApiParam(name = "repositoryMavenQuery",desc = "repositoryMavenQuery",required = true)
    public Result<List<RepositoryMaven>> findRepositoryMavenList(@RequestBody @Valid @NotNull RepositoryMavenQuery repositoryMavenQuery){
        List<RepositoryMaven> repositoryMavenList = repositoryMavenService.findRepositoryMavenList(repositoryMavenQuery);

        return Result.ok(repositoryMavenList);
    }

    @RequestMapping(path = "/findRepositoryMavenPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryMavenPage",desc = "条件分页查询maven 存储库")
    @ApiParam(name = "repositoryMavenQuery",desc = "repositoryMavenQuery",required = true)
    public Result<Pagination<RepositoryMaven>> findRepositoryMavenPage(@RequestBody @Valid @NotNull RepositoryMavenQuery repositoryMavenQuery){
        Pagination<RepositoryMaven> pagination = repositoryMavenService.findRepositoryMavenPage(repositoryMavenQuery);

        return Result.ok(pagination);
    }

}
