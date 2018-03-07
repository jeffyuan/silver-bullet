package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthUserMapper;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.utils.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
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
    public DataResult list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        DataResult listResults = new DataResult();
        listResults.setResultList(sysAuthUserMapper.findListWithMap());
        listResults.setTotalNum(sysAuthUserMapper.countNum());

        // 替换参数
        listResults.runDictionary("state", "0:禁用,1:可用,2:删除");
        listResults.runDictionary("type", "1:系统用户,2:其他,3:超级用户");
        listResults.runformatTime("logintime", "yyyy-MM-dd HH:mm:ss");

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
}
