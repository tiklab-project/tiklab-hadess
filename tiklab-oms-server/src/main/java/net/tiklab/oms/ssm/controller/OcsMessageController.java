package net.tiklab.oms.ssm.controller;

//import net.tiklab.apibox.annotation.Api;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.message.message.model.Message;
import net.tiklab.message.message.model.MessageQuery;
import net.tiklab.message.message.service.MessageService;
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
@RequestMapping("/ocs/message/message")
//@Api(name = "MessageController",desc = "消息管理")
public class OcsMessageController {

    private static Logger logger = LoggerFactory.getLogger(OcsMessageController.class);

    @Resource(name="ocsMessageService")
    private MessageService ocsMessageService;

    @RequestMapping(path="/createMessage",method = RequestMethod.POST)
    //@ApiMethod(name = "createMessage",desc = "createMessage")
    //@ApiParam(name = "message",desc = "message",required = true)
    public Result<String> createMessage(@RequestBody @NotNull @Valid Message message){
        String id = ocsMessageService.createMessage(message);

        return Result.ok(id);
    }

    @RequestMapping(path="/sendMessage",method = RequestMethod.POST)
    //@ApiMethod(name = "sendMessage",desc = "发送消息")
    //@ApiParam(name = "message",desc = "message",required = true)
    public Result<String> sendMessage(@RequestBody @NotNull @Valid Message message){
        String id = ocsMessageService.sendMessage(message);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateMessage",method = RequestMethod.POST)
    //@ApiMethod(name = "updateMessage",desc = "updateMessage")
    //@ApiParam(name = "message",desc = "message",required = true)
    public Result<Void> updateMessage(@RequestBody @NotNull @Valid Message message){
        ocsMessageService.updateMessage(message);

        return Result.ok();
    }

    @RequestMapping(path="/deleteMessage",method = RequestMethod.POST)
    //@ApiMethod(name = "deleteMessage",desc = "deleteMessage")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteMessage(@NotNull String id){
        ocsMessageService.deleteMessage(id);

        return Result.ok();
    }

    @RequestMapping(path="/findMessage",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessage",desc = "findMessage")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<Message> findMessage(@NotNull String id){
        Message message = ocsMessageService.findMessage(id);

        return Result.ok(message);
    }


    @RequestMapping(path="/findMessageWithNest",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageWithNest",desc = "findMessageWithNest")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<Message> findMessageWithNest(@NotNull String id){
        Message message = ocsMessageService.findMessageWithNest(id);

        return Result.ok(message);
    }

    @RequestMapping(path="/findAllMessage",method = RequestMethod.POST)
    //@ApiMethod(name = "findAllMessage",desc = "findAllMessage")
    public Result<List<Message>> findAllMessage(){
        List<Message> messageList = ocsMessageService.findAllMessage();

        return Result.ok(messageList);
    }


    @RequestMapping(path = "/findMessageList",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageList",desc = "findMessageList")
    //@ApiParam(name = "messageQuery",desc = "messageQuery",required = true)
    public Result<List<Message>> findMessageList(@RequestBody @Valid @NotNull MessageQuery messageQuery){
        List<Message> messageList = ocsMessageService.findMessageList(messageQuery);

        return Result.ok(messageList);
    }


    @RequestMapping(path = "/findMessagePage",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessagePage",desc = "findMessagePage")
    //@ApiParam(name = "messageQuery",desc = "messageQuery",required = true)
    public Result<Pagination<Message>> findMessagePage(@RequestBody @Valid @NotNull MessageQuery messageQuery){
        Pagination<Message> pagination = ocsMessageService.findMessagePage(messageQuery);

        return Result.ok(pagination);
    }

}
