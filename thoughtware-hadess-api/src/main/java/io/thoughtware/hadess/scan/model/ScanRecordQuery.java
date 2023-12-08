package io.thoughtware.hadess.scan.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class ScanRecordQuery {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="scanLibraryId",desc = "扫描制品id")
        private String scanLibraryId;

        @ApiProperty(name ="scanPlayId",desc = "扫描计划的id")
        private String scanPlayId;

        @ApiProperty(name ="scanGroup",desc = "扫描组")
        private String scanGroup;



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

        public String getScanLibraryId() {
            return scanLibraryId;
        }

        public ScanRecordQuery setScanLibraryId(String scanLibraryId) {
            this.scanLibraryId = scanLibraryId;
            return this;
        }

        public String getScanPlayId() {
            return scanPlayId;
        }

        public void setScanPlayId(String scanPlayId) {
            this.scanPlayId = scanPlayId;
        }

        public String getScanGroup() {
            return scanGroup;
        }

        public ScanRecordQuery setScanGroup(String scanGroup) {
            this.scanGroup = scanGroup;
            return this;
        }
}