package net.tiklab.xpack.annotation;

import  net.tiklab.dsm.annotation.SQL;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
@SQL(modules = {"xpack"})
@ComponentScan({"net.tiklab.xpack"})
public class XpackServerAutoConfiguration {
}

