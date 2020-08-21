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

    private String parameters;
    /**
     * 例：/targetInterface/var
     */
    private String httpRout;
    /**
     * 方法描述
     */
    private String description;


    private List<? extends Param> params;

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

    public List<? extends Param> getParams() {
        return params;
    }

    public void setParams(List<? extends Param> params) {
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

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
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

    public static abstract class Param{
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
    public static class TypeParam extends Param{
        private String type;

        @Override
        public String getType() {
            return type;
        }

        @Override
        public void setType(String type) {
            this.type = type;
        }
    }
    public static class SchemaParam extends Param{
        /**
         * 实体的引用
         */
        private String schema;

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }
    }

}
