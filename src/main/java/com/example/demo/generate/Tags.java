package com.example.demo.generate;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/19 16:14
 * desc : 接口的controller 名称和描述
 */
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class Tags
{
    private String name ;
    @JSONField
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
