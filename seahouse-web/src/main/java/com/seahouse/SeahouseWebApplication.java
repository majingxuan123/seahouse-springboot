package com.seahouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//默认扫描当前包下的子包
//@SpringBootApplication(scanBasePackages="com.seahouse.**")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SeahouseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeahouseWebApplication.class, args);
    }

}
