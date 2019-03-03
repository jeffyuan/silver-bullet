package com.silverbullet.cms.service;

import com.silverbullet.cms.domain.CmsArticleComment;
import com.silverbullet.utils.BaseDataResult;


/**
 * 文章评论 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ICmsArticleCommentService {
    public int countNum();
    public BaseDataResult<CmsArticleComment> list(int pageNum, int pageSize);
    public CmsArticleComment getOneById(String id);
    public boolean Update(CmsArticleComment cmsArticleComment);
    public boolean delete(String ids);
    public boolean Insert(CmsArticleComment cmsArticleComment);
}
