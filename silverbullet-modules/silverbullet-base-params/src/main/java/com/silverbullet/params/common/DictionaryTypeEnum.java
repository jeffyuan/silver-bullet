package com.silverbullet.params.common;

/**
 * 词典对应的类型
 * @author jeffyuan
 * @version 1.0
 * @createDate 2019/3/3 13:53
 * @updateUser jeffyuan
 * @updateDate 2019/3/3 13:53
 * @updateRemark
 */
public enum DictionaryTypeEnum {
    // 关键字类型
    KEY_VALUE("0"),
    // list 类型
    LIST_VALUE("1"),
    // 树类型
    TREE_VALUE("21");

    private String value;
    DictionaryTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值
     * @return java.lang.String
     * @author jeffyuan
     * @createDate 2019/3/3 14:14
     * @updateUser jeffyuan
     * @updateDate 2019/3/3 14:14
     * @updateRemark
     */
    public String getValue() {
        return value;
    }

    /**
     * 根据值获取enum
     * @param valueString
     * @return com.silverbullet.params.common.DictionaryTypeEnum
     * @author jeffyuan
     * @createDate 2019/3/3 14:14
     * @updateUser jeffyuan
     * @updateDate 2019/3/3 14:14
     * @updateRemark
     */
    public static DictionaryTypeEnum parse(String valueString) {

        for (DictionaryTypeEnum item : DictionaryTypeEnum.values()) {
            if (valueString.equals(item.getValue())) {
                return item;
            }
        }

        return null;
    }

     public static void main(String [] args) {
        System.out.println(DictionaryTypeEnum.TREE_VALUE.toString());
        System.out.println(DictionaryTypeEnum.TREE_VALUE.getValue());
        System.out.println(DictionaryTypeEnum.parse("21"));
    }
}
