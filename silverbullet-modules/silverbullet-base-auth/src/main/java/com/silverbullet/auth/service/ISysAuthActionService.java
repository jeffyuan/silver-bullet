package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.utils.BaseDataResult;

import java.util.List;
import java.util.Map;


/**
 * 功能权限 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysAuthActionService {
    public int countNum();
    public BaseDataResult<SysAuthAction> list(String parentId, int pageNum, int pageSize);
    public BaseDataResult<SysAuthAction> list();
    public SysAuthAction getOneById(String id);
    public boolean Update(SysAuthAction sysAuthAction);
    public boolean delete(String id);
    public boolean Insert(SysAuthAction sysAuthAction);

    /**
     * 获取岗位下的功能权限
     * @param postId
     * @return
     */
    public List<SysAuthAction> getPostActionList(String postId);
    public List<Map<String, Object>> getActionByParentId(String parentId);
}
