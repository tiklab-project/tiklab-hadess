package net.tiklab.oms.tenant.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import net.tiklab.rpc.annotation.Reference;
import net.tiklab.tenant.tenant.model.Tenant;
import net.tiklab.tenant.tenant.model.TenantMember;
import net.tiklab.tenant.tenant.model.TenantMemberQuery;
import net.tiklab.tenant.tenant.service.TenantManagerMemberService;
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
 * TenantMemberController
 */
@RestController
@RequestMapping("/tenantMember")
@Api(name = "TenantManagerMemberController",desc = "租户会员关系管理")
public class TenantMemberController {

    private static Logger logger = LoggerFactory.getLogger(TenantMemberController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private TenantManagerMemberService tenantManagerMemberService;

    @RequestMapping(path="/createTenantMember",method = RequestMethod.POST)
    @ApiMethod(name = "createTenantMember",desc = "createTenantMember")
    @ApiParam(name = "tenantMember",desc = "tenantMember",required = true)
    public Result<String> createTenantMember(@RequestBody @Valid @NotNull TenantMember tenantMember){
        String id = tenantManagerMemberService.createTenantMember(tenantMember);

        return Result.ok(id);
    }
    @RequestMapping(path="/updateTenantMember",method = RequestMethod.POST)
    @ApiMethod(name = "updateTenantMember",desc = "updateTenantMember")
    @ApiParam(name = "tenantMember",desc = "tenantMember",required = true)
    public Result<Void> updateTenantMember(@RequestBody @Valid @NotNull TenantMember tenantMember){
         tenantManagerMemberService.updateTenantMember(tenantMember);

        return Result.ok(tenantMember);
    }

    @RequestMapping(path="/deleteTenantMember",method = RequestMethod.POST)
    @ApiMethod(name = "deleteTenantMember",desc = "deleteTenantMember")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteTenantMember(@NotNull String id){
         tenantManagerMemberService.deleteTenantMember(id);

        return Result.ok();
    }

    @RequestMapping(path="/findTenantMember",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantMember",desc = "findTenantMember")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<TenantMember> findTenantMember(@NotNull String id){
        TenantMember tenantMember = tenantManagerMemberService.findTenantMember(id);

        return Result.ok(tenantMember);
    }

    @RequestMapping(path="/findAllTenantMember",method = RequestMethod.POST)
    @ApiMethod(name = "findAllTenantMember",desc = "findAllTenantMember")
    public Result<List<TenantMember>> findAllTenantMember(){
        List<TenantMember> tenantMemberList = tenantManagerMemberService.findAllTenantMember();

        return Result.ok(tenantMemberList);
    }

    @RequestMapping(path = "/findTenantMemberList",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantMemberList",desc = "通过条件查询租户")
    @ApiParam(name = "tenantMemberQuery",desc = "tenantMemberQuery",required = true)
    public Result<List<Tenant>> findTenantMemberList(@RequestBody @Valid @NotNull TenantMemberQuery tenantMemberQuery){
        List<Tenant> tenantMemberList = tenantManagerMemberService.findTenantMemberList(tenantMemberQuery);

        return Result.ok(tenantMemberList);
    }

    @RequestMapping(path = "/findTenantMemberPage",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantMemberPage",desc = "findTenantMemberPage")
    @ApiParam(name = "tenantMemberQuery",desc = "tenantMemberQuery",required = true)
    public Result<Pagination<TenantMember>> findTenantMemberPage(@RequestBody @Valid @NotNull TenantMemberQuery tenantMemberQuery){
        Pagination<TenantMember> pagination = tenantManagerMemberService.findTenantMemberPage(tenantMemberQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findTenantMemberByCon",method = RequestMethod.POST)
    @ApiMethod(name = "findTenantMemberByCon",desc = "通过租户id和用户id查询")
    @ApiParam(name = "tenantMemberQuery",desc = "tenantMemberQuery",required = true)
    public Result<TenantMember> findTenantMemberByCon(@RequestBody @Valid @NotNull TenantMemberQuery tenantMemberQuery){
        TenantMember tenantMember = tenantManagerMemberService.findTenantMemberByCon(tenantMemberQuery);

        return Result.ok(tenantMember);
    }

}
