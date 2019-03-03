package com.silverbullet.cms.util;

import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.cms.domain.CmsArticleContent;
import com.silverbullet.cms.domain.CmsArticleRunBehavior;
import com.silverbullet.cms.pojo.CmsArticleEntity;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;

import java.util.Calendar;

/**
 * 对象生成工厂
 * Created by jeffyuan on 2018/4/24.
 */
public class EntryCreateFactory {

    public static CmsArticleEntity createCmsArticleEntry() {
        return new CmsArticleEntity();
    }

    /**
     * 初始化文档对象
     * @param cmsArticle
     * @return
     */
    public static CmsArticle initCmsArticle(CmsArticle cmsArticle) {
        if (cmsArticle.getId() == null || cmsArticle.getId().length() == 0) {
            cmsArticle.setId(ToolUtil.getUUID());
        }

        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

        if (cmsArticle.getCreateUser() == null) {
            cmsArticle.setCreateUser(userInfo.getId());
        }

        if (cmsArticle.getCreateUsername() == null) {
            cmsArticle.setCreateUsername(userInfo.getUsername());
        }

        if (cmsArticle.getCreateTime() == null) {
            cmsArticle.setCreateTime(Calendar.getInstance().getTime());
        }

        if (cmsArticle.getModifyUser() == null) {
            cmsArticle.setModifyUser(userInfo.getId());
        }

        if (cmsArticle.getModifyUsername() == null) {
            cmsArticle.setModifyUsername(userInfo.getUsername());
        }

        if (cmsArticle.getModifyTime() == null) {
            cmsArticle.setModifyTime(cmsArticle.getCreateTime());
        }

        if (cmsArticle.getPublicTime() == null) {
            cmsArticle.setPublicTime(Calendar.getInstance().getTime());
        }

        if (cmsArticle.getArtImgId() == null) {
            cmsArticle.setArtImgId("");
        }

        if (cmsArticle.getState() == null) {
            cmsArticle.setState("1");
        }

        return cmsArticle;
    }

    /**
     * 生成文档运行数据对象
     * @return
     */
    public static CmsArticleRunBehavior createCmsArticleRunBehavior() {
        CmsArticleRunBehavior cmsArticleRunBehavior = new CmsArticleRunBehavior();
        cmsArticleRunBehavior.setId(ToolUtil.getUUID());
        cmsArticleRunBehavior.setCommentNum(0);
        cmsArticleRunBehavior.setHotNum(0);
        cmsArticleRunBehavior.setLastVisitTime(Calendar.getInstance().getTime());
        cmsArticleRunBehavior.setPraisNo(0);
        cmsArticleRunBehavior.setPraisYes(0);
        cmsArticleRunBehavior.setVisitNum(0);

        return cmsArticleRunBehavior;
    }

    /**
     * 生成内容对象
     * @return
     */
    public static CmsArticleContent createCmsArticleContent() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

        CmsArticleContent cmsArticleContent = new CmsArticleContent();
        cmsArticleContent.setId(ToolUtil.getUUID());
        cmsArticleContent.setCreateUser(userInfo.getId());
        cmsArticleContent.setCreateUsername(userInfo.getUsername());
        cmsArticleContent.setCreateTime(Calendar.getInstance().getTime());
        cmsArticleContent.setModifyUser(userInfo.getId());
        cmsArticleContent.setModifyUsername(userInfo.getUsername());
        cmsArticleContent.setModifyTime(cmsArticleContent.getCreateTime());
        cmsArticleContent.setContVersion(1);
        cmsArticleContent.setContState("1");

        return cmsArticleContent;
    }
}

