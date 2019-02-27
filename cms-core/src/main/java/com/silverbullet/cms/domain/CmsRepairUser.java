package com.silverbullet.cms.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CmsRepairUser {

    //id
    @NotBlank(message = "id 不能为空", groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;

    //customName
    @NotBlank(message = "customName 不能为空", groups = {FullValidate.class})
    @Size(max=32, message = "customName 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String cName;

    //familyName
    @NotBlank(message = "不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "familyName 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String familyName;

    //mobPhone
    @NotBlank(message = "不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max=14, message = "mobPhone 长度不能超过14", groups = {FullValidate.class, AddValidate.class})
    private String mobPhone;

    //address
    @NotBlank(message = "不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max=50, message = "address 长度不能超过50", groups = {FullValidate.class, AddValidate.class})
    private String address;

    // createUsername
    @NotBlank(message = "createUsername 不能为空" , groups = {FullValidate.class})
    @Size(max=64, message = "createUsername 长度不能超过64", groups = {FullValidate.class})
    private String createUsername;

    // createUser
    private Integer createUser;

    //createTime
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class})
    private Date createTime;

    // modifyUsername
    @NotBlank(message = "modifyUsername 不能为空" , groups = {FullValidate.class})
    @Size(max=64, message = "modifyUsername 长度不能超过64", groups = {FullValidate.class})
    private String modifyUsername;

    // modifyUser
    private Integer modifyUser;

    // modifyTime
    @NotNull(message = "modifyTime 不能为空" , groups = {FullValidate.class})
    private Date modifyTime;

    //email
    @Size(max=32, message = "email 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String email;

    // pageNum
    @NotNull(message = "pageNum 不能为空" , groups = {FullValidate.class})
    private Integer pageNum;

    // sex
    @NotBlank(message = "不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "sex 长度不能超过2", groups = {FullValidate.class, AddValidate.class})
    private String sex;

    //blackList
    private Integer blackList;

    // comments
    private String comments;

    //delSign
    private Integer delSign;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {

        this.familyName = familyName == null ? null : familyName.trim();
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(String mobPhone) {

        this.mobPhone = mobPhone == null ? null : mobPhone.trim();
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }


    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername == null ? null : createUsername.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getModifyUsername() {
        return modifyUsername;
    }

    public void setModifyUsername(String modifyUsername) {
        this.modifyUsername = modifyUsername == null ? null : modifyUsername.trim();
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
}

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email == null ? null : email.trim();
    }

    public Integer getDelSign() {
        return delSign;
    }

    public void setDelSign(Integer delSign) {
        this.delSign = delSign == null ? null : delSign;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {

        this.cName = cName == null ? null : cName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getBlackList() {
        return blackList;
    }

    public void setBlackList(Integer blackList) {
        this.blackList = blackList == null ? null : blackList;
    }
}
