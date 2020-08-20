package com.example.demo.controller;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author 35716 苗小鹏 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/05/08 16:21
 * desc :
 */
public class JFrameClazz {
    private static final int m_1 = 1 * 1024 * 1025;
    public static void main(String[] args) throws InterruptedException {
        Executor DIRECT_EXECUTOR = Runnable::run;
        DIRECT_EXECUTOR.execute(()->{
            System.out.println(1);
        });
    }
}
