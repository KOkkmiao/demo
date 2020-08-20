package com.example.demo.controller;

import groovy.lang.GroovyClassLoader;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 35716 苗小鹏 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/04/17 11:25
 * desc :
 */
public class Myclazz extends ClassLoader {
    @Override
    public String toString() {
        return "this is Myclazz";
    }

    public static void me(int i, int j) {
        System.out.println(i);
        System.out.println(j);
    }

    public static String resourceAsString(ClassLoader classLoader, String resourceName) {
        URL url = classLoader.getResource(resourceName);

        InputStreamReader isr = null;
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches(false);
            isr = new InputStreamReader(urlConnection.getInputStream());
            char[] buf = new char[128];
            StringBuilder builder = new StringBuilder();
            int count = -1;
            while ((count = isr.read(buf, 0, buf.length)) != -1) {
                builder.append(buf, 0, count);
            }
            return builder.toString();
        } catch (IOException e) {
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return null;
    }

    public static final void main(String[] args) throws InterruptedException {
        final NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(20);
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);
        System.out.println( nf.format(1024*1024*1024));
    }
    public static void method(int i){
        System.out.println(i);
    }
    public static class TestRun2 implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (true) {
                i++;
                if (i != 0) {
                    boolean b = 1 % i == 0;
                }
            }
        }
    }

    public static class TestRun implements Runnable {
        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("运行时长：" + (System.currentTimeMillis() - start));
            }
        }
    }
}
