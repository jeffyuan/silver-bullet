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
    private String defaultVlue;  //默认值
    private String typeAndLen;  //字段类型加长度
    private String comments;  //备注

    private Long valueLength;  //长度约束
    private Long numPrecision;  //某列类型的精确度(类型的长度)
    private Long numSCALE;  //小数点后的位数
    private String columnKey;  //PRI
    private String privileges; //select,insert,update,references

    private boolean primaryKey = false; //是否是主键
    private boolean keyIsNull = true;  //是否允许为空

    private String javaType;   //对应java的类型
    private String javaName;  // 对应java的字段名称
    private String javaTypePackage;  //对应java的包名

    private String validate; // 验证

    private String javaGetName;
    private String javaSetName;

    public TableColumnsInfo() {

    }

    public TableColumnsInfo(String name, String type, String typeAndLen, String comments,
                            String defaultVlue, String columnKey, Long valueLength, Long numPrecision,Long numSCALE,
                            String privileges,boolean bIsNull) {
        this.name = name;
        this.type = type;
        this.typeAndLen = typeAndLen;
        this.comments = comments;
        this.defaultVlue = defaultVlue;
        this.columnKey = columnKey;
        this.valueLength = valueLength;
        this.numPrecision = numPrecision;
        this.numSCALE = numSCALE;
        this.privileges = privileges;
        this.keyIsNull = bIsNull;
        this.validate = "";
        this.javaGetName = "";
        this.javaSetName = "";
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

    public String getDefaultVlue() {
        return defaultVlue;
    }

    public void setDefaultVlue(String defaultVlue) {
        this.defaultVlue = defaultVlue;
    }

    public Long getValueLength() {
        return valueLength;
    }

    public void setValueLength(Long valueLength) {
        this.valueLength = valueLength;
    }

    public Long getNumPrecision() {
        return numPrecision;
    }

    public void setNumPrecision(Long numPrecision) {
        this.numPrecision = numPrecision;
    }

    public Long getNumSCALE() {
        return numSCALE;
    }

    public void setNumSCALE(Long numSCALE) {
        this.numSCALE = numSCALE;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public boolean isKeyIsNull() {
        return keyIsNull;
    }

    public void setKeyIsNull(boolean keyIsNull) {
        this.keyIsNull = keyIsNull;
    }

    public String getValidate() {
        // 判断数据库中要求，得到validate的条件

        String group = "";
        if (isPrimaryKey()) {
            group = ", groups = {FullValidate.class}";
        } else {
            group = ", groups = {FullValidate.class, AddValidate.class}";
        }

        if (!keyIsNull) {
            if (javaType.equals("String")) {
                validate = "@NotBlank(message = \"" + javaName +" 不能为空\" #GROUP#)";
                validate += "\n    @Size(max="+ valueLength +", message = \"" + javaName + " 长度不能超过"+ valueLength +"\"#GROUP#)";
            } else if (javaType.equals("Date") || javaType.equals("Long") || javaName.equals("Integer")) {
                validate = "@NotNull(message = \"" + javaName +" 不能为空\" #GROUP#)";
                if (javaType.equals("Long")) {
                    validate += "\n    @Size(max="+ Long.MAX_VALUE +", message = \"" + javaName + " 长度不能超过"+ Long.MAX_VALUE +"\"#GROUP#)";
                } else if (javaType.equals("Integer")) {
                    validate += "\n    @Size(max="+ Integer.MAX_VALUE +", message = \"" + javaName + " 长度不能超过"+ Integer.MAX_VALUE +"\"#GROUP#)";
                }
            } else {
                validate = "@NotNull(message = \"" + javaName +" 不能为空\" #GROUP#)";
            }
        }


        return validate.replaceAll("#GROUP#", group);
    }
}
