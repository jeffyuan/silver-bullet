package com.silverbullet.auth.domain;

import javax.validation.constraints.Size;
import java.util.Date;
import com.silverbullet.core.validate.AddValidate;
import java.lang.Integer;
import java.lang.String;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SysAuthUser {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // name 
    @NotBlank(message = "name 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "name 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String name;
    // password MD5(password+loginname)
    @NotBlank(message = "password 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "password 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String password;
    // salt 
    @NotBlank(message = "salt 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "salt 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String salt;
    // comments 
    
    private String comments;
    // userType 1:超级用户 2:系统用户 3:其他
    
    private String userType;
    // createUser 
    @NotBlank(message = "createUser 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "createUser 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String createUser;
    // createTime 
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date createTime;
    // modifyUser 
    @NotBlank(message = "modifyUser 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "modifyUser 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String modifyUser;
    // modifyTime 
    @NotNull(message = "modifyTime 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date modifyTime;
    // state 1: 正常 2:锁定
    @NotBlank(message = "state 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1, message = "state 长度不能超过1", groups = {FullValidate.class, AddValidate.class})
    private String state;
    // username 
    @NotBlank(message = "username 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "username 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String username;
    // loginTime 
    
    private Date loginTime;
    // imageId 
    
    private String imageId;
    // classify 
    @NotNull(message = "classify 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer classify;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {

        this.salt = salt == null ? null : salt.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments == null ? null : comments.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {

        this.userType = userType == null ? null : userType.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {

        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {

        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {

        this.modifyTime = modifyTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {

        this.state = state == null ? null : state.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {

        this.username = username == null ? null : username.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {

        this.loginTime = loginTime;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {

        this.imageId = imageId == null ? null : imageId.trim();
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {

        this.classify = classify;
    }
}