package net.tiklab.xpack;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({XpackAutoConfiguration.class})
public @interface EnableXpack {
}
