package io.tiklab.hadess.starter.config;


import io.tiklab.openapi.config.AllowConfig;
import io.tiklab.openapi.config.AllowConfigBuilder;
import io.tiklab.openapi.config.OpenApiConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiAutoConfiguration {
    @Value("${soular.address:null}")
    String authAddress;

    @Value("${soular.embbed.enable:false}")
    Boolean enableEam;

    @Value("${server.port}")
    String serverPort;

    //路由
    @Bean
    OpenApiConfig openApiConfig(AllowConfig allowConfig){
        OpenApiConfig openApiConfig = new OpenApiConfig();
        openApiConfig.setAllowConfig(allowConfig);

        return openApiConfig;
    }

    //开放许可配置
    @Bean
    AllowConfig allowConfig(){
        String[] s =  new String[]{
                "/library/findLibrary",
                "/library/findLibraryListByRepository",
                "/library/findAllLibrary",
                "/library/findLibraryList",
                "/library/findLibraryPage",
                "/library/findLibraryListByCond",

                "/libraryVersion/findLibraryVersion",
                "/libraryVersion/findAllLibraryVersion",
                "/libraryVersion/findLibraryVersionList",
                "/libraryVersion/findLibraryVersionPage",
                "/libraryVersion/findVersionByLibraryId",

                "/libraryFile/findLibraryFile",
                "/libraryFile/findAllLibraryFile",
                "/libraryFile/findLibraryFileList",
                "/libraryFile/findLibraryFilePage",
                "/libraryFile/findLibraryNewFileList",

                "/xpackRepository/findLocalAndRemoteRepository",
                "/xpackRepository/findAllRepository",
                "/xpackRepository/findRepositoryList",
                "/xpackRepository/findRepositoryPage",
                "/xpackRepository/findRepositoryByGroup",
                "/xpackRepository/findRepository",

                "/remoteProxy/findRemoteProxyList",
                "/remoteProxy/findRemoteProxyPage",
                "/remoteProxy/findProxyListByRpyId",

                "/repositoryRemoteProxy/findRepositoryRemoteProxyList",
        };

        return AllowConfigBuilder.instance()
                .allowUrls(s)
                .get();
    }
}
