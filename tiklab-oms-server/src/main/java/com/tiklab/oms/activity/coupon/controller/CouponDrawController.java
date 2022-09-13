package com.tiklab.oms.activity.coupon.controller;

import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.promotion.coupon.model.CouponDraw;
import com.tiklab.promotion.coupon.model.CouponDrawQuery;
import com.tiklab.promotion.coupon.service.CouponDrawService;
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
 * CouponDrawController
 */
@RestController
@RequestMapping("/couponDraw")
@Api(name = "CouponDrawController",desc = "优惠券领取记录管理")
public class CouponDrawController {

    private static Logger logger = LoggerFactory.getLogger(CouponDrawController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private CouponDrawService couponDrawService;

    @RequestMapping(path="/createCouponDraw",method = RequestMethod.POST)
    @ApiMethod(name = "createCouponDraw",desc = "领取优惠券")
    @ApiParam(name = "couponDraw",desc = "couponDraw",required = true)
    public Result<String> createCouponDraw(@RequestBody @NotNull @Valid CouponDraw couponDraw){
        String id = couponDrawService.createCouponDraw(couponDraw);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateCouponDraw",method = RequestMethod.POST)
    @ApiMethod(name = "updateCouponDraw",desc = "updateCouponDraw")
    @ApiParam(name = "couponDraw",desc = "couponDraw",required = true)
    public Result<Void> updateCouponDraw(@RequestBody @NotNull @Valid CouponDraw couponDraw){
        couponDrawService.updateCouponDraw(couponDraw);

        return Result.ok();
    }

    @RequestMapping(path="/deleteCouponDraw",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCouponDraw",desc = "deleteCouponDraw")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteCouponDraw(@NotNull String id){
        couponDrawService.deleteCouponDraw(id);

        return Result.ok();
    }

    @RequestMapping(path="/findCouponDraw",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponDraw",desc = "findCouponDraw")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<CouponDraw> findCouponDraw(@NotNull String id){
        CouponDraw couponDraw = couponDrawService.findCouponDraw(id);

        return Result.ok(couponDraw);
    }

    @RequestMapping(path="/findAllCouponDraw",method = RequestMethod.POST)
    @ApiMethod(name = "findAllCouponDraw",desc = "findAllCouponDraw")
    public Result<List<CouponDraw>> findAllCouponDraw(){
        List<CouponDraw> couponDrawList = couponDrawService.findAllCouponDraw();

        return Result.ok(couponDrawList);
    }

    @RequestMapping(path = "/findCouponDrawList",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponDrawList",desc = "findCouponDrawList")
    @ApiParam(name = "couponDrawQuery",desc = "couponDrawQuery",required = true)
    public Result<List<CouponDraw>> findCouponDrawList(@RequestBody @Valid @NotNull CouponDrawQuery couponDrawQuery){
        List<CouponDraw> couponDrawList = couponDrawService.findCouponDrawList(couponDrawQuery);

        return Result.ok(couponDrawList);
    }

    @RequestMapping(path = "/findCouponDrawPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponDrawPage",desc = "findCouponDrawPage")
    @ApiParam(name = "couponDrawQuery",desc = "couponDrawQuery",required = true)
    public Result<Pagination<CouponDraw>> findCouponDrawPage(@RequestBody @Valid @NotNull CouponDrawQuery couponDrawQuery){
        Pagination<CouponDraw> pagination = couponDrawService.findCouponDrawPage(couponDrawQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findUsableCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "findUsableCoupon",desc = " 查询有效期内可用的券")
    @ApiParam(name = "RollMemberQuery",desc = "参数：orderPrice ；memberId  ；isUse ",required = true)
    public Result<List<CouponDraw>> findUsableCoupon(@RequestBody @Valid @NotNull CouponDrawQuery couponDrawQuery){
        List<CouponDraw> CouponDrawList = couponDrawService.findUsableCoupon(couponDrawQuery);

        return Result.ok(CouponDrawList);
    }
}
