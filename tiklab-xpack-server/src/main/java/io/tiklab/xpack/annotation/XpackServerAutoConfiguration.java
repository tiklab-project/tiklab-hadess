package io.tiklab.xpack.annotation;

import io.tiklab.dsm.model.SQL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
@ComponentScan({"io.tiklab.xpack"})
public class XpackServerAutoConfiguration {

    @Bean
    SQL xpackSQL(){
        return new SQL(new String[]{
                "xpack"
        },101);
    }
}

