package com.silverbullet.cms.domain;

import javax.validation.constraints.Size;
import com.silverbullet.core.validate.AddValidate;
import java.lang.String;
import com.silverbullet.core.validate.FullValidate;
import javax.validation.constraints.NotBlank;


public class CmsRfArticleFile {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // fileId 
    @NotBlank(message = "fileId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "fileId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String fileId;
    // artId 
    @NotBlank(message = "artId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "artId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String artId;


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

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {

        this.artId = artId == null ? null : artId.trim();
    }
}