package com.example.rest_example.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingService {

    @Scheduled(fixedDelay = 2000)
    public void test() {
        System.out.println("Hello from test");
    }

}
