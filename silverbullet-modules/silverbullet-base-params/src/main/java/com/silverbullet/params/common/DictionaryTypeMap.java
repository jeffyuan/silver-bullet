package com.silverbullet.params.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储数据库value和界面显示对应的MAP
 * @author jeffyuan
 * @version 1.0
 * @createDate 2019/3/3 14:23
 * @updateUser jeffyuan
 * @updateDate 2019/3/3 14:23
 * @updateRemark
 */
public class DictionaryTypeMap {
    private static Map<String, String> mapDictionType = new HashMap<String, String>();
    static {
        mapDictionType.put("0", "键值");
        mapDictionType.put("1", "列值");
        mapDictionType.put("2", "树值");
    }

    /**
     * 获取显示类名
     * @param type
     * @return java.lang.String
     * @author jeffyuan
     * @createDate 2019/3/3 14:28
     * @updateUser jeffyuan
     * @updateDate 2019/3/3 14:28
     * @updateRemark
     */
    public static String getTypeName(String type) {
        if (mapDictionType.containsKey(type)) {
            return mapDictionType.get(type);
        }

        return "未知";
    }

    /**
     * 获取dictiontype map
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author jeffyuan
     * @createDate 2019/3/3 14:48
     * @updateUser jeffyuan
     * @updateDate 2019/3/3 14:48
     * @updateRemark
     */
    public static Map<String, String> getDictionTypeMap() {
        return mapDictionType;
    }
}
