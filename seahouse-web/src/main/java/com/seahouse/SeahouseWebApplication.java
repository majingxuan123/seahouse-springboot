package com.seahouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mjx
 */
//默认扫描当前包下的子包
@SpringBootApplication(scanBasePackages="com.seahouse.**")
public class SeahouseWebApplication{
    public static void main(String[] args) {
        SpringApplication.run(SeahouseWebApplication.class, args);
    }

}
