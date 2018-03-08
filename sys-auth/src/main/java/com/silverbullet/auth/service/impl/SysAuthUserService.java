package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthUserMapper;
import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthUserService implements ISysAuthUserService {

    @Autowired
    private SysAuthUserMapper sysAuthUserMapper;

    @Override
    public int countNum() {
        return sysAuthUserMapper.countNum();
    }

    @Override
    public BaseDataResult<SysAuthUser> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthUser> listResults = new BaseDataResult<SysAuthUser>();
        listResults.setResultList(sysAuthUserMapper.findList());
        listResults.setTotalNum(sysAuthUserMapper.countNum());

             return listResults;
    }

    @Override
    public SysAuthUser getOneById(String id) {
        return sysAuthUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAuthUser sysAuthUser) {
        return sysAuthUserMapper.updateByPrimaryKey(sysAuthUser) == 1 ? true : false;
    }

    @Override
    public boolean delete(String id) {
        return sysAuthUserMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean Insert(SysAuthUser sysAuthUser) {
        return sysAuthUserMapper.insert(sysAuthUser) == 1 ? true : false;
    }

    @Override
    public SysAuthUser getOneByUserName(String userName) {
        return sysAuthUserMapper.selectByUserName(userName);
    }
}
