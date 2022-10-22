package  net.tiklab.oms.activity.coupon.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.promotion.coupon.model.Coupon;
import  net.tiklab.promotion.coupon.model.CouponQuery;
import  net.tiklab.promotion.coupon.service.CouponService;
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
 * CouponController
 */
@RestController
@RequestMapping("/coupon")
@Api(name = "CouponController",desc = "优惠券管理")
public class CouponController {

    private static Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private CouponService couponService;

    @RequestMapping(path="/createCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "createCoupon",desc = "创建优惠券")
    @ApiParam(name = "coupon",desc = "coupon",required = true)
    public Result<String> createCoupon(@RequestBody @NotNull @Valid Coupon coupon){
        String id = couponService.createCoupon(coupon);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "updateCoupon",desc = "更新优惠券")
    @ApiParam(name = "coupon",desc = "coupon",required = true)
    public Result<Void> updateCoupon(@RequestBody @NotNull @Valid Coupon coupon){
        couponService.updateCoupon(coupon);

        return Result.ok();
    }

    @RequestMapping(path="/deleteCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCoupon",desc = "删除优惠券")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteCoupon(@NotNull String id){
        couponService.deleteCoupon(id);

        return Result.ok();
    }

    @RequestMapping(path="/findCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "findCoupon",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Coupon> findCoupon(@NotNull String id){
        Coupon coupon = couponService.findCoupon(id);

        return Result.ok(coupon);
    }

    @RequestMapping(path="/findAllCoupon",method = RequestMethod.POST)
    @ApiMethod(name = "findAllCoupon",desc = "查询所有优惠券")
    public Result<List<Coupon>> findAllCoupon(){
        List<Coupon> couponList = couponService.findAllCoupon();

        return Result.ok(couponList);
    }

    @RequestMapping(path = "/findCouponList",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponList",desc = "通过条件查询优惠券")
    @ApiParam(name = "couponQuery",desc = "couponQuery",required = true)
    public Result<List<Coupon>> findCouponList(@RequestBody @Valid @NotNull CouponQuery couponQuery){
        List<Coupon> couponList = couponService.findCouponList(couponQuery);

        return Result.ok(couponList);
    }

    @RequestMapping(path = "/findCouponPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponPage",desc = "条件分页查询优惠券")
    @ApiParam(name = "couponQuery",desc = "couponQuery",required = true)
    public Result<Pagination<Coupon>> findCouponPage(@RequestBody @Valid @NotNull CouponQuery couponQuery){
        Pagination<Coupon> pagination = couponService.findCouponPage(couponQuery);

        return Result.ok(pagination);
    }
    @RequestMapping(path = "/findTakeEffectRoll",method = RequestMethod.POST)
    @ApiMethod(name = "findTakeEffectRoll",desc = " 条件查询生效的优惠卷 以及剩余数量和 使用情况")
    @ApiParam(name = "rollQuery",desc = "rollQuery",required = true)
    public Result<List<Coupon>> findTakeEffectRoll(@RequestBody @Valid @NotNull CouponQuery couponQuery){
        List<Coupon> couponList = couponService.findTakeEffectCoupon(couponQuery);

        return Result.ok(couponList);
    }
}
