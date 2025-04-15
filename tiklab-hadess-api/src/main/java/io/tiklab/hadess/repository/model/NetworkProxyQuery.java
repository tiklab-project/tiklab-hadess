package io.tiklab.hadess.repository.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;

public class NetworkProxyQuery implements Serializable {

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().asc("type").get();


    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam = new Page();

    @ApiProperty(name ="enable",desc = "是否启动")
    private Integer enable;

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

    public Integer getEnable() {
        return enable;
    }

    public NetworkProxyQuery setEnable(Integer enable) {
        this.enable = enable;
        return this;
    }
}
