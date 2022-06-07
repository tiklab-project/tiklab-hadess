package com.doublekit.oms.tenant.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.rpc.annotation.Reference;
import com.doublekit.tenant.tenant.model.TenantDatabase;
import com.doublekit.tenant.tenant.model.TenantDatabaseQuery;
import com.doublekit.tenant.tenant.service.TenantDatabaseService;
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
 * TenantDatabaseController
 */
@RestController
@RequestMapping("/tenantDatabase")
@Api(name = "TenantDatabaseController",desc = "TenantDatabaseController")
public class TenantDatabaseController {

    private static Logger logger = LoggerFactory.getLogger(TenantDatabaseController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private TenantDatabaseService tenantDatabaseService;

    @RequestMapping(path="/createTenantDatabase",method = RequestMethod.POST)
    @ApiMethod(name = "createTenantDatabase",desc = "createTenantDatabase")
    @ApiParam(name = "tenantDatabase",desc = "tenantDatabase",required = true)
    public Result<String> createTenantDatabase(@RequestBody @NotNull @Valid TenantDatabase tenantDatabase){
        String id = tenantDatabaseService.createTenantDatabase(tenantDatabase);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateTenantDatabase",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDatabase",desc = "updateTenantDatabase")
    @ApiParam(name = "tenantDatabase",desc = "tenantDatabase",required = true)
    public Result<Void> updateTenantDatabase(@RequestBody @NotNull @Valid TenantDatabase tenantDatabase){
        tenantDatabaseService.updateTenantDatabase(tenantDatabase);

        return Result.ok();
    }

    @RequestMapping(path="/updateTenantDatabaseByIds",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDatabaseByIds",desc = "通过id 批量修改租户db数据源")
    @ApiParam(name = "ids",desc = "ids",required = true)
    public Result<Void> updateTenantDatabaseByIds(String ids,String dbId){
        tenantDatabaseService.updateTenantDatabaseByIds(ids,dbId);

        return Result.ok();
    }


    @RequestMapping(path="/deleteTenantDatabase",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenantDatabase",desc = "deleteTenantDatabase")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTenantDatabase(@NotNull String id){
        tenantDatabaseService.deleteTenantDatabase(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenantDatabase",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDatabase",desc = "findTenantDatabase")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TenantDatabase> findTenantDatabase(@NotNull String id){
        TenantDatabase tenantDatabase = tenantDatabaseService.findTenantDatabase(id);

        return Result.ok(tenantDatabase);
    }

    @RequestMapping(path="/findAllTenantDatabase",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenantDatabase",desc = "findAllTenantDatabase")
    public Result<List<TenantDatabase>> findAllTenantDatabase(){
        List<TenantDatabase> tenantDatabaseList = tenantDatabaseService.findAllTenantDatabase();

        return Result.ok(tenantDatabaseList);
    }

    @RequestMapping(path = "/findTenantDatabaseList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDatabaseList",desc = "findTenantDatabaseList")
    @ApiParam(name = "tenantDatabaseQuery",desc = "tenantDatabaseQuery",required = true)
    public Result<List<TenantDatabase>> findTenantDatabaseList(@RequestBody @Valid @NotNull TenantDatabaseQuery tenantDatabaseQuery){
        List<TenantDatabase> tenantDatabaseList = tenantDatabaseService.findTenantDatabaseList(tenantDatabaseQuery);

        return Result.ok(tenantDatabaseList);
    }

    @RequestMapping(path = "/findTenantDatabasePage",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDatabasePage",desc = "findTenantDatabasePage")
    @ApiParam(name = "tenantDatabaseQuery",desc = "tenantDatabaseQuery",required = true)
    public Result<Pagination<TenantDatabase>> findTenantDatabasePage(@RequestBody @Valid @NotNull TenantDatabaseQuery tenantDatabaseQuery){
        Pagination<TenantDatabase> pagination = tenantDatabaseService.findTenantDatabasePage(tenantDatabaseQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findTenantDatabaseByDb",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDatabaseByDb",desc = "findTenantList")
    @ApiParam(name = "tenantDatabaseQuery",desc = "oms使用 条件查询并把租户名字查询出来",required = true)
    public Result<List<TenantDatabase>> findTenantDatabaseByDb(@RequestBody @Valid @NotNull TenantDatabaseQuery tenantDatabaseQuery){
        List<TenantDatabase> tenantDatabaseList = tenantDatabaseService.findTenantDatabaseByDb(tenantDatabaseQuery);

        return Result.ok(tenantDatabaseList);
    }

    @RequestMapping(path = "/findTenantDatabaseT",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDatabaseT",desc = "findTenantDatabaseT")
    @ApiParam(name = "tenantId",desc = "oms使用 条件查询并把租户名字查询出来",required = true)
    public Result<TenantDatabase> findTenantDatabaseT(String tenantId, String type){
        TenantDatabase tenantDatabase = tenantDatabaseService.findTenantDatabase(tenantId, type);

        return Result.ok(tenantDatabase);
    }

}
