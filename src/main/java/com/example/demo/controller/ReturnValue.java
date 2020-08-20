package com.example.demo.controller;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 35716 苗小鹏 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/05/09 13:53
 * desc :
 */
public class ReturnValue{
    private  String name;
    private  Integer age=20;
    @JSONField
    private Date time = new Date();
    private BigDecimal bigNumber=new BigDecimal(20);
    private Double doubleNumber = 20.00;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getBigNumber() {
        return bigNumber;
    }

    public void setBigNumber(BigDecimal bigNumber) {
        this.bigNumber = bigNumber;
    }

    public Double getDoubleNumber() {
        return doubleNumber;
    }

    public void setDoubleNumber(Double doubleNumber) {
        this.doubleNumber = doubleNumber;
    }

    @Override
    public String toString() {
        return "ReturnValue{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", time=" + time +
                ", bigNumber=" + bigNumber +
                ", doubleNumber=" + doubleNumber +
                '}';
    }
}
