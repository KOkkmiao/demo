package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author xiaopeng miao
 * @Description //TODO
 * @Date 2019/5/31 15:51
 * @Param
 * @return
 **/
@RestController
public class TestController {


    private void privateMethod() {

    }

    @GetMapping("/{i}")
    public String method(@PathVariable("i") int i) throws InterruptedException {
        if (i==0){
            Thread.sleep(10*1000);
        }
        return LocalDate.now().toString();
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(1000,2,3);
        System.out.println(date);
        System.out.println(LocalDate.of(1000, 1, 1)
                .atStartOfDay()
                .atZone(ZoneId.systemDefault()));
        System.out.println(Date.from(LocalDate.of(1000, 1, 1)
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()));

    }
}
