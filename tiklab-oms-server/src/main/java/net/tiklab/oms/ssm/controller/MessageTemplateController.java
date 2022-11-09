package net.tiklab.oms.ssm.controller;

//import net.tiklab.apibox.annotation.Api;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.message.message.model.MessageTemplate;
import net.tiklab.message.message.model.MessageTemplateQuery;
import net.tiklab.message.message.service.MessageTemplateService;
import net.tiklab.rpc.annotation.Reference;
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
 * ManagerController
 * Created by Zhangzhihua on 2017/9/25.
 */
@RestController
@RequestMapping("/message/messageTemplate")
//@Api(name = "MessageTemplateController",desc = "消息模板管理")
public class MessageTemplateController {

    private static Logger logger = LoggerFactory.getLogger(MessageTemplateController.class);

    @Autowired
    @Reference(address = "${ocs.address}")
    private MessageTemplateService messageTemplateService;

    @RequestMapping(path="/createMessageTemplate",method = RequestMethod.POST)
    //@ApiMethod(name = "createMessageTemplate",desc = "createMessageTemplate")
    //@ApiParam(name = "messageTemplate",desc = "messageTemplate",required = true)
    public Result<String> createMessageTemplate(@RequestBody @NotNull @Valid MessageTemplate messageTemplate){
        String id = messageTemplateService.createMessageTemplate(messageTemplate);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateMessageTemplate",method = RequestMethod.POST)
    //@ApiMethod(name = "updateMessageTemplate",desc = "updateMessageTemplate")
    //@ApiParam(name = "messageTemplate",desc = "messageTemplate",required = true)
    public Result<Void> updateMessageTemplate(@RequestBody @NotNull @Valid MessageTemplate messageTemplate){
        messageTemplateService.updateMessageTemplate(messageTemplate);

        return Result.ok();
    }

    @RequestMapping(path="/deleteMessageTemplate",method = RequestMethod.POST)
    //@ApiMethod(name = "deleteMessageTemplate",desc = "deleteMessageTemplate")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteMessageTemplate(@NotNull String id){
        messageTemplateService.deleteMessageTemplate(id);

        return Result.ok();
    }

    @RequestMapping(path="/findMessageTemplate",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageTemplate",desc = "findMessageTemplate")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<MessageTemplate> findMessageTemplate(@NotNull String id){
        MessageTemplate messageTemplate = messageTemplateService.findMessageTemplate(id);

        return Result.ok(messageTemplate);
    }

    @RequestMapping(path="/findAllMessageTemplate",method = RequestMethod.POST)
    //@ApiMethod(name = "findAllMessageTemplate",desc = "findAllMessageTemplate")
    public Result<List<MessageTemplate>> findAllMessageTemplate(){
        List<MessageTemplate> messageTemplateList = messageTemplateService.findAllMessageTemplate();

        return Result.ok(messageTemplateList);
    }


    @RequestMapping(path = "/findMessageTemplateList",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageTemplateList",desc = "findMessageTemplateList")
    //@ApiParam(name = "messageTemplateQuery",desc = "messageTemplateQuery",required = true)
    public Result<List<MessageTemplate>> findMessageTemplateList(@RequestBody @Valid @NotNull MessageTemplateQuery messageTemplateQuery){
        List<MessageTemplate> messageTemplateList = messageTemplateService.findMessageTemplateList(messageTemplateQuery);

        return Result.ok(messageTemplateList);
    }


    @RequestMapping(path = "/findMessageTemplatePage",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageTemplatePage",desc = "findMessageTemplatePage")
    //@ApiParam(name = "messageTemplateQuery",desc = "messageTemplateQuery",required = true)
    public Result<Pagination<MessageTemplate>> findMessageTemplatePage(@RequestBody @Valid @NotNull MessageTemplateQuery messageTemplateQuery){
        Pagination<MessageTemplate> pagination = messageTemplateService.findMessageTemplatePage(messageTemplateQuery);

        return Result.ok(pagination);
    }

}
