package com.example.demo.generate;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/20 18:03
 * desc : 请求方法名
 */
public enum  RequestMethod {
        GET("get"), HEAD("head"), POST("post"), PUT("put"), PATCH("patch"), DELETE("delete"), OPTIONS("options"), TRACE("trace");

    private String lowName;

    RequestMethod(String lowName) {
        this.lowName = lowName;
    }
}
