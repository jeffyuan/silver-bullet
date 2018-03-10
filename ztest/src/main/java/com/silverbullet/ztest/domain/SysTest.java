package com.silverbullet.ztest.domain;

import java.lang.String;
import java.lang.String;
import java.util.Date;
import java.lang.Integer;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.util.Date;
import java.lang.Integer;
import java.lang.String;
import java.lang.String;
import java.lang.String;

public class SysTest {

    // 
    private String id;
    // 
    private String myName;
    // 
    private Date createTime;
    // 
    private Integer cInt;
    // 
    private String cLength;
    // 
    private String commen;
    // 
    private String delSign;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {

        this.myName = myName == null ? null : myName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    public Integer getcInt() {
        return cInt;
    }

    public void setcInt(Integer cInt) {

        this.cInt = cInt;
    }

    public String getcLength() {
        return cLength;
    }

    public void setcLength(String cLength) {

        this.cLength = cLength == null ? null : cLength.trim();
    }

    public String getCommen() {
        return commen;
    }

    public void setCommen(String commen) {

        this.commen = commen == null ? null : commen.trim();
    }

    public String getDelSign() {
        return delSign;
    }

    public void setDelSign(String delSign) {

        this.delSign = delSign == null ? null : delSign.trim();
    }
}