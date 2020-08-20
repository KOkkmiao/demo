package com.example.demo.annotation;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/07/21 9:48
 * desc : bean实例中url变化后如何修改等操作
 */
public interface BeanKeyChangeRegister {
    String setTargetInterFace(Class<?> targetName, String key);

    class Default implements BeanKeyChangeRegister {
        private ConcurrentHashMap<String/*key*/, String/*url*/> keyUrl = new ConcurrentHashMap();
        private Set<String> keySet = new HashSet();

        /**
         * 最简单的做法直接容器refresh
         * @param targetName
         * @param key
         * @return
         */
        @Override
        public String setTargetInterFace(Class<?> targetName, String key) {

            return "url";
        }
    }
}

