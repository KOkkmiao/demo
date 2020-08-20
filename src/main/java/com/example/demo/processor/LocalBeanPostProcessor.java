package com.example.demo.processor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/22 16:58
 * desc :
 */
@Component
public class LocalBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;
    private int i =0;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("test55")) {
            boolean containsBean = defaultListableBeanFactory.containsBean("test55");
            if (containsBean) {
                //移除bean的定义和实例
                defaultListableBeanFactory.removeBeanDefinition("test55");
            }
            //注册新的bean定义和实例
            defaultListableBeanFactory.registerBeanDefinition("test55", BeanDefinitionBuilder.genericBeanDefinition(Test55.class).getBeanDefinition());
            bean = null;
            return new Test55("张三"+i++);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
