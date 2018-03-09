package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.utils.BaseDataResult;

import java.util.List;
import java.util.Map;


/**
 * 权限定义 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysAuthActionTreeService {
    public int countNum();
    public BaseDataResult<SysAuthActionTree> list(int pageNum, int pageSize);
    public List<Map<String,String>> getActionsByUserId(String userId);
    public SysAuthActionTree getOneById(String id);
    public boolean Update(SysAuthActionTree sysAuthActionTree);
    public boolean delete(String id);
    public boolean Insert(SysAuthActionTree sysAuthActionTree);
}
