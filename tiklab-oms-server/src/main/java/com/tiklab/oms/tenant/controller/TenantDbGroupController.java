package com.tiklab.oms.tenant.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.rpc.annotation.Reference;
import com.tiklab.tenant.tenant.model.TenantDbGroup;
import com.tiklab.tenant.tenant.model.TenantDbGroupQuery;
import com.tiklab.tenant.tenant.service.TenantDbGroupService;
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
 * TenantDbGroupController
 */
@RestController
@RequestMapping("/tenantDbGroup")
@Api(name = "TenantDbGroupController",desc = "租户的db数据源管理")
public class TenantDbGroupController {

    private static Logger logger = LoggerFactory.getLogger(TenantDbGroupController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private TenantDbGroupService tenantDbGroupService;

    @RequestMapping(path="/createTenantDbGroup",method = RequestMethod.POST)
    @ApiMethod(name = "createTenantDbGroup",desc = "createTenantDbGroup")
    @ApiParam(name = "tenantDbGroup",desc = "tenantDbGroup",required = true)
    public Result<String> createTenantDbGroup(@RequestBody @NotNull @Valid TenantDbGroup tenantDbGroup){
        String id = tenantDbGroupService.createTenantDbGroup(tenantDbGroup);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateTenantDbGroup",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDbGroup",desc = "updateTenantDbGroup")
    @ApiParam(name = "tenantDbGroup",desc = "tenantDbGroup",required = true)
    public Result<Void> updateTenantDbGroup(@RequestBody @NotNull @Valid TenantDbGroup tenantDbGroup){
        tenantDbGroupService.updateTenantDbGroup(tenantDbGroup);

        return Result.ok();
    }

    @RequestMapping(path="/deleteTenantDbGroup",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenantDbGroup",desc = "deleteTenantDbGroup")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTenantDbGroup(@NotNull String id){
        tenantDbGroupService.deleteTenantDbGroup(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenantDbGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDbGroup",desc = "findTenantDbGroup")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TenantDbGroup> findTenantDbGroup(@NotNull String id){
        TenantDbGroup tenantDbGroup = tenantDbGroupService.findTenantDbGroup(id);

        return Result.ok(tenantDbGroup);
    }

    @RequestMapping(path="/findAllTenantDbGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenantDbGroup",desc = "findAllTenantDbGroup")
    public Result<List<TenantDbGroup>> findAllTenantDbGroup(){
        List<TenantDbGroup> tenantDbGroupList = tenantDbGroupService.findAllTenantDbGroup();

        return Result.ok(tenantDbGroupList);
    }

    @RequestMapping(path = "/findTenantDbGroupList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDbGroupList",desc = "findTenantDbGroupList")
    @ApiParam(name = "tenantDbGroupQuery",desc = "tenantDbGroupQuery",required = true)
    public Result<List<TenantDbGroup>> findTenantDbGroupList(@RequestBody @Valid @NotNull TenantDbGroupQuery tenantDbGroupQuery){
        List<TenantDbGroup> tenantDbGroupList = tenantDbGroupService.findTenantDbGroupList(tenantDbGroupQuery);

        return Result.ok(tenantDbGroupList);
    }

    @RequestMapping(path = "/findTenantDbGroupPage",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDbGroupPage",desc = "findTenantDbGroupPage")
    @ApiParam(name = "tenantDbGroupQuery",desc = "tenantDbGroupQuery",required = true)
    public Result<Pagination<TenantDbGroup>> findTenantDbGroupPage(@RequestBody @Valid @NotNull TenantDbGroupQuery tenantDbGroupQuery){
        Pagination<TenantDbGroup> pagination = tenantDbGroupService.findTenantDbGroupPage(tenantDbGroupQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path="/updateAtPresent",method = RequestMethod.POST)
    @ApiMethod(name = "updateAtPresent",desc = "修改数据源为当前创建租户生效的数据源")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> updateAtPresent(@NotNull String id){
        tenantDbGroupService.updateAtPresent(id);

        return Result.ok();
    }

}
