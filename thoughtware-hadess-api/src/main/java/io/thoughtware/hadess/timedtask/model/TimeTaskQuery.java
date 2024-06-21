package io.thoughtware.hadess.timedtask.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

public class TimeTaskQuery {

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();

    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam = new Page();

    @ApiProperty(name ="scanPlayId",desc = "扫描计划id")
    private String scanPlayId ;

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public void setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public String getScanPlayId() {
        return scanPlayId;
    }

    public TimeTaskQuery setScanPlayId(String scanPlayId) {
        this.scanPlayId = scanPlayId;
        return this;
    }

}