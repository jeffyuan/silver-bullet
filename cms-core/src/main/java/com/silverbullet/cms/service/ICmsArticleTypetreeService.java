package com.silverbullet.cms.service;

import com.silverbullet.cms.domain.CmsArticleTypetree;
import com.silverbullet.utils.BaseDataResult;

import java.util.List;
import java.util.Map;


/**
 * 文章类别 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ICmsArticleTypetreeService {
    public int countNum();
    public BaseDataResult<CmsArticleTypetree> list(int pageNum, int pageSize);
    public List<Map<String, Object>> findListByModule(String moduleName, String domain, String parentId);
    public CmsArticleTypetree getOneById(String id);
    public boolean Update(CmsArticleTypetree cmsArticleTypetree);
    public boolean delete(String ids);
    public boolean Insert(CmsArticleTypetree cmsArticleTypetree);
}
