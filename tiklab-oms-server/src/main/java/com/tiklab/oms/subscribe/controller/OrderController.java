package com.tiklab.oms.subscribe.controller;

import com.tiklab.postin.annotation.Api;
import com.tiklab.postin.annotation.ApiMethod;
import com.tiklab.postin.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.rpc.annotation.Reference;
import com.tiklab.subscribe.order.model.Order;
import com.tiklab.subscribe.order.model.OrderQuery;
import com.tiklab.subscribe.order.service.OrderService;
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
 * OrderController
 */
@RestController
@RequestMapping("/order")
@Api(name = "OrderController",desc = "订单管理")
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private OrderService orderService;

    @RequestMapping(path="/createOrder",method = RequestMethod.POST)
    @ApiMethod(name = "createOrder",desc = "创建订单")
    @ApiParam(name = "order",desc = "order",required = true)
    public Result<String> createOrder(@RequestBody @NotNull @Valid Order order){
        String id = orderService.createOrder(order);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateOrder",method = RequestMethod.POST)
    @ApiMethod(name = "updateOrder",desc = "修改订单")
    @ApiParam(name = "order",desc = "order",required = true)
    public Result<Void> updateOrder(@RequestBody @NotNull @Valid Order order){
        orderService.updateOrder(order);

        return Result.ok();
    }

    @RequestMapping(path="/deleteOrder",method = RequestMethod.POST)
    @ApiMethod(name = "deleteOrder",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteOrder(@NotNull String id){
        orderService.deleteOrder(id);

        return Result.ok();
    }

    @RequestMapping(path="/findOrder",method = RequestMethod.POST)
    @ApiMethod(name = "findOrder",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Order> findOrder(@NotNull String id){
        Order order = orderService.findOrder(id);

        return Result.ok(order);
    }

    @RequestMapping(path="/findAllOrder",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOrder",desc = "查询所有")
    public Result<List<Order>> findAllOrder(){
        List<Order> orderList = orderService.findAllOrder();

        return Result.ok(orderList);
    }

    @RequestMapping(path = "/findOrderList",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderList",desc = "条件查询")
    @ApiParam(name = "orderQuery",desc = "orderQuery",required = true)
    public Result<List<Order>> findOrderList(@RequestBody @Valid @NotNull OrderQuery orderQuery){
        List<Order> orderList = orderService.findOrderList(orderQuery);

        return Result.ok(orderList);
    }

    @RequestMapping(path = "/findOrderPage",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderPage",desc = "条件分页查询")
    @ApiParam(name = "orderQuery",desc = "orderQuery",required = true)
    public Result<Pagination<Order>> findOrderPage(@RequestBody @Valid @NotNull OrderQuery orderQuery){
        Pagination<Order> pagination = orderService.findOrderPage(orderQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findAdminOrderPage",method = RequestMethod.POST)
    @ApiMethod(name = "findAdminOrderPage",desc = "管理平台订单查询")
    @ApiParam(name = "orderQuery",desc = "orderQuery",required = true)
    public Result<Pagination<Order>> findAdminOrderPage(@RequestBody @Valid @NotNull OrderQuery orderQuery){
        Pagination<Order> pagination = orderService.findOrderPage(orderQuery);
        return Result.ok(pagination);
    }

}
