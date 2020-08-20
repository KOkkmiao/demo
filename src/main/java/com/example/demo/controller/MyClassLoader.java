package com.example.demo.controller;

import demojar.A;
import demojar.B;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends ClassLoader  {
    //指定路径
    private String path ;
    public MyClassLoader(String classPath)throws MalformedURLException {
        super(new MyClassLoader());
        path=classPath;
	}
    public MyClassLoader(){
    }
    /**
     * 重写findClass方法
     * @param name 是我们这个类的全路径
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class log = null;
        // 获取该class文件字节码数组
        byte[] classData = getData();

        if (classData != null) {
            // 将class的字节码数组转换成Class类的实例
            log = defineClass(name, classData, 0, classData.length);
        }
        return log;
    }

    /**
     * 将class文件转化为字节码数组
     * @return
     */
    private byte[] getData() {

        File file = new File(path);
        if (file.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        }else{
            return null;
        }


    }

    public static void main(String[] args) throws Exception {
        String path = "E:\\ideauser\\demo\\target\\classes\\com\\example\\demo\\controller\\Myclazz.class";
        String name  ="com.example.demo.controller.Myclazz";
         MyClassLoader myClassLoader = new MyClassLoader(path);
         Class<?> aClass = myClassLoader.loadClass(name);
         Object o = aClass.newInstance();

    }
	
}
