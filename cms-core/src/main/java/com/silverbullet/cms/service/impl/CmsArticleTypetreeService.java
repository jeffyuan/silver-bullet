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

import java.util.Calendar;
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

    /**
     * 更新节点
     * @param id 需要更新的id
     * @param cmsArticleTypetree 更新的字段
     * @return
     */
    @Override
    public boolean Update(String id, CmsArticleTypetree cmsArticleTypetree) {
        try {
            CmsArticleTypetree cmsArticleTypetreeNew = getOneById(cmsArticleTypetree.getId());
            if (cmsArticleTypetreeNew == null) {
                return false;
            }

            cmsArticleTypetreeNew.setName(cmsArticleTypetree.getName());
            cmsArticleTypetreeNew.setDomain(cmsArticleTypetree.getDomain());
            cmsArticleTypetreeNew.setComments(cmsArticleTypetree.getComments());
            cmsArticleTypetreeNew.setSort(cmsArticleTypetree.getSort());
            cmsArticleTypetreeNew.setType(cmsArticleTypetree.getType());

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            cmsArticleTypetreeNew.setModifyUser(userInfo.getId());
            cmsArticleTypetreeNew.setModifyTime(Calendar.getInstance().getTime());

            return cmsArticleTypetreeMapper.updateByPrimaryKey(cmsArticleTypetreeNew) == 1 ? true : false;
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
            // 将父节点数量减掉1
            CmsArticleTypetree cmsArticleTypetree = cmsArticleTypetreeMapper.selectByPrimaryKey(id);
            if (cmsArticleTypetree == null) {
                continue;
            }

            // 更新父节点中子节点的数量
            boolean bRet = updateParentChildrenNum(cmsArticleTypetree, -1);
            if (!bRet) {
                throw new RuntimeException("delete faild");
            }

            bret = cmsArticleTypetreeMapper.deleteByPrimaryKey(id) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    /**
     * 更新父节点的子节点数量
     * @param cmsArticleTypetree
     * @param n 增加数量  +1 -1
     * @return
     */
    private boolean updateParentChildrenNum(CmsArticleTypetree cmsArticleTypetree, int n) {
        // 非根节点，则修改父节点子节点数量
        if (!cmsArticleTypetree.getParentId().equals("NONE")) {
            cmsArticleTypetree = cmsArticleTypetreeMapper.selectByPrimaryKey(cmsArticleTypetree.getParentId());
            if (cmsArticleTypetree == null) {
                return false;
            }

            cmsArticleTypetree.setChildrenNum(cmsArticleTypetree.getChildrenNum() + n);
            return cmsArticleTypetreeMapper.updateByPrimaryKeySelective(cmsArticleTypetree) == 1;
        }

        return true;
    }

    @Override
    public boolean Insert(CmsArticleTypetree cmsArticleTypetree) {
        try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            if (cmsArticleTypetree.getId() == null || cmsArticleTypetree.getId().equals("")) {
                cmsArticleTypetree.setId(ToolUtil.getUUID());
            }

            cmsArticleTypetree.setCreateUser(userInfo.getId());
            cmsArticleTypetree.setCreateTime(Calendar.getInstance().getTime());
            cmsArticleTypetree.setModifyUser(userInfo.getId());
            cmsArticleTypetree.setModifyTime(cmsArticleTypetree.getCreateTime());

            // 根据parentId 插入path
            String parentId = cmsArticleTypetree.getParentId();
            if (parentId == null) {
                parentId = "NONE";
            }

            if (parentId.equals("NONE")) {
                cmsArticleTypetree.setPath(cmsArticleTypetree.getId());
            } else {
                CmsArticleTypetree cmsArticleTypetreeParent = cmsArticleTypetreeMapper.selectByPrimaryKey(parentId);
                if (cmsArticleTypetreeParent == null) {
                    return false;
                }

                cmsArticleTypetree.setPath(cmsArticleTypetreeParent.getPath() + cmsArticleTypetree.getId());
            }

            boolean bRet = cmsArticleTypetreeMapper.insert(cmsArticleTypetree) == 1;
            if (!bRet) {
                return false;
            }

            // 更新父节点中子节点的数量
            bRet = updateParentChildrenNum(cmsArticleTypetree, 1);
            if (!bRet) {
               throw new RuntimeException("delete faild");
            }

            return true;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            throw new RuntimeException("delete faild");
        }
    }
}
