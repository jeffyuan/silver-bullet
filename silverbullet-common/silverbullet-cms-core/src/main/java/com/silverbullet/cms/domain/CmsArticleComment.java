package com.silverbullet.cms.domain;

import javax.validation.constraints.Size;
import java.util.Date;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.Integer;

public class CmsArticleComment {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // module 所属的模块名称
    @NotBlank(message = "module 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "module 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String module;
    // moduleFilterKey 用于在某个模块下过滤的字段
    @NotBlank(message = "moduleFilterKey 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "moduleFilterKey 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String moduleFilterKey;
    // content 
    
    private String content;
    // contentHtml 
    @NotBlank(message = "contentHtml 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(message = "contentHtml 长度不能超过4294967295", groups = {FullValidate.class, AddValidate.class})
    private String contentHtml;
    // createTime 
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date createTime;
    // createUsername 
    @NotBlank(message = "createUsername 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "createUsername 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String createUsername;
    // createUser 
    @NotBlank(message = "createUser 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "createUser 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String createUser;
    // praisYes 
    @NotNull(message = "praisYes 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer praisYes;
    // praisNo 
    @NotNull(message = "praisNo 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer praisNo;
    // adopt 
    @NotBlank(message = "adopt 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=2, message = "adopt 长度不能超过2", groups = {FullValidate.class, AddValidate.class})
    private String adopt;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {

        this.module = module == null ? null : module.trim();
    }

    public String getModuleFilterKey() {
        return moduleFilterKey;
    }

    public void setModuleFilterKey(String moduleFilterKey) {

        this.moduleFilterKey = moduleFilterKey == null ? null : moduleFilterKey.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        this.content = content == null ? null : content.trim();
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {

        this.contentHtml = contentHtml == null ? null : contentHtml.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
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

    public Integer getPraisYes() {
        return praisYes;
    }

    public void setPraisYes(Integer praisYes) {

        this.praisYes = praisYes;
    }

    public Integer getPraisNo() {
        return praisNo;
    }

    public void setPraisNo(Integer praisNo) {

        this.praisNo = praisNo;
    }

    public String getAdopt() {
        return adopt;
    }

    public void setAdopt(String adopt) {

        this.adopt = adopt == null ? null : adopt.trim();
    }
}