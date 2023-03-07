package io.tiklab.xpack;

import  io.tiklab.utils.property.PropertyAndYamlSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * WorkbenchApplication
 */
@SpringBootApplication
@PropertySource(value = {"classpath:application.yaml" },factory = PropertyAndYamlSourceFactory.class )
@EnableXpack
public class XpackApplication {

    public static final Logger logger = LoggerFactory.getLogger(XpackApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(XpackApplication.class, args);
    }

}

