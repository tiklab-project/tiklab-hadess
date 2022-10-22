package  net.tiklab.oms.activity.coupon.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.promotion.coupon.model.CouponDiscount;
import  net.tiklab.promotion.coupon.model.CouponDiscountQuery;
import  net.tiklab.promotion.coupon.service.CouponDiscountService;
import  net.tiklab.rpc.annotation.Reference;
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
 * CouponDiscountController
 */
@RestController
@RequestMapping("/couponDiscount")
@Api(name = "CouponDiscountController",desc = "折扣券管理")
public class CouponDiscountController {

    private static Logger logger = LoggerFactory.getLogger(CouponDiscountController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private CouponDiscountService couponDiscountService;

    @RequestMapping(path="/createCouponDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "createCouponDiscount",desc = "createCouponDiscount")
    @ApiParam(name = "couponDiscount",desc = "couponDiscount",required = true)
    public Result<String> createCouponDiscount(@RequestBody @NotNull @Valid CouponDiscount couponDiscount){
        String id = couponDiscountService.createCouponDiscount(couponDiscount);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateCouponDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "updateCouponDiscount",desc = "updateCouponDiscount")
    @ApiParam(name = "couponDiscount",desc = "couponDiscount",required = true)
    public Result<Void> updateCouponDiscount(@RequestBody @NotNull @Valid CouponDiscount couponDiscount){
        couponDiscountService.updateCouponDiscount(couponDiscount);

        return Result.ok();
    }

    @RequestMapping(path="/deleteCouponDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCouponDiscount",desc = "deleteCouponDiscount")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteCouponDiscount(@NotNull String id){
        couponDiscountService.deleteCouponDiscount(id);

        return Result.ok();
    }

    @RequestMapping(path="/findCouponDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponDiscount",desc = "findCouponDiscount")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<CouponDiscount> findCouponDiscount(@NotNull String id){
        CouponDiscount couponDiscount = couponDiscountService.findCouponDiscount(id);

        return Result.ok(couponDiscount);
    }

    @RequestMapping(path="/findAllCouponDiscount",method = RequestMethod.POST)
    @ApiMethod(name = "findAllCouponDiscount",desc = "findAllCouponDiscount")
    public Result<List<CouponDiscount>> findAllCouponDiscount(){
        List<CouponDiscount> couponDiscountList = couponDiscountService.findAllCouponDiscount();

        return Result.ok(couponDiscountList);
    }

    @RequestMapping(path = "/findCouponDiscountList",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponDiscountList",desc = "findCouponDiscountList")
    @ApiParam(name = "couponDiscountQuery",desc = "couponDiscountQuery",required = true)
    public Result<List<CouponDiscount>> findCouponDiscountList(@RequestBody @Valid @NotNull CouponDiscountQuery couponDiscountQuery){
        List<CouponDiscount> couponDiscountList = couponDiscountService.findCouponDiscountList(couponDiscountQuery);

        return Result.ok(couponDiscountList);
    }

    @RequestMapping(path = "/findCouponDiscountPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponDiscountPage",desc = "findCouponDiscountPage")
    @ApiParam(name = "couponDiscountQuery",desc = "couponDiscountQuery",required = true)
    public Result<Pagination<CouponDiscount>> findCouponDiscountPage(@RequestBody @Valid @NotNull CouponDiscountQuery couponDiscountQuery){
        Pagination<CouponDiscount> pagination = couponDiscountService.findCouponDiscountPage(couponDiscountQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findCouponDisAccessPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponDisAccessPage",desc = "折扣券的领取使用情况")
    @ApiParam(name = "couponDiscountQuery",desc = "couponDiscountQuery",required = true)
    public Result<Pagination<CouponDiscount>> findCouponDisAccessPage(@RequestBody @Valid @NotNull CouponDiscountQuery couponDiscountQuery){
        Pagination<CouponDiscount> pagination = couponDiscountService.findMergeDiscountCouponPage(couponDiscountQuery);

        return Result.ok(pagination);
    }

}
