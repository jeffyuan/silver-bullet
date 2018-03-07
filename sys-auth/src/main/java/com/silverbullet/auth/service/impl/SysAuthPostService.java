package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthPostMapper;
import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.auth.service.ISysAuthPostService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthPostService implements ISysAuthPostService {

    @Autowired
    private SysAuthPostMapper sysAuthPostMapper;

    @Override
    public int countNum() {
        return sysAuthPostMapper.countNum();
    }

    @Override
    public BaseDataResult<SysAuthPost> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthPost> listResults = new BaseDataResult<SysAuthPost>();
        listResults.setResultList(sysAuthPostMapper.findList());
        listResults.setTotalNum(sysAuthPostMapper.countNum());

             return listResults;
    }

    @Override
    public SysAuthPost getOneById(String id) {
        return sysAuthPostMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAuthPost sysAuthUser) {
        return sysAuthPostMapper.updateByPrimaryKey(sysAuthUser) == 1 ? true : false;
    }

    @Override
    public boolean delete(String id) {
        return sysAuthPostMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean Insert(SysAuthPost sysAuthPost) {
        return sysAuthPostMapper.insert(sysAuthPost) == 1 ? true : false;
    }
}
