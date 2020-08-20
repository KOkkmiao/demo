package com.example.demo.controller.http;

import com.example.demo.annotation.HttpUrl;
import com.example.demo.controller.User;
import feign.Body;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/20 10:20
 * desc :
 */
@HttpUrl( url = "http://www.baidu.com")
public interface HttpRes {
    @GetMapping
    String GetBaidu();
}
