package  net.tiklab.oms.subscribe.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.subscribe.order.model.OrderDiscounts;
import  net.tiklab.subscribe.order.model.OrderDiscountsQuery;
import  net.tiklab.subscribe.order.service.OrderDiscountsService;
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
 * OrderDiscountsController
 */
@RestController
@RequestMapping("/orderDiscounts")
@Api(name = "OrderDiscountsController",desc = "订单优惠管理")
public class OrderDiscountsController {

    private static Logger logger = LoggerFactory.getLogger(OrderDiscountsController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private OrderDiscountsService orderDiscountsService;

    @RequestMapping(path="/createOrderDiscounts",method = RequestMethod.POST)
    @ApiMethod(name = "createOrderDiscounts",desc = "创建订单优惠管理")
    @ApiParam(name = "orderDiscounts",desc = "orderDiscounts",required = true)
    public Result<String> createOrderDiscounts(@RequestBody @NotNull @Valid OrderDiscounts orderDiscounts){
        String id = orderDiscountsService.createOrderDiscounts(orderDiscounts);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateOrderDiscounts",method = RequestMethod.POST)
    @ApiMethod(name = "updateOrderDiscounts",desc = "updateOrderDiscounts")
    @ApiParam(name = "orderDiscounts",desc = "orderDiscounts",required = true)
    public Result<Void> updateOrderDiscounts(@RequestBody @NotNull @Valid OrderDiscounts orderDiscounts){
        orderDiscountsService.updateOrderDiscounts(orderDiscounts);

        return Result.ok();
    }

    @RequestMapping(path="/deleteOrderDiscounts",method = RequestMethod.POST)
    @ApiMethod(name = "deleteOrderDiscounts",desc = "deleteOrderDiscounts")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteOrderDiscounts(@NotNull String id){
        orderDiscountsService.deleteOrderDiscounts(id);

        return Result.ok();
    }

    @RequestMapping(path="/findOrderDiscounts",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderDiscounts",desc = "findOrderDiscounts")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<OrderDiscounts> findOrderDiscounts(@NotNull String id){
        OrderDiscounts orderDiscounts = orderDiscountsService.findOrderDiscounts(id);

        return Result.ok(orderDiscounts);
    }

    @RequestMapping(path="/findAllOrderDiscounts",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOrderDiscounts",desc = "findAllOrderDiscounts")
    public Result<List<OrderDiscounts>> findAllOrderDiscounts(){
        List<OrderDiscounts> orderDiscountsList = orderDiscountsService.findAllOrderDiscounts();

        return Result.ok(orderDiscountsList);
    }

    @RequestMapping(path = "/findOrderDiscountsList",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderDiscountsList",desc = "findOrderDiscountsList")
    @ApiParam(name = "orderDiscountsQuery",desc = "orderDiscountsQuery",required = true)
    public Result<List<OrderDiscounts>> findOrderDiscountsList(@RequestBody @Valid @NotNull OrderDiscountsQuery orderDiscountsQuery){
        List<OrderDiscounts> orderDiscountsList = orderDiscountsService.findOrderDiscountsList(orderDiscountsQuery);

        return Result.ok(orderDiscountsList);
    }

    @RequestMapping(path = "/findOrderDiscountsPage",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderDiscountsPage",desc = "findOrderDiscountsPage")
    @ApiParam(name = "orderDiscountsQuery",desc = "orderDiscountsQuery",required = true)
    public Result<Pagination<OrderDiscounts>> findOrderDiscountsPage(@RequestBody @Valid @NotNull OrderDiscountsQuery orderDiscountsQuery){
        Pagination<OrderDiscounts> pagination = orderDiscountsService.findOrderDiscountsPage(orderDiscountsQuery);

        return Result.ok(pagination);
    }

}
