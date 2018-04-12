package com.silverbullet.cms.service;

import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.cms.domain.CmsArticleFile;
import com.silverbullet.cms.pojo.CmsArticleEntity;
import com.silverbullet.cms.pojo.CmsFileInfo;
import com.silverbullet.utils.BaseDataResult;

import java.io.InputStream;


/**
 * 文章表 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ICmsArticleService {
    public int countNum();
    public BaseDataResult<CmsArticle> list(int pageNum, int pageSize);
    public CmsArticle getOneById(String id);
    public boolean Update(CmsArticle cmsArticle);
    public boolean delete(String ids);
    public boolean createArticle(CmsArticleEntity cmsArticleEntity);

    /**
     * 插入文件
     * @param cmsFileInfo
     * @return
     */
    public CmsArticleFile insertFile(CmsFileInfo cmsFileInfo);

    /**
     * 更新文件
     * @param file 旧的文件对象
     * @param inputStream 新文件信息
     * @return
     */
    public CmsArticleFile updateFile(CmsArticleFile file, InputStream inputStream);

    /**
     * 删除文件
     * @param file
     * @return
     */
    public boolean deleteFile(CmsArticleFile file);

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    public boolean deleteFile(String fileId);

    /**
     * 根据文件id号获取文件
     * @param fileId
     * @return
     */
    public CmsArticleFile getOneFileById(String fileId);
}
