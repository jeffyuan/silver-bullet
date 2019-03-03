package com.silverbullet.auth.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @Auther: OFG
 * @Despriction:
 * @Data: Created in 14:43 2018/9/7
 * @Modify By:
 **/
public class SysIcon {
    // id
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // icoName
    @NotBlank(message = "title 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=256, message = "title 长度不能超过256", groups = {FullValidate.class, AddValidate.class})
    private String iconName;
    // createTime
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class})
    private Date createTime;
    //state
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class})
    private Integer state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
