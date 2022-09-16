package  net.tiklab.oms;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OmsAutoConfiguration.class})
public @interface EnableOms {
}
