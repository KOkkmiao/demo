package com.example.demo.controller;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/11 16:58
 * desc :
 */
public class SingletonBean {
    private final static SingletonBean bean = new SingletonBean();

    private SingletonBean(){

    }
    public static SingletonBean getInstance(){
        synchronized (bean){

        }
        return bean;
    }
}
