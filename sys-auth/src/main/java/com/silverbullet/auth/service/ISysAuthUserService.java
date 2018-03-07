package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.utils.DataResult;

/**
 * Created by jeffyuan on 2018/2/11.
 */

public interface ISysAuthUserService {
    public int countNum();
    public DataResult list(int pageNum, int pageSize);
    public SysAuthUser getOneById(String id);
    public boolean Update(SysAuthUser sysAuthUser);
    public boolean delete(String id);
    public boolean Insert(SysAuthUser sysAuthUser);
}
