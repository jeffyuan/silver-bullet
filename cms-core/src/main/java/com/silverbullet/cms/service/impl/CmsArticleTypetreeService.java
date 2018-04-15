package com.silverbullet.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.cms.dao.CmsArticleTypetreeMapper;
import com.silverbullet.cms.domain.CmsArticleTypetree;
import com.silverbullet.cms.service.ICmsArticleTypetreeService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.core.pojo.UserInfo;;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章类别 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class CmsArticleTypetreeService implements ICmsArticleTypetreeService {

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleTypetreeService.class);

    @Autowired
    private CmsArticleTypetreeMapper cmsArticleTypetreeMapper;

    @Override
    public int countNum() {
        return cmsArticleTypetreeMapper.countNum();
    }

    @Override
    public BaseDataResult<CmsArticleTypetree> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsArticleTypetree> listResults = new BaseDataResult<CmsArticleTypetree>();
        listResults.setResultList(cmsArticleTypetreeMapper.findList());
        listResults.setTotalNum(cmsArticleTypetreeMapper.countNum());

        return listResults;
    }

    @Override
    public List<Map<String, Object>> findListByModule(String moduleName, String domain, String parentId) {

        HashMap<String, String> mapParams = new HashMap<String,String>();
        mapParams.put("moduleName", moduleName);
        mapParams.put("domain", domain);
        mapParams.put("parentId", parentId);
        return cmsArticleTypetreeMapper.findArticleTypeByModule(mapParams);
    }

    @Override
    public CmsArticleTypetree getOneById(String id) {
        return cmsArticleTypetreeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(CmsArticleTypetree cmsArticleTypetree) {
        try {
            CmsArticleTypetree cmsArticleTypetreeNew = getOneById(cmsArticleTypetree.getId());

            if (cmsArticleTypetreeNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return cmsArticleTypetreeMapper.updateByPrimaryKey(cmsArticleTypetree) == 1 ? true : false;
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
            bret = cmsArticleTypetreeMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(CmsArticleTypetree cmsArticleTypetree) {
      try {
            if (cmsArticleTypetree.getId() == null || cmsArticleTypetree.getId().equals("")) {
                cmsArticleTypetree.setId(ToolUtil.getUUID());
            }

            return cmsArticleTypetreeMapper.insert(cmsArticleTypetree) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
