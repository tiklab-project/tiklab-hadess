package com.doublekit.oms.activity.coupon.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.promotion.coupon.model.RollMember;
import com.doublekit.promotion.coupon.model.RollMemberQuery;
import com.doublekit.promotion.coupon.service.RollMemberService;
import com.doublekit.rpc.annotation.Reference;
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
 * RollMemberController
 */
@RestController
@RequestMapping("/RollMember")
@Api(name = "RollMemberController",desc = "用户现金卷管理")
public class RollMemberController {

    private static Logger logger = LoggerFactory.getLogger(RollMemberController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private RollMemberService RollMemberService;

    @RequestMapping(path="/createRollMember",method = RequestMethod.POST)
    @ApiMethod(name = "createRollMember",desc = "createRollMember")
    @ApiParam(name = "RollMember",desc = "RollMember",required = true)
    public Result<String> createRollMember(@RequestBody @NotNull @Valid RollMember RollMember){
        String id = RollMemberService.createRollMember(RollMember);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateRollMember",method = RequestMethod.POST)
    @ApiMethod(name = "updateRollMember",desc = "updateRollMember")
    @ApiParam(name = "RollMember",desc = "RollMember",required = true)
    public Result<Void> updateRollMember(@RequestBody @NotNull @Valid RollMember RollMember){
        RollMemberService.updateRollMember(RollMember);

        return Result.ok();
    }

    @RequestMapping(path="/deleteRollMember",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRollMember",desc = "deleteRollMember")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteRollMember(@NotNull String id){
        RollMemberService.deleteRollMember(id);

        return Result.ok();
    }

    @RequestMapping(path="/findRollMember",method = RequestMethod.POST)
    @ApiMethod(name = "findRollMember",desc = "findRollMember")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<RollMember> findRollMember(@NotNull String id){
        RollMember RollMember = RollMemberService.findRollMember(id);

        return Result.ok(RollMember);
    }

    @RequestMapping(path="/findAllRollMember",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRollMember",desc = "findAllRollMember")
    public Result<List<RollMember>> findAllRollMember(){
        List<RollMember> RollMemberList = RollMemberService.findAllRollMember();

        return Result.ok(RollMemberList);
    }

    @RequestMapping(path = "/findRollMemberList",method = RequestMethod.POST)
    @ApiMethod(name = "findRollMemberList",desc = "findRollMemberList")
    @ApiParam(name = "RollMemberQuery",desc = "RollMemberQuery",required = true)
    public Result<List<RollMember>> findRollMemberList(@RequestBody @Valid @NotNull RollMemberQuery RollMemberQuery){
        List<RollMember> RollMemberList = RollMemberService.findRollMemberList(RollMemberQuery);

        return Result.ok(RollMemberList);
    }

    @RequestMapping(path = "/findRollMemberPage",method = RequestMethod.POST)
    @ApiMethod(name = "findRollMemberPage",desc = "findRollMemberPage")
    @ApiParam(name = "RollMemberQuery",desc = "RollMemberQuery",required = true)
    public Result<Pagination<RollMember>> findRollMemberPage(@RequestBody @Valid @NotNull RollMemberQuery RollMemberQuery){
        Pagination<RollMember> pagination = RollMemberService.findRollMemberPage(RollMemberQuery);

        return Result.ok(pagination);
    }



}
