package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/06/28 10:47
 * desc :
 */
//@Service
public class Test implements Testim {
    @Override
    public void print() {
        Logger.getLogger(Test.class.getName()).info("test类");
    }
}
