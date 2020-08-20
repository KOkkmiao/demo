package com.example.demo.controller;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @author 35716 苗小鹏 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/05/09 10:19
 * desc :
 */
public interface ApiDemo {
    @RequestLine("POST /postmapping")
    //@Headers("Content-Type: application/json")
    ReturnValueUpper getheart(Result json);
}
