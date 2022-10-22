package  net.tiklab.oms.activity.activity.controller;

import  net.tiklab.promotion.activity.model.Discount;
import  net.tiklab.promotion.activity.model.DiscountQuery;
import  net.tiklab.promotion.activity.service.DiscountService;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
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
 * DiscountController
 */
@RestController
@RequestMapping("/discount")
@Api(name = "DiscountController",desc = "折扣活动管理")
public class DiscountController {

    private static Logger logger = LoggerFactory.getLogger(DiscountController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
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
