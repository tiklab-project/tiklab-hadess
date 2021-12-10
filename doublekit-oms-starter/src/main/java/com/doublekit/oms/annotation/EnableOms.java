package com.doublekit.oms.annotation;

import com.doublekit.oms.config.OmsAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OmsAutoConfiguration.class})
public @interface EnableOms {
}
