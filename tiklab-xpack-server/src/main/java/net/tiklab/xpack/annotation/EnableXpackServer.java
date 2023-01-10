package net.tiklab.xpack.annotation;

import net.tiklab.xpack.config.XpackServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({XpackServerAutoConfiguration.class})
public @interface EnableXpackServer {
}