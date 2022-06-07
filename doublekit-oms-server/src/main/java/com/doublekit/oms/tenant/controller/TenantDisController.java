package com.doublekit.oms.tenant.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.rpc.annotation.Reference;
import com.doublekit.tenant.tenant.model.TenantDis;
import com.doublekit.tenant.tenant.model.TenantDisQuery;
import com.doublekit.tenant.tenant.service.TenantDisService;
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
 * TenantDssController
 */
@RestController
@RequestMapping("/tenantDss")
@Api(name = "TenantDssController",desc = "租户dss数据源管理")
public class TenantDisController {

    private static Logger logger = LoggerFactory.getLogger(TenantDisController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private TenantDisService tenantDssService;

    @RequestMapping(path="/createTenantDss",method = RequestMethod.POST)
    @ApiMethod(name = "createTenantDss",desc = "createTenantDss")
    @ApiParam(name = "tenantDss",desc = "tenantDss",required = true)
    public Result<String> createTenantDss(@RequestBody @NotNull @Valid TenantDis tenantDss){
        String id = tenantDssService.createTenantDss(tenantDss);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateTenantDss",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDss",desc = "updateTenantDss")
    @ApiParam(name = "tenantDss",desc = "tenantDss",required = true)
    public Result<Void> updateTenantDss(@RequestBody @NotNull @Valid TenantDis tenantDss){
        tenantDssService.updateTenantDss(tenantDss);

        return Result.ok();
    }

    @RequestMapping(path="/updateTenantDssByIds",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantDssByIds",desc = "通过id 批量修改租户dss数据源")
    @ApiParam(name = "ids",desc = "ids",required = true)
    public Result<Void> updateTenantDssByIds(String ids,String dssId){
        tenantDssService.updateTenantDssByIds(ids,dssId);

        return Result.ok();
    }

    @RequestMapping(path="/deleteTenantDss",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenantDss",desc = "deleteTenantDss")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTenantDss(@NotNull String id){
        tenantDssService.deleteTenantDss(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenantDss",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDss",desc = "findTenantDss")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TenantDis> findTenantDss(@NotNull String id){
        TenantDis tenantDss = tenantDssService.findTenantDss(id);

        return Result.ok(tenantDss);
    }

    @RequestMapping(path="/findAllTenantDss",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenantDss",desc = "findAllTenantDss")
    public Result<List<TenantDis>> findAllTenantDss(){
        List<TenantDis> tenantDssList = tenantDssService.findAllTenantDss();

        return Result.ok(tenantDssList);
    }

    @RequestMapping(path = "/findTenantDssList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDssList",desc = "findTenantDssList")
    @ApiParam(name = "tenantDssQuery",desc = "tenantDssQuery",required = true)
    public Result<List<TenantDis>> findTenantDssList(@RequestBody @Valid @NotNull TenantDisQuery tenantDssQuery){
        List<TenantDis> tenantDssList = tenantDssService.findTenantDssList(tenantDssQuery);

        return Result.ok(tenantDssList);
    }

    @RequestMapping(path = "/findTenantDssPage",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDssPage",desc = "findTenantDssPage")
    @ApiParam(name = "tenantDssQuery",desc = "tenantDssQuery",required = true)
    public Result<Pagination<TenantDis>> findTenantDssPage(@RequestBody @Valid @NotNull TenantDisQuery tenantDssQuery){
        Pagination<TenantDis> pagination = tenantDssService.findTenantDssPage(tenantDssQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findTenantDssByCon",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantDssByCon",desc = "findTenantDssList")
    @ApiParam(name = "tenantDssQuery",desc = "oms使用 条件查询并把租户名字查询出来",required = true)
    public Result<List<TenantDis>> findTenantDssByCon(@RequestBody @Valid @NotNull TenantDisQuery tenantDssQuery){
        List<TenantDis> tenantDssList = tenantDssService.findTenantDssByCon(tenantDssQuery);

        return Result.ok(tenantDssList);
    }
}
