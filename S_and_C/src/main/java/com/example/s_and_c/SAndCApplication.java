package com.example.s_and_c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication

@EnableAsync
public class SAndCApplication {

    public static void main(String[] args) {
        SpringApplication.run(SAndCApplication.class, args);
    }


}
