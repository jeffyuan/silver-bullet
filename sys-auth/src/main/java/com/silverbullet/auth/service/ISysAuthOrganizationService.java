package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.TreeNode;
import com.silverbullet.utils.TreeNode1;

import java.util.List;


/**
 * 组织机构管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysAuthOrganizationService {
    public int countNum();
    public BaseDataResult<SysAuthOrganization> list(String parentId, int pageNum, int pageSize);
    public SysAuthOrganization getOneById(String id);
    public SysAuthOrganization getOneByParentId(String parentId);
    public boolean Update(SysAuthOrganization sysAuthOrganization);
    public boolean delete(String id);
    public boolean Insert(SysAuthOrganization sysAuthOrganization);

    public BaseDataResult<SysAuthOrganization> itemList(String dictKeyId, int pageNum, int pageSize);

    BaseDataResult<SysAuthOrganization> getOrgSelect();

    public BaseDataResult<SysAuthOrganization> localList(String parentId, int pageNum, int pageSize);

    public List<TreeNode1> findTreeNode();
}
