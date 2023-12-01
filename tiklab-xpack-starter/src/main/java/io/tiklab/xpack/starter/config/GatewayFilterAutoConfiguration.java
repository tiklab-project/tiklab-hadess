package io.tiklab.xpack.starter.config;


import io.tiklab.eam.author.Authenticator;
import io.tiklab.eam.client.author.config.AuthorConfig;
import io.tiklab.eam.client.author.config.AuthorConfigBuilder;
import io.tiklab.eam.client.author.handler.AuthorHandler;
import io.tiklab.gateway.router.Router;
import io.tiklab.gateway.router.RouterBuilder;
import  io.tiklab.gateway.router.config.RouterConfig;
import  io.tiklab.gateway.router.config.RouterConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayFilterAutoConfiguration {


    @Bean
    Router router(RouterConfig routerConfig){
        return RouterBuilder.newRouter(routerConfig);
    }

    //认证filter
    @Bean
    AuthorHandler authorFilter(Authenticator authenticator, AuthorConfig ignoreConfig){
        return new AuthorHandler()
                .setAuthenticator(authenticator)
                .setAuthorConfig(ignoreConfig);
    }

    @Bean
    public AuthorConfig authorConfig(){
        return AuthorConfigBuilder.instance()
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
                })
                .ignoreUrls(new String[]{
                        "/",
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
                        "/repository",
                        "/library",
                        "/repository",
                        "/generic",
                        "/pushCenWarehouse",
                        "/libraryFile",
                        "/xpack",
                        "/backups",
                        "/fileHand",
                        "/scan",
                        "/v2"
                })
                .get();
    }


    //路由转发配置
    @Value("${eas.address:null}")
    String authUrl;


    @Value("${eas.embbed.enable}")
    Boolean enableEam;


    //gateway路由配置
    @Bean
    RouterConfig routerConfig(){
        String[] s = {
                "/user",
                "/eam",
                "/appLink",
                "/todo/deletetodo",
                "/todo/updatetodo",
                "/todo/detailtodo",
                "/todo/findtodopage",
                "/message/message",
                "/message/messageItem",
                "/message/messageReceiver",
                "/oplog/deletelog",
                "/oplog/updatelog",
                "/oplog/detaillog",
                "/oplog/findlogpage",
        };

        if (enableEam){

            s = new String[]{};
        }

        return RouterConfigBuilder.instance()
                .preRoute(s, authUrl)
                .get();
    }

}
