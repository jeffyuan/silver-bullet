package com.silverbullet.cms.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CmsArticleContent {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // artId 所属文档ID
    @NotBlank(message = "artId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "artId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String artId;
    // contHtml 
    @NotBlank(message = "contHtml 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(message = "contHtml 长度不能超过4294967295", groups = {FullValidate.class, AddValidate.class})
    private String contHtml;
    // contText 
    
    private String contText;
    // createTime 
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date createTime;
    // modifyTime 
    @NotNull(message = "modifyTime 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date modifyTime;
    // createUsername 
    @NotBlank(message = "createUsername 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "createUsername 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String createUsername;
    // createUser 
    private Integer createUser;
    // modifyUsername 
    @NotBlank(message = "modifyUsername 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "modifyUsername 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String modifyUsername;
    // modifyUser 
    private Integer modifyUser;
    // contVersion 
    @NotNull(message = "contVersion 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer contVersion;
    // contState 1:当前内容；2:历史信息；3：待审核
    @NotBlank(message = "contState 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=2, message = "contState 长度不能超过2", groups = {FullValidate.class, AddValidate.class})
    private String contState;
    // contModifyReason 
    
    private String contModifyReason;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {

        this.artId = artId == null ? null : artId.trim();
    }

    public String getContHtml() {
        return contHtml;
    }

    public void setContHtml(String contHtml) {

        this.contHtml = contHtml == null ? null : contHtml.trim();
    }

    public String getContText() {
        return contText;
    }

    public void setContText(String contText) {

        this.contText = contText == null ? null : contText.trim();
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

    public Integer getContVersion() {
        return contVersion;
    }

    public void setContVersion(Integer contVersion) {

        this.contVersion = contVersion;
    }

    public String getContState() {
        return contState;
    }

    public void setContState(String contState) {

        this.contState = contState == null ? null : contState.trim();
    }

    public String getContModifyReason() {
        return contModifyReason;
    }

    public void setContModifyReason(String contModifyReason) {

        this.contModifyReason = contModifyReason == null ? null : contModifyReason.trim();
    }
}