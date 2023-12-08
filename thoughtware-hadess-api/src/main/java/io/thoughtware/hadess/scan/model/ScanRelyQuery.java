package io.thoughtware.hadess.scan.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class ScanRelyQuery {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="scanLibraryId",desc = "扫描依赖id")
        private String scanLibraryId;

        @ApiProperty(name ="scanRecordId",desc = "扫描记录id")
        private String scanRecordId;

        @ApiProperty(name ="relyOneId",desc = "第一层依赖id")
        private String relyOneId;

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


        public String getScanLibraryId() {
            return scanLibraryId;
        }

        public ScanRelyQuery setScanLibraryId(String scanLibraryId) {
            this.scanLibraryId = scanLibraryId;
            return this;
        }

        public String getRelyOneId() {
            return relyOneId;
        }

        public void setRelyOneId(String relyOneId) {
            this.relyOneId = relyOneId;
        }

        public Integer getHoleLevel() {
            return holeLevel;
        }

        public void setHoleLevel(Integer holeLevel) {
            this.holeLevel = holeLevel;
        }

        public String getScanRecordId() {
            return scanRecordId;
        }

        public ScanRelyQuery setScanRecordId(String scanRecordId) {
            this.scanRecordId = scanRecordId;
            return this;
        }
}