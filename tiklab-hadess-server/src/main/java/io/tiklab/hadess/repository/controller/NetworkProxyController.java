package io.tiklab.hadess.repository.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.repository.model.NetworkProxy;
import io.tiklab.hadess.repository.model.NetworkProxyQuery;
import io.tiklab.hadess.repository.service.NetworkProxyService;
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
 * NetworkProxyController
 */
@RestController
@RequestMapping("/networkProxy")
//@Api(name = "NetworkProxyController",desc = "网络代理地址管理")
public class NetworkProxyController {

    private static Logger logger = LoggerFactory.getLogger(NetworkProxyController.class);

    @Autowired
    private NetworkProxyService networkProxyService;

    @RequestMapping(path="/createNetworkProxy",method = RequestMethod.POST)
    @ApiMethod(name = "createNetworkProxy",desc = "创建网络代理地址")
    @ApiParam(name = "networkProxy",desc = "networkProxy",required = true)
    public Result<String> createNetworkProxy(@RequestBody @NotNull @Valid NetworkProxy networkProxy){
        String id = networkProxyService.createNetworkProxy(networkProxy);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateNetworkProxy",method = RequestMethod.POST)
    @ApiMethod(name = "updateNetworkProxy",desc = "更新网络代理地址")
    @ApiParam(name = "networkProxy",desc = "networkProxy",required = true)
    public Result<Void> updateNetworkProxy(@RequestBody @NotNull @Valid NetworkProxy networkProxy){
        networkProxyService.updateNetworkProxy(networkProxy);

        return Result.ok();
    }

    @RequestMapping(path="/deleteNetworkProxy",method = RequestMethod.POST)
    @ApiMethod(name = "deleteNetworkProxy",desc = "删除网络代理地址")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteNetworkProxy(@NotNull String id){
        networkProxyService.deleteNetworkProxy(id);

        return Result.ok();
    }

    @RequestMapping(path="/findNetworkProxy",method = RequestMethod.POST)
    @ApiMethod(name = "findNetworkProxy",desc = "通过id查询网络代理地址")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<NetworkProxy> findNetworkProxy(@NotNull String id){
        NetworkProxy networkProxy = networkProxyService.findNetworkProxy(id);

        return Result.ok(networkProxy);
    }

    @RequestMapping(path="/findAllNetworkProxy",method = RequestMethod.POST)
    @ApiMethod(name = "findAllNetworkProxy",desc = "查询所有网络代理地址")
    public Result<List<NetworkProxy>> findAllNetworkProxy(){
        List<NetworkProxy> networkProxyList = networkProxyService.findAllNetworkProxy();

        return Result.ok(networkProxyList);
    }

    @RequestMapping(path = "/findNetworkProxyList",method = RequestMethod.POST)
    @ApiMethod(name = "findNetworkProxyList",desc = "条件查询网络代理地址")
    @ApiParam(name = "networkProxyQuery",desc = "networkProxyQuery",required = true)
    public Result<List<NetworkProxy>> findNetworkProxyList(@RequestBody @Valid @NotNull NetworkProxyQuery networkProxyQuery){
        List<NetworkProxy> networkProxyList = networkProxyService.findNetworkProxyList(networkProxyQuery);

        return Result.ok(networkProxyList);
    }

    @RequestMapping(path = "/findNetworkProxyPage",method = RequestMethod.POST)
    @ApiMethod(name = "findNetworkProxyPage",desc = "条件分页查询网络代理地址")
    @ApiParam(name = "networkProxyQuery",desc = "networkProxyQuery",required = true)
    public Result<Pagination<NetworkProxy>> findNetworkProxyPage(@RequestBody @Valid @NotNull NetworkProxyQuery networkProxyQuery){
        Pagination<NetworkProxy> pagination = networkProxyService.findNetworkProxyPage(networkProxyQuery);

        return Result.ok(pagination);
    }

}
