package com.silverbullet.cms.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CmsArticle {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // title 
    @NotBlank(message = "title 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=256, message = "title 长度不能超过256", groups = {FullValidate.class, AddValidate.class})
    private String title;
    // abs 
    
    private String abs;
    // author 
    
    private String author;
    // publicTime 
    @NotNull(message = "publicTime 不能为空" , groups = {FullValidate.class})
    private Date publicTime;
    // artType 1:html; 2:file doc; 3:site 4: text
    @NotBlank(message = "artType 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=2, message = "artType 长度不能超过2", groups = {FullValidate.class, AddValidate.class})
    private String artType;
    // tagkey 
    
    private String tagkey;
    // source 
    
    private String source;
    // topLevel 
    
    private Integer topLevel;
    // artImgId 
    
    private String artImgId;
    // artState 1开放、0禁用、2待审核
    @NotBlank(message = "artState 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=2, message = "artState 长度不能超过2", groups = {FullValidate.class, AddValidate.class})
    private String artState;
    // createTime 
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class})
    private Date createTime;
    // modifyTime 
    @NotBlank(message = "modifyTime 不能为空" , groups = {FullValidate.class})
    @Size(max=16, message = "modifyTime 长度不能超过16", groups = {FullValidate.class})
    private Date modifyTime;
    // createUsername 
    @NotBlank(message = "createUsername 不能为空" , groups = {FullValidate.class})
    @Size(max=64, message = "createUsername 长度不能超过64", groups = {FullValidate.class})
    private String createUsername;
    // createUser 
    private Integer createUser;
    // modifyUsername 
    @NotBlank(message = "modifyUsername 不能为空" , groups = {FullValidate.class})
    @Size(max=64, message = "modifyUsername 长度不能超过64", groups = {FullValidate.class})
    private String modifyUsername;
    // modifyUser 
    private Integer modifyUser;
    // writeAuthority 1:公开; 0:本人; 2:站点; 3:禁止编辑
    @NotBlank(message = "writeAuthority 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=2, message = "writeAuthority 长度不能超过2", groups = {FullValidate.class, AddValidate.class})
    private String writeAuthority;
    // readAuthority 1:公开; 0:本人; 2:站点; 3:禁止查看
    @NotBlank(message = "readAuthority 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=2, message = "readAuthority 长度不能超过2", groups = {FullValidate.class, AddValidate.class})
    private String readAuthority;
    // runinfoId 
    @NotBlank(message = "runinfoId 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "runinfoId 长度不能超过32", groups = {FullValidate.class})
    private String runinfoId;
    // module 所属的模块名称
    @NotBlank(message = "module 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "module 长度不能超过32", groups = {FullValidate.class})
    private String module;
    // moduleFilterKey 用于在某个模块下过滤的字段
    
    private String moduleFilterKey;
    // lastUpdateTime 
    @NotNull(message = "lastUpdateTime 不能为空" , groups = {FullValidate.class})
    private Date lastUpdateTime;
    // state 
    @NotBlank(message = "state 不能为空" , groups = {FullValidate.class})
    @Size(max=1, message = "state 长度不能超过1", groups = {FullValidate.class})
    private String state = "1";


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title == null ? null : title.trim();
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {

        this.abs = abs == null ? null : abs.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {

        this.author = author == null ? null : author.trim();
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {

        this.publicTime = publicTime;
    }

    public String getArtType() {
        return artType;
    }

    public void setArtType(String artType) {

        this.artType = artType == null ? null : artType.trim();
    }

    public String getTagkey() {
        return tagkey;
    }

    public void setTagkey(String tagkey) {

        this.tagkey = tagkey == null ? null : tagkey.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {

        this.source = source == null ? null : source.trim();
    }

    public Integer getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Integer topLevel) {

        this.topLevel = topLevel;
    }

    public String getArtImgId() {
        return artImgId;
    }

    public void setArtImgId(String artImgId) {

        this.artImgId = artImgId == null ? null : artImgId.trim();
    }

    public String getArtState() {
        return artState;
    }

    public void setArtState(String artState) {

        this.artState = artState == null ? null : artState.trim();
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

    public String getWriteAuthority() {
        return writeAuthority;
    }

    public void setWriteAuthority(String writeAuthority) {

        this.writeAuthority = writeAuthority == null ? null : writeAuthority.trim();
    }

    public String getReadAuthority() {
        return readAuthority;
    }

    public void setReadAuthority(String readAuthority) {

        this.readAuthority = readAuthority == null ? null : readAuthority.trim();
    }

    public String getRuninfoId() {
        return runinfoId;
    }

    public void setRuninfoId(String runinfoId) {

        this.runinfoId = runinfoId == null ? null : runinfoId.trim();
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {

        this.lastUpdateTime = lastUpdateTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {

        this.state = state == null ? null : state.trim();
    }
}