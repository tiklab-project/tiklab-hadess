package  net.tiklab.oms.activity.coupon.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.promotion.coupon.model.CouponCash;
import  net.tiklab.promotion.coupon.model.CouponCashQuery;
import  net.tiklab.promotion.coupon.service.CouponCashService;
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
 * CouponCashController
 */
@RestController
@RequestMapping("/couponCash")
@Api(name = "CouponCashController",desc = "现金券管理")
public class CouponCashController {

    private static Logger logger = LoggerFactory.getLogger(CouponCashController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private CouponCashService couponCashService;

    @RequestMapping(path="/createCouponCash",method = RequestMethod.POST)
    @ApiMethod(name = "createCouponCash",desc = "createCouponCash")
    @ApiParam(name = "couponCash",desc = "couponCash",required = true)
    public Result<String> createCouponCash(@RequestBody @NotNull @Valid CouponCash couponCash){
        String id = couponCashService.createCouponCash(couponCash);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateCouponCash",method = RequestMethod.POST)
    @ApiMethod(name = "updateCouponCash",desc = "updateCouponCash")
    @ApiParam(name = "couponCash",desc = "couponCash",required = true)
    public Result<Void> updateCouponCash(@RequestBody @NotNull @Valid CouponCash couponCash){
        couponCashService.updateCouponCash(couponCash);

        return Result.ok();
    }

    @RequestMapping(path="/deleteCouponCash",method = RequestMethod.POST)
    @ApiMethod(name = "deleteCouponCash",desc = "deleteCouponCash")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteCouponCash(@NotNull String id){
        couponCashService.deleteCouponCash(id);

        return Result.ok();
    }

    @RequestMapping(path="/findCouponCash",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponCash",desc = "findCouponCash")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<CouponCash> findCouponCash(@NotNull String id){
        CouponCash couponCash = couponCashService.findCouponCash(id);

        return Result.ok(couponCash);
    }

    @RequestMapping(path="/findAllCouponCash",method = RequestMethod.POST)
    @ApiMethod(name = "findAllCouponCash",desc = "findAllCouponCash")
    public Result<List<CouponCash>> findAllCouponCash(){
        List<CouponCash> couponCashList = couponCashService.findAllCouponCash();

        return Result.ok(couponCashList);
    }

    @RequestMapping(path = "/findCouponCashList",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponCashList",desc = "findCouponCashList")
    @ApiParam(name = "couponCashQuery",desc = "couponCashQuery",required = true)
    public Result<List<CouponCash>> findCouponCashList(@RequestBody @Valid @NotNull CouponCashQuery couponCashQuery){
        List<CouponCash> couponCashList = couponCashService.findCouponCashList(couponCashQuery);

        return Result.ok(couponCashList);
    }

    @RequestMapping(path = "/findCouponCashPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponCashPage",desc = "findCouponCashPage")
    @ApiParam(name = "couponCashQuery",desc = "couponCashQuery",required = true)
    public Result<Pagination<CouponCash>> findCouponCashPage(@RequestBody @Valid @NotNull CouponCashQuery couponCashQuery){
        Pagination<CouponCash> pagination = couponCashService.findCouponCashPage(couponCashQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findCouponCashAccessPage",method = RequestMethod.POST)
    @ApiMethod(name = "findCouponCashAccessPage",desc = "现金券的领取使用情况")
    @ApiParam(name = "couponCashQuery",desc = "couponCashQuery",required = true)
    public Result<Pagination<CouponCash>> findCouponCashAccessPage(@RequestBody @Valid @NotNull CouponCashQuery couponCashQuery){
        Pagination<CouponCash> pagination = couponCashService.findCouponCashAccessPage(couponCashQuery);

        return Result.ok(pagination);
    }

}
