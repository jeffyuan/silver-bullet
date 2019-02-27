package com.silverbullet.cms.domain;

import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CmsRepairServiceType {

    //id
    @NotBlank(message = "不能为空", groups = {FullValidate.class})
    @Size(max=32, message = "长度不能超过32", groups = {FullValidate.class})
    private String id;

    //typeName
    @NotBlank(message = "不能为空", groups = {FullValidate.class})
    @Size(max=64, message = "长度不能超过64", groups = {FullValidate.class})
    private String typeName;

    //typeDetails
    @NotBlank(message = "不能为空", groups = {FullValidate.class})
    @Size(max=255, message = "长度不能超过255", groups = {FullValidate.class})
    private String typeDetails;

    //parentId
    @NotBlank(message = "不能为空", groups = {FullValidate.class})
    @Size(max=32, message = "长度不能超过32", groups = {FullValidate.class})
    private String parentId;

    //sort
    @NotBlank(message = "不能为空", groups = {FullValidate.class})
    @Size(max=11, message = "长度不能超过11", groups = {FullValidate.class})
    private Integer sort;

    //path
    @NotBlank(message = "不能为空", groups = {FullValidate.class})
    @Size(max=256, message = "长度不能超过11", groups = {FullValidate.class})
    private String path;


    //icon
    @Size(max=64, message = "长度不能超过64", groups = {FullValidate.class})
    private String icon;

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

    // type
    @NotNull(message = "不能为空" , groups = {FullValidate.class})
    private Integer type;

    // comments
    @NotNull(message = "不能为空" , groups = {FullValidate.class})
    private String comments;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeDetails() {
        return typeDetails;
    }

    public void setTypeDetails(String typeDetails) {
        this.typeDetails = typeDetails == null ? null : typeDetails.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort == null ? null : sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
            this.path = path == null ? null : path.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
