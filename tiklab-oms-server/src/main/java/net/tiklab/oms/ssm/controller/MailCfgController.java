package net.tiklab.oms.ssm.controller;


//import net.tiklab.apibox.annotation.Api;
//import net.tiklab.apibox.annotation.ApiMethod;
//import net.tiklab.apibox.annotation.ApiParam;

import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.message.mail.modal.MailCfg;
import net.tiklab.message.mail.modal.MailCfgQuery;
import net.tiklab.message.mail.modal.MailQuery;
import net.tiklab.message.mail.service.MailCfgService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.rpc.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/message/mailcfg")
@Api(name = "MailCfgController",desc = "邮箱配置")
public class MailCfgController {

    @Autowired
    @Reference(address = "${ocs.address}")
    private MailCfgService mailCfgService;

    @RequestMapping(path = "/findMailCfgPage",method = RequestMethod.POST)
    //@ApiMethod(name = "findMailCfgPage",desc = "分页查询邮箱配置数据")
    //@ApiParam(name = "mailCfgQuery",desc = "mailCfgQuery",required = true)
    public Result<Pagination<MailCfg>> findMailCfgPage(@RequestBody @Valid @NotNull MailCfgQuery mailCfgQuery) {
        Pagination<MailCfg> pagination = mailCfgService.findMailCfgPage(mailCfgQuery);
        return Result.ok(pagination);
    }

    @RequestMapping(path = "config", method = RequestMethod.POST)
    //@ApiMethod(name = "config",desc = "配置邮箱参数")
    //@ApiParam(name = "mailCfg",desc = "message",required = true)
    public Result<String> setMailConfig(@RequestBody @NotNull @Valid MailCfg mailCfg){
        MailQuery mailQuery = new MailQuery();
        mailQuery.setUserName(mailCfg.getUserName());
        MailCfg hasAccount = mailCfgService.findMailAccount(mailQuery);
        if (hasAccount !=null) {
            return Result.error(6000, "存在邮箱账号");
        }
        String id = mailCfgService.createMailConfig(mailCfg);
        return Result.ok(id);
    }


    @RequestMapping(path = "delete", method = RequestMethod.POST)
    //@ApiMethod(name = "delete",desc = "删除配置邮箱")
    //@ApiParam(name = "id",desc = "message config id",required = true)
    public Result<Void> setMailConfig(@NotNull @Valid String id){
        mailCfgService.deleteMailCfg(id);
        return Result.ok();
    }

    @RequestMapping(path = "update", method = RequestMethod.POST)
    //@ApiMethod(name = "update",desc = "更新配置邮箱")
    //@ApiParam(name = "mailCfg",desc = "mailCfg",required = true)
    public Result<Void> updateMailCfg(@RequestBody @NotNull @Valid MailCfg mailCfg) {
        MailQuery mailQuery = new MailQuery();
        mailQuery.setUserName(mailCfg.getUserName());
        MailCfg hasAccount = mailCfgService.findMailAccount(mailQuery);
        if (hasAccount !=null) {
            if (!hasAccount.getId().equals(mailCfg.getId())) {
                return Result.error(6000, "存在邮箱账号");
            }
        }
        mailCfgService.updateMailCfg(mailCfg);
        return Result.ok();
    }

}
