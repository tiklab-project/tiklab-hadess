package  net.tiklab.oms.ssm.controller;

import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.ssm.workorder.model.WorkOrderReply;
import  net.tiklab.ssm.workorder.model.WorkOrderReplyQuery;
import  net.tiklab.ssm.workorder.service.WorkOrderReplyService;
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
 * WorkOrderReplyController
 */
@RestController
@RequestMapping("/workOrderReply")
@Api(name = "WorkOrderReplyController",desc = "工单回复管理")
public class WorkOrderReplyController {

    private static Logger logger = LoggerFactory.getLogger(WorkOrderReplyController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private WorkOrderReplyService workOrderReplyService;

    @RequestMapping(path="/createWorkOrderReply",method = RequestMethod.POST)
    @ApiMethod(name = "createWorkOrderReply",desc = "创建工单回复")
    @ApiParam(name = "workOrderReply",desc = "workOrderReply",required = true)
    public Result<String> createWorkOrderReply(@RequestBody @NotNull @Valid WorkOrderReply workOrderReply){
        String id = workOrderReplyService.createWorkOrderReply(workOrderReply);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateWorkOrderReply",method = RequestMethod.POST)
    @ApiMethod(name = "updateWorkOrderReply",desc = "修改")
    @ApiParam(name = "workOrderReply",desc = "workOrderReply",required = true)
    public Result<Void> updateWorkOrderReply(@RequestBody @NotNull @Valid WorkOrderReply workOrderReply){
        workOrderReplyService.updateWorkOrderReply(workOrderReply);

        return Result.ok();
    }

    @RequestMapping(path="/deleteWorkOrderReply",method = RequestMethod.POST)
    @ApiMethod(name = "deleteWorkOrderReply",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteWorkOrderReply(@NotNull String id){
        workOrderReplyService.deleteWorkOrderReply(id);

        return Result.ok();
    }

    @RequestMapping(path="/findWorkOrderReply",method = RequestMethod.POST)
    @ApiMethod(name = "findWorkOrderReply",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<WorkOrderReply> findWorkOrderReply(@NotNull String id){
        WorkOrderReply workOrderReply = workOrderReplyService.findWorkOrderReply(id);

        return Result.ok(workOrderReply);
    }

    @RequestMapping(path="/findAllWorkOrderReply",method = RequestMethod.POST)
    @ApiMethod(name = "findAllWorkOrderReply",desc = "查询所有")
    public Result<List<WorkOrderReply>> findAllWorkOrderReply(){
        List<WorkOrderReply> workOrderReplyList = workOrderReplyService.findAllWorkOrderReply();

        return Result.ok(workOrderReplyList);
    }

    @RequestMapping(path = "/findWorkOrderReplyList",method = RequestMethod.POST)
    @ApiMethod(name = "findWorkOrderReplyList",desc = "通过条件查询")
    @ApiParam(name = "workOrderReplyQuery",desc = "workOrderReplyQuery",required = true)
    public Result<List<WorkOrderReply>> findWorkOrderReplyList(@RequestBody @Valid @NotNull WorkOrderReplyQuery workOrderReplyQuery){
        List<WorkOrderReply> workOrderReplyList = workOrderReplyService.findWorkOrderReplyList(workOrderReplyQuery);

        return Result.ok(workOrderReplyList);
    }

    @RequestMapping(path = "/findWorkOrderReplyPage",method = RequestMethod.POST)
    @ApiMethod(name = "findWorkOrderReplyPage",desc = "分页条件条件")
    @ApiParam(name = "workOrderReplyQuery",desc = "workOrderReplyQuery",required = true)
    public Result<Pagination<WorkOrderReply>> findWorkOrderReplyPage(@RequestBody @Valid @NotNull WorkOrderReplyQuery workOrderReplyQuery){
        Pagination<WorkOrderReply> pagination = workOrderReplyService.findWorkOrderReplyPage(workOrderReplyQuery);

        return Result.ok(pagination);
    }

}
