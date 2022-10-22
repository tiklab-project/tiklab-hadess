package  net.tiklab.oms.tenant.controller;

import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.tenant.tenant.model.TenantDfs;
import  net.tiklab.tenant.tenant.model.TenantDfsQuery;
import  net.tiklab.tenant.tenant.service.TenantDfsService;
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
 * TenantDfsController
 */
@RestController
@RequestMapping("/tenantDfs")
@Api(name = "TenantDfsController",desc = "TenantDfsController")
public class TenantDfsController {

    private static Logger logger = LoggerFactory.getLogger(TenantDfsController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private TenantDfsService tenantDfsService;

    @RequestMapping(path="/createTenantDfs",method = RequestMethod.POST)
    @ApiMethod(name = "createTenantDfs",desc = "createTenantDfs")
    @ApiParam(name = "tenantDfs",desc = "tenantDfs",required = true)
    public Result<String> createTenantDfs(@RequestBody @NotNull @Valid TenantDfs tenantDfs){
        String id = tenantDfsService.createTenantDfs(tenantDfs);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateTenantDfs",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDfs",desc = "updateTenantDfs")
    @ApiParam(name = "tenantDfs",desc = "tenantDfs",required = true)
    public Result<Void> updateTenantDfs(@RequestBody @NotNull @Valid TenantDfs tenantDfs){
        tenantDfsService.updateTenantDfs(tenantDfs);

        return Result.ok();
    }

    @RequestMapping(path="/deleteTenantDfs",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenantDfs",desc = "deleteTenantDfs")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTenantDfs(@NotNull String id){
        tenantDfsService.deleteTenantDfs(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenantDfs",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDfs",desc = "findTenantDfs")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TenantDfs> findTenantDfs(@NotNull String id){
        TenantDfs tenantDfs = tenantDfsService.findTenantDfs(id);

        return Result.ok(tenantDfs);
    }

    @RequestMapping(path="/findAllTenantDfs",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenantDfs",desc = "findAllTenantDfs")
    public Result<List<TenantDfs>> findAllTenantDfs(){
        List<TenantDfs> tenantDfsList = tenantDfsService.findAllTenantDfs();

        return Result.ok(tenantDfsList);
    }

    @RequestMapping(path = "/findTenantDfsList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDfsList",desc = "findTenantDfsList")
    @ApiParam(name = "tenantDfsQuery",desc = "tenantDfsQuery",required = true)
    public Result<List<TenantDfs>> findTenantDfsList(@RequestBody @Valid @NotNull TenantDfsQuery tenantDfsQuery){
        List<TenantDfs> tenantDfsList = tenantDfsService.findTenantDfsList(tenantDfsQuery);

        return Result.ok(tenantDfsList);
    }

    @RequestMapping(path = "/findTenantDfsPage",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDfsPage",desc = "findTenantDfsPage")
    @ApiParam(name = "tenantDfsQuery",desc = "tenantDfsQuery",required = true)
    public Result<Pagination<TenantDfs>> findTenantDfsPage(@RequestBody @Valid @NotNull TenantDfsQuery tenantDfsQuery){
        Pagination<TenantDfs> pagination = tenantDfsService.findTenantDfsPage(tenantDfsQuery);

        return Result.ok(pagination);
    }


    @RequestMapping(path = "/findTenantDfsByCon",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDfsByCon",desc = "findTenantDfsList")
    @ApiParam(name = "tenantDfsQuery",desc = "oms使用 条件查询并把租户名字查询出来",required = true)
    public Result<List<TenantDfs>> findTenantDfsByCon(@RequestBody @Valid @NotNull TenantDfsQuery tenantDfsQuery){
        List<TenantDfs> tenantDfsList = tenantDfsService.findTenantDfsByCon(tenantDfsQuery);

        return Result.ok(tenantDfsList);
    }
}
