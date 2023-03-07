package io.tiklab.xpack.annotation;

import  io.tiklab.dsm.annotation.SQL;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
@SQL(modules = {"xpack"})
@ComponentScan({"io.tiklab.xpack"})
public class XpackServerAutoConfiguration {
}

