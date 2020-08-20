package com.example.demo.generate;

import java.util.List;
import java.util.Set;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/19 17:04
 * desc :实体信息
 */
public class Model {
    /*
    实体类名字
     */
    private String modelName;
    /**
     * 文件路径根据root路径传入
     */
    private String packagePath;
    /**
     * 需要导入的包
     */
    private Set<String> importProperties;

    /**
     * 成员属性
     */
    private List<MemberModel> memberModel;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public List<MemberModel> getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(List<MemberModel> memberModel) {
        this.memberModel = memberModel;
    }

    public Set<String> getImportProperties() {
        return importProperties;
    }

    public void setImportProperties(Set<String> importProperties) {
        this.importProperties = importProperties;
    }
}
