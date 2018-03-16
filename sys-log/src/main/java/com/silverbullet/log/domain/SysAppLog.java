package com.silverbullet.log.domain;

import java.lang.String;

public class SysAppLog {

    // 
    private String id;
    // 
    private String createTime;
    // 
    private String actionUrl;
    // 
    private String descripts;
    // 
    private String createUser;
    // 
    private String logLevels;
    // 
    private String method;
    // 
    private String className;
    // 
    private String ip;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {

        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {

        this.actionUrl = actionUrl == null ? null : actionUrl.trim();
    }

    public String getDescripts() {
        return descripts;
    }

    public void setDescripts(String descripts) {

        this.descripts = descripts == null ? null : descripts.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {

        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getLogLevels() {
        return logLevels;
    }

    public void setLogLevels(String logLevels) {

        this.logLevels = logLevels == null ? null : logLevels.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {

        this.method = method == null ? null : method.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {

        this.className = className == null ? null : className.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {

        this.ip = ip == null ? null : ip.trim();
    }
}