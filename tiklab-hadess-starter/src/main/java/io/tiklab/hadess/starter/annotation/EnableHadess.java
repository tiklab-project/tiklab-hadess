package io.tiklab.hadess.starter.annotation;

import io.tiklab.hadess.starter.config.HadessAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({HadessAutoConfiguration.class})
public @interface EnableHadess {
}
