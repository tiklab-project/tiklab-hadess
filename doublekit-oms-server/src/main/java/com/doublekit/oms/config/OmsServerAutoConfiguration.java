package com.doublekit.oms.config;

import com.doublekit.dsm.annotation.SQL;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
@SQL(modules = {
})
@ComponentScan({"com.doublekit.oms"})
public class OmsServerAutoConfiguration {
}

