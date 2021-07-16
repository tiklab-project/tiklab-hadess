package com.doublekit.workbench;

import com.doublekit.workbench.annotation.EnableWorkbenchServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * WorkbenchApplication
 */
@SpringBootApplication
@PropertySource(value = "classpath:application-${env:local}.properties")
@EnableWorkbenchServer
public class WorkbenchApplication {

    public static final Logger logger = LoggerFactory.getLogger(WorkbenchApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WorkbenchApplication.class, args);
    }

}

