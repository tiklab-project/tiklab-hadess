package io.thoughtware.hadess.library.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel
public class PullInfoQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().desc("pullCreate").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="libraryVersionId",desc = "制品版本id")
        private String libraryVersionId;

        @ApiProperty(name ="libraryId",desc = "制品id")
        private String libraryId;



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

        public PullInfoQuery setLibraryId(String libraryId) {
            this.libraryId = libraryId;
            return this;
        }

        public String getLibraryVersionId() {
            return libraryVersionId;
        }

        public PullInfoQuery setLibraryVersionId(String libraryVersionId) {
            this.libraryVersionId = libraryVersionId;
            return this;
        }
}