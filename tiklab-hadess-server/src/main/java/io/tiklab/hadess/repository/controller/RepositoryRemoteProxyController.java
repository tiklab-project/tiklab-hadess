package io.tiklab.hadess.repository.controller;

import io.tiklab.hadess.repository.model.RepositoryRemoteProxy;
import io.tiklab.hadess.repository.model.RepositoryRemoteProxyQuery;
import io.tiklab.hadess.repository.service.RepositoryRemoteProxyService;
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
 * RepositoryRemoteProxyController
 */
@RestController
@RequestMapping("/repositoryRemoteProxy")
@Api(name = "制品远程库代理信息",desc = "制品远程库代理信息")
public class RepositoryRemoteProxyController {

    private static Logger logger = LoggerFactory.getLogger(RepositoryRemoteProxyController.class);

    @Autowired
    private RepositoryRemoteProxyService repositoryRemoteProxyService;

    @RequestMapping(path="/createRepositoryRemoteProxy",method = RequestMethod.POST)
    public Result<String> createRepositoryRemoteProxy(@RequestBody @NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy){
        String id = repositoryRemoteProxyService.createRepositoryRemoteProxy(repositoryRemoteProxy);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRepositoryRemoteProxy",method = RequestMethod.POST)
    public Result<Void> updateRepositoryRemoteProxy(@RequestBody @NotNull @Valid RepositoryRemoteProxy repositoryRemoteProxy){
        repositoryRemoteProxyService.updateRepositoryRemoteProxy(repositoryRemoteProxy);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRepositoryRemoteProxy",method = RequestMethod.POST)
    public Result<Void> deleteRepositoryRemoteProxy(@NotNull String id){
        repositoryRemoteProxyService.deleteRepositoryRemoteProxy(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRepositoryRemoteProxy",method = RequestMethod.POST)
    //@ApiMethod(name = "findRepositoryRemoteProxy",desc = "通过id查询代理信息")
    public Result<RepositoryRemoteProxy> findRepositoryRemoteProxy(@NotNull String id){
        RepositoryRemoteProxy repositoryRemoteProxy = repositoryRemoteProxyService.findRepositoryRemoteProxy(id);

        return Result.ok(repositoryRemoteProxy);
    }

    @RequestMapping(path="/findAllRepositoryRemoteProxy",method = RequestMethod.POST)
    //@ApiMethod(name = "findAllRepositoryRemoteProxy",desc = "查询所有代理信息")
    public Result<List<RepositoryRemoteProxy>> findAllRepositoryRemoteProxy(){
        List<RepositoryRemoteProxy> repositoryRemoteProxyList = repositoryRemoteProxyService.findAllRepositoryRemoteProxy();

        return Result.ok(repositoryRemoteProxyList);
    }

    @RequestMapping(path = "/findRepositoryRemoteProxyList",method = RequestMethod.POST)
    @ApiMethod(name = "条件查询代理信息",desc = "条件查询代理信息")
    @ApiParam(name = "repositoryRemoteProxyQuery",desc = "repositoryRemoteProxyQuery",required = true)
    public Result<List<RepositoryRemoteProxy>> findRepositoryRemoteProxyList(@RequestBody @Valid @NotNull RepositoryRemoteProxyQuery repositoryRemoteProxyQuery){
        List<RepositoryRemoteProxy> repositoryRemoteProxyList = repositoryRemoteProxyService.findRepositoryRemoteProxyList(repositoryRemoteProxyQuery);

        return Result.ok(repositoryRemoteProxyList);
    }

    @RequestMapping(path = "/findRepositoryRemoteProxyPage",method = RequestMethod.POST)
    public Result<Pagination<RepositoryRemoteProxy>> findRepositoryRemoteProxyPage(@RequestBody @Valid @NotNull RepositoryRemoteProxyQuery repositoryRemoteProxyQuery){
        Pagination<RepositoryRemoteProxy> pagination = repositoryRemoteProxyService.findRepositoryRemoteProxyPage(repositoryRemoteProxyQuery);

        return Result.ok(pagination);
    }


}
