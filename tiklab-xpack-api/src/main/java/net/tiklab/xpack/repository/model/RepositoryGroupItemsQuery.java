package net.tiklab.xpack.repository.model;

import net.tiklab.core.order.Order;
import net.tiklab.core.order.OrderBuilders;
import net.tiklab.core.page.Page;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class RepositoryGroupItemsQuery {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="repositoryGroupId",desc = "组合库id")
        private String repositoryGroupId;

        @ApiProperty(name ="repositoryId",desc = "repositoryId")
        private String repositoryId;

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

        public String getRepositoryGroupId() {
            return repositoryGroupId;
        }

        public RepositoryGroupItemsQuery setRepositoryGroupId(String repositoryGroupId) {
            this.repositoryGroupId = repositoryGroupId;
            return this;
        }

        public String getRepositoryId() {
            return repositoryId;
        }

        public RepositoryGroupItemsQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }
}