package io.thoughtware.hadess.repository.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;


@ApiModel
public class RepositoryQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("createTime").get();


        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="name",desc = "制品库名字")
        private String name;

        @ApiProperty(name ="repositoryType",desc = "类型 local、remote、group")
        private String repositoryType;

        @ApiProperty(name ="type",desc = "制品库类型 maven、npm ")
        private String type;

        @ApiProperty(name ="category",desc = "1演示、2正式")
        private Integer category;


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

        public Integer getCategory() {
            return category;
        }

        public RepositoryQuery setCategory(Integer category) {
            this.category = category;
            return this;
        }
}