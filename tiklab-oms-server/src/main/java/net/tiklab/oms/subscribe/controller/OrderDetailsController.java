package  net.tiklab.oms.subscribe.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.subscribe.order.model.OrderDetails;
import  net.tiklab.subscribe.order.model.OrderDetailsQuery;
import  net.tiklab.subscribe.order.service.OrderDetailsService;
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
 * OrderDetailsController
 */
@RestController
@RequestMapping("/orderDetails")
@Api(name = "OrderDetailsController",desc = "订单详情")
public class OrderDetailsController {

    private static Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private OrderDetailsService orderDetailsService;

    @RequestMapping(path="/createOrderDetails",method = RequestMethod.POST)
    @ApiMethod(name = "createOrderDetails",desc = "创建订单详情")
    @ApiParam(name = "orderDetails",desc = "orderDetails",required = true)
    public Result<String> createOrderDetails(@RequestBody @NotNull @Valid OrderDetails orderDetails){
        String id = orderDetailsService.createOrderDetails(orderDetails);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateOrderDetails",method = RequestMethod.POST)
    @ApiMethod(name = "updateOrderDetails",desc = "修改订单详情")
    @ApiParam(name = "orderDetails",desc = "orderDetails",required = true)
    public Result<Void> updateOrderDetails(@RequestBody @NotNull @Valid OrderDetails orderDetails){
        orderDetailsService.updateOrderDetails(orderDetails);

        return Result.ok();
    }

    @RequestMapping(path="/deleteOrderDetails",method = RequestMethod.POST)
    @ApiMethod(name = "deleteOrderDetails",desc = "删除订单详情")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteOrderDetails(@NotNull String id){
        orderDetailsService.deleteOrderDetails(id);

        return Result.ok();
    }

    @RequestMapping(path="/findOrderDetails",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderDetails",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<OrderDetails> findOrderDetails(@NotNull String id){
        OrderDetails orderDetails = orderDetailsService.findOrderDetails(id);

        return Result.ok(orderDetails);
    }

    @RequestMapping(path="/findAllOrderDetails",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOrderDetails",desc = "查询所有订单详情")
    public Result<List<OrderDetails>> findAllOrderDetails(){
        List<OrderDetails> orderDetailsList = orderDetailsService.findAllOrderDetails();

        return Result.ok(orderDetailsList);
    }

    @RequestMapping(path = "/findOrderDetailsList",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderDetailsList",desc = "通过条件查询")
    @ApiParam(name = "orderDetailsQuery",desc = "orderDetailsQuery",required = true)
    public Result<List<OrderDetails>> findOrderDetailsList(@RequestBody @Valid @NotNull OrderDetailsQuery orderDetailsQuery){
        List<OrderDetails> orderDetailsList = orderDetailsService.findOrderDetailsList(orderDetailsQuery);

        return Result.ok(orderDetailsList);
    }

    @RequestMapping(path = "/findOrderDetailsPage",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderDetailsPage",desc = "条件分页查询")
    @ApiParam(name = "orderDetailsQuery",desc = "orderDetailsQuery",required = true)
    public Result<Pagination<OrderDetails>> findOrderDetailsPage(@RequestBody @Valid @NotNull OrderDetailsQuery orderDetailsQuery){
        Pagination<OrderDetails> pagination = orderDetailsService.findOrderDetailsPage(orderDetailsQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path="/findOrderIsExist",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderDetails",desc = "通过条件查询未支付的订单")
    @ApiParam(name = "orderDetailsQuery",desc = "产品id 租户id或者会员id",required = true)
    public Result<OrderDetails> findOrderIsExist(@RequestBody @Valid @NotNull OrderDetailsQuery orderDetailsQuery){
        OrderDetails orderDetails = orderDetailsService.findOrderIsExist(orderDetailsQuery);

        return Result.ok(orderDetails);
    }

}
