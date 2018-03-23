package com.silverbullet.ztest.service;

import com.silverbullet.ztest.domain.SysTest;
import com.silverbullet.utils.BaseDataResult;


/**
 * 测试 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysTestService {
    public int countNum();
    public BaseDataResult<SysTest> list(int pageNum, int pageSize);
    public SysTest getOneById(String id);
    public boolean Update(SysTest sysTest);
    public boolean delete(String ids);
    public boolean Insert(SysTest sysTest);
}
