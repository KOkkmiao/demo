package com.example.demo.annotation;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/20 14:02
 * desc : 扫描bean的类
 */
public class FeginClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private Class<? extends Annotation> annotation ;
    private String basePackage;
    private FeginFactoryBean<?> beanFactory = new FeginFactoryBean<>();

    public FeginClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry,false);
    }
    public void addPackage(String basePackage){
        this.basePackage=basePackage;
    }
    public  void registryFilter(){
        if (annotation!=null){
            addIncludeFilter(new AnnotationTypeFilter(this.annotation));
        }
        //必须是接口才进行拦截
       addIncludeFilter((metadataReader, metadataReaderFactory) -> metadataReader.getClassMetadata().isInterface());
    }
    public void scan(){
         Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackage);
         if (!beanDefinitionHolders.isEmpty()){
             processBeanDefinition(beanDefinitionHolders);
         }
    }
    //具体执行类的实例化
    public void processBeanDefinition(Set<BeanDefinitionHolder> beanDefinitionHolders){
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition)beanDefinitionHolder.getBeanDefinition();
            //设置bean的名称在构造初始化的时候放入
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClass(this.beanFactory.getClass());
        }
    }
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return  beanDefinition.getMetadata().isInterface()&& beanDefinition.getMetadata().isIndependent();
    }
}
