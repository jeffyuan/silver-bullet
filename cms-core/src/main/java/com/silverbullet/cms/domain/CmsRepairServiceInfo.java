package com.silverbullet.cms.domain;


import java.util.Date;

/**
 * 维修业务信息类
 */
public class CmsRepairServiceInfo {
    //id
    private String id;

    //createUsername
    private String createUsername;

    //createUser
    private String createUser;

    //createTime
    private Date createTime;

    //modifyUsername
    private String modifyUsername;

    //modifyUser
    private String modifyUser;

    //modifyTime
    private Date modifyTime;

    //comments
    private String comments;

    //delSign
    private String delSign;

    //customName
    private String customName;

    //mobPhone
    private String mobPhone;

    //address
    private String address;

    //faultName
    private String faultName;

    //repairFaultId
    private String repairFaultId;

    //repairUserId
    private String repairUserId;

    //status
    private Integer status;

    //startTime
    private Date startTime;

    //finishTime
    private Date finishTime;

    private String serviceTypeId;

    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUsername() {
        return modifyUsername;
    }

    public void setModifyUsername(String modifyUsername) {
        this.modifyUsername = modifyUsername;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
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
        this.comments = comments;
    }

    public String getDelSign() {
        return delSign;
    }

    public void setDelSign(String delSign) {
        this.delSign = delSign;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public String getRepairFaultId() {
        return repairFaultId;
    }

    public void setRepairFaultId(String repairFaultId) {
        this.repairFaultId = repairFaultId;
    }

    public String getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(String repairUserId) {
        this.repairUserId = repairUserId;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
