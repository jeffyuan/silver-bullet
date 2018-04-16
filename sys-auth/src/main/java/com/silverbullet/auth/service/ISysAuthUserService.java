package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.utils.BaseDataResult;


/**
 * 用户管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysAuthUserService {
    public int countNum();
    public BaseDataResult<SysAuthUser> list(int pageNum, int pageSize);
    public SysAuthUser getOneById(String id);
    public SysAuthUser getOneByUserName(String userName);
    public boolean Update(SysAuthUser sysAuthUser);
    public boolean delete(String ids);
    public boolean Insert(SysAuthUser sysAuthUser);
}
