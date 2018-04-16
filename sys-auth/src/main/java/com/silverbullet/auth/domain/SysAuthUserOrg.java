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

public class SysAuthUserOrg {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // userId 
    @NotBlank(message = "userId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "userId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String userId;
    // organizationId 
    @NotBlank(message = "organizationId 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "organizationId 长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String organizationId;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {

        this.organizationId = organizationId == null ? null : organizationId.trim();
    }
}