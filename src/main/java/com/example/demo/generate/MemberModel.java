package com.example.demo.generate;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/19 17:18
 * desc :
 */
public class MemberModel {
    private String memberName;
    /**
     * 实体类型
     * {@link ModelClassType}
     */
    private String memberType;

    /**
     * 如果modelType ：array 则才会有这个字段
     */
    private String itemName;
    /**
     * 字段的描述信息
     */
    private String description;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
