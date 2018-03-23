package com.silverbullet.params.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.String;
import java.lang.String;
import java.lang.String;
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
import java.lang.String;
import java.util.Date;
import java.util.Date;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import java.lang.String;

public class SysParamsDictionary {

    //id
    @NotBlank(message = "id 不能为空", groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    //name
    @NotBlank(message = "name 不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max = 64, message = "name 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String name;
    //dicKey
    @NotBlank(message = "dicKey 不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max = 32, message = "dicKey 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String dicKey;
    //dicValue
    @NotNull(message = "dicValue 不能为空", groups = {FullValidate.class})
    @Size(max = 256, message = "dicValue 长度不能超过256", groups = {FullValidate.class})
    private String dicValue = "";
    // comments
    @Size(max = 128, message = "comments 长度不能超过256", groups = {FullValidate.class, AddValidate.class})
    private String comments;
    // 0:列表内容，1:树形结构，
    @NotBlank(message = "type 不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max = 1, message = "type 长度不能超过1", groups = {FullValidate.class})
    private String type;
    //
    @NotNull(message = "createTime 不能为空", groups = {FullValidate.class})
    private Date createTime;
    //
    @NotNull(message = "modifyTime 不能为空", groups = {FullValidate.class})
    private Date modifyTime;
    //
    @NotBlank(message = "createUsername 不能为空", groups = {FullValidate.class})
    @Size(max = 64, message = "createUsername 长度不能超64")
    private String createUsername;
    //
    @NotBlank(message = "createUser 不能为空", groups = {FullValidate.class})
    @Size(max = 32, message = "createUser 长度不能超32")
    private String createUser;
    //
    @NotBlank(message = "modifyUsername 不能为空", groups = {FullValidate.class})
    @Size(max = 64, message = "modifyUsername 不能超64")
    private String modifyUsername;
    //
    @NotBlank(message = "modifyUser 不能为空", groups = {FullValidate.class})
    @Size(max = 32, message = "modifyUser 不能超64")
    private String modifyUser;
    //
    @NotBlank(message = "state 不能为空", groups = {FullValidate.class})
    @Size(max = 1, message = "state 不能超1")
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

    public String getDicKey() {
        return dicKey;
    }

    public void setDicKey(String dicKey) {

        this.dicKey = dicKey == null ? null : dicKey.trim();
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {

        this.dicValue = dicValue == null ? null : dicValue.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments == null ? null : comments.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {

        this.type = type == null ? null : type.trim();
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