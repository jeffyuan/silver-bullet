package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.utils.BaseDataResult;


/**
 * 机构管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysAuthPostService {
    public int countNum();
    public BaseDataResult<SysAuthPost> list(int pageNum, int pageSize);
    public SysAuthPost getOneById(String id);
    public boolean Update(SysAuthPost sysAuthPost);
    public boolean delete(String id);
    public boolean Insert(SysAuthPost sysAuthPost);
}
