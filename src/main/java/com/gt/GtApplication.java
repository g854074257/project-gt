package com.gt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.gt"})
@MapperScan("com.gt.**.mapper")
public class GtApplication {

    public static void main(String[] args) {
        SpringApplication.run(GtApplication.class, args);
    }

}
