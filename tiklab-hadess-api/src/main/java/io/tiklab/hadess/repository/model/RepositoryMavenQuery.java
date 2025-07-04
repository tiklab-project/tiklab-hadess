package io.tiklab.hadess.repository.model;


import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class RepositoryMavenQuery {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();


        @ApiProperty(name ="repositoryId",desc = "制品库id")
        private String repositoryId;

        @ApiProperty(name ="repositoryId",desc = "制品库ids")
        private String[] repIds;
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

        public RepositoryMavenQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }

        public String[] getRepIds() {
            return repIds;
        }

        public RepositoryMavenQuery setRepIds(String[] repIds) {
            this.repIds = repIds;
            return this;
        }
}