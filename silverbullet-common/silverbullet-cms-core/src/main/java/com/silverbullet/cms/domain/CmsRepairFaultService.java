package com.silverbullet.cms.domain;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CmsRepairFaultService {
    //id
    @NotBlank(message = "id 不能为空", groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;

    //repairFaultId
    @NotBlank(message = "不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String repairFaultId;

    //repairUserId
    @NotBlank(message = "不能为空", groups = {FullValidate.class, AddValidate.class})
    @Size(max=32, message = "长度不能超过32", groups = {FullValidate.class, AddValidate.class})
    private String repairUserId;

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

    // comments
    private String comments;

    //delSign
    private Integer delSign;

    //status
    private Integer status;

    //startTime
    private Date startTime;

    //finishTime
    private Date finishTime;

    //serviceTypeId
    private String serviceTypeId;



    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public String getRepairFaultId() {
        return repairFaultId;
    }

    public void setRepairFaultId(String repairFaultId) {
        this.repairFaultId = repairFaultId == null ? null : repairFaultId.trim();
    }

    public String getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(String repairUserId) {
        this.repairUserId = repairUserId == null ? null : repairUserId.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status == null ? null : status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
