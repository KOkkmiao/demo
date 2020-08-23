package com.example.demo.generate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Author: xiaopengmiao conact:json2388873680@163.com
 * @Description:
 * @Date: 2020-08-21 22:10
 * @Version: 1.0
 */

public abstract class Param {
    private String name;
    private boolean required;
    private String description;
    private String in;

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

