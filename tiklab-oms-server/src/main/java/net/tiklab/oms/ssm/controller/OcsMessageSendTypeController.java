package net.tiklab.oms.ssm.controller;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.message.setting.model.MessageSendType;
import net.tiklab.message.setting.model.MessageSendTypeQuery;
import net.tiklab.message.setting.service.MessageSendTypeService;
import net.tiklab.rpc.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/ocs/message/messageSendType")
public class OcsMessageSendTypeController {

    @Resource(name="ocsMessageSendTypeService")
    private MessageSendTypeService ocsMessageSendTypeService;

    @RequestMapping(path="/createMessageSendType",method = RequestMethod.POST)
    //@ApiMethod(name = "createMessageSendType",desc = "createMessageSendType")
    //@ApiParam(name = "messageSendType",desc = "messageSendType",required = true)
    public Result<String> createMessageSendType(@RequestBody @NotNull @Valid MessageSendType messageSendType){
        String id = ocsMessageSendTypeService.createMessageSendType(messageSendType);

        return Result.ok(id);
    }

    @RequestMapping(path="/updateMessageSendType",method = RequestMethod.POST)
    //@ApiMethod(name = "updateMessageSendType",desc = "updateMessageSendType")
    //@ApiParam(name = "messageSendType",desc = "messageSendType",required = true)
    public Result<Void> updateMessageSendType(@RequestBody @NotNull @Valid MessageSendType messageSendType){
        ocsMessageSendTypeService.updateMessageSendType(messageSendType);

        return Result.ok();
    }

    @RequestMapping(path="/deleteMessageSendType",method = RequestMethod.POST)
    //@ApiMethod(name = "deleteMessageSendType",desc = "deleteMessageSendType")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<Void> deleteMessageSendType(@NotNull String id){
        ocsMessageSendTypeService.deleteMessageSendType(id);

        return Result.ok();
    }

    @RequestMapping(path="/findMessageSendType",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageSendType",desc = "findMessageSendType")
    //@ApiParam(name = "id",desc = "id",required = true)
    public Result<MessageSendType> findMessageSendType(@NotNull String id){
        MessageSendType messageSendType = ocsMessageSendTypeService.findMessageSendType(id);

        return Result.ok(messageSendType);
    }

    @RequestMapping(path="/findAllMessageSendType",method = RequestMethod.POST)
    //@ApiMethod(name = "findAllMessageSendType",desc = "findAllMessageSendType")
    public Result<List<MessageSendType>> findAllMessageSendType(){
        List<MessageSendType> messageSendTypeList = ocsMessageSendTypeService.findAllMessageSendType();

        return Result.ok(messageSendTypeList);
    }


    @RequestMapping(path = "/findMessageSendTypeList",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageSendTypeList",desc = "findMessageSendTypeList")
    //@ApiParam(name = "messageSendTypeQuery",desc = "messageSendTypeQuery",required = true)
    public Result<List<MessageSendType>> findMessageSendTypeList(@RequestBody @Valid @NotNull MessageSendTypeQuery messageSendTypeQuery){
        List<MessageSendType> messageSendTypeList = ocsMessageSendTypeService.findMessageSendTypeList(messageSendTypeQuery);

        return Result.ok(messageSendTypeList);
    }


    @RequestMapping(path = "/findMessageSendTypePage",method = RequestMethod.POST)
    //@ApiMethod(name = "findMessageSendTypePage",desc = "findMessageSendTypePage")
    //@ApiParam(name = "messageSendTypeQuery",desc = "messageSendTypeQuery",required = true)
    public Result<Pagination<MessageSendType>> findMessageSendTypePage(@RequestBody @Valid @NotNull MessageSendTypeQuery messageSendTypeQuery){
        Pagination<MessageSendType> pagination = ocsMessageSendTypeService.findMessageSendTypePage(messageSendTypeQuery);

        return Result.ok(pagination);
    }
}
