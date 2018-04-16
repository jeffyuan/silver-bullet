package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthOrganizationMapper;
import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
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
 * 组织机构管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthOrganizationService implements ISysAuthOrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthOrganizationService.class);

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
    public boolean Update(SysAuthOrganization sysAuthOrganization) {
        try {
            SysAuthOrganization sysAuthOrganizationNew = getOneById(sysAuthOrganization.getId());

            if (sysAuthOrganizationNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return sysAuthOrganizationMapper.updateByPrimaryKey(sysAuthOrganization) == 1;
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
            bret = sysAuthOrganizationMapper.deleteByPrimaryKey(id) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(SysAuthOrganization sysAuthOrganization) {
      try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthOrganization.setId(ToolUtil.getUUID());

            return sysAuthOrganizationMapper.insert(sysAuthOrganization) == 1;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
