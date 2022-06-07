package com.doublekit.oms.annotation;

import com.doublekit.oms.config.OmsServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OmsServerAutoConfiguration.class})
public @interface EnableOmsServer {
}
