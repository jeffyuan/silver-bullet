package com.silverbullet.codegenerator.beetl.config;

import com.silverbullet.utils.ToolUtil;

/**
 * 控制模板全局变量
 * Created by jeffyuan on 2018/3/6.
 */
public class ContextConfig {
    private String bizChName; // 模块的中文名称，主要写到注释里面
    private String bizEnName; // 驼峰表示，例如SysAuth，包名会通过此变量转为小写
    private String bizEnNameSmall; //通过bizEnName 转小写；
    private String bizEnNameCapSmall; //首字母小写

    private String prjPackage; //项目包名，可以理解为com下面的子包名，如com.silverbullet
    private String modulePackage; // 模块的包名, 项目报下的子包名，如com.silverbullet.auth

    public String getBizChName() {
        return bizChName;
    }

    public void setBizChName(String bizChName) {
        this.bizChName = bizChName;
    }

    public String getBizEnName() {
        return bizEnName;
    }

    public void setBizEnName(String bizEnName) {

        this.bizEnName = bizEnName;
        this.bizEnNameSmall = bizEnName.toLowerCase();
        this.bizEnNameCapSmall = ToolUtil.formatStringCapLower(bizEnName);
    }

    public String getModulePackage() {
        return modulePackage;
    }

    public void setModulePackage(String modulePackage) {
        this.modulePackage = modulePackage;
    }

    public String getPrjPackage() {
        return prjPackage;
    }

    public void setPrjPackage(String prjPackage) {
        this.prjPackage = prjPackage;
    }

    public String getBizEnNameSmall() {
        return bizEnNameSmall;
    }

    public void setBizEnNameSmall(String bizEnNameSmall) {
        this.bizEnNameSmall = bizEnNameSmall;
    }

    public String getBizEnNameCapSmall() {
        return bizEnNameCapSmall;
    }

    public void setBizEnNameCapSmall(String bizEnNameCapSmall) {
        this.bizEnNameCapSmall = bizEnNameCapSmall;
    }
}
