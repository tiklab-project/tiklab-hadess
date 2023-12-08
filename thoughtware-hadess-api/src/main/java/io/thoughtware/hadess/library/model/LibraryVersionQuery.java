package io.thoughtware.hadess.library.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel
public class LibraryVersionQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("createTime").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="repositoryId",desc = "制品库id")
        private String repositoryId;

        @ApiProperty(name ="libraryId",desc = "制品id")
        private String libraryId;

        @ApiProperty(name ="version",desc = "版本")
        private String version;

        @ApiProperty(name ="libraryType",desc = "制品类型")
        private String libraryType;

        @ApiProperty(name ="currentVersionId",desc = "当前版本id")
        private String currentVersionId;

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

        public LibraryVersionQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }

        public String getLibraryId() {
            return libraryId;
        }

        public LibraryVersionQuery setLibraryId(String libraryId) {
            this.libraryId = libraryId;
            return this;
        }

        public String getVersion() {
            return version;
        }

        public LibraryVersionQuery setVersion(String version) {
            this.version = version;
            return this;
        }

        public String getLibraryType() {
            return libraryType;
        }

        public LibraryVersionQuery setLibraryType(String libraryType) {
            this.libraryType = libraryType;
            return this;
        }

    public String getCurrentVersionId() {
        return currentVersionId;
    }

    public void setCurrentVersionId(String currentVersionId) {
        this.currentVersionId = currentVersionId;
    }
}