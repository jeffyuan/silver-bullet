package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthActionMapper;
import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.service.ISysAuthActionService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能权限 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthActionService implements ISysAuthActionService {

    @Autowired
    private SysAuthActionMapper sysAuthActionMapper;

    @Override
    public int countNum() {
        return sysAuthActionMapper.countNum();
    }

    @Override
    public BaseDataResult<SysAuthAction> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthAction> listResults = new BaseDataResult<SysAuthAction>();
        listResults.setResultList(sysAuthActionMapper.findList());
        listResults.setTotalNum(sysAuthActionMapper.countNum());

             return listResults;
    }

    @Override
    public SysAuthAction getOneById(String id) {
        return sysAuthActionMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAuthAction sysAuthUser) {
        return sysAuthActionMapper.updateByPrimaryKey(sysAuthUser) == 1 ? true : false;
    }

    @Override
    public boolean delete(String id) {
        return sysAuthActionMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean Insert(SysAuthAction sysAuthAction) {
        return sysAuthActionMapper.insert(sysAuthAction) == 1 ? true : false;
    }

    @Override
    public List<SysAuthAction> getPostActionList(String postId) {
        return sysAuthActionMapper.findListByPostId(postId);
    }
}
