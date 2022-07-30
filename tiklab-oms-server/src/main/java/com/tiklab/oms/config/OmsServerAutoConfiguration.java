package com.tiklab.oms.config;

import com.tiklab.dsm.annotation.SQL;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
@SQL(modules = {
})
@ComponentScan({"com.tiklab.oms"})
public class OmsServerAutoConfiguration {
}

