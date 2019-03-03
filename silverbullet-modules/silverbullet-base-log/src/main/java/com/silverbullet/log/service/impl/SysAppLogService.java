package com.silverbullet.log.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.log.dao.SysAppLogMapper;
import com.silverbullet.log.domain.SysAppLog;
import com.silverbullet.log.service.ISysAppLogService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试类 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAppLogService implements ISysAppLogService {

    @Autowired
    private SysAppLogMapper sysAppLogMapper;

    @Override
    public int countNum() {
        return sysAppLogMapper.countNum();
    }

    @Override
    public BaseDataResult<SysAppLog> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAppLog> listResults = new BaseDataResult<SysAppLog>();
        listResults.setResultList(sysAppLogMapper.findList());
        listResults.setTotalNum(sysAppLogMapper.countNum());

             return listResults;
    }

    @Override
    public SysAppLog getOneById(String id) {
        return sysAppLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAppLog sysAppLog) {
        return sysAppLogMapper.updateByPrimaryKey(sysAppLog) == 1 ? true : false;
    }

    @Override
    public boolean delete(String id) {
        return sysAppLogMapper.deleteByPrimaryKey(id) == 1 ? true : false;
    }

    @Override
    public boolean Insert(SysAppLog sysAppLog) {
        return sysAppLogMapper.insert(sysAppLog) == 1 ? true : false;
    }
}
