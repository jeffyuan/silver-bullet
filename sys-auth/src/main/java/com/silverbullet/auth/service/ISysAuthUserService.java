package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.*;
import com.silverbullet.utils.BaseDataResult;

import java.util.List;
import java.util.Map;


/**
 * 用户管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysAuthUserService {
    public int countNum();
    public BaseDataResult<SysAuthUser> list(int pageNum, int pageSize);
    public SysAuthUser getOneById(String id);
    public List<Map<String, String>> getOneByUserId(String id);
    public List<Map<String, String>> getPostByUserId(String id);
    public SysAuthUser getOneByUserName(String userName);
    public boolean Update(SysAuthUser sysAuthUser, String postId, String OrganizationId, String UorgId, String UpostId);
    public boolean delete(String id);
    public boolean Insert(SysAuthUser sysAuthUser, String postId, String OrganizationId);
    public List<SysAuthActionTree> findList(SysAuthActionTree sysAuthActionTree);
    public List<Map<String, Object>> findPostNameByActionTreeId(String id);
}
