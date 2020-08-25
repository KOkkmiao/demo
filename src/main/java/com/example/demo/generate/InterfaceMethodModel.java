package com.example.demo.generate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/18 11:21
 * desc : 接口信息
 */
public class InterfaceMethodModel {
    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * 接口描述
     */
    private String interfaceDescription;
    /**
     * http路径
     */
    private String httpPath;

    /**
     * 文件路径根据root路径传入
     */
    private String packagePath;

    public InterfaceMethodModel(String interfaceName, String interfaceDescription, String httpPath, String packagePath) {
        this.interfaceName = interfaceName;
        this.interfaceDescription = interfaceDescription;
        this.httpPath = httpPath;
        this.packagePath = packagePath;
    }

    /**
     * 需要导入的包
     */
    private Set<String> importProperties=new HashSet<>();
    /**
     * 方法信息
     */
    private List<MethodModel> methodModels=new ArrayList<>();

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceDescription() {
        return interfaceDescription;
    }

    public void setInterfaceDescription(String interfaceDescription) {
        this.interfaceDescription = interfaceDescription;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public Set<String> getImportProperties() {
        return importProperties;
    }

    public void setImportProperties(Set<String> importProperties) {
        this.importProperties = importProperties;
    }

    public void addPackage(String packagePath){
        importProperties.add(packagePath);
    }

    public List<MethodModel> getMethodModels() {
        return methodModels;
    }

    public void setMethodModels(List<MethodModel> methodModels) {
        this.methodModels = methodModels;
    }
    public void addMethodModels(MethodModel methodModels) {
        this.methodModels.add(methodModels);
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }
}
