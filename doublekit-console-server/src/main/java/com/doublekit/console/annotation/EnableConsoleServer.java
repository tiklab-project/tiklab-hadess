package com.doublekit.console.annotation;

import com.doublekit.console.ConsoleServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ConsoleServerAutoConfiguration.class})
public @interface EnableConsoleServer {
}
