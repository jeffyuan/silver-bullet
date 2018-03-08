package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthActionTreeMapper;
import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.service.ISysAuthActionTreeService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权限定义 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthActionTreeService implements ISysAuthActionTreeService {

    @Autowired
    private SysAuthActionTreeMapper sysAuthActionTreeMapper;

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
    public SysAuthActionTree getOneById(String id) {
        return sysAuthActionTreeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAuthActionTree sysAuthUser) {
        return sysAuthActionTreeMapper.updateByPrimaryKey(sysAuthUser) == 1 ? true : false;
    }

    @Override
    public boolean delete(String id) {
        return sysAuthActionTreeMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean Insert(SysAuthActionTree sysAuthActionTree) {
        return sysAuthActionTreeMapper.insert(sysAuthActionTree) == 1 ? true : false;
    }
}
