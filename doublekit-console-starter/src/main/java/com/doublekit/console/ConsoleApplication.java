package com.doublekit.console;

import com.doublekit.console.annotation.EnableConsoleServer;
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
@EnableConsoleServer
public class ConsoleApplication {

    public static final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

}

