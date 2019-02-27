package com.silverbullet.cms.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CmsArticleTypetree {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // name 
    @NotBlank(message = "name 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "name 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String name;
    // sort 
    @NotNull(message = "sort 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer sort;
    // parentId 
    @NotBlank(message = "parentId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "parentId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String parentId;
    // path 
    @NotBlank(message = "path 不能为空" , groups = {FullValidate.class})
    @Size(max=256, message = "path 长度不能超过256", groups = {FullValidate.class})
    private String path;
    // comments 
    
    private String comments;
    // type 1:结构；2:数据
    @NotBlank(message = "type 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1, message = "type 长度不能超过1", groups = {FullValidate.class, AddValidate.class})
    private String type;
    // createUser 
    private Integer createUser;
    // createTime 
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class})
    private Date createTime;
    // modifyUser 
    private Integer modifyUser;
    // modifyTime 
    @NotNull(message = "modifyTime 不能为空" , groups = {FullValidate.class})
    private Date modifyTime;
    // state 
    @NotBlank(message = "state 不能为空" , groups = {FullValidate.class})
    @Size(max=1, message = "state 长度不能超过1", groups = {FullValidate.class})
    private String state;
    // module 所属的模块名称
    @NotBlank(message = "module 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "module 长度不能超过32", groups = {FullValidate.class})
    private String module;
    // domain 
    @NotBlank(message = "domain 不能为空" , groups = {FullValidate.class})
    @Size(max=64, message = "domain 长度不能超过64", groups = {FullValidate.class})
    private String domain;

    private String icon;
    // params 参数

    private String params;
    // childrenNum 子节点数量
    @NotNull(message = "childrenNum 不能为空" , groups = {FullValidate.class})
    private Integer childrenNum = 0;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {

        this.type = type == null ? null : type.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {

        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {

        this.state = state == null ? null : state.trim();
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {

        this.module = module == null ? null : module.trim();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {

        this.domain = domain == null ? null : domain.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {

        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {

        this.childrenNum = childrenNum;
    }
}