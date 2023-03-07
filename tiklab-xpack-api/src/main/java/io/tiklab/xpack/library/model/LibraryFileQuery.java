package io.tiklab.xpack.library.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class LibraryFileQuery {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("createTime").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="libraryId",desc = "制品id")
        private String libraryId;

        @ApiProperty(name ="libraryVersionId",desc = "制品版本id")
        private String libraryVersionId;

        @ApiProperty(name ="fileName",desc = "制品文件文件")
        private String fileName;

        @ApiProperty(name ="fileUrl",desc = "制品文件地址")
        private String fileUrl;

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

        public void setLibraryId(String libraryId) {
            this.libraryId = libraryId;
        }

        public String getLibraryVersionId() {
            return libraryVersionId;
        }

        public LibraryFileQuery setLibraryVersionId(String libraryVersionId) {
            this.libraryVersionId = libraryVersionId;
            return this;
        }

        public String getFileName() {
            return fileName;
        }

        public LibraryFileQuery setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public LibraryFileQuery setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }
}