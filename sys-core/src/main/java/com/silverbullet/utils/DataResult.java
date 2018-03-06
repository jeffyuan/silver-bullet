package com.silverbullet.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有list的返回类型
 * Created by jeffyuan on 2018/3/5.
 */
public class DataResult {

    private static final Logger log = Logger.getLogger(DataResult.class);

    private List<Map<String, Object>> resultList = null;
    private int totalNum = 0;

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * 根据参数，替换显示的内容
     * @param key 需要List元素的关键字
     * @param value 替换的值，格式为 A:value1,B:value2
     * @return
     */
    public DataResult runDictionary(String key, String value) {
        String [] mapStr = value.trim().split(",");
        Map<String, String> mapDictionary = new HashMap<String, String>();

        for (String keyValue : mapStr) {
            String [] node = keyValue.split(":");
            if (node.length == 2) {
                mapDictionary.put(node[0], node[1]);
            }

        }

        runDictionary(key, mapDictionary);
        return this;
    }

    /**
     * 翻译结果集合中的时间格式 yyyyMMddHHmmss
     * 前提是数据库中的字段值必须是如201208220408的格式yyyyMMddHHmmss支持14或12位的
     *
     * @param title
     *            翻译字段名 如：TYPE
     * @param yyyyMMddHHmmss
     *            翻译成的格式
     */
    public void runformatTime(String title, String yyyyMMddHHmmss) {
        List<Map<String, Object>> list = this.getResultList();
        for (Map<String, Object> node : list) {
            try {
                String time = null;
                if (node.get(title) instanceof java.sql.Date) {
                    java.util.Date d = new java.util.Date(((java.sql.Date) node.get(title)).getTime());
                    SimpleDateFormat ormat = new SimpleDateFormat("yyyyMMdd");
                    time = ormat.format(d);
                } else {
                    time = (String) node.get(title);
                }

                if (time == null || time.trim().length() <= 0) {
                    continue;
                }

                if (12 == time.length()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                    SimpleDateFormat newSdf = new SimpleDateFormat(yyyyMMddHHmmss);
                    Date date = sdf.parse(time);
                    node.put(title, newSdf.format(date));
                } else {
                    try {
                        time = (time + "00000000000000").substring(0, 14);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        SimpleDateFormat newSdf = new SimpleDateFormat(yyyyMMddHHmmss);
                        Date date = sdf.parse(time);
                        node.put(title, newSdf.format(date));
                    } catch (ParseException e) {
                        node.put(title, null);
                    }
                }
            } catch (Exception e) {
                log.warn(e);
            }
        }
    }

    /**
     * 替换list中的值
     * @param key
     * @param mapDictionary
     * @return
     */
    private  DataResult runDictionary(String key, Map<String, String> mapDictionary) {
        for (Map<String, Object> node : resultList) {
            String subkey = String.valueOf(node.get(key));
            Object value = mapDictionary.get(subkey);

            if (value != null) {
                node.put(key, value);
            }
        }

        return this;
    }
}
