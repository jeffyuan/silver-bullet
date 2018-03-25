package com.silverbullet.params.domain;

import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.Integer;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.util.Date;
import java.util.Date;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.Integer;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.util.Date;
import java.util.Date;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;

public class SysParamsDictionaryItem {

    // 
    private String id;
    // 
    private String name;
    // 
    private String dicKeyId;
    // 
    private String itemKey;
    // 
    private String itemValue;
    // 
    private Integer sort;
    // 
    private String parentId = "NONE";
    // 
    private String path;
    // 
    private String comments;
    // 
    private Date createTime;
    // 
    private Date modifyTime;
    // 
    private String createUsername;
    // 
    private String createUser;
    // 
    private String modifyUsername;
    // 
    private String modifyUser;
    // 
    private String state = "0";


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name == null ? null : name.trim();
    }

    public String getDicKeyId() {
        return dicKeyId;
    }

    public void setDicKeyId(String dicKeyId) {

        this.dicKeyId = dicKeyId == null ? null : dicKeyId.trim();
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {

        this.itemKey = itemKey == null ? null : itemKey.trim();
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {

        this.itemValue = itemValue == null ? null : itemValue.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {

        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {

        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {

        this.path = path == null ? null : path.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments == null ? null : comments.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {

        this.modifyTime = modifyTime;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {

        this.createUsername = createUsername == null ? null : createUsername.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {

        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getModifyUsername() {
        return modifyUsername;
    }

    public void setModifyUsername(String modifyUsername) {

        this.modifyUsername = modifyUsername == null ? null : modifyUsername.trim();
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {

        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {

        this.state = state == null ? null : state.trim();
    }
}