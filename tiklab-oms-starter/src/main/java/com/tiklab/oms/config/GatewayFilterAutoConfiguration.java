package com.tiklab.oms.config;


import com.tiklab.gateway.GatewayFilter;
import com.tiklab.iam.author.Authenticator;
import com.tiklab.iam.client.config.IgnoreConfig;
import com.tiklab.iam.client.config.IgnoreConfigBuilder;
import com.tiklab.iam.client.handler.AuthorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterAutoConfiguration {


    //网关filter
  /*  @Bean
    GatewayFilter gatewayFilter(AuthorHandler authorHandler){
        return new GatewayFilter()
                .addHandler(authorHandler);
    }

    //认证handler
    @Bean
    AuthorHandler authorHandler(Authenticator authenticator, com.tiklab.iam.client.config.IgnoreConfig ignoreConfig){
        return new AuthorHandler()
                .setAuthenticator(authenticator)
                .setIgnoreConfig(ignoreConfig);

    }*/

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
}
