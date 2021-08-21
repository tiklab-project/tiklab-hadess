package com.doublekit.oms.tenant.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.tenant.tenant.model.Tenant;
import com.doublekit.tenant.tenant.model.TenantQuery;
import com.doublekit.tenant.tenant.service.TenantService;
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
 * ManagerController
 * Created by Zhangzhihua on 2017/9/25.
 */
@RestController
@RequestMapping("/tenant")
@Api(name = "TenantController",desc = "租户管理")
public class TenantController {

    private static Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    private TenantService tenantService;

    @RequestMapping(path="/updateTenant",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenant",desc = "更新租户")
    @ApiParam(name = "tenant",desc = "租户DTO",required = true)
    public Result<Void> updateTenant(@RequestBody @NotNull @Valid Tenant tenant){
        tenantService.updateTenant(tenant);

        return Result.ok();
    }

    @RequestMapping(path="/deleteTenant",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenant",desc = "根据租户ID删除租户")
    @ApiParam(name = "id",desc = "租户ID",required = true)
    public Result<Void> deleteTenant(@NotNull String id){
        tenantService.deleteTenant(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenant",method = RequestMethod.POST)
    @ApiMethod(name = "findTenant",desc = "根据租户ID查找租户")
    @ApiParam(name = "id",desc = "租户ID",required = true)
    public Result<Tenant> findTenant(@NotNull String id){
        Tenant tenant = tenantService.findTenant(id);

        return Result.ok(tenant);
    }

    @RequestMapping(path="/findAllTenant",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenant",desc = "查找所有租户")
    public Result<List<Tenant>> findAllTenant(){
        List<Tenant> tenantList = tenantService.findAllTenant();

        return Result.ok(tenantList);
    }

    @RequestMapping(path = "/findTenantList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantList",desc = "根据查询对象查询租户列表")
    @ApiParam(name = "tenantQuery",desc = "租户查询对象",required = true)
    public Result<List<Tenant>> findTenantList(@RequestBody @Valid @NotNull TenantQuery tenantQuery){
        List<Tenant> tenantList = tenantService.findTenantList(tenantQuery);

        return Result.ok(tenantList);
    }
}
