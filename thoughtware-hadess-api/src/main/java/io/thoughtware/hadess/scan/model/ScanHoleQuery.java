package io.thoughtware.hadess.scan.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class ScanHoleQuery {

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().asc("holeLevel").get();

    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam = new Page();


    @ApiProperty(name="holeLevel",desc="漏洞等级")
    private Integer holeLevel;

    @ApiProperty(name="language",desc="语言")
    private String language;

    @ApiProperty(name="holeName",desc="漏洞名字")
    private String holeName;

    /*------其他字段--------*/

    @ApiProperty(name="scanSchemeId",desc="扫描方案id")
    private String scanSchemeId;

    @ApiProperty(name="holeLevelList",desc="漏洞等级List")
    private List<Integer> holeLevelList;

    @ApiProperty(name="sort",desc="排序 desc、asc")
    private String sort;

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

    public String getScanSchemeId() {
        return scanSchemeId;
    }

    public void setScanSchemeId(String scanSchemeId) {
        this.scanSchemeId = scanSchemeId;
    }

    public Integer getHoleLevel() {
        return holeLevel;
    }

    public void setHoleLevel(Integer holeLevel) {
        this.holeLevel = holeLevel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Integer> getHoleLevelList() {
        return holeLevelList;
    }

    public void setHoleLevelList(List<Integer> holeLevelList) {
        this.holeLevelList = holeLevelList;
    }

    public String getHoleName() {
        return holeName;
    }

    public void setHoleName(String holeName) {
        this.holeName = holeName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
