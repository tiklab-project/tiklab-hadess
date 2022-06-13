package com.doublekit.oms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * WorkbenchApplication
 */
@SpringBootApplication
@PropertySource(value = "classpath:application-${env:dev}.properties")
@EnableOms
public class OmsApplication {

    public static final Logger logger = LoggerFactory.getLogger(OmsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OmsApplication.class, args);
    }

}

