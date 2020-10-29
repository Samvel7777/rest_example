package com.example.rest_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestExampleApplication.class, args);
    }

}
