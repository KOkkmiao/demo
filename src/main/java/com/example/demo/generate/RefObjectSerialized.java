package com.example.demo.generate;

import com.alibaba.fastjson.JSON;
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
        if (fieldName.equals("schema")) {
            final JSONObject schema = (JSONObject) parser.parse("schema");
            if (schema.containsKey("items")) {
                String itemType = ModelClassType.getClassName((schema.getJSONObject("items").getString("type")));
                return (T) ("List<" + itemType + ">");
            } else {
                String ref = schema.getString("ref");
                if (ref.contains("#/definitions/")) {
                    ref = ref.replace("#/definitions/", "");
                }
                return (T) ref;
            }
        } else {
            String string = ((JSONObject) parser.parse("ref")).getString("ref");
            if (string.contains("#/definitions/")) {
                string = string.replace("#/definitions/", "");
            }
            return (T) string;
        }
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.NEW;
    }

    public static void main(String[] args) {
        String string = "{\n" +
                "                        \"in\":\"body\",\n" +
                "                        \"name\":\"condition\",\n" +
                "                        \"description\":\"condition\",\n" +
                "                        \"required\":true,\n" +
                "                        \"schema\":{\n" +
                "                            \"ref\":\"#/definitions/ReportServiceSettingEntity\"\n" +
                "                        }\n" +
                "                    }";
        SchemaParam schemaParam1 = JSON.parseObject(string, SchemaParam.class);
        SchemaParam schemaParam = JSON.parseObject(JSON.parseObject(string).toJSONString(), SchemaParam.class);
        System.out.println();
    }
}
