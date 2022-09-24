package  net.tiklab.oms.config;

import  net.tiklab.dsm.annotation.SQL;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * OmsServerAutoConfiguration
 */
@Configuration
@SQL(modules = {"oms"})
@ComponentScan({"net.tiklab.oms"})
public class OmsServerAutoConfiguration {
}

