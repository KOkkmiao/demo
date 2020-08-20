package com.example.demo.generate;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/19 17:06
 * desc : 获取对应关系
 */
public enum ModelClassType {
    object("Object"),
    string("String"),
    integer("Integer"),
    array("List");

    private String stringClassName;

    ModelClassType(java.lang.String stringClassName) {
        this.stringClassName = stringClassName;
    }
    public static String getClassName(String stringName){
        return valueOf(stringName).stringClassName;
    }
    public static boolean isArray(String target){
        return array.name().equals(target);
    }
}
