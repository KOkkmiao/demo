package com.example.demo.annotation;

import feign.Feign;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/20 14:44
 * desc :
 */
@Component
public class FeginFactoryBean<T> implements FactoryBean<T> {
    private Class<T> initInterface;
    @Autowired
    private Feign.Builder builder;

    //具体获取实例bean的地方
    public FeginFactoryBean() {
    }

    public FeginFactoryBean(Class<T> initInterface) {
        this.initInterface = initInterface;
    }

    /**
     * 将类的实例化在builder中进行
     *
     * @return
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        return builder.target(initInterface, "http://www.baidu.com");
    }

    //返回当前实例bean的类型
    @Override
    public Class<?> getObjectType() {
        return this.initInterface;
    }

    //默认单例
    @Override
    public boolean isSingleton() {
        return true;
    }
}
