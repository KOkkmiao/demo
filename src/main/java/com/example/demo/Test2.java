package com.example.demo;

import com.example.demo.controller.http.HttpRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/06/28 10:47
 * desc :
 */
@Service
public class Test2 implements Testim {
    @Override
    public void print() {
        Logger.getLogger(Test.class.getName()).info("test1类");
    }

    public static void main(String[] args) throws IOException {

         FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\35716\\Desktop\\word.txt"));
         int read =0;
         while((read=fileInputStream.read())!=-1){
             System.out.println(read);
         }
        System.out.println("\u73af\u5883\u53d8\u91cf");
    }
}
