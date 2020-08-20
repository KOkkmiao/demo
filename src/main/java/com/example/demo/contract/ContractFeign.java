package com.example.demo.contract;

import com.example.demo.annotation.HttpUrl;
import feign.Contract;
import feign.MethodMetadata;
import org.springframework.web.bind.annotation.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/20 11:02
 * desc : 类的解析。 通过apollo 来进行获取或者通过url获取 支持fegin的http的请求方式
 */
public class ContractFeign extends Contract.Default {
    //处理http url的参数注入到templete 中的url中

    @Override
    protected void processAnnotationOnClass(MethodMetadata data, Class<?> targetType) {
        HttpUrl annotation = targetType.getAnnotation(HttpUrl.class);
        if (annotation != null) {
            String key = annotation.key();
            String url = annotation.url();
            //判断是否有key 如果有key 则从apollo中获取，并且注册监听
//            if("".equals(key)){
//                url = GlobalRegister.register(targetType, key);
//            }
            data.template().append(url);
        }
        super.processAnnotationOnClass(data, targetType);
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation,
                                             Method method) {
        Class<? extends Annotation> annotationType = methodAnnotation.annotationType();
        String path = null;
        String methodName = null;
        if (annotationType == GetMapping.class) {
            GetMapping cast = GetMapping.class.cast(methodAnnotation);
            path = cast.name();
            methodName = "GET";

        } else if (annotationType == PostMapping.class) {
            PostMapping cast = PostMapping.class.cast(methodAnnotation);
            methodName = "POST";
            path = cast.name();
        } else if (annotationType == PutMapping.class) {
            PutMapping cast = PutMapping.class.cast(methodAnnotation);
            methodName = "PUT";
            path = cast.name();
        } else if (annotationType == DeleteMapping.class) {
            DeleteMapping cast = DeleteMapping.class.cast(methodAnnotation);
            methodName = "DELETE";
            path = cast.name();
        }
        if (path != null) {
            data.template().method(methodName);
            data.template().append(path);
        }
        super.processAnnotationOnMethod(data, methodAnnotation, method);
    }
}
