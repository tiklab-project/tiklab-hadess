package  net.tiklab.oms.tenant.controller;

import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.tenant.tenant.model.TenantDsGroup;
import  net.tiklab.tenant.tenant.model.TenantDsGroupQuery;
import  net.tiklab.tenant.tenant.service.TenantDsGroupService;
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
 * TenantDsGroupController
 */
@RestController
@RequestMapping("/tenantDsGroup")
@Api(name = "TenantDsGroupController",desc = "TenantDsGroupController")
public class TenantDsGroupController {

    private static Logger logger = LoggerFactory.getLogger(TenantDsGroupController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private TenantDsGroupService tenantDsGroupService;

    @RequestMapping(path="/createTenantDsGroup",method = RequestMethod.POST)
    @ApiMethod(name = "createTenantDsGroup",desc = "createTenantDsGroup")
    @ApiParam(name = "tenantDsGroup",desc = "tenantDsGroup",required = true)
    public Result<String> createTenantDsGroup(@RequestBody @NotNull @Valid TenantDsGroup tenantDsGroup){
        String id = tenantDsGroupService.createTenantDsGroup(tenantDsGroup);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateTenantDsGroup",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDsGroup",desc = "updateTenantDsGroup")
    @ApiParam(name = "tenantDsGroup",desc = "tenantDsGroup",required = true)
    public Result<Void> updateTenantDsGroup(@RequestBody @NotNull @Valid TenantDsGroup tenantDsGroup){
        tenantDsGroupService.updateTenantDsGroup(tenantDsGroup);

        return Result.ok();
    }

    @RequestMapping(path="/deleteTenantDsGroup",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenantDsGroup",desc = "deleteTenantDsGroup")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTenantDsGroup(@NotNull String id){
        tenantDsGroupService.deleteTenantDsGroup(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenantDsGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDsGroup",desc = "findTenantDsGroup")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TenantDsGroup> findTenantDsGroup(@NotNull String id){
        TenantDsGroup tenantDsGroup = tenantDsGroupService.findTenantDsGroup(id);

        return Result.ok(tenantDsGroup);
    }

    @RequestMapping(path="/findAllTenantDsGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenantDsGroup",desc = "findAllTenantDsGroup")
    public Result<List<TenantDsGroup>> findAllTenantDsGroup(){
        List<TenantDsGroup> tenantDsGroupList = tenantDsGroupService.findAllTenantDsGroup();

        return Result.ok(tenantDsGroupList);
    }

    @RequestMapping(path = "/findTenantDsGroupList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDsGroupList",desc = "findTenantDsGroupList")
    @ApiParam(name = "tenantDsGroupQuery",desc = "tenantDsGroupQuery",required = true)
    public Result<List<TenantDsGroup>> findTenantDsGroupList(@RequestBody @Valid @NotNull TenantDsGroupQuery tenantDsGroupQuery){
        List<TenantDsGroup> tenantDsGroupList = tenantDsGroupService.findTenantDsGroupList(tenantDsGroupQuery);

        return Result.ok(tenantDsGroupList);
    }

    @RequestMapping(path = "/findTenantDsGroupPage",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDsGroupPage",desc = "findTenantDsGroupPage")
    @ApiParam(name = "tenantDsGroupQuery",desc = "tenantDsGroupQuery",required = true)
    public Result<Pagination<TenantDsGroup>> findTenantDsGroupPage(@RequestBody @Valid @NotNull TenantDsGroupQuery tenantDsGroupQuery){
        Pagination<TenantDsGroup> pagination = tenantDsGroupService.findTenantDsGroupPage(tenantDsGroupQuery);

        return Result.ok(pagination);
    }

}
