package com.silverbullet.utils;

import org.apache.ibatis.type.JdbcType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Table转JAVA文件的格式处理类
 * 用于根据数据库表，自动生成domain和mapper文件
 * Created by jeffyuan on 2018/3/9.
 */
public class Table2JavaUtil {

    private static final char UNDERLINE = '_';

    /**
     * 将表中的字段转为JAVA 对应的对象
     * @param tableColumn
     * 大写->小写
     * abc_def  -> abcDef
     * @return
     */
    public static String underline2Camel(String tableColumn) {
        if (tableColumn == null || "".equals(tableColumn.trim())) {
            return "";
        }

        int len = tableColumn.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = tableColumn.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(tableColumn.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取getter和setter的名称
     * @param funType 方法为 get set
     * @param filedName 变量名
     * @return
     */
    public static String javaGetterSetterFunName(String funType, String filedName) {
        // 如果第一个字母小写，第二个字母大写，则直接加上funType返回
        // 例如 ePath
        if (Character.isLowerCase(filedName.charAt(0)) && Character.isUpperCase(filedName.charAt(1))) {
            return funType + filedName;
        } else if (Character.isLowerCase(filedName.charAt(0))) {
            return funType + Character.toUpperCase(filedName.charAt(0)) + filedName.substring(1);
        }

        return funType + filedName;
    }

    /**
     * 驼峰转下划线
     * @param javaCamelString
     * @return
     */
    public static String camel2Underline(String javaCamelString) {
        if (javaCamelString == null || "".equals(javaCamelString.trim())) {
            return "";
        }
        int len = javaCamelString.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = javaCamelString.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将数据库字段类型转为JAVA对应数据类型
     * @param tableType
     * @return
     */
    public static List<String> tableType2JavaType(String tableType) {
        tableType = tableType.toLowerCase();
        List<String> javaType = new ArrayList<String>();

        if (tableType.equals("datetime") || tableType.equals("date") || tableType.equals("timestamp")) {
            javaType.add("Date");
            javaType.add("java.util.Date");
        } else if (tableType.equals("varchar") || tableType.equals("char") || tableType.equals("mediumtext")
                || tableType.equals("text") || tableType.equals("longtext")) {
            javaType.add("String");
            javaType.add("java.lang.String");
        } else if (tableType.equals("integer") || tableType.equals("int") || tableType.equals("smallint") || tableType.equals("mediumint")) {
            javaType.add("Integer");
            javaType.add("java.lang.Integer");
        } else if (tableType.equals("bigint")) {
            javaType.add("Long");
            javaType.add("java.lang.Long");
        } else if (tableType.equals("blog")) {
            javaType.add("byte[]");
            javaType.add("java.lang.Byte");
        } else if (tableType.equals("bit")) {
            javaType.add("boolean");
            javaType.add("java.lang.Boolean");
        } else if (tableType.equals("float")) {
            javaType.add("Float");
            javaType.add("java.lang.Float");
        } else if (tableType.equals("double")) {
            javaType.add("Double");
            javaType.add("java.lang.Double");
        } else if (tableType.equals("decimal")) {
            javaType.add("BigDecimal");
            javaType.add("java.math.BigDecimal");
        }

        return javaType;
    }

    /**
     * 将table类型转batis对应类型
     * @param tableColumnType 数据库中字段名称
     * @return
     */
    public static String tableType2BatisType(String tableColumnType) {
        // 对type进行处理，例如数据库中DATETIME,而mybatis只有 TIMESTAMP
        if (tableColumnType.equalsIgnoreCase("datetime") || tableColumnType.equalsIgnoreCase("date") ) {
            tableColumnType = "TIMESTAMP";
        } else if(tableColumnType.equalsIgnoreCase("int")) {
            tableColumnType = "INTEGER";
        } else if(tableColumnType.equals("varchar") || tableColumnType.equals("mediumtext")
                || tableColumnType.equals("text") || tableColumnType.equals("longtext")) {
            tableColumnType = "VARCHAR";
        } else if(tableColumnType.equals("char") ) {
            tableColumnType = "CHAR";
        }

        return tableColumnType;
    }
}
