package io.tiklab.hadess.starter.config;


import io.tiklab.eam.author.Authenticator;

import io.tiklab.eam.client.author.handler.DefaultAuthorHandler;
import io.tiklab.gateway.config.GatewayConfig;
import io.tiklab.gateway.config.IgnoreConfig;
import io.tiklab.gateway.config.IgnoreConfigBuilder;
import io.tiklab.gateway.handler.author.AuthorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayFilterAutoConfiguration {


    @Bean
    AuthorHandler authorHandler(Authenticator authenticator, IgnoreConfig ignoreConfig){
        DefaultAuthorHandler authorHandler = new DefaultAuthorHandler();
        authorHandler.setAuthenticator(authenticator);
        authorHandler.setIgnoreConfig(ignoreConfig);

        return authorHandler;
    }

    @Bean
    GatewayConfig gatewayConfig(IgnoreConfig ignoreConfig){
        GatewayConfig gatewayConfig = new GatewayConfig();
        gatewayConfig.setIgnoreConfig(ignoreConfig);

        return gatewayConfig;
    }

    @Bean
    //许行配置
    public IgnoreConfig ignoreConfig(){
        return IgnoreConfigBuilder.instance()
                .ignoreTypes(new String[]{
                        ".ico",
                        ".jpg",
                        ".jpeg",
                        ".png",
                        ".gif",
                        ".html",
                        ".js",
                        ".css",
                        ".json",
                        ".xml",
                        ".ftl",
                        ".txt"
                })
                .ignoreUrls(new String[]{
                        "/",
                        "/eam/auth/login",
                        "/eam/auth/logout",
                        "/eam/auth/valid",
                        "/note/vaildSmsCode",
                        "/note/sendSmsCode",
                        "/passport/member/login",
                        "/subscribe/findSubscribePrice",
                        "/wechatCallback/data",
                        "/wechatCallback/instruct",
                        "/wechatCallback/getUserinfo3rd",
                        "/wechatapplication/getTenant",
                        "/wechatapplication/create",
                        "/wechatapplication/syncdata",
                        "/wechatapplication/vaildTentant",

                        "/dfs/upload",
                        "/uploadFile/ftpUpload",

                        "/message/mailcfg/config",
                        "/message/messageSendType/findMessageSendTypePage",
                        "/message/messageSendType/createMessageSendType",
                        "/message/messageTemplate/findMessageTemplateList",
                        "/passport/member/authCodeLogin",
                        "/passport/ent/user/login",
                        "/passport/person/wechat/findUserinfo",
                        "/passport/person/dingding/findUserinfo",
                        "/visit/addVisit",
                        "/ram/passport/login",
                        "/passport/login",
                        "/ram/passport/logout",
                        "/ram/passport/valid",
                        "/passport/third/login",
                        "/third/passport/logout",
                        "/third/passport/valid",
                        "/artifact/findNewArtifactPage",
                        "/sockjs-node/info",
                        "/eam/auth/login",
                        "/libraryFile/tag",
                        "/xpackRepository/findAllRepository",
                        "/version/getVersion",
                        "/licence/import",
                        "/alterSql/updateId",

                        "/message/messageItem/syncUpdateMessage",
                        "/message/messageItem/syncDeleteMessage",
                        "/permission/findPermissions",
                        "/init/install/findStatus",
                        "/state/apply/findApply"

                })
                .ignorePreUrls(new String[]{
                        "/service",
                        "/api/list",
                        "/api/detail",
                        "/file",
                        "/image",
                        "/plugin",
                        "/authConfig",
                        "/services",
                        "/library",
                        "/repository",
                        "/generic",
                        "/helm",
                        "/go",
                        "/pypi",
                        "/composer",
                        "/composerPack",
                        "/nuget",
                        "/pushCenWarehouse",
                        "/libraryFile",
                        "/xpack",
                        "/backups",
                        "/scan",
                        "/v2"
                })
                .get();
    }

}
