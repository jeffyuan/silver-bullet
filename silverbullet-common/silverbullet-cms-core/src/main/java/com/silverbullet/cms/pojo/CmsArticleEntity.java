package com.silverbullet.cms.pojo;

import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.cms.domain.CmsArticleContent;
import com.silverbullet.cms.domain.CmsArticleFile;
import com.silverbullet.cms.domain.CmsArticleRunBehavior;

import java.util.List;

/**
 * 内容管理实体类
 * Created by jeffyuan on 2018/4/12.
 */
public class CmsArticleEntity {
    // 基本内容信息
    private CmsArticle cmsArticle;
    // 文档全文内容
    private CmsArticleContent cmsArticleContent;
    // 文档图片信息
    private CmsFileInfo cmsArticleImage;
    // 运行行为
    private CmsArticleRunBehavior cmsArticleRunBehavior;
    // 附件清单
    private List<CmsFileInfo> cmsArticleFileList;

    public CmsArticle getCmsArticle() {
        return cmsArticle;
    }

    public void setCmsArticle(CmsArticle cmsArticle) {
        this.cmsArticle = cmsArticle;
    }

    public CmsArticleContent getCmsArticleContent() {
        return cmsArticleContent;
    }

    public void setCmsArticleContent(CmsArticleContent cmsArticleContent) {
        this.cmsArticleContent = cmsArticleContent;
    }

    public CmsFileInfo getCmsArticleImage() {
        return cmsArticleImage;
    }

    public void setCmsArticleImage(CmsFileInfo cmsArticleImage) {
        this.cmsArticleImage = cmsArticleImage;
    }

    public CmsArticleRunBehavior getCmsArticleRunBehavior() {
        return cmsArticleRunBehavior;
    }

    public void setCmsArticleRunBehavior(CmsArticleRunBehavior cmsArticleRunBehavior) {
        this.cmsArticleRunBehavior = cmsArticleRunBehavior;
    }

    public List<CmsFileInfo> getCmsArticleFileList() {
        return cmsArticleFileList;
    }

    public void setCmsArticleFileList(List<CmsFileInfo> cmsArticleFileList) {
        this.cmsArticleFileList = cmsArticleFileList;
    }
}
