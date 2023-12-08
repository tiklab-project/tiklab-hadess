package io.thoughtware.hadess.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({HadessServerAutoConfiguration.class})
public @interface EnableHadessServer {
}
