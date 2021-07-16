package com.doublekit.workbench.annotation;

import com.doublekit.workbench.WorkbenchServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WorkbenchServerAutoConfiguration.class})
public @interface EnableWorkbenchServer {
}
