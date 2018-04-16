package com.silverbullet.auth.domain;

import javax.validation.constraints.Size;
import java.util.Date;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import java.lang.String;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SysAuthAction {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // name 
    @NotBlank(message = "name 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "name 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String name;
    // url 
    @NotBlank(message = "url 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=128, message = "url 长度不能超过128", groups = {FullValidate.class, AddValidate.class})
    private String url;
    // resourceType 资源类型，[menu|button]
    @NotBlank(message = "resourceType 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=20, message = "resourceType 长度不能超过20", groups = {FullValidate.class, AddValidate.class})
    private String resourceType;
    // parentId 
    
    private String parentId;
    // path
    @NotBlank(message = "path 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=256, message = "path 长度不能超过256", groups = {FullValidate.class, AddValidate.class})
    private String path;
    // comments 
    
    private String comments;
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
    // state 
    @NotBlank(message = "state 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1, message = "state 长度不能超过1", groups = {FullValidate.class, AddValidate.class})
    private String state;
    // isNeedCheck 是否需要检查权限
    @NotBlank(message = "isNeedCheck 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1, message = "isNeedCheck 长度不能超过1", groups = {FullValidate.class, AddValidate.class})
    private String isNeedCheck;
    // isNeedLogin 是否需要登录，默认所有ACTION都要登录
    @NotBlank(message = "isNeedLogin 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1, message = "isNeedLogin 长度不能超过1", groups = {FullValidate.class, AddValidate.class})
    private String isNeedLogin;
    // permission 
    @NotBlank(message = "permission 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=50, message = "permission 长度不能超过50", groups = {FullValidate.class, AddValidate.class})
    private String permission;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        this.url = url == null ? null : url.trim();
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {

        this.resourceType = resourceType == null ? null : resourceType.trim();
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

    public String getIsNeedCheck() {
        return isNeedCheck;
    }

    public void setIsNeedCheck(String isNeedCheck) {

        this.isNeedCheck = isNeedCheck == null ? null : isNeedCheck.trim();
    }

    public String getIsNeedLogin() {
        return isNeedLogin;
    }

    public void setIsNeedLogin(String isNeedLogin) {

        this.isNeedLogin = isNeedLogin == null ? null : isNeedLogin.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {

        this.permission = permission == null ? null : permission.trim();
    }
}