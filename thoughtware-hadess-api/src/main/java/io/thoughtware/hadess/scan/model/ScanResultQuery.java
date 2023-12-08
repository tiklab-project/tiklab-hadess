package io.thoughtware.hadess.scan.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class ScanResultQuery {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="libraryId",desc = "制品的id 本地制品id、依赖制品的id")
        private String libraryId;

        @ApiProperty(name ="scanLibraryId",desc = "扫描制品的id")
        private String scanLibraryId;

        @ApiProperty(name ="scanRecordId",desc = "扫描制品结果的id")
        private String scanRecordId;


        @ApiProperty(name ="holeType",desc = "漏洞类型 本地制品：scanLibrary、依赖：relyLibrary")
        private String holeType;

        @ApiProperty(name="holeLevel",desc="漏洞等级 1-4递减")
        private Integer holeLevel;

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

        public ScanResultQuery setLibraryId(String libraryId) {
            this.libraryId = libraryId;
            return this;
        }

        public String getHoleType() {
            return holeType;
        }

        public ScanResultQuery setHoleType(String holeType) {
            this.holeType = holeType;
            return this;
        }

        public String getScanLibraryId() {
            return scanLibraryId;
        }

        public ScanResultQuery setScanLibraryId(String scanLibraryId) {
            this.scanLibraryId = scanLibraryId;
            return this;
        }

        public Integer getHoleLevel() {
            return holeLevel;
        }

        public ScanResultQuery setHoleLevel(Integer holeLevel) {
            this.holeLevel = holeLevel;
            return this;
        }

        public String getScanRecordId() {
            return scanRecordId;
        }

        public ScanResultQuery setScanRecordId(String scanRecordId) {
            this.scanRecordId = scanRecordId;
            return this;
        }
}