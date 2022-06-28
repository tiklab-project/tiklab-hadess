package com.doublekit.oms.activity.activity.controller;

import com.doublekit.promotion.activity.model.Discount;
import com.doublekit.promotion.activity.model.DiscountQuery;
import com.doublekit.promotion.activity.service.DiscountService;
import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.core.page.Pagination;
import com.doublekit.promotion.coupon.model.DiscountCoupon;
import com.doublekit.promotion.coupon.model.DiscountCouponQuery;
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
 * DiscountController
 */
@RestController
@RequestMapping("/discount")
@Api(name = "DiscountController",desc = "折扣活动管理")
public class DiscountController {

    private static Logger logger = LoggerFactory.getLogger(DiscountController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private DiscountService discountService;

    @RequestMapping(path="/createDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "createDiscount",desc = "createDiscount")
    @ApiParam(name = "discount",desc = "discount",required = true)
    public Result<String> createDiscount(@RequestBody @NotNull @Valid Discount discount){
        String id = discountService.createDiscount(discount);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "updateDiscount",desc = "updateDiscount")
    @ApiParam(name = "discount",desc = "discount",required = true)
    public Result<Void> updateDiscount(@RequestBody @NotNull @Valid Discount discount){
        discountService.updateDiscount(discount);

        return Result.ok();
    }

    @RequestMapping(path="/deleteDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "deleteDiscount",desc = "deleteDiscount")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteDiscount(@NotNull String id){
        discountService.deleteDiscount(id);

        return Result.ok();
    }

    @RequestMapping(path="/findDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "findDiscount",desc = "findDiscount")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Discount> findDiscount(@NotNull String id){
        Discount discount = discountService.findDiscount(id);

        return Result.ok(discount);
    }

    @RequestMapping(path="/findAllDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "findAllDiscount",desc = "findAllDiscount")
    public Result<List<Discount>> findAllDiscount(){
        List<Discount> discountList = discountService.findAllDiscount();

        return Result.ok(discountList);
    }

    @RequestMapping(path = "/findDiscountList",method = RequestMethod.POST)
    @ApiMethod(name = "findDiscountList",desc = "findDiscountList")
    @ApiParam(name = "discountQuery",desc = "discountQuery",required = true)
    public Result<List<Discount>> findDiscountList(@RequestBody @Valid @NotNull DiscountQuery discountQuery){
        List<Discount> discountList = discountService.findDiscountList(discountQuery);

        return Result.ok(discountList);
    }

    @RequestMapping(path = "/findDiscountPage",method = RequestMethod.POST)
    @ApiMethod(name = "findDiscountPage",desc = "findDiscountPage")
    @ApiParam(name = "discountQuery",desc = "discountQuery",required = true)
    public Result<Pagination<Discount>> findDiscountPage(@RequestBody @Valid @NotNull DiscountQuery discountQuery){
        Pagination<Discount> pagination = discountService.findDiscountPage(discountQuery);

        return Result.ok(pagination);
    }

}
