package com.example.demo.generate;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/21 11:17
 * desc : 根据名称进行转化 如file ->MultipartFile 文件上传
 */
public enum  ContentsConvert {
    file("MultipartFile","import org.springframework.web.multipart;\n");
    public String name;
    public String packageName;
    ContentsConvert(String name,String packageName) {
        this.name = name;
        this.packageName=packageName;
    }

    /**
     * 判断是否存在此类型 如果存在则返回对应的字段
     * @return
     */
    public static Optional<ContentsConvert> ifContainGet(String target){
       return Arrays.stream(values()).filter(item -> item.name().equals(target)).findFirst();
    }
}
