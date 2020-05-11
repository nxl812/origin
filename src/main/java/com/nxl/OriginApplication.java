package com.nxl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.nxl.dao")
public class OriginApplication {

    public static void main(String[] args) {
        SpringApplication.run(OriginApplication.class, args);
    }

}
