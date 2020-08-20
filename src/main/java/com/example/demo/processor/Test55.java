package com.example.demo.processor;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/22 17:00
 * desc :
 */

public class Test55 {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Test55(){}
    public Test55(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test55{" +
                "name='" + name + '\'' +
                '}';
    }
}
