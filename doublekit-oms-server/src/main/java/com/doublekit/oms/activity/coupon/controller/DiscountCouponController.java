package com.doublekit.oms.activity.coupon.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.promotion.coupon.model.DiscountCoupon;
import com.doublekit.promotion.coupon.model.DiscountCouponQuery;
import com.doublekit.promotion.coupon.service.DiscountCouponService;
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
 * DiscountCouponController
 */
@RestController
@RequestMapping("/discountCoupon")
@Api(name = "DiscountCouponController",desc = "折扣卷管理")
public class DiscountCouponController {

    private static Logger logger = LoggerFactory.getLogger(DiscountCouponController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private DiscountCouponService discountCouponService;

    @RequestMapping(path="/createDiscountCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "createDiscountCoupon",desc = "createDiscountCoupon")
    @ApiParam(name = "discountCoupon",desc = "discountCoupon",required = true)
    public Result<String> createDiscountCoupon(@RequestBody @NotNull @Valid DiscountCoupon discountCoupon){
        String id = discountCouponService.createDiscountCoupon(discountCoupon);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateDiscountCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "updateDiscountCoupon",desc = "updateDiscountCoupon")
    @ApiParam(name = "discountCoupon",desc = "discountCoupon",required = true)
    public Result<Void> updateDiscountCoupon(@RequestBody @NotNull @Valid DiscountCoupon discountCoupon){
        discountCouponService.updateDiscountCoupon(discountCoupon);

        return Result.ok();
    }

    @RequestMapping(path="/deleteDiscountCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "deleteDiscountCoupon",desc = "deleteDiscountCoupon")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteDiscountCoupon(@NotNull String id){
        discountCouponService.deleteDiscountCoupon(id);

        return Result.ok();
    }

    @RequestMapping(path="/findDiscountCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "findDiscountCoupon",desc = "findDiscountCoupon")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<DiscountCoupon> findDiscountCoupon(@NotNull String id){
        DiscountCoupon discountCoupon = discountCouponService.findDiscountCoupon(id);

        return Result.ok(discountCoupon);
    }

    @RequestMapping(path="/findAllDiscountCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "findAllDiscountCoupon",desc = "findAllDiscountCoupon")
    public Result<List<DiscountCoupon>> findAllDiscountCoupon(){
        List<DiscountCoupon> discountCouponList = discountCouponService.findAllDiscountCoupon();

        return Result.ok(discountCouponList);
    }

    @RequestMapping(path = "/findDiscountCouponList",method = RequestMethod.POST)
    @ApiMethod(name = "findDiscountCouponList",desc = "findDiscountCouponList")
    @ApiParam(name = "discountCouponQuery",desc = "discountCouponQuery",required = true)
    public Result<List<DiscountCoupon>> findDiscountCouponList(@RequestBody @Valid @NotNull DiscountCouponQuery discountCouponQuery){
        List<DiscountCoupon> discountCouponList = discountCouponService.findDiscountCouponList(discountCouponQuery);

        return Result.ok(discountCouponList);
    }

    @RequestMapping(path = "/findDiscountCouponPage",method = RequestMethod.POST)
    @ApiMethod(name = "findDiscountCouponPage",desc = "findDiscountCouponPage")
    @ApiParam(name = "discountCouponQuery",desc = "discountCouponQuery",required = true)
    public Result<Pagination<DiscountCoupon>> findDiscountCouponPage(@RequestBody @Valid @NotNull DiscountCouponQuery discountCouponQuery){
        Pagination<DiscountCoupon> pagination = discountCouponService.findDiscountCouponPage(discountCouponQuery);

        return Result.ok(pagination);
    }

}
