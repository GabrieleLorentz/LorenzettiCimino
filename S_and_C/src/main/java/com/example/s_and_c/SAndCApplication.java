package com.example.s_and_c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication


public class SAndCApplication {

    public static void main(String[] args) {
        SpringApplication.run(SAndCApplication.class, args);
    }


}
