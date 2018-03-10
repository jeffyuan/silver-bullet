package com.silverbullet.ztest.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.ztest.dao.SysTestMapper;
import com.silverbullet.ztest.domain.SysTest;
import com.silverbullet.ztest.service.ISysTestService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysTestService implements ISysTestService {

    @Autowired
    private SysTestMapper sysTestMapper;

    @Override
    public int countNum() {
        return sysTestMapper.countNum();
    }

    @Override
    public BaseDataResult<SysTest> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysTest> listResults = new BaseDataResult<SysTest>();
        listResults.setResultList(sysTestMapper.findList());
        listResults.setTotalNum(sysTestMapper.countNum());

             return listResults;
    }

    @Override
    public SysTest getOneById(String id) {
        return sysTestMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysTest sysTest) {
        return sysTestMapper.updateByPrimaryKey(sysTest) == 1 ? true : false;
    }

    @Override
    public boolean delete(String id) {
        return sysTestMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean Insert(SysTest sysTest) {
        return sysTestMapper.insert(sysTest) == 1 ? true : false;
    }
}
