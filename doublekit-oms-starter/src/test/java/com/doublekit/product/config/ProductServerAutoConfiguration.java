package com.doublekit.product.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.doublekit.product.test"})
public class ProductServerAutoConfiguration {

}
