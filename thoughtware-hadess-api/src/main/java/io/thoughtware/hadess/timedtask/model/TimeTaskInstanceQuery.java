package io.thoughtware.hadess.timedtask.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiProperty;

import java.util.List;

public class TimeTaskInstanceQuery {

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam = new Page();

    @ApiProperty(name ="timeTaskId",desc = "定时任务id")
    private String timeTaskId ;

    @ApiProperty(name ="cron",desc = "表达式")
    private String cron ;

    @ApiProperty(name ="weekDay",desc = "周数")
    private Integer weekDay ;

    @ApiProperty(name ="execTime",desc = "执行时间")
    private String execTime ;
    @ApiProperty(name ="execObjectId",desc = "执行对象的id")
    private String execObjectId ;

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

    public String getTimeTaskId() {
        return timeTaskId;
    }

    public TimeTaskInstanceQuery setTimeTaskId(String timeTaskId) {
        this.timeTaskId = timeTaskId;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public TimeTaskInstanceQuery setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public String getExecObjectId() {
        return execObjectId;
    }

    public TimeTaskInstanceQuery setExecObjectId(String execObjectId) {
        this.execObjectId = execObjectId;
        return this;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public TimeTaskInstanceQuery setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
        return this;
    }

    public String getExecTime() {
        return execTime;
    }

    public TimeTaskInstanceQuery setExecTime(String execTime) {
        this.execTime = execTime;
        return this;
    }
}
