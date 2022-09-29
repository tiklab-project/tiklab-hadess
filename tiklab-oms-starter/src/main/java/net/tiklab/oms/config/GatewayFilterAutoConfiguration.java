package  net.tiklab.oms.config;


import  net.tiklab.eam.author.Authenticator;
import  net.tiklab.eam.client.author.AuthorHandler;
import  net.tiklab.eam.client.author.config.IgnoreConfig;
import  net.tiklab.eam.client.author.config.IgnoreConfigBuilder;
import  net.tiklab.gateway.GatewayFilter;
import  net.tiklab.gateway.router.RouterHandler;
import  net.tiklab.gateway.router.config.RouterConfig;
import  net.tiklab.gateway.router.config.RouterConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterAutoConfiguration {


    @Bean
    GatewayFilter gatewayFilter(RouterHandler routerHandler, AuthorHandler authorHandler){
        return new GatewayFilter()
                .setRouterHandler(routerHandler)
                .addHandler(authorHandler);
    }

    //认证handler
    @Bean
    AuthorHandler authorHandler(Authenticator authenticator, IgnoreConfig ignoreConfig){
        return new AuthorHandler()
                .setAuthenticator(authenticator)
                .setIgnoreConfig(ignoreConfig);

    }

    @Bean
    RouterHandler routerHandler(RouterConfig routerConfig){
        return new RouterHandler()
                .setRouterConfig(routerConfig);
    }

    @Bean
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
                })
                .ignoreUrls(new String[]{
                        "/",
                        "/member/registeMember",
                        "/thirdMember/registeThirdMember",
                        "/repository/findAllRepository",
                        "/repository/findRepository",
                        "/repository/findRepositoryList",
                        "/category/findCategoryListTree",
                        "/category/likeFindCategoryListTree",
                        "/document/findDocument",
                        "/comment/findCommentTreePage",
                        "/product/findNewProductAll",
                        "/product/findProductList",
                        "/product/findProduct",
                        "/product/findNewProductAll",
                        "/productVersion/findProductVersionList",
                        "/blogs/findBlogsPage",
                        "/blogs/findBlogs",
                        "/question/findQuestionPage",
                        "/question/findQuestionList",
                        "/question/findQuestion",
                        "/comment/findCommentAndLike",
                        "/artifact/findNewArtifactPage",
                        "/artifactVersion/findArtifactVersionList",
                        "/payment/alipayNotify",
                        "/payment/weChatPayNotify",
                        "/payment/findPaymentList",
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

                        "/passport/member/authCodeLogin",
                        "/passport/member/logout",
                        "/passport/member/valid",
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
                        "/sockjs-node/info"

                })
                .ignorePreUrls(new String[]{
                        "/service",
                        "/api/list",
                        "/api/detail",
                        "/file",
                        "/image",
                        "/plugin",
                        "/authConfig",
                        "/services"
                })
                .get();
    }


    //路由转发配置
    @Value("${eas.address:null}")
    String authUrl;

    @Bean
    RouterConfig routerConfig(){
        return RouterConfigBuilder.instance()
                .preRoute(new String[]{
                        "/user",
                        "/eam",
                        "/message",
                },authUrl)
                .get();
    }
}
