package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthOrganizationMapper;
import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.utils.TreeNode1;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 组织机构管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthOrganizationService implements ISysAuthOrganizationService {
    private static final Logger logger = LoggerFactory.getLogger(SysAuthOrganization.class);
    @Autowired
    private SysAuthOrganizationMapper sysAuthOrganizationMapper;

    @Override
    public int countNum() {
        return sysAuthOrganizationMapper.countNum();
    }

    @Override
    public BaseDataResult<SysAuthOrganization> list(String parentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthOrganization> listResults = new BaseDataResult<SysAuthOrganization>();
        listResults.setResultList(sysAuthOrganizationMapper.findListById(parentId));
        listResults.setTotalNum(sysAuthOrganizationMapper.countNumById(parentId));

        return listResults;
    }

    @Override
    public SysAuthOrganization getOneById(String id) {
        return sysAuthOrganizationMapper.selectByPrimaryKey(id);
    }

    @Override
    public SysAuthOrganization getOneByParentId(String parentId) {
        return sysAuthOrganizationMapper.findListByParentId(parentId);
    }

    @Override
    public boolean Update(SysAuthOrganization sysAuthUser) {

        try{
            SysAuthOrganization sysAuthAction1 = getOneById(sysAuthUser.getId());
            if(sysAuthAction1 == null){
                return false;
            }
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthUser.setModifyTime(Calendar.getInstance().getTime());
            sysAuthUser.setModifyUser(userInfo.getName());
            sysAuthUser.setCreateUser(sysAuthAction1.getCreateUser());
            sysAuthUser.setCreateTime(sysAuthAction1.getCreateTime());
            sysAuthUser.setState(sysAuthAction1.getState());
            if(sysAuthUser.getParentId() == "NONE"){
                sysAuthUser.setPath(sysAuthUser.getId());
            }else{
                sysAuthUser.setPath(sysAuthUser.getParentId()+sysAuthUser.getId());
            }
            return sysAuthOrganizationMapper.updateByPrimaryKey(sysAuthUser) == 1 ? true : false;
        }catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            bret =sysAuthOrganizationMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }
        return bret;

    }

    @Override
    public boolean Insert(SysAuthOrganization sysAuthOrganization) {
        try{
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthOrganization.setId(ToolUtil.getUUID());
            sysAuthOrganization.setCreateTime(Calendar.getInstance().getTime());
            sysAuthOrganization.setCreateUser(userInfo.getName());
            sysAuthOrganization.setModifyTime(Calendar.getInstance().getTime());
            sysAuthOrganization.setModifyUser(userInfo.getName());
            sysAuthOrganization.setState("1");
            if(sysAuthOrganization.getParentId() == "NONE"){
                sysAuthOrganization.setPath(sysAuthOrganization.getId());
            }else{
                sysAuthOrganization.setPath(sysAuthOrganization.getParentId()+sysAuthOrganization.getId());
            }
            return sysAuthOrganizationMapper.insert(sysAuthOrganization) == 1 ? true : false;
        }catch (Exception e){
            logger.error("Update Error: " + e.getMessage());
            return false;
        }


    }

    @Override
    public BaseDataResult<SysAuthOrganization> itemList(String KeyId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthOrganization> listResults = new BaseDataResult<SysAuthOrganization>();
        listResults.setResultList(sysAuthOrganizationMapper.findListByKeyId(KeyId));
        listResults.setTotalNum(sysAuthOrganizationMapper.countNumByKeyId(KeyId));

        return listResults;
    }


    @Override
    public BaseDataResult<SysAuthOrganization> getOrgSelect() {
        BaseDataResult<SysAuthOrganization> listResults = new BaseDataResult<SysAuthOrganization>();
        listResults.setResultList(sysAuthOrganizationMapper.findList());
        return listResults;
    }

    @Override
    public BaseDataResult<SysAuthOrganization> localList(String parentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthOrganization> listResults = new BaseDataResult<SysAuthOrganization>();
        listResults.setResultList(sysAuthOrganizationMapper.findListById(parentId));
        listResults.setTotalNum(sysAuthOrganizationMapper.countNumById(parentId));

        return listResults;
    }

    @Override
    public List<TreeNode1> findTreeNode() {
        List<Map<String, String>> list = sysAuthOrganizationMapper.findTreeNode();
        List<TreeNode1> treeNodes = TreeNode1.formatNodes2TreeNode(list,"name","parent_id",
                "id","path","createUser","createUser","icon");
        return treeNodes;
    }
}
