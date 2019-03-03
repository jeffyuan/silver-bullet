package com.silverbullet.cms.pojo;

import com.silverbullet.cms.domain.CmsArticleFile;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.util.Date;

/**
 * 文件描述信息类
 * Created by jeffyuan on 2018/4/1.
 */
public class CmsFileInfo {

    // fileUrlShort
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
    private Float fileLen = 0F;

    // createUsername
    @NotBlank(message = "createUsername 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "createUsername 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String createUsername;

    // createUser
    @NotBlank(message = "createUser 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "createUser 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String createUser;

    // pageNum
    @NotNull(message = "pageNum 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer pageNum = 0;

    @NotNull(message = "文件流不能为空", groups = {FullValidate.class, AddValidate.class})
    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileUrlShort() {
        return fileUrlShort;
    }

    public void setFileUrlShort(String fileUrlShort) {
        this.fileUrlShort = fileUrlShort;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExname() {
        return fileExname;
    }

    public void setFileExname(String fileExname) {
        this.fileExname = fileExname;
    }

    public Float getFileLen() {
        return fileLen;
    }

    public void setFileLen(Float fileLen) {
        this.fileLen = fileLen;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
