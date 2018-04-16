package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthActionTreeMapper;
import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.auth.service.ISysAuthActionTreeService;
import com.silverbullet.auth.service.ISysAuthPostService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.core.pojo.UserInfo;;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限定义 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthActionTreeService implements ISysAuthActionTreeService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthActionTreeService.class);

    @Autowired
    private SysAuthActionTreeMapper sysAuthActionTreeMapper;

    @Autowired
    private ISysAuthPostService iSysAuthPostService;

    @Override
    public int countNum() {
        return sysAuthActionTreeMapper.countNum();
    }

    @Override
    public BaseDataResult<SysAuthActionTree> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthActionTree> listResults = new BaseDataResult<SysAuthActionTree>();
        listResults.setResultList(sysAuthActionTreeMapper.findList());
        listResults.setTotalNum(sysAuthActionTreeMapper.countNum());

        return listResults;
    }

    /**
     * 根据用户id号获取具有权限的菜单信息
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getActionsByUserId(String userId) {

        // 根据userId获取岗位信息
        List<SysAuthPost> listPosts = iSysAuthPostService.getPostList(userId);
        if (listPosts.size() == 0) {
            return null;
        }

        List<String> postIds = new ArrayList<String>();
        for (SysAuthPost sap : listPosts) {
            postIds.add(sap.getId());
        }

        return sysAuthActionTreeMapper.findListByPostids(postIds);
    }

    @Override
    public SysAuthActionTree getOneById(String id) {
        return sysAuthActionTreeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAuthActionTree sysAuthActionTree) {
        try {
            SysAuthActionTree sysAuthActionTreeNew = getOneById(sysAuthActionTree.getId());

            if (sysAuthActionTreeNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return sysAuthActionTreeMapper.updateByPrimaryKey(sysAuthActionTree) == 1;
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
            SysAuthActionTree sysAuthActionTree = sysAuthActionTreeMapper.selectByPrimaryKey(id);
            if (sysAuthActionTree == null) {
                continue;
            }

            // 更新父节点中子节点的数量
            boolean bRet = updateParentChildrenNum(sysAuthActionTree, -1);
            if (!bRet) {
                throw new RuntimeException("delete faild");
            }

            bret = sysAuthActionTreeMapper.deleteByPrimaryKey(id) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    /**
     * 更新父节点的子节点数量
     * @param sysAuthActionTree
     * @param n 增加数量  +1 -1
     * @return
     */
    private boolean updateParentChildrenNum(SysAuthActionTree sysAuthActionTree, int n) {
        // 非根节点，则修改父节点子节点数量
        if (!sysAuthActionTree.getParentId().equals("NONE")) {
            sysAuthActionTree = sysAuthActionTreeMapper.selectByPrimaryKey(sysAuthActionTree.getParentId());
            if (sysAuthActionTree == null) {
                return false;
            }

            sysAuthActionTree.setChildrenNum(sysAuthActionTree.getChildrenNum() + n);
            return  sysAuthActionTreeMapper.updateByPrimaryKeySelective(sysAuthActionTree) == 1;
        }

        return true;
    }

    @Transactional
    @Override
    public boolean Insert(SysAuthActionTree sysAuthActionTree) {
        try {
            //UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            if (sysAuthActionTree.getId() == null || sysAuthActionTree.getId().equals("")) {
                sysAuthActionTree.setId(ToolUtil.getUUID());
            }

            boolean bRet = sysAuthActionTreeMapper.insert(sysAuthActionTree) == 1;
            if (!bRet) {
                return false;
            }

            // 更新父节点中子节点的数量
            bRet = updateParentChildrenNum(sysAuthActionTree, 1);
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
