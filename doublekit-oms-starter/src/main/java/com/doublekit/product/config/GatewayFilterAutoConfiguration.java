package com.doublekit.product.config;

import com.doublekit.gateway.author.config.IgnoreConfig;
import com.doublekit.gateway.author.config.IgnoreConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterAutoConfiguration {

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
                        "/weChatSign/findWeChatSignList",
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
