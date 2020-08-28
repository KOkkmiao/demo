package com.example.demo.enums;

import com.example.demo.generate.ModelClassType;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/27 11:11
 * desc : 类的基本成员变量的基本显示方式
 */
public enum ClassType {
    object("object", "Object",""),
    string("string", "String",""),
    integer("integer", "Integer","int"),
    array("array", "List",""),
    Boolean("boolean", "Boolean","boolean"),
    number("number", "BigDecimal","");
    private String basicName;
    private String lowerName;
    private String uppperName;

    ClassType(String lowerName, String uppperName, String basicName) {
        this.basicName = basicName;
        this.lowerName = lowerName;
        this.uppperName = uppperName;
    }

    public static String getClassName(String stringName) {
        Optional<ClassType> first = Arrays.stream(values()).filter(item -> item.lowerName.equals(stringName)).findFirst();
        if (first.isPresent()) {
            return first.get().uppperName;
        } else if (stringName == null) {
            return string.uppperName;
        }
        return stringName;
    }

    public static boolean isArray(String target) {
        return array.lowerName.equals(target);
    }

    public static boolean isList(String target) {
        return array.uppperName.equals(target);
    }
}
