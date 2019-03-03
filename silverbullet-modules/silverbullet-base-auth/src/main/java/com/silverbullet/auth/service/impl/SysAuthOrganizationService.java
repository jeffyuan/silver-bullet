package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthOrganizationMapper;
import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.tools.Tool;
import java.util.Calendar;

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
    public BaseDataResult<SysAuthOrganization> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthOrganization> listResults = new BaseDataResult<SysAuthOrganization>();
        listResults.setResultList(sysAuthOrganizationMapper.findList());
        listResults.setTotalNum(sysAuthOrganizationMapper.countNum());

        return listResults;
    }

    @Override
    public SysAuthOrganization getOneById(String id) {
        return sysAuthOrganizationMapper.selectByPrimaryKey(id);
    }

    @Override
    public BaseDataResult<SysAuthOrganization> getParamByParentId(String parentId, int pageNum, int pageSize, Boolean sign) {

        BaseDataResult<SysAuthOrganization> listResults = new BaseDataResult<SysAuthOrganization>();

        if(sign == true){
            PageHelper.startPage(pageNum, pageSize);

            listResults.setResultList(sysAuthOrganizationMapper.findListByParentId(parentId));
            listResults.setTotalNum(sysAuthOrganizationMapper.countNumByParentId(parentId));

            return listResults;
        }else {
            listResults.setResultList(sysAuthOrganizationMapper.findListByParentId(parentId));
            return listResults;
        }
    }

    @Override
    public boolean Update(SysAuthOrganization sysAuthOrganization) {

        try{
            SysAuthOrganization sysAuthOrganizationOld = getOneById(sysAuthOrganization.getId());
            if(sysAuthOrganizationOld == null){
                return false;
            }
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthOrganization.setModifyTime(Calendar.getInstance().getTime());
            sysAuthOrganization.setModifyUser(userInfo.getId());
            sysAuthOrganization.setCreateUser(sysAuthOrganizationOld.getCreateUser());
            sysAuthOrganization.setCreateTime(sysAuthOrganizationOld.getCreateTime());
            sysAuthOrganization.setCreateUsername(sysAuthOrganizationOld.getCreateUsername());
            sysAuthOrganization.setModifyUsername(userInfo.getUsername());
            sysAuthOrganization.setOrganizationType(sysAuthOrganizationOld.getOrganizationType());
            sysAuthOrganization.setState(sysAuthOrganizationOld.getState());
            if(sysAuthOrganization.getParentId() == "NONE"){
                sysAuthOrganization.setPath(sysAuthOrganization.getId());
            }else{
                sysAuthOrganization.setPath(sysAuthOrganization.getParentId()+sysAuthOrganization.getId());
            }
            return sysAuthOrganizationMapper.updateByPrimaryKey(sysAuthOrganization) == 1 ? true : false;
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
            sysAuthOrganization.setCreateUser(userInfo.getId());
            sysAuthOrganization.setModifyTime(Calendar.getInstance().getTime());
            sysAuthOrganization.setModifyUser(userInfo.getId());
            sysAuthOrganization.setCreateUsername(userInfo.getUsername());
            sysAuthOrganization.setModifyUsername(userInfo.getUsername());
            sysAuthOrganization.setState("1");
            sysAuthOrganization.setOrganizationType("0");
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
        listResults.setResultList(sysAuthOrganizationMapper.findListByParentId(parentId));
        listResults.setTotalNum(sysAuthOrganizationMapper.countNumByParentId(parentId));

        return listResults;
    }

    @Override
    public BaseDataResult<SysAuthOrganization> findTreeNode(String parentId) {

        BaseDataResult<SysAuthOrganization> listResults = new BaseDataResult<SysAuthOrganization>();

        listResults.setResultList(sysAuthOrganizationMapper.findTreeNode(parentId));

        return listResults;
    }
}
