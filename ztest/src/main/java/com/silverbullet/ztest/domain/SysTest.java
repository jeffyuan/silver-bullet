package com.silverbullet.ztest.domain;

import javax.validation.constraints.Size;
import java.util.Date;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import org.hibernate.validator.constraints.NotEmpty;
import java.lang.String;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SysTest {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // myName 
    @NotBlank(message = "myName 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "myName 长度不能超过64", groups = {FullValidate.class, AddValidate.class})
    private String myName;
    // createTime 
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date createTime;
    // cInt 
    @NotNull(message = "cInt 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer cInt;
    // cLength 
    @NotBlank(message = "cLength 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=16777215, message = "cLength 长度不能超过16777215", groups = {FullValidate.class, AddValidate.class})
    private String cLength;
    // commen 
    
    private String commen;
    // delSign 
    @NotBlank(message = "delSign 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1, message = "delSign 长度不能超过1", groups = {FullValidate.class, AddValidate.class})
    private String delSign;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {

        this.myName = myName == null ? null : myName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    public Integer getcInt() {
        return cInt;
    }

    public void setcInt(Integer cInt) {

        this.cInt = cInt;
    }

    public String getcLength() {
        return cLength;
    }

    public void setcLength(String cLength) {

        this.cLength = cLength == null ? null : cLength.trim();
    }

    public String getCommen() {
        return commen;
    }

    public void setCommen(String commen) {

        this.commen = commen == null ? null : commen.trim();
    }

    public String getDelSign() {
        return delSign;
    }

    public void setDelSign(String delSign) {

        this.delSign = delSign == null ? null : delSign.trim();
    }
}