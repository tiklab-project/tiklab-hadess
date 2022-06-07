package com.doublekit.oms.tenant.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.rpc.annotation.Reference;
import com.doublekit.tenant.tenant.model.TenantDssGroup;
import com.doublekit.tenant.tenant.model.TenantDssGroupQuery;
import com.doublekit.tenant.tenant.service.TenantDssGroupService;
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
 * TenantDssGroupController
 */
@RestController
@RequestMapping("/tenantDssGroup")
@Api(name = "TenantDssGroupController",desc = "租户的dss源管理")
public class TenantDssGroupController {

    private static Logger logger = LoggerFactory.getLogger(TenantDssGroupController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private TenantDssGroupService tenantDssGroupService;

    @RequestMapping(path="/createTenantDssGroup",method = RequestMethod.POST)
    @ApiMethod(name = "createTenantDssGroup",desc = "createTenantDssGroup")
    @ApiParam(name = "tenantDssGroup",desc = "tenantDssGroup",required = true)
    public Result<String> createTenantDssGroup(@RequestBody @NotNull @Valid TenantDssGroup tenantDssGroup){
        String id = tenantDssGroupService.createTenantDssGroup(tenantDssGroup);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateTenantDssGroup",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDssGroup",desc = "updateTenantDssGroup")
    @ApiParam(name = "tenantDssGroup",desc = "tenantDssGroup",required = true)
    public Result<Void> updateTenantDssGroup(@RequestBody @NotNull @Valid TenantDssGroup tenantDssGroup){
        tenantDssGroupService.updateTenantDssGroup(tenantDssGroup);

        return Result.ok();
    }

    @RequestMapping(path="/deleteTenantDssGroup",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenantDssGroup",desc = "deleteTenantDssGroup")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTenantDssGroup(@NotNull String id){
        tenantDssGroupService.deleteTenantDssGroup(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenantDssGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDssGroup",desc = "findTenantDssGroup")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TenantDssGroup> findTenantDssGroup(@NotNull String id){
        TenantDssGroup tenantDssGroup = tenantDssGroupService.findTenantDssGroup(id);

        return Result.ok(tenantDssGroup);
    }

    @RequestMapping(path="/findAllTenantDssGroup",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenantDssGroup",desc = "findAllTenantDssGroup")
    public Result<List<TenantDssGroup>> findAllTenantDssGroup(){
        List<TenantDssGroup> tenantDssGroupList = tenantDssGroupService.findAllTenantDssGroup();

        return Result.ok(tenantDssGroupList);
    }

    @RequestMapping(path = "/findTenantDssGroupList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDssGroupList",desc = "findTenantDssGroupList")
    @ApiParam(name = "tenantDssGroupQuery",desc = "tenantDssGroupQuery",required = true)
    public Result<List<TenantDssGroup>> findTenantDssGroupList(@RequestBody @Valid @NotNull TenantDssGroupQuery tenantDssGroupQuery){
        List<TenantDssGroup> tenantDssGroupList = tenantDssGroupService.findTenantDssGroupList(tenantDssGroupQuery);

        return Result.ok(tenantDssGroupList);
    }

    @RequestMapping(path = "/findTenantDssGroupPage",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDssGroupPage",desc = "findTenantDssGroupPage")
    @ApiParam(name = "tenantDssGroupQuery",desc = "tenantDssGroupQuery",required = true)
    public Result<Pagination<TenantDssGroup>> findTenantDssGroupPage(@RequestBody @Valid @NotNull TenantDssGroupQuery tenantDssGroupQuery){
        Pagination<TenantDssGroup> pagination = tenantDssGroupService.findTenantDssGroupPage(tenantDssGroupQuery);

        return Result.ok(pagination);
    }




}
