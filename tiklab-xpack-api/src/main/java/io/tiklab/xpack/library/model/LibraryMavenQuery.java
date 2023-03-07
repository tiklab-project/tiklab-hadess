package io.tiklab.xpack.library.model;


import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;


@ApiModel
public class LibraryMavenQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="libraryId",desc = "制品id")
        private String libraryId;


        @ApiProperty(name ="groupId",desc = "groupId")
        private String groupId;

        @ApiProperty(name ="artifactId",desc = "artifactId")
        private String artifactId;

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

        public LibraryMavenQuery setLibraryId(String libraryId) {
            this.libraryId = libraryId;
            return this;
        }

        public String getGroupId() {
            return groupId;
        }

        public LibraryMavenQuery setGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public LibraryMavenQuery setArtifactId(String artifactId) {
            this.artifactId = artifactId;
            return this;
        }
}