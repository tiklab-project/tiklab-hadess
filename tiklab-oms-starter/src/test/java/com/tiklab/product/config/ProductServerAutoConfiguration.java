package com.tiklab.product.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.tiklab.product.test"})
public class ProductServerAutoConfiguration {

}
