package com.silverbullet.cms.domain;

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

public class CmsArticleCommentReply {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // commentId 
    @NotBlank(message = "commentId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "commentId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String commentId;
    // content 
    @NotBlank(message = "content 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1024, message = "content 长度不能超过1024", groups = {FullValidate.class, AddValidate.class})
    private String content;
    // createUser 
    @NotBlank(message = "createUser 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "createUser 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String createUser;
    // createUsername 
    @NotBlank(message = "createUsername 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "createUsername 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String createUsername;
    // createTime 
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {

        this.commentId = commentId == null ? null : commentId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {

        this.content = content == null ? null : content.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {

        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {

        this.createUsername = createUsername == null ? null : createUsername.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }
}