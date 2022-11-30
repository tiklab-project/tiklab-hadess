package net.tiklab.oms.ssm.controller;

//import net.tiklab.apibox.annotation.Api;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.message.setting.model.MessageType;
import net.tiklab.message.setting.model.MessageTypeQuery;
import net.tiklab.message.setting.service.MessageTypeService;
import net.tiklab.rpc.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ManagerController
 * Created by Zhangzhihua on 2017/9/25.
 */
@RestController
@RequestMapping("/ocs/message/messageType")
//@Api(name = "MessageTypeController",desc = "消息类型管理")
public class OcsMessageTypeController {

    private static Logger logger = LoggerFactory.getLogger(OcsMessageTypeController.class);

    @Resource(name="ocsMessageTypeService")
    private MessageTypeService ocsMessageTypeService;

    @RequestMapping(path="/createMessageType",method = RequestMethod.POST)
    //@ApiMethod(name = "createMessageType",desc = "createMessageType")
    //@ApiParam(name = "messageType",desc = "messageType",required = true)
    public Result<String> createMessageType(@RequestBody @NotNull @Valid MessageType messageType){
        String id = ocsMessageTypeService.createMessageType(messageType);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateMessageType",method = RequestMethod.POST)
    //@ApiMethod(name = "updateMessageType",desc = "updateMessageType")
    //@ApiParam(name = "messageType",desc = "messageType",required = true)
    public Result<Void> updateMessageType(@RequestBody @NotNull @Valid MessageType messageType){
        ocsMessageTypeService.updateMessageType(messageType);

        return Result.ok();
    }

    @RequestMapping(path="/deleteMessageType",method = RequestMethod.POST)
    //@ApiMethod(name = "deleteMessageType",desc = "deleteMessageType")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteMessageType(@NotNull String id){
        ocsMessageTypeService.deleteMessageType(id);

        return Result.ok();
    }

    @RequestMapping(path="/findMessageType",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageType",desc = "findMessageType")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<MessageType> findMessageType(@NotNull String id){
        MessageType messageType = ocsMessageTypeService.findMessageType(id);

        return Result.ok(messageType);
    }

    @RequestMapping(path="/findAllMessageType",method = RequestMethod.POST)
    //@ApiMethod(name = "findAllMessageType",desc = "findAllMessageType")
    public Result<List<MessageType>> findAllMessageType(){
        List<MessageType> messageTypeList = ocsMessageTypeService.findAllMessageType();

        return Result.ok(messageTypeList);
    }


    @RequestMapping(path = "/findMessageTypeList",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageTypeList",desc = "findMessageTypeList")
    //@ApiParam(name = "messageTypeQuery",desc = "messageTypeQuery",required = true)
    public Result<List<MessageType>> findMessageTypeList(@RequestBody @Valid @NotNull MessageTypeQuery messageTypeQuery){
        List<MessageType> messageTypeList = ocsMessageTypeService.findMessageTypeList(messageTypeQuery);

        return Result.ok(messageTypeList);
    }


    @RequestMapping(path = "/findMessageTypePage",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageTypePage",desc = "findMessageTypePage")
    //@ApiParam(name = "messageTypeQuery",desc = "messageTypeQuery",required = true)
    public Result<Pagination<MessageType>> findMessageTypePage(@RequestBody @Valid @NotNull MessageTypeQuery messageTypeQuery){
        Pagination<MessageType> pagination = ocsMessageTypeService.findMessageTypePage(messageTypeQuery);

        return Result.ok(pagination);
    }

}
