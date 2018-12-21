package com.silverbullet.specialcloudstore.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class BitStoreType {

    // id 
    @NotNull(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=255, message = "id 长度不能超过255", groups = {FullValidate.class})
    private Long id;
    // version 
    @NotNull(message = "version 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=255, message = "version 长度不能超过255", groups = {FullValidate.class, AddValidate.class})
    private Long version;
    // author 
    @NotNull(message = "author 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=255, message = "author 长度不能超过255", groups = {FullValidate.class, AddValidate.class})
    private Long author;
    // createDate 
    @NotNull(message = "createDate 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date createDate;
    // delType 
    @NotBlank(message = "delType 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1, message = "delType 长度不能超过1", groups = {FullValidate.class, AddValidate.class})
    private String delType;
    // name 
    @NotBlank(message = "name 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=255, message = "name 长度不能超过255", groups = {FullValidate.class, AddValidate.class})
    private String name;
    // no 
    @NotBlank(message = "no 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=20, message = "no 长度不能超过20", groups = {FullValidate.class, AddValidate.class})
    private String no;
    // remark 
    
    private String remark;
    // updateDate 
    
    private Date updateDate;
    // updator 
    
    private Long updator;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {

        this.version = version;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {

        this.author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {

        this.createDate = createDate;
    }

    public String getDelType() {
        return delType;
    }

    public void setDelType(String delType) {

        this.delType = delType == null ? null : delType.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name == null ? null : name.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {

        this.no = no == null ? null : no.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {

        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {

        this.updateDate = updateDate;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {

        this.updator = updator;
    }
}