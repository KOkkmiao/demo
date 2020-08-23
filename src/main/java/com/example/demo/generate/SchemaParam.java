package com.example.demo.generate;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author: xiaopengmiao conact:json2388873680@163.com
 * @Description:
 * @Date: 2020-08-21 22:09
 * @Version: 1.0
 */
public class SchemaParam extends Param {
    /**
     * 实体的引用
     */
    @JSONField(deserializeUsing = RefObjectSerialized.class)
    private String schema;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
