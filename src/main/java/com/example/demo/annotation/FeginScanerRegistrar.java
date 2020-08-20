package com.example.demo.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/20 13:53
 * desc :
 */
public class FeginScanerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(FeginScan.class.getName()));
        String baseBackage = annoAttrs.getString("baseBackage");
        FeginClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new FeginClassPathBeanDefinitionScanner(registry);
        classPathBeanDefinitionScanner.addPackage(baseBackage);
        classPathBeanDefinitionScanner.registryFilter();
        classPathBeanDefinitionScanner.scan();

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
    }
}
