package io.thoughtware.hadess.repository.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class RepositoryGroupQuery {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="repositoryGroupId",desc = "组合库id")
        private String repositoryGroupId;

        @ApiProperty(name ="repositoryId",desc = "repositoryId")
        private String repositoryId;

        private List<Repository> repositoryList;

        public List<Repository> getRepositoryList() {
            return repositoryList;
        }

        public void setRepositoryList(List<Repository> repositoryList) {
            this.repositoryList = repositoryList;
        }

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

        public RepositoryGroupQuery setRepositoryGroupId(String repositoryGroupId) {
            this.repositoryGroupId = repositoryGroupId;
            return this;
        }

        public String getRepositoryId() {
            return repositoryId;
        }

        public RepositoryGroupQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }
}