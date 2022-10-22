package  net.tiklab.oms.tenant.controller;

import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.tenant.tenant.model.TenantDcs;
import  net.tiklab.tenant.tenant.model.TenantDcsQuery;
import  net.tiklab.tenant.tenant.service.TenantDcsService;
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
 * TenantDcsController
 */
@RestController
@RequestMapping("/tenantDcs")
@Api(name = "TenantDcsController",desc = "TenantDcsController")
public class TenantDcsController {

    private static Logger logger = LoggerFactory.getLogger(TenantDcsController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private TenantDcsService tenantDcsService;

    @RequestMapping(path="/createTenantDcs",method = RequestMethod.POST)
    @ApiMethod(name = "createTenantDcs",desc = "createTenantDcs")
    @ApiParam(name = "tenantDcs",desc = "tenantDcs",required = true)
    public Result<String> createTenantDcs(@RequestBody @NotNull @Valid TenantDcs tenantDcs){
        String id = tenantDcsService.createTenantDcs(tenantDcs);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateTenantDcs",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDcs",desc = "updateTenantDcs")
    @ApiParam(name = "tenantDcs",desc = "tenantDcs",required = true)
    public Result<Void> updateTenantDcs(@RequestBody @NotNull @Valid TenantDcs tenantDcs){
        tenantDcsService.updateTenantDcs(tenantDcs);

        return Result.ok();
    }

    @RequestMapping(path="/deleteTenantDcs",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenantDcs",desc = "deleteTenantDcs")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTenantDcs(@NotNull String id){
        tenantDcsService.deleteTenantDcs(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenantDcs",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDcs",desc = "findTenantDcs")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TenantDcs> findTenantDcs(@NotNull String id){
        TenantDcs tenantDcs = tenantDcsService.findTenantDcs(id);

        return Result.ok(tenantDcs);
    }

    @RequestMapping(path="/findAllTenantDcs",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenantDcs",desc = "findAllTenantDcs")
    public Result<List<TenantDcs>> findAllTenantDcs(){
        List<TenantDcs> tenantDcsList = tenantDcsService.findAllTenantDcs();

        return Result.ok(tenantDcsList);
    }

    @RequestMapping(path = "/findTenantDcsList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDcsList",desc = "findTenantDcsList")
    @ApiParam(name = "tenantDcsQuery",desc = "tenantDcsQuery",required = true)
    public Result<List<TenantDcs>> findTenantDcsList(@RequestBody @Valid @NotNull TenantDcsQuery tenantDcsQuery){
        List<TenantDcs> tenantDcsList = tenantDcsService.findTenantDcsList(tenantDcsQuery);

        return Result.ok(tenantDcsList);
    }

    @RequestMapping(path = "/findTenantDcsPage",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDcsPage",desc = "findTenantDcsPage")
    @ApiParam(name = "tenantDcsQuery",desc = "tenantDcsQuery",required = true)
    public Result<Pagination<TenantDcs>> findTenantDcsPage(@RequestBody @Valid @NotNull TenantDcsQuery tenantDcsQuery){
        Pagination<TenantDcs> pagination = tenantDcsService.findTenantDcsPage(tenantDcsQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findTenantDcsByCon",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDcsByCon",desc = "findTenantDcsByCon")
    @ApiParam(name = "tenantDcsQuery",desc = "oms使用 条件查询并把租户名字查询出来",required = true)
    public Result<List<TenantDcs>> findTenantDcsByCon(@RequestBody @Valid @NotNull TenantDcsQuery tenantDcsQuery){
        List<TenantDcs> tenantDfsList = tenantDcsService.findTenantDcsByCon(tenantDcsQuery);

        return Result.ok(tenantDfsList);
    }
}
