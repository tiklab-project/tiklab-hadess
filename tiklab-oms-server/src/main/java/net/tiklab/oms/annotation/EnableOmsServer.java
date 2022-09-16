package  net.tiklab.oms.annotation;

import  net.tiklab.oms.config.OmsServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OmsServerAutoConfiguration.class})
public @interface EnableOmsServer {
}
