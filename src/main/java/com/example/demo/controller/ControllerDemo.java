package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.Testim;
import com.example.demo.controller.http.HttpRes;
import com.example.demo.processor.Test55;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @Author 35716
 * @Date 2019/11/21 9:23
 * @Version 1.0
 **/
@RestController
public class ControllerDemo {

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;
    @Autowired
    Test55 test55;
    int i = 1;

    @GetMapping("/gethttp")
    public String getHttp() {
        if (i == 1) {
            new Thread(() -> {
                defaultListableBeanFactory.destroySingleton("test55");
                defaultListableBeanFactory.removeBeanDefinition("test55");
                defaultListableBeanFactory.registerBeanDefinition("test55", BeanDefinitionBuilder.genericBeanDefinition(Test55.class).getBeanDefinition());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println(test55);
        return "value";
    }

    public static void main(String[] args) {
        System.out.println(MessageFormat.format("当前{0}原值未导入", "2019-11"));
    }
}
