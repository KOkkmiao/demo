package com.example.demo.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.internal.Streams;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author 35716 苗小鹏 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/05/09 13:53
 * desc :
 */
public class ReturnValueUpper {
    @JSONField(name="name")
    private  String Name;
    @JSONField(name="age")
    private  Integer age=20;
    private Date time = new Date();
    private BigDecimal bigNumber=new BigDecimal(20);
    private Double doubleNumber = 20.00;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
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
                "name='" + Name + '\'' +
                ", age=" + age +
                ", time=" + time +
                ", bigNumber=" + bigNumber +
                ", doubleNumber=" + doubleNumber +
                '}';
    }

    public static void main(String[] args) {
    }
}
