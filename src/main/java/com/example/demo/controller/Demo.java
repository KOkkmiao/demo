package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.sun.rowset.JdbcRowSetImpl;
import feign.*;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.apache.catalina.User;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * @author 35716 苗小鹏 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/04/21 18:00
 * desc : fastJson 无法指定时区
 */
public class Demo {
    public static void main(String[] args) throws Throwable {
        Object o = new Object();
        synchronized (o) {
            o.wait();
        }

        System.out.println(1);
    }

}

class Model {
    String name;

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}

class DemoFastJson implements Encoder {
    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        try {
            template.body(JSONObject.toJSONString(object));
        } catch (Exception e) {
            throw new EncodeException(e.getMessage(), e);
        }
    }
}

class DemoFastJsonDecode implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response.status() == 404) {
            return Util.emptyValueOf(type);
        }
        if (response.body() == null) {
            return null;
        }
        InputStream inputStream = response.body().asInputStream();
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream, 1);
        }
        inputStream.mark(1);
        if (inputStream.read() == -1) {
            return null; // Eagerly returning null avoids "No content to map due to end-of-input"
        }
        inputStream.reset();
        Object o = JSONObject.parseObject(inputStream, type);
        return o;
    }
}

class RequestPostContentType implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        if (template.method().equalsIgnoreCase("post")) {
            template.header("Content-Type", "application/json");
        }
    }
}

