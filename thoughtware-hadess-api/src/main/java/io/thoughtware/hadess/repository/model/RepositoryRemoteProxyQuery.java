package io.thoughtware.hadess.repository.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel
public class RepositoryRemoteProxyQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="repositoryId",desc = "制品库id")
        private String repositoryId;

        @ApiProperty(name ="remoteProxyId",desc = "代理地址id")
        private String remoteProxyId;

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

        public RepositoryRemoteProxyQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }

        public String getRemoteProxyId() {
            return remoteProxyId;
        }

        public RepositoryRemoteProxyQuery setRemoteProxyId(String remoteProxyId) {
            this.remoteProxyId = remoteProxyId;
            return this;
        }
}