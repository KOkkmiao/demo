package com.example.demo;

import com.example.demo.annotation.FeginScan;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@FeginScan(baseBackage = "com.example.demo.controller.http")
public class Demo11Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo11Application.class, args);
    }
}

