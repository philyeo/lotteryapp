package com.philyeo.lotteryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.philyeo"})
public class LotteryappApplication {

    public static void main(String[] args) {

        SpringApplication.run(LotteryappApplication.class, args);
    }

}
