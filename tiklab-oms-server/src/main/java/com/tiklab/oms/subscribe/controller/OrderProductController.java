package com.tiklab.oms.subscribe.controller;

import com.tiklab.postlink.annotation.Api;
import com.tiklab.postlink.annotation.ApiMethod;
import com.tiklab.postlink.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.rpc.annotation.Reference;
import com.tiklab.subscribe.order.model.OrderProduct;
import com.tiklab.subscribe.order.model.OrderProductQuery;
import com.tiklab.subscribe.order.service.OrderProductService;
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
 * OrderProductController
 */
@RestController
@RequestMapping("/orderProduct")
@Api(name = "OrderProductController",desc = "OrderProductController")
public class OrderProductController {

    private static Logger logger = LoggerFactory.getLogger(OrderProductController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private OrderProductService orderProductService;

    @RequestMapping(path="/createOrderProduct",method = RequestMethod.POST)
    @ApiMethod(name = "createOrderProduct",desc = "createOrderProduct")
    @ApiParam(name = "orderProduct",desc = "orderProduct",required = true)
    public Result<String> createOrderProduct(@RequestBody @NotNull @Valid OrderProduct orderProduct){
        String id = orderProductService.createOrderProduct(orderProduct);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateOrderProduct",method = RequestMethod.POST)
    @ApiMethod(name = "updateOrderProduct",desc = "updateOrderProduct")
    @ApiParam(name = "orderProduct",desc = "orderProduct",required = true)
    public Result<Void> updateOrderProduct(@RequestBody @NotNull @Valid OrderProduct orderProduct){
        orderProductService.updateOrderProduct(orderProduct);

        return Result.ok();
    }

    @RequestMapping(path="/deleteOrderProduct",method = RequestMethod.POST)
    @ApiMethod(name = "deleteOrderProduct",desc = "deleteOrderProduct")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteOrderProduct(@NotNull String id){
        orderProductService.deleteOrderProduct(id);

        return Result.ok();
    }

    @RequestMapping(path="/findOrderProduct",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderProduct",desc = "findOrderProduct")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<OrderProduct> findOrderProduct(@NotNull String id){
        OrderProduct orderProduct = orderProductService.findOrderProduct(id);

        return Result.ok(orderProduct);
    }

    @RequestMapping(path="/findAllOrderProduct",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOrderProduct",desc = "findAllOrderProduct")
    public Result<List<OrderProduct>> findAllOrderProduct(){
        List<OrderProduct> orderProductList = orderProductService.findAllOrderProduct();

        return Result.ok(orderProductList);
    }

    @RequestMapping(path = "/findOrderProductList",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderProductList",desc = "findOrderProductList")
    @ApiParam(name = "orderProductQuery",desc = "orderProductQuery",required = true)
    public Result<List<OrderProduct>> findOrderProductList(@RequestBody @Valid @NotNull OrderProductQuery orderProductQuery){
        List<OrderProduct> orderProductList = orderProductService.findOrderProductList(orderProductQuery);

        return Result.ok(orderProductList);
    }

    @RequestMapping(path = "/findOrderProductPage",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderProductPage",desc = "findOrderProductPage")
    @ApiParam(name = "orderProductQuery",desc = "orderProductQuery",required = true)
    public Result<Pagination<OrderProduct>> findOrderProductPage(@RequestBody @Valid @NotNull OrderProductQuery orderProductQuery){
        Pagination<OrderProduct> pagination = orderProductService.findOrderProductPage(orderProductQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path = "/findOrderIsExist",method = RequestMethod.POST)
    @ApiMethod(name = "findOrderIsExist",desc = "通过租户和产品查询是否存在有效期未支付的订单")
    @ApiParam(name = "orderProductQuery",desc = "orderProductQuery",required = true)
    public Result<OrderProduct> findOrderIsExist(@RequestBody @Valid @NotNull OrderProductQuery orderProductQuery){
        OrderProduct orderProduct = orderProductService.findOrderIsExist(orderProductQuery);

        return Result.ok(orderProduct);
    }
}
