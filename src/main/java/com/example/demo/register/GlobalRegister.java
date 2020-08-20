package com.example.demo.register;

import com.example.demo.annotation.BeanKeyChangeRegister;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/21 9:53
 * desc :
 */
@Component
public class GlobalRegister implements ApplicationContextAware {

    private static ConcurrentHashMap<String,Class<?>> REGISTERMAP = new ConcurrentHashMap();

    private static BeanKeyChangeRegister register;
    public static ApplicationContext applicationContext;

    public static String register(Class<?> clazz,String key){
        if (REGISTERMAP.get(clazz.getName())!=null){
            return "";
        }
        REGISTERMAP.put(key,clazz);
        return "";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GlobalRegister.applicationContext =applicationContext;
        //GlobalRegister.register=applicationContext.getBean(BeanKeyChangeRegister.class);
    }
}
