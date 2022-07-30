package com.tiklab.oms.member.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.postin.annotation.ApiParams;
import com.tiklab.core.Result;
import com.tiklab.core.exception.ApplicationException;
import com.tiklab.core.page.Pagination;
import com.tiklab.member.member.model.Member;
import com.tiklab.member.member.model.MemberQuery;
import com.tiklab.member.member.service.MemberService;
import com.tiklab.rpc.annotation.Reference;
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
 * MemberController
 * Created by Zhangzhihua on 2017/9/25.
 */
@RestController
@RequestMapping("/member")
@Api(name = "MemberController",desc = "会员管理")
public class MemberController {

    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private MemberService memberService;

    @RequestMapping(path = "/registeMember",method = RequestMethod.POST)
    @ApiMethod(name = "registeMember",desc = "注册会员")
    @ApiParam(name = "member",desc = "会员DTO",required = true)
    public Result<String> registeMember(@RequestBody @Valid @NotNull Member member){
        String id=null;
        try {
           id = memberService.createMember(member);
        }catch (ApplicationException e){
          return Result.error(e.getErrorCode(),e.getMessage());
        }

        return Result.ok(id);
    }

    @RequestMapping(path = "/createMember",method = RequestMethod.POST)
    @ApiMethod(name = "createMember",desc = "创建会员")
    @ApiParam(name = "member",desc = "会员DTO",required = true)
    public Result<String> createMember(@RequestBody @Valid @NotNull Member member){
        String id = memberService.createMember(member);

        return Result.ok(id);
    }

    @RequestMapping(path = "/updateMember",method = RequestMethod.POST)
    @ApiMethod(name = "updateMember",desc = "更新会员")
    @ApiParams(
        @ApiParam(name = "member",desc = "会员DTO",required = true)
    )
    public Result<Void> updateMember(@RequestBody @Valid @NotNull Member member){
        memberService.updateMember(member);
        return Result.ok();
    }

    @RequestMapping(path = "/deleteMember",method = RequestMethod.POST)
    @ApiMethod(name = "deleteMember",desc = "根据会员ID删除会员")
    @ApiParam(name = "id",desc = "会员ID",required = true)
    public Result<Void> deleteMember(@NotNull String id){
        memberService.deleteMember(id);

        return Result.ok();
    }

    @RequestMapping(path = "/findMember",method = RequestMethod.POST)
    @ApiMethod(name = "findMember",desc = "根据会员ID查找会员")
    @ApiParam(name = "id",desc = "会员ID",required = true)
    public Result<Member> findMember(@NotNull String id){
        Member member = memberService.findMember(id);

        return Result.ok(member);
    }

    @RequestMapping(path = "/findAllMember",method = RequestMethod.POST)
    @ApiMethod(name = "findAllMember",desc = "查找所有会员")
    public Result<List<Member>> findAllMember(){
        List<Member> memberList = memberService.findAllMember();

        return Result.ok(memberList);
    }


    @RequestMapping(path = "/findMemberList",method = RequestMethod.POST)
    @ApiMethod(name = "findMemberList",desc = "根据查询对象查询会员列表")
    @ApiParam(name = "memberQuery",desc = "会员查询对象",required = true)
    public Result<List<Member>> findMemberList(@RequestBody @Valid @NotNull MemberQuery memberQuery){
        List<Member> memberList = memberService.findMemberList(memberQuery);

        return Result.ok(memberList);
    }


    @RequestMapping(path = "/findMemberPage",method = RequestMethod.POST)
    @ApiMethod(name = "findMemberPage",desc = "根据查询对象按分页查询会员列表")
    @ApiParam(name = "memberQuery",desc = "会员查询对象",required = true)
    public Result<Pagination<Member>> findMemberPage(@RequestBody @Valid @NotNull MemberQuery memberQuery){
        Pagination<Member> pagination = memberService.findMemberPage(memberQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/updatePassWord",method = RequestMethod.POST)
    @ApiMethod(name = "updatePassWord",desc = "修改密码")
    @ApiParam(name = "member",desc = "member",required = true)
    public Result<Void> updatePassWord(@RequestBody @Valid @NotNull Member member){
        memberService.updatePassWord(member);

        return Result.ok();
    }


}
