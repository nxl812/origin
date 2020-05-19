package com.nxl;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigInteger;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.nxl.dao")
public class OriginApplication {

    public static void main(String[] args) {
        SpringApplication.run(OriginApplication.class, args);
    }

    @Test
    public void rabbit(){
        BigInteger a1 = new BigInteger("1");
        BigInteger a2 = new BigInteger("1");
        BigInteger sn = new BigInteger("0");

        for (int i = 0; i < 10000; i++) {
            sn=a1.add(a2);
            a1=a2;
            a2=sn;
        }

        System.out.println(sn);
    }

}
