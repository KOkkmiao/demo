package com.example.demo.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/27 11:18
 * desc : 全局配置中心 可添加用户自定义的导入类路径和对应实体的名字
 */
public class GlobalConfig {
    /**
     * 可导入的包
     */
    private static Map<String,String> importPath = new HashMap<>();
    /**
     * 可导入类的唯一名称
     */
    private static Set<String> importClazz = new HashSet<>();
    /**
     * 项目基本路径
     */
    private static String root = System.getProperty("user.dir")+"\\src\\main\\java\\";
    /**
     * 默认生成目录
     */
    private static String generatePath="generate";

    public static void addPath(String modelName,String path){
        if (importClazz.contains(modelName)){
            throw new IllegalArgumentException("参数"+modelName+"已存在对应路径："+importPath.get(modelName));
        }
        importClazz.add(modelName);
        importPath.put(modelName,path);
    }

    public static void main(String[] args) {
        System.out.println(root);
    }
}
