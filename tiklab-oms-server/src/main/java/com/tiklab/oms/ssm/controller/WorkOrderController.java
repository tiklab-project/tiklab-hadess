package com.tiklab.oms.ssm.controller;

import com.tiklab.postlink.annotation.Api;
import com.tiklab.postlink.annotation.ApiMethod;
import com.tiklab.postlink.annotation.ApiParam;
import com.tiklab.core.Result;
import com.tiklab.core.page.Pagination;
import com.tiklab.rpc.annotation.Reference;
import com.tiklab.ssm.workorder.model.WorkOrder;
import com.tiklab.ssm.workorder.model.WorkOrderQuery;
import com.tiklab.ssm.workorder.service.WorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * WorkOrderController
 */
@RestController
@RequestMapping("/workOrder")
@Api(name = "WorkOrderController",desc = "工单管理")
public class WorkOrderController {

    private static Logger logger = LoggerFactory.getLogger(WorkOrderController.class);

    @Autowired
    @Reference(address = "${ocs.service.address}")
    private WorkOrderService workOrderService;

    @RequestMapping(path="/createWorkOrder",method = RequestMethod.POST)
    @ApiMethod(name = "createWorkOrder",desc = "创建工单")
    @ApiParam(name = "workOrder",desc = "workOrder",required = true)
    public Result<String> createWorkOrder(@RequestBody @NotNull @Valid WorkOrder workOrder){
        String id = workOrderService.createWorkOrder(workOrder);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateWorkOrder",method = RequestMethod.POST)
    @ApiMethod(name = "updateWorkOrder",desc = "修改工单")
    @ApiParam(name = "workOrder",desc = "workOrder",required = true)
    public Result<Void> updateWorkOrder(@RequestBody @NotNull @Valid WorkOrder workOrder){
        workOrderService.updateWorkOrder(workOrder);

        return Result.ok();
    }

    @RequestMapping(path="/deleteWorkOrder",method = RequestMethod.POST)
    @ApiMethod(name = "deleteWorkOrder",desc = "删除工单")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteWorkOrder(@NotNull String id){
        workOrderService.deleteWorkOrder(id);

        return Result.ok();
    }

    @RequestMapping(path="/findWorkOrder",method = RequestMethod.POST)
    @ApiMethod(name = "findWorkOrder",desc = "通过id查询工单")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<WorkOrder> findWorkOrder(@NotNull String id){
        WorkOrder workOrder = workOrderService.findWorkOrder(id);

        return Result.ok(workOrder);
    }

    @RequestMapping(path="/findAllWorkOrder",method = RequestMethod.POST)
    @ApiMethod(name = "findAllWorkOrder",desc = "查询所有工单")
    public Result<List<WorkOrder>> findAllWorkOrder(){
        List<WorkOrder> workOrderList = workOrderService.findAllWorkOrder();

        return Result.ok(workOrderList);
    }

    @RequestMapping(path = "/findWorkOrderList",method = RequestMethod.POST)
    @ApiMethod(name = "findWorkOrderList",desc = "条件查询工单")
    @ApiParam(name = "workOrderQuery",desc = "workOrderQuery",required = true)
    public Result<List<WorkOrder>> findWorkOrderList(@RequestBody @Valid @NotNull WorkOrderQuery workOrderQuery){
        List<WorkOrder> workOrderList = workOrderService.findWorkOrderList(workOrderQuery);

        return Result.ok(workOrderList);
    }

    @RequestMapping(path = "/findWorkOrderPage",method = RequestMethod.POST)
    @ApiMethod(name = "findWorkOrderPage",desc = "分页查询工单")
    @ApiParam(name = "workOrderQuery",desc = "workOrderQuery",required = true)
    public Result<Pagination<WorkOrder>> findWorkOrderPage(@RequestBody @Valid @NotNull WorkOrderQuery workOrderQuery){
        Pagination<WorkOrder> pagination = workOrderService.findWorkOrderPage(workOrderQuery);

        return Result.ok(pagination);
    }

}
