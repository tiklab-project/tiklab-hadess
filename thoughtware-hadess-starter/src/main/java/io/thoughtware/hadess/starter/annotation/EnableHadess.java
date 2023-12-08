package io.thoughtware.hadess.starter.annotation;

import io.thoughtware.hadess.starter.config.HadessAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({HadessAutoConfiguration.class})
public @interface EnableHadess {
}
