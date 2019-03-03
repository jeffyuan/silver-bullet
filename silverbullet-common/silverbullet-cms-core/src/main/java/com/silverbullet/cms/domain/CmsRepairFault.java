package com.silverbullet.cms.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CmsRepairFault {
    //id
    @NotBlank(message = "id 不能为空", groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;

    //faultName
    @NotBlank(message = "不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max=64, message = "faultName 长度不能超过64", groups = {FullValidate.class})
    private String faultName;

    //solveId
    @Size(max=32, message = "solveId 长度不能超过32", groups = {FullValidate.class})
    private String solveId;

    // createUsername
    @NotBlank(message = "createUsername 不能为空" , groups = {FullValidate.class})
    @Size(max=64, message = "createUsername 长度不能超过64", groups = {FullValidate.class})
    private String createUsername;

    // createUser
    private Integer createUser;

    //createTime
    @NotNull(message = "createTime 不能为空" , groups = {FullValidate.class})
    private Date createTime;

    // modifyUsername
    @NotBlank(message = "modifyUsername 不能为空" , groups = {FullValidate.class})
    @Size(max=64, message = "modifyUsername 长度不能超过64", groups = {FullValidate.class})
    private String modifyUsername;

    // modifyUser
    private Integer modifyUser;

    // modifyTime
    @NotNull(message = "modifyTime 不能为空" , groups = {FullValidate.class})
    private Date modifyTime;

    //faultImgId
    @Size(max=32, message = "modifyUser 长度不能超过32", groups = {FullValidate.class})
    private String faultImgId;

    //sort
    @Size(max=11, message = "modifyUser 长度不能超过11", groups = {FullValidate.class})
    private String sort;

    //faultDescribe
    @Size(max=128, message = "modifyUser 长度不能超过128", groups = {FullValidate.class})
    private String faultDescribe;

    //faultTypeId
    @Size(max=32, message = "modifyUser 长度不能超过32", groups = {FullValidate.class})
    private String faultTypeId;

    // pageNum
    @NotNull(message = "pageNum 不能为空" , groups = {FullValidate.class})
    private Integer pageNum;


    // comments
    @Size(max=128, message = "modifyUser 长度不能超过128", groups = {FullValidate.class})
    private String comments;

    // faultReason
    @Size(max=50, message = "modifyUser 长度不能超过50", groups = {FullValidate.class})
    private String faultReason;

    //delSign
    private Integer delSign;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }


    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername == null ? null : createUsername.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getModifyUsername() {
        return modifyUsername;
    }

    public void setModifyUsername(String modifyUsername) {
        this.modifyUsername = modifyUsername == null ? null : modifyUsername.trim();
    }

    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getDelSign() {
        return delSign;
    }

    public void setDelSign(Integer delSign) {
        this.delSign = delSign == null ? null : 0;
    }


    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName == null ? null : faultName.trim();
    }

    public String getSolveId() {
        return solveId;
    }

    public void setSolveId(String solveId) {
        this.solveId = solveId == null ? null : solveId.trim();
    }

    public String getFaultImgId() {
        return faultImgId;
    }

    public void setFaultImgId(String faultImgId) {
        this.faultImgId = faultImgId == null ? null : faultImgId.trim();
    }

    public String getFaultDescribe() {
        return faultDescribe;
    }

    public void setFaultDescribe(String faultDescribe) {
        this.faultDescribe = faultDescribe == null ? null : faultDescribe.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public String getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(String faultTypeId) {
        this.faultTypeId = faultTypeId == null ? null : faultTypeId.trim();
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason == null ? null : faultReason.trim();
    }
}
