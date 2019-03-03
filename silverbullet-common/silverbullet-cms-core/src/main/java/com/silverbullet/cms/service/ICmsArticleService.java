package com.silverbullet.cms.service;

import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.cms.domain.CmsArticleFile;
import com.silverbullet.cms.pojo.CmsArticleEntity;
import com.silverbullet.cms.pojo.CmsArticleEx;
import com.silverbullet.cms.pojo.CmsFileInfo;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


/**
 * 文章表 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ICmsArticleService {
    public int countNum();
    public BaseDataResult<CmsArticle> list(String module, String moduleFilterKey, int pageNum, int pageSize);
    public CmsArticle getOneById(String id);
    public CmsArticleEx getOneExById(String id);
    public boolean Update(CmsArticleEx cmsArticle);
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

    public CmsFileInfo fileDispose(MultipartFile file, HttpServletRequest request) throws IOException;

    /**
     * 弹窗内置提示判断
     */
    public String ops(String con);
}
