package com.silverbullet.specialcloudstore.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class BitStoreAnalyseField {

    // id 
    @NotNull(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=255, message = "id 长度不能超过9223372036854775807", groups = {FullValidate.class})
    private Long id;
    // version 
    @NotNull(message = "version 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=255, message = "version 长度不能超过9223372036854775807", groups = {FullValidate.class, AddValidate.class})
    private Long version;
    // author 
    @NotNull(message = "author 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=255, message = "author 长度不能超过9223372036854775807", groups = {FullValidate.class, AddValidate.class})
    private Long author;
    // bitstoreType 
    @NotNull(message = "bitstoreType 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=255, message = "bitstoreType 长度不能超过9223372036854775807", groups = {FullValidate.class, AddValidate.class})
    private Long bitstoreType;
    // createDate 
    @NotNull(message = "createDate 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date createDate;
    // delType 
    @NotBlank(message = "delType 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=1, message = "delType 长度不能超过1", groups = {FullValidate.class, AddValidate.class})
    private String delType;
    // fieldtype 
    @NotBlank(message = "fieldtype 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=20, message = "fieldtype 长度不能超过20", groups = {FullValidate.class, AddValidate.class})
    private String fieldtype;
    // name 
    @NotBlank(message = "name 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=255, message = "name 长度不能超过255", groups = {FullValidate.class, AddValidate.class})
    private String name;
    // no 
    @NotBlank(message = "no 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=20, message = "no 长度不能超过20", groups = {FullValidate.class, AddValidate.class})
    private String no;
    // status 
    @NotBlank(message = "status 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(max=20, message = "status 长度不能超过20", groups = {FullValidate.class, AddValidate.class})
    private String status;
    // updateDate 
    
    private Date updateDate;
    // updator 
    
    private Long updator;
    // fieldmapping 
    
    private String fieldmapping;


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

    public Long getBitstoreType() {
        return bitstoreType;
    }

    public void setBitstoreType(Long bitstoreType) {

        this.bitstoreType = bitstoreType;
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

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {

        this.fieldtype = fieldtype == null ? null : fieldtype.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        this.status = status == null ? null : status.trim();
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

    public String getFieldmapping() {
        return fieldmapping;
    }

    public void setFieldmapping(String fieldmapping) {

        this.fieldmapping = fieldmapping == null ? null : fieldmapping.trim();
    }
}