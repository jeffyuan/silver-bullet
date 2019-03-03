package com.silverbullet.codegenerator.beetl.config;

import java.util.List;

/**
 * 网页信息配置
 * Created by jeffyuan on 2018/3/7.
 */
public class HtmlConfig {

    //记录显示到列表中的字段名
    private List<String> tableNo;

    public HtmlConfig() {

    }

    public List<String> getTableNo() {
        return tableNo;
    }

    public void setTableNo(List<String> tableNo) {
        this.tableNo = tableNo;
    }
}
