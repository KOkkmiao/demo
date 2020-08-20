package com.example.demo;

import com.example.demo.contract.ContractFeign;
import com.example.demo.controller.User;
import com.example.demo.controller.http.HttpRes;
import com.example.demo.processor.Test55;
import feign.Feign;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/06/28 11:02
 * desc :
 */
@Configuration
public class BeanConfig {

    @ConditionalOnMissingBean(name = "1111")
    @Bean
    public Test2 beanTest2() {
        return new Test2();
    }

    @Bean
    public Feign.Builder builder() {

        return Feign.builder().contract(new ContractFeign()).encoder(new JacksonEncoder());
    }
    @Bean("test55")
    public Test55 getTest55(){
        return new Test55();
    }


}