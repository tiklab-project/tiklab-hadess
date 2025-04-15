package io.tiklab.hadess.library.model;

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

        @ApiProperty(name ="repositoryId",desc = "制品库id")
        private String repositoryId;

        @ApiProperty(name ="libraryId",desc = "制品id")
        private String libraryId;

        @ApiProperty(name ="libraryVersionId",desc = "制品版本id")
        private String libraryVersionId;

        @ApiProperty(name ="fileName",desc = "制品文件文件")
        private String fileName;

        @ApiProperty(name ="fileUrl",desc = "制品文件地址")
        private String fileUrl;

        @ApiProperty(name ="relativePath",desc = "制品文件相对路径")
        private String relativePath;

        @ApiProperty(name ="findNameWay",desc = "查询文件名字的方式 默认是eq、like: 模糊匹配")
        private String findNameWay;

        @ApiProperty(name ="snapshotVersion",desc = "快照版本的时间戳")
        private String snapshotVersion;





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

        public LibraryFileQuery setLibraryId(String libraryId) {
            this.libraryId = libraryId;
            return this;
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

        public String getRelativePath() {
            return relativePath;
        }

        public LibraryFileQuery setRelativePath(String relativePath) {
            this.relativePath = relativePath;
            return this;
        }

        public String getRepositoryId() {
            return repositoryId;
        }

        public LibraryFileQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }

        public String getFindNameWay() {
            return findNameWay;
        }

        public LibraryFileQuery setFindNameWay(String findNameWay) {
            this.findNameWay = findNameWay;
            return this;
        }

    public String getSnapshotVersion() {
        return snapshotVersion;
    }

    public void setSnapshotVersion(String snapshotVersion) {
        this.snapshotVersion = snapshotVersion;
    }
}