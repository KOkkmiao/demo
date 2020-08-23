package com.example.demo.generate;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @Author: xiaopengmiao conact:json2388873680@163.com
 * @Description:
 * @Date: 2020-08-21 21:50
 * @Version: 1.0
 */
public class RefObjectSerialized implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Object parse = parser.parse();
        return (T)"123";
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.NEW;
    }
}
