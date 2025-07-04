package io.tiklab.hadess.repository.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.repository.model.RemoteProxy;
import io.tiklab.hadess.repository.model.RemoteProxyQuery;
import io.tiklab.hadess.repository.service.RemoteProxyService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
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
 * RemoteProxyController
 */
@RestController
@RequestMapping("/remoteProxy")
@Api(name = "代理地址管理",desc = "代理地址管理")
public class RemoteProxyController {

    private static Logger logger = LoggerFactory.getLogger(RemoteProxyController.class);

    @Autowired
    private RemoteProxyService remoteProxyService;

    @RequestMapping(path="/createRemoteProxy",method = RequestMethod.POST)
    //@ApiMethod(name = "createRemoteProxy",desc = "创建代理地址")
    public Result<String> createRemoteProxy(@RequestBody @NotNull @Valid RemoteProxy remoteProxy){
        String id = remoteProxyService.createRemoteProxy(remoteProxy);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRemoteProxy",method = RequestMethod.POST)
    //@ApiMethod(name = "updateRemoteProxy",desc = "更新代理地址")
    public Result<Void> updateRemoteProxy(@RequestBody @NotNull @Valid RemoteProxy remoteProxy){
        remoteProxyService.updateRemoteProxy(remoteProxy);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRemoteProxy",method = RequestMethod.POST)
    public Result<Void> deleteRemoteProxy(@NotNull String id){
        remoteProxyService.deleteRemoteProxy(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRemoteProxy",method = RequestMethod.POST)
    public Result<RemoteProxy> findRemoteProxy(@NotNull String id){
        RemoteProxy remoteProxy = remoteProxyService.findRemoteProxy(id);

        return Result.ok(remoteProxy);
    }

    @RequestMapping(path="/findAllRemoteProxy",method = RequestMethod.POST)
    public Result<List<RemoteProxy>> findAllRemoteProxy(){
        List<RemoteProxy> remoteProxyList = remoteProxyService.findAllRemoteProxy();

        return Result.ok(remoteProxyList);
    }

    @RequestMapping(path = "/findRemoteProxyList",method = RequestMethod.POST)
    @ApiMethod(name = "条件查询代理地址",desc = "条件查询代理地址")
    @ApiParam(name = "remoteProxyQuery",desc = "remoteProxyQuery",required = true)
    public Result<List<RemoteProxy>> findRemoteProxyList(@RequestBody @Valid @NotNull RemoteProxyQuery remoteProxyQuery){
        List<RemoteProxy> remoteProxyList = remoteProxyService.findRemoteProxyList(remoteProxyQuery);

        return Result.ok(remoteProxyList);
    }

    @RequestMapping(path = "/findRemoteProxyPage",method = RequestMethod.POST)
    @ApiMethod(name = "条件分页查询代理地址",desc = "条件分页查询代理地址")
    @ApiParam(name = "remoteProxyQuery",desc = "remoteProxyQuery",required = true)
    public Result<Pagination<RemoteProxy>> findRemoteProxyPage(@RequestBody @Valid @NotNull RemoteProxyQuery remoteProxyQuery){
        Pagination<RemoteProxy> pagination = remoteProxyService.findRemoteProxyPage(remoteProxyQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findProxyListByRpyId",method = RequestMethod.POST)
    @ApiMethod(name = "通过仓库id查询代理",desc = "通过仓库id查询代理")
    @ApiParam(name = "repositoryId",desc = "仓库id",required = true)
    public Result<List<RemoteProxy>> findProxyListByRpyId(@NotNull String repositoryId){
        List<RemoteProxy> remoteProxyList = remoteProxyService.findProxyListByRpyId(repositoryId);

        return Result.ok(remoteProxyList);
    }

    @RequestMapping(path = "/findRepositoryByProxyId",method = RequestMethod.POST)
    //@ApiMethod(name = "findRepositoryByProxyId",desc = "通过代理地址的id 查询是关联的仓库")
    public Result<List<String>> findRepositoryByProxyId(@NotNull String id){
        List<String> repositoryName = remoteProxyService.findRepositoryByProxyId(id);

        return Result.ok(repositoryName);
    }
}
