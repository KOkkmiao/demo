package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/20 15:20
 * desc : fegin 的url
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpUrl {
    String key() default "";
    String url();
}
