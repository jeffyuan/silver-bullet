package com.silverbullet.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.cms.dao.CmsArticleCommentMapper;
import com.silverbullet.cms.domain.CmsArticleComment;
import com.silverbullet.cms.service.ICmsArticleCommentService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.core.pojo.UserInfo;;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文章评论 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class CmsArticleCommentService implements ICmsArticleCommentService {

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleCommentService.class);

    @Autowired
    private CmsArticleCommentMapper cmsArticleCommentMapper;

    @Override
    public int countNum() {
        return cmsArticleCommentMapper.countNum();
    }

    @Override
    public BaseDataResult<CmsArticleComment> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsArticleComment> listResults = new BaseDataResult<CmsArticleComment>();
        listResults.setResultList(cmsArticleCommentMapper.findList());
        listResults.setTotalNum(cmsArticleCommentMapper.countNum());

        return listResults;
    }

    @Override
    public CmsArticleComment getOneById(String id) {
        return cmsArticleCommentMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(CmsArticleComment cmsArticleComment) {
        try {
            CmsArticleComment cmsArticleCommentNew = getOneById(cmsArticleComment.getId());

            if (cmsArticleCommentNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return cmsArticleCommentMapper.updateByPrimaryKey(cmsArticleComment) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            bret = cmsArticleCommentMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(CmsArticleComment cmsArticleComment) {
      try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            cmsArticleComment.setId(ToolUtil.getUUID());

            return cmsArticleCommentMapper.insert(cmsArticleComment) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
