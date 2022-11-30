package net.tiklab.oms.rpcConfig;

import net.tiklab.message.mail.service.MailCfgService;
import net.tiklab.message.message.model.Message;
import net.tiklab.message.message.service.MessageService;
import net.tiklab.message.message.service.MessageTemplateService;
import net.tiklab.message.setting.service.MessageSendTypeService;
import net.tiklab.message.setting.service.MessageTypeService;
import net.tiklab.rpc.client.RpcClient;
import net.tiklab.rpc.client.config.RpcClientConfig;
import net.tiklab.rpc.client.router.lookup.FixedLookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.Authenticator;


@Component
public class RpcConfig {

    @Value("${ocs.address:null}")
    String authServiceUrl;

    //rpc配置
    RpcClient rpcClient(){
        RpcClientConfig rpcClientConfig = RpcClientConfig.instance();
        RpcClient rpcClient = new RpcClient(rpcClientConfig);
        return rpcClient;
    }


    @Bean(value = "ocsMailCfgService",autowireCandidate = false)
    MailCfgService ocsMailCfgService(){
        return rpcClient().getBean(MailCfgService.class, new FixedLookup(authServiceUrl));
    }

    @Bean(value = "ocsMessageService",autowireCandidate = false)
    MessageService ocsMessageService(){
        return rpcClient().getBean(MessageService.class, new FixedLookup(authServiceUrl));
    }

    @Bean(value = "ocsMessageSendTypeService",autowireCandidate = false)
    MessageSendTypeService ocsMessageSendTypeService(){
        return rpcClient().getBean(MessageSendTypeService.class, new FixedLookup(authServiceUrl));
    }

    @Bean(value = "ocsMessageTemplateService" ,autowireCandidate = false)
    MessageTemplateService ocsMessageTemplateService(){
        return rpcClient().getBean(MessageTemplateService.class, new FixedLookup(authServiceUrl));
    }

    @Bean(value = "ocsMessageTypeService",autowireCandidate = false)
    MessageTypeService ocsMessageTypeService(){
        return rpcClient().getBean(MessageTypeService.class, new FixedLookup(authServiceUrl));
    }
}
