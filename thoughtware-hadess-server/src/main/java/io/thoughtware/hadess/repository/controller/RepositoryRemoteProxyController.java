package io.thoughtware.hadess.repository.controller;

import io.thoughtware.hadess.repository.model.RepositoryRemoteProxy;
import io.thoughtware.hadess.repository.model.RepositoryRemoteProxyQuery;
import io.thoughtware.hadess.repository.service.RepositoryRemoteProxyService;
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
 * RepositoryRemoteProxyController
 */
@RestController
@RequestMapping("/repositoryRemoteProxy")
@Api(name = "RepositoryRemoteProxyController",desc = "制品远程库代理信息")
public class RepositoryRemoteProxyController {

    private static Logger logger = LoggerFactory.getLogger(RepositoryRemoteProxyController.class);

    @Autowired
    private RepositoryRemoteProxyService repositoryRemoteProxyService;

    @RequestMapping(path="/createRepositoryRemoteProxy",method = RequestMethod.POST)
    @ApiMethod(name = "createRepositoryRemoteProxy",desc = "创建远程代理信息")
    @ApiParam(name = "repositoryRemoteProxy",desc = "repositoryRemoteProxy",required = true)
    public Result<String> createRepositoryRemoteProxy(@RequestBody @NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy){
        String id = repositoryRemoteProxyService.createRepositoryRemoteProxy(repositoryRemoteProxy);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRepositoryRemoteProxy",method = RequestMethod.POST)
    @ApiMethod(name = "updateRepositoryRemoteProxy",desc = "更新代理信息")
    @ApiParam(name = "repositoryRemoteProxy",desc = "repositoryRemoteProxy",required = true)
    public Result<Void> updateRepositoryRemoteProxy(@RequestBody @NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy){
        repositoryRemoteProxyService.updateRepositoryRemoteProxy(repositoryRemoteProxy);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRepositoryRemoteProxy",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRepositoryRemoteProxy",desc = "删除代理信息")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRepositoryRemoteProxy(@NotNull String id){
        repositoryRemoteProxyService.deleteRepositoryRemoteProxy(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRepositoryRemoteProxy",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryRemoteProxy",desc = "通过id查询代理信息")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<RepositoryRemoteProxy> findRepositoryRemoteProxy(@NotNull String id){
        RepositoryRemoteProxy repositoryRemoteProxy = repositoryRemoteProxyService.findRepositoryRemoteProxy(id);

        return Result.ok(repositoryRemoteProxy);
    }

    @RequestMapping(path="/findAllRepositoryRemoteProxy",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRepositoryRemoteProxy",desc = "查询所有代理信息")
    public Result<List<RepositoryRemoteProxy>> findAllRepositoryRemoteProxy(){
        List<RepositoryRemoteProxy> repositoryRemoteProxyList = repositoryRemoteProxyService.findAllRepositoryRemoteProxy();

        return Result.ok(repositoryRemoteProxyList);
    }

    @RequestMapping(path = "/findRepositoryRemoteProxyList",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryRemoteProxyList",desc = "条件查询代理信息")
    @ApiParam(name = "repositoryRemoteProxyQuery",desc = "repositoryRemoteProxyQuery",required = true)
    public Result<List<RepositoryRemoteProxy>> findRepositoryRemoteProxyList(@RequestBody @Valid @NotNull RepositoryRemoteProxyQuery repositoryRemoteProxyQuery){
        List<RepositoryRemoteProxy> repositoryRemoteProxyList = repositoryRemoteProxyService.findRepositoryRemoteProxyList(repositoryRemoteProxyQuery);

        return Result.ok(repositoryRemoteProxyList);
    }

    @RequestMapping(path = "/findRepositoryRemoteProxyPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRepositoryRemoteProxyPage",desc = "条件分页查询代理信息")
    @ApiParam(name = "repositoryRemoteProxyQuery",desc = "repositoryRemoteProxyQuery",required = true)
    public Result<Pagination<RepositoryRemoteProxy>> findRepositoryRemoteProxyPage(@RequestBody @Valid @NotNull RepositoryRemoteProxyQuery repositoryRemoteProxyQuery){
        Pagination<RepositoryRemoteProxy> pagination = repositoryRemoteProxyService.findRepositoryRemoteProxyPage(repositoryRemoteProxyQuery);

        return Result.ok(pagination);
    }


}
