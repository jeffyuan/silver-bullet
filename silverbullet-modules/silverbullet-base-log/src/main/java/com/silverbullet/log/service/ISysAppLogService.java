package com.silverbullet.log.service;

import com.silverbullet.log.domain.SysAppLog;
import com.silverbullet.utils.BaseDataResult;


/**
 * 测试类 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysAppLogService {
    public int countNum();
    public BaseDataResult<SysAppLog> list(int pageNum, int pageSize);
    public SysAppLog getOneById(String id);
    public boolean Update(SysAppLog sysAppLog);
    public boolean delete(String id);
    public boolean Insert(SysAppLog sysAppLog);
}
