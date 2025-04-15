package io.tiklab.hadess.library.model;


import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;


@ApiModel
public class LibraryPypiQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().desc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="libraryId",desc = "制品id")
        private String libraryId;

        @ApiProperty(name ="libraryIds",desc = "制品ids")
        private String[] libraryIds;


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

    public String getLibraryId() {
        return libraryId;
    }

    public LibraryPypiQuery setLibraryId(String libraryId) {
        this.libraryId = libraryId;
        return this;
    }

    public String[] getLibraryIds() {
        return libraryIds;
    }

    public LibraryPypiQuery setLibraryIds(String[] libraryIds) {
        this.libraryIds = libraryIds;
        return this;
    }
}