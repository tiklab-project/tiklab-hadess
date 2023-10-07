package io.tiklab.xpack.repository.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;


@ApiModel
public class RepositoryQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();


        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="name",desc = "制品库名字")
        private String name;

        @ApiProperty(name ="repositoryType",desc = "制品库类型 maven、npm")
        private String repositoryType;

        @ApiProperty(name ="type",desc = "类型 local、remote、group")
        private String type;

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

        public String getRepositoryType() {
            return repositoryType;
        }

        public RepositoryQuery setRepositoryType(String repositoryType) {
            this.repositoryType = repositoryType;
            return this;
        }

        public String getType() {
            return type;
        }

        public RepositoryQuery setType(String type) {
            this.type = type;
            return this;
        }

        public String getName() {
            return name;
        }

        public RepositoryQuery setName(String name) {
            this.name = name;
            return this;
        }
}