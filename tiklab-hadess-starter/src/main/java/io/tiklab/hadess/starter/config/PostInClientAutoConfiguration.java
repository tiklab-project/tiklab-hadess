package io.tiklab.hadess.starter.config;

import io.tiklab.postin.client.openapi.PostInClientConfig;
import io.tiklab.postin.client.openapi.ParamConfig;
import io.tiklab.postin.client.openapi.ParamConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
public class PostInClientAutoConfiguration {

    @Bean
    PostInClientConfig postInClientConfig(ParamConfig paramConfig){
        PostInClientConfig config = new PostInClientConfig();
        config.setParamConfig(paramConfig);

        return config;
    }

    @Bean
    ParamConfig paramConfig(){
        //设置请求头，属性名称：属性描述
        HashMap<String,String> headers = new HashMap<>();
        headers.put("accessToken","设置的apiKey");

        return ParamConfigBuilder.instance()
                .setScanPackage("io.tiklab.hadess") //设置扫描的包路径
                .prePath("/api")             //设置额外的前缀
                .setHeaders(headers)               //设置请求头
                .get();
    }

}