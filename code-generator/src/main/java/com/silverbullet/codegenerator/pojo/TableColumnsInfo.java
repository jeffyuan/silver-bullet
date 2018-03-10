package com.silverbullet.codegenerator.pojo;

import com.silverbullet.utils.Table2JavaUtil;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

/**
 * table 表字段信息
 * Created by jeffyuan on 2018/3/9.
 */
public class TableColumnsInfo {
    private String name;   //字段名称
    private String type;   //字段类型
    private String typeAndLen;  //字段类型加长度
    private String comments;  //备注
    private boolean primaryKey = false; //是否是主键

    private String javaType;   //对应java的类型
    private String javaName;  // 对应java的字段名称
    private String javaTypePackage;  //对应java的包名


    private String javaGetName;
    private String javaSetName;

    public TableColumnsInfo() {

    }

    public TableColumnsInfo(String name, String type, String typeAndLen, String comments) {
        this.name = name;
        this.type = type;
        this.typeAndLen = typeAndLen;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeAndLen() {
        return typeAndLen;
    }

    public void setTypeAndLen(String typeAndLen) {
        this.typeAndLen = typeAndLen;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getJavaTypePackage() {
        return javaTypePackage;
    }

    public void setJavaTypePackage(String javaTypePackage) {
        this.javaTypePackage = javaTypePackage;
    }

    public String getJavaGetName() {
        return Table2JavaUtil.javaGetterSetterFunName("get" , javaName);
    }

    public String getJavaSetName() {
        return Table2JavaUtil.javaGetterSetterFunName("set" , javaName);
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
