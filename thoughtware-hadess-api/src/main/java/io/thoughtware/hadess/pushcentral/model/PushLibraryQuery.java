package io.thoughtware.hadess.pushcentral.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel
public class PushLibraryQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().desc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="repositoryId",desc = "制品库id")
        private String repositoryId;

        @ApiProperty(name ="libraryId",desc = "制品id")
        private String libraryId;

        @ApiProperty(name ="pushGroupId",desc = "推送组名字")
        private String pushGroupId;



        @ApiProperty(name ="type",desc = "推送类型 maven、npm")
        private String type;

        @ApiProperty(name ="libraryName",desc = "制品名称")
        private String libraryName;


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

        public String getRepositoryId() {
            return repositoryId;
        }

        public PushLibraryQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }

        public String getLibraryId() {
            return libraryId;
        }

        public PushLibraryQuery setLibraryId(String libraryId) {
            this.libraryId = libraryId;
            return this;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getPushGroupId() {
        return pushGroupId;
    }

    public PushLibraryQuery setPushGroupId(String pushGroupId) {
        this.pushGroupId = pushGroupId;
        return this;
    }
}