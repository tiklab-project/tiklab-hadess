package  net.tiklab.oms.subscribe.controller;

import  net.tiklab.core.Result;
import  net.tiklab.core.page.Pagination;
import  net.tiklab.postin.annotation.Api;
import  net.tiklab.postin.annotation.ApiMethod;
import  net.tiklab.postin.annotation.ApiParam;
import  net.tiklab.rpc.annotation.Reference;
import  net.tiklab.subscribe.subscribe.model.SubscribeRecord;
import  net.tiklab.subscribe.subscribe.model.SubscribeRecordQuery;
import  net.tiklab.subscribe.subscribe.service.SubscribeRecordService;
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
 * SubscribeRecordController
 */
@RestController
@RequestMapping("/subscribeRecord")
@Api(name = "SubscribeRecordController",desc = "订阅记录表")
public class SubscribeRecordController {

    private static Logger logger = LoggerFactory.getLogger(SubscribeRecordController.class);

    @Autowired
    @Reference(address = "${homes.address}")
    private SubscribeRecordService subscribeRecordService;

    @RequestMapping(path="/createSubscribeRecord",method = RequestMethod.POST)
    @ApiMethod(name = "createSubscribeRecord",desc = "创建订阅记录表")
    @ApiParam(name = "subscribeRecord",desc = "subscribeRecord",required = true)
    public Result<String> createSubscribeRecord(@RequestBody @NotNull @Valid SubscribeRecord subscribeRecord){
        String id = subscribeRecordService.createSubscribeRecord(subscribeRecord);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateSubscribeRecord",method = RequestMethod.POST)
    @ApiMethod(name = "updateSubscribeRecord",desc = "修改订阅记录表")
    @ApiParam(name = "subscribeRecord",desc = "subscribeRecord",required = true)
    public Result<Void> updateSubscribeRecord(@RequestBody @NotNull @Valid SubscribeRecord subscribeRecord){
        subscribeRecordService.updateSubscribeRecord(subscribeRecord);

        return Result.ok();
    }

    @RequestMapping(path="/deleteSubscribeRecord",method = RequestMethod.POST)
    @ApiMethod(name = "deleteSubscribeRecord",desc = "删除")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteSubscribeRecord(@NotNull String id){
        subscribeRecordService.deleteSubscribeRecord(id);

        return Result.ok();
    }

    @RequestMapping(path="/findSubscribeRecord",method = RequestMethod.POST)
    @ApiMethod(name = "findSubscribeRecord",desc = "通过id查询")
    @ApiParam(name = "id",desc = "id",required = true)
    public Result<SubscribeRecord> findSubscribeRecord(@NotNull String id){
        SubscribeRecord subscribeRecord = subscribeRecordService.findSubscribeRecord(id);

        return Result.ok(subscribeRecord);
    }

    @RequestMapping(path="/findAllSubscribeRecord",method = RequestMethod.POST)
    @ApiMethod(name = "findAllSubscribeRecord",desc = "查询所有")
    public Result<List<SubscribeRecord>> findAllSubscribeRecord(){
        List<SubscribeRecord> subscribeRecordList = subscribeRecordService.findAllSubscribeRecord();

        return Result.ok(subscribeRecordList);
    }

    @RequestMapping(path = "/findSubscribeRecordList",method = RequestMethod.POST)
    @ApiMethod(name = "findSubscribeRecordList",desc = "条件查询")
    @ApiParam(name = "subscribeRecordQuery",desc = "subscribeRecordQuery",required = true)
    public Result<List<SubscribeRecord>> findSubscribeRecordList(@RequestBody @Valid @NotNull SubscribeRecordQuery subscribeRecordQuery){
        List<SubscribeRecord> subscribeRecordList = subscribeRecordService.findSubscribeRecordList(subscribeRecordQuery);

        return Result.ok(subscribeRecordList);
    }

    @RequestMapping(path = "/findSubscribeRecordPage",method = RequestMethod.POST)
    @ApiMethod(name = "findSubscribeRecordPage",desc = "条件分页查询")
    @ApiParam(name = "subscribeRecordQuery",desc = "subscribeRecordQuery",required = true)
    public Result<Pagination<SubscribeRecord>> findSubscribeRecordPage(@RequestBody @Valid @NotNull SubscribeRecordQuery subscribeRecordQuery){
        Pagination<SubscribeRecord> pagination = subscribeRecordService.findSubscribeRecordPage(subscribeRecordQuery);

        return Result.ok(pagination);
    }

    @RequestMapping(path="/createSubscribeRecordByOrder",method = RequestMethod.POST)
    @ApiMethod(name = "deleteSubscribeRecord",desc = " 支付成功后创建订阅数据")
    @ApiParam(name = "orderId",desc = "orderId",required = true)
    public Result<Void> createSubscribeRecordByOrder(@NotNull String orderId){
        subscribeRecordService.createSubscribeRecordByOrder(orderId);

        return Result.ok();
    }



}
