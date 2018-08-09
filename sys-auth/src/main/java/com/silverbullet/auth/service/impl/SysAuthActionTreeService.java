package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthActionTreeMapper;
import com.silverbullet.auth.domain.*;
import com.silverbullet.auth.service.ISysAuthActionTreeService;
import com.silverbullet.auth.service.ISysAuthPostService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.utils.TreeNode1;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 权限定义 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthActionTreeService implements ISysAuthActionTreeService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthActionTree.class);

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

    @Override
    public BaseDataResult<SysAuthActionTree> list() {
        BaseDataResult<SysAuthActionTree> listResults = new BaseDataResult<SysAuthActionTree>();
        listResults.setResultList(sysAuthActionTreeMapper.findList());
        return listResults;
    }

    @Override
    public List<SysAuthUser> findUserById(List<SysAuthUser> result) {
        return null;
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
        try{
            SysAuthActionTree sysAuthActionTree1 = getOneById(sysAuthActionTree.getId());
            if(sysAuthActionTree1 == null){
                return false;
            }
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthActionTree.setModifyTime(Calendar.getInstance().getTime());
            sysAuthActionTree.setModifyUser(userInfo.getName());
            sysAuthActionTree.setCreateUser(sysAuthActionTree1.getCreateUser());
            sysAuthActionTree.setCreateTime(sysAuthActionTree1.getCreateTime());
            sysAuthActionTree.setState(sysAuthActionTree1.getState());
            if(sysAuthActionTree.getParentId() == "NONE"){
                sysAuthActionTree.setPath(sysAuthActionTree.getId());
            }else{
                sysAuthActionTree.setPath(sysAuthActionTree.getParentId()+sysAuthActionTree.getId());
            }

            return sysAuthActionTreeMapper.updateByPrimaryKey(sysAuthActionTree) == 1 ? true : false;
        }catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        return sysAuthActionTreeMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean Insert(SysAuthActionTree sysAuthActionTree) {
        try{
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            sysAuthActionTree.setId(ToolUtil.getUUID());
            sysAuthActionTree.setCreateUser(userInfo.getName());
            sysAuthActionTree.setCreateTime(Calendar.getInstance().getTime());
            sysAuthActionTree.setModifyTime(Calendar.getInstance().getTime());
            sysAuthActionTree.setModifyUser(userInfo.getName());
            sysAuthActionTree.setState("1");
            if(sysAuthActionTree.getParentId() == "NONE"){
                sysAuthActionTree.setPath(sysAuthActionTree.getId());
            }else{
                sysAuthActionTree.setPath(sysAuthActionTree.getParentId()+sysAuthActionTree.getId());
            }

            return sysAuthActionTreeMapper.insert(sysAuthActionTree) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<TreeNode1> findTreeNode() {
        List<Map<String, String>> list = sysAuthActionTreeMapper.findTreeNode();
        List<TreeNode1> treeNodes = TreeNode1.formatNodes2TreeNode(list,"name","parent_id",
                "id","path","permission","action_id","icon");
        return treeNodes;
    }
}
