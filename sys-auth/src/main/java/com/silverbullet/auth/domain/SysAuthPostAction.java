package com.silverbullet.auth.domain;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import com.silverbullet.core.validate.AddValidate;
import java.lang.String;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SysAuthPostAction {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // actionId 
    @NotBlank(message = "actionId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "actionId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String actionId;
    // postId 
    @NotBlank(message = "postId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "postId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String postId;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {

        this.actionId = actionId == null ? null : actionId.trim();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {

        this.postId = postId == null ? null : postId.trim();
    }
}