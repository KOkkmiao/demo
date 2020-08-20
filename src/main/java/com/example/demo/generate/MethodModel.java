package com.example.demo.generate;

import java.util.List;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/18 11:24
 * desc : 接口的方法信息
 */
public class MethodModel {
    private String methodName;

    private String returnName;
    /**
     * get post put delete
     */
    private String apiType;

    private String interfaceName;

    /**
     * 入参类型
     */
    private String consumes;
    /**
     * 返参类型
     */
    private String produces;

    private String parameters;
    /**
     * 例：/targetInterface/var
     */
    private String httpRout;
    /**
     * 方法描述
     */
    private String description;


    private List<GetParam> getParams;

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

    public String getConsumes() {
        return consumes;
    }

    public void setConsumes(String consumes) {
        this.consumes = consumes;
    }

    public String getProduces() {
        return produces;
    }

    public void setProduces(String produces) {
        this.produces = produces;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public List<GetParam> getGetParams() {
        return getParams;
    }

    public void setGetParams(List<GetParam> getParams) {
        this.getParams = getParams;
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

    public static class GetParam{
        private String name;
        private boolean required;
        /**
         * 基本数据类型
         */
        private String type;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
