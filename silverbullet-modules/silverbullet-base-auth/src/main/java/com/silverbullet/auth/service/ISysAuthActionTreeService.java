package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.TreeNode;
import com.silverbullet.utils.TreeNode1;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;


/**
 * 权限定义 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysAuthActionTreeService {
    public int countNum();
    public BaseDataResult<SysAuthActionTree> list(String parentId, int pageNum, int pageSize);
    public BaseDataResult<SysAuthActionTree> list();
    public List<SysAuthUser> findUserById(List<SysAuthUser> result);
    public List<Map<String,Object>> getActionsByUserId(String userId);
    public SysAuthActionTree getOneById(String id);
    public boolean Update(SysAuthActionTree sysAuthActionTree);
    public boolean delete(String id);
    public boolean Insert(SysAuthActionTree sysAuthActionTree);
    public BaseDataResult<SysAuthActionTree> findTreeNode(String parentId);

    /* 切换两个节点的sort
     * @param currentId 当前节点
     * @param brotherId 兄弟节点
     * @return java.lang.Boolean
     * @author jeffyuan
     * @createDate 2019/2/24 11:14
     * @updateUser jeffyuan
     * @updateDate 2019/2/24 11:14
     * @updateRemark
     */
    public Boolean exchangeActionTreeSort(String currentId, String brotherId);
}
