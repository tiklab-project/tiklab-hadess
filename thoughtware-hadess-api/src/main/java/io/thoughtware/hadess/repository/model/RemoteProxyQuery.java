package io.thoughtware.hadess.repository.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;

public class RemoteProxyQuery implements Serializable {

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().asc("type").get();


    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam = new Page();

    @ApiProperty(name ="agencyType",desc = "类型")
    private String agencyType;

    @ApiProperty(name ="agencyUrl",desc = "地址")
    private String agencyUrl;

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

    public String getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(String agencyType) {
        this.agencyType = agencyType;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public RemoteProxyQuery setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
        return this;
    }
}
