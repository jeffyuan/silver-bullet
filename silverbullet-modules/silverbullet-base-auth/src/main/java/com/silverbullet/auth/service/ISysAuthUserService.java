package com.silverbullet.auth.service;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthUser;
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

    public SysAuthUser getOneById(Integer id);

    public List<Map<String, String>> getOneByUserId(String id);

    public List<Map<String, String>> getPostByUserId(String id);

    public SysAuthUser getOneByUserName(String userName);

    public boolean Update(SysAuthUser sysAuthUser, String postId, String OrganizationId, String UorgId, String UpostId);

    public boolean delete(String id);

    public boolean Insert(SysAuthUser sysAuthUser, String postId, String OrganizationId);

    public List<SysAuthActionTree> findList(SysAuthActionTree sysAuthActionTree);

    public List<Map<String, Object>> findPostNameByActionTreeId(String id);

    public boolean insertUserOrgPost(String UserId, String OrganizationId, String postId);

    public boolean updatetUserOrgPost(String org_id,String post_id, String UserId, String organizationId, String postId);

    public String getUserOrgId(String UserId);

    public String getUserPostId(String UserId);

    public boolean resetPassword(String id);

    public String getUserPassword(String id);

    public boolean changePassword(String id,String newPassword);

    public Map<String, Object> changeOrgPost(String userId);

    public Map<String, String> getUserOrgPost(String id);
}
