package com.silverbullet.cms.domain;

import java.lang.Float;
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

public class CmsArticleFileHistory {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // fileId 
    @NotBlank(message = "fileId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "fileId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String fileId;
    // fileUrl 
    @NotBlank(message = "fileUrl 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=256, message = "fileUrl 长度不能超过256", groups = {FullValidate.class, AddValidate.class})
    private String fileUrl;
    // fileUrlShort 
    @NotBlank(message = "fileUrlShort 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "fileUrlShort 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String fileUrlShort;
    // fileType 1:文档；2:图片；3:视频
    @NotBlank(message = "fileType 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=2, message = "fileType 长度不能超过2", groups = {FullValidate.class, AddValidate.class})
    private String fileType;
    // fileName 
    @NotBlank(message = "fileName 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=255, message = "fileName 长度不能超过255", groups = {FullValidate.class, AddValidate.class})
    private String fileName;
    // fileExname 
    @NotBlank(message = "fileExname 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=16, message = "fileExname 长度不能超过16", groups = {FullValidate.class, AddValidate.class})
    private String fileExname;
    // fileLen 
    @NotNull(message = "fileLen 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Float fileLen;
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
    @NotBlank(message = "createUser 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "createUser 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String createUser;
    // modifyUsername 
    @NotBlank(message = "modifyUsername 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "modifyUsername 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String modifyUsername;
    // modifyUser 
    @NotBlank(message = "modifyUser 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "modifyUser 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String modifyUser;
    // pageNum 
    @NotNull(message = "pageNum 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer pageNum;
    // contText 
    
    private String contText;
    // contModifyReason 
    
    private String contModifyReason;
    // contVersion 
    @NotNull(message = "contVersion 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer contVersion;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {

        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {

        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public String getFileUrlShort() {
        return fileUrlShort;
    }

    public void setFileUrlShort(String fileUrlShort) {

        this.fileUrlShort = fileUrlShort == null ? null : fileUrlShort.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {

        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileExname() {
        return fileExname;
    }

    public void setFileExname(String fileExname) {

        this.fileExname = fileExname == null ? null : fileExname.trim();
    }

    public Float getFileLen() {
        return fileLen;
    }

    public void setFileLen(Float fileLen) {

        this.fileLen = fileLen;
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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {

        this.pageNum = pageNum;
    }

    public String getContText() {
        return contText;
    }

    public void setContText(String contText) {

        this.contText = contText == null ? null : contText.trim();
    }

    public String getContModifyReason() {
        return contModifyReason;
    }

    public void setContModifyReason(String contModifyReason) {

        this.contModifyReason = contModifyReason == null ? null : contModifyReason.trim();
    }

    public Integer getContVersion() {
        return contVersion;
    }

    public void setContVersion(Integer contVersion) {

        this.contVersion = contVersion;
    }
}