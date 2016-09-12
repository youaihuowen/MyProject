package com.example.entity;

/**
 * Created by WUYIXIONG on 2016-9-1.
 * description 主页电话类型listview的实体类
 */
public class PhoneTypeEntity {
    //电话类型
    private String phoneType;
    //要跳转的表名
    private String subTable;

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getSubTable() {
        return subTable;
    }

    public void setSubTable(String subTable) {
        this.subTable = subTable;
    }
}
