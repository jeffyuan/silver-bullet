package com.silverbullet.cms.domain;

import javax.validation.constraints.Size;
import java.util.Date;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.Integer;
import java.lang.String;
import javax.validation.constraints.NotEmpty;

public class CmsArticleRunBehavior {

    // id 
    @NotBlank(message = "id 不能为空" , groups = {FullValidate.class})
    @Size(max=32, message = "id 长度不能超过32", groups = {FullValidate.class})
    private String id;
    // visitNum 
    @NotNull(message = "visitNum 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer visitNum;
    // hotNum 
    @NotNull(message = "hotNum 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer hotNum;
    // lastVisitTime 
    @NotNull(message = "lastVisitTime 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Date lastVisitTime;
    // praisYes 
    @NotNull(message = "praisYes 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer praisYes;
    // praisNo 
    @NotNull(message = "praisNo 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer praisNo;
    // commentNum 
    @NotNull(message = "commentNum 不能为空" , groups = {FullValidate.class, AddValidate.class})
    private Integer commentNum;


    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id == null ? null : id.trim();
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(Integer visitNum) {

        this.visitNum = visitNum;
    }

    public Integer getHotNum() {
        return hotNum;
    }

    public void setHotNum(Integer hotNum) {

        this.hotNum = hotNum;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {

        this.lastVisitTime = lastVisitTime;
    }

    public Integer getPraisYes() {
        return praisYes;
    }

    public void setPraisYes(Integer praisYes) {

        this.praisYes = praisYes;
    }

    public Integer getPraisNo() {
        return praisNo;
    }

    public void setPraisNo(Integer praisNo) {

        this.praisNo = praisNo;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {

        this.commentNum = commentNum;
    }
}