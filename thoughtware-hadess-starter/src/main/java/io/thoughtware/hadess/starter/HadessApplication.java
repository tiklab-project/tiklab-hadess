package io.thoughtware.hadess.starter;

import io.thoughtware.core.property.PropertyAndYamlSourceFactory;
import io.thoughtware.hadess.starter.annotation.EnableHadess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * WorkbenchApplication
 */
@SpringBootApplication
@PropertySource(value = {"classpath:application.yaml" },factory = PropertyAndYamlSourceFactory.class )
@EnableScheduling
@EnableHadess
@ServletComponentScan("io.thoughtware.hadess")
public class HadessApplication {

    public static final Logger logger = LoggerFactory.getLogger(HadessApplication.class);

    public static void main(String[] args) {
        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH","true");
        SpringApplication.run(HadessApplication.class, args);

    }

}

