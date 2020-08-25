package com.example.demo.generate;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/18 11:24
 * desc : 接口的方法信息
 */
public class MethodModel {
    private String methodName;
    /**
     * 返回值需要特殊处理。
     * 如果是单独返回一个基本类型 则为"string"
     * 如果是实体 则#/definitions/Result
     */
    private String returnName;
    /**
     * get post put delete
     */
    private String apiType;

    private String interfaceName;

    /**
     * 入参类型
     */
    private List<String> consumes;
    /**
     * 返参类型
     */
    private List<String> produces;

    /**
     * 例：/targetInterface/var
     */
    private String httpRout;
    /**
     * 方法描述
     */
    private String description;

    /**
     * get请求体
     */
    private List<TypeParam> params=new ArrayList<>();
    /**
     * post 请求body 体
     */
    private List<SchemaParam> paramsObject=new ArrayList<>();

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public List<TypeParam> getParams() {
        return params;
    }

    public void setParams(List<TypeParam> params) {
        this.params = params;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }


    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getHttpRout() {
        return httpRout;
    }

    public void setHttpRout(String httpRout) {
        this.httpRout = httpRout;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SchemaParam> getParamsObject() {
        return paramsObject;
    }

    public void setParamsObject(List<SchemaParam> paramsObject) {
        this.paramsObject = paramsObject;
    }
}
