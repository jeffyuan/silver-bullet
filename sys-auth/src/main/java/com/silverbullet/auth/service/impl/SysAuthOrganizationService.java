package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthOrganizationMapper;
import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 组织机构管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthOrganizationService implements ISysAuthOrganizationService {

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
    public boolean Update(SysAuthOrganization sysAuthUser) {
        return sysAuthOrganizationMapper.updateByPrimaryKey(sysAuthUser) == 1 ? true : false;
    }

    @Override
    public boolean delete(String id) {
        return sysAuthOrganizationMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean Insert(SysAuthOrganization sysAuthOrganization) {
        return sysAuthOrganizationMapper.insert(sysAuthOrganization) == 1 ? true : false;
    }
}
