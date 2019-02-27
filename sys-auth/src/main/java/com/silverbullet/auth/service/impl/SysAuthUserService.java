package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthUserMapper;
import com.silverbullet.auth.dao.SysAuthUserOrgMapper;
import com.silverbullet.auth.dao.SysAuthUserPostMapper;
import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.domain.SysAuthUserOrg;
import com.silverbullet.auth.domain.SysAuthUserPost;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthUserService implements ISysAuthUserService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthUser.class);
    @Autowired
    private SysAuthUserMapper sysAuthUserMapper;

    @Autowired
    private SysAuthUserOrgMapper sysAuthUserOrgMapper;

    @Autowired
    private SysAuthUserPostMapper sysAuthUserPostMapper;

    @Override
    public int countNum() {
        return sysAuthUserMapper.countNum();
    }

    @Override
    public BaseDataResult<SysAuthUser> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthUser> listResults = new BaseDataResult<SysAuthUser>();
        listResults.setResultList(sysAuthUserMapper.findList());
        listResults.setTotalNum(sysAuthUserMapper.countNum());

        return listResults;
    }

    @Override
    public SysAuthUser getOneById(Integer id) {

        return sysAuthUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, String>> getOneByUserId(String id) {

        return sysAuthUserOrgMapper.findListByUserId(ToolUtil.toInteger(id));
    }

    @Override
    public List<Map<String, String>> getPostByUserId(String id) {

        return sysAuthUserPostMapper.findListByUserId(ToolUtil.toInteger(id));
    }

    @Override
    public String getUserOrgId(String UserId) {
        return sysAuthUserOrgMapper.getUserOrgId(ToolUtil.toInteger(UserId));
    }

    @Override
    public String getUserPostId(String UserId) {
        return sysAuthUserPostMapper.getUserPostId(ToolUtil.toInteger(UserId));
    }

    @Override
    public boolean Update(SysAuthUser sysAuthUser, String postId, String OrganizationId, String UorgId, String UpostId) {
        try {
            SysAuthUser sysAuthUserNew = getOneById(sysAuthUser.getId());
            if (sysAuthUser == null) {
                return false;
            }

            SysAuthUserOrg sysAuthUserOrg = new SysAuthUserOrg();
            SysAuthUserPost sysAuthUserPost = new SysAuthUserPost();

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthUserNew.setName(sysAuthUser.getName());
            sysAuthUserNew.setUsername(sysAuthUser.getUsername());
            sysAuthUserNew.setUserType(sysAuthUser.getUserType());
            sysAuthUserNew.setComments(sysAuthUser.getComments());
            sysAuthUserNew.setModifyUser(userInfo.getId());
            sysAuthUserNew.setModifyTime(Calendar.getInstance().getTime());
            //userOrg
            sysAuthUserOrg.setId(UorgId);
            sysAuthUserOrg.setUserId(sysAuthUser.getId());
            sysAuthUserOrg.setOrganizationId(OrganizationId);
            sysAuthUserOrgMapper.updateByPrimaryKey(sysAuthUserOrg);
            //userPost
            sysAuthUserPost.setId(UpostId);
            sysAuthUserPost.setUserId(sysAuthUser.getId());
            sysAuthUserPost.setPostId(postId);
            sysAuthUserPostMapper.updateByPrimaryKey(sysAuthUserPost);


            return sysAuthUserMapper.updateByPrimaryKey(sysAuthUserNew) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(String ids) {
        String[] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            sysAuthUserOrgMapper.deleteByUserId(ToolUtil.toInteger(id));
            sysAuthUserPostMapper.deleteByUserId(ToolUtil.toInteger(id));
            bret = sysAuthUserMapper.deleteByPrimaryKey(ToolUtil.toInteger(id)) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }
        return bret;
    }

    @Override
    public boolean Insert(SysAuthUser sysAuthUser, String postId, String OrganizationId) {
        try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            SysAuthUserOrg sysAuthUserOrg = new SysAuthUserOrg();
            SysAuthUserPost sysAuthUserPost = new SysAuthUserPost();


            Date time = Calendar.getInstance().getTime();
            sysAuthUser.setModifyUser(userInfo.getId());
            sysAuthUser.setCreateUser(userInfo.getId());
            sysAuthUser.setState("0");
            sysAuthUser.setPassword(ToolUtil.getPassword(10, "11", "SYSADMIN", "MD5"));
            sysAuthUser.setSalt("11");
            sysAuthUser.setUserType("1");
            sysAuthUser.setLoginTime(time);
            sysAuthUser.setCreateTime(time);
            sysAuthUser.setModifyTime(time);
            sysAuthUserMapper.insert(sysAuthUser);
            Integer userId = sysAuthUserMapper.getUserId(time);
            if (userId != null) {
                sysAuthUserOrg.setId(ToolUtil.getUUID());
                sysAuthUserOrg.setUserId(userId);
                sysAuthUserOrg.setOrganizationId(OrganizationId);
                sysAuthUserOrgMapper.insert(sysAuthUserOrg);
                sysAuthUserPost.setId(ToolUtil.getUUID());
                sysAuthUserPost.setUserId(userId);
                sysAuthUserPost.setPostId(postId);
                sysAuthUserPostMapper.insert(sysAuthUserPost);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }

    }

    @Override
    public List<SysAuthActionTree> findList(SysAuthActionTree sysAuthActionTree) {
        return sysAuthUserMapper.findLists(sysAuthActionTree);
    }

    @Override
    public List<Map<String, Object>> findPostNameByActionTreeId(String id) {
        return sysAuthUserMapper.findPostNameByActionTreeId(id);
    }

    @Override
    public SysAuthUser getOneByUserName(String userName) {
        return sysAuthUserMapper.selectByUserName(userName);
    }


    @Override
    public boolean insertUserOrgPost(String UserId, String OrganizationId, String PostId) {
        try {
            SysAuthUserOrg sysAuthUserOrg = new SysAuthUserOrg();
            SysAuthUserPost sysAuthUserPost = new SysAuthUserPost();
            sysAuthUserOrg.setId(ToolUtil.getUUID());
            sysAuthUserOrg.setUserId(ToolUtil.toInteger(UserId));
            sysAuthUserOrg.setOrganizationId(OrganizationId);
            int orgInsert = sysAuthUserOrgMapper.insert(sysAuthUserOrg);
            sysAuthUserPost.setId(ToolUtil.getUUID());
            sysAuthUserPost.setUserId(ToolUtil.toInteger(UserId));
            sysAuthUserPost.setPostId(PostId);
            int postInsert = sysAuthUserPostMapper.insert(sysAuthUserPost);
            return orgInsert + postInsert == 2 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatetUserOrgPost(String org_id, String post_id, String UserId, String OrganizationId, String postId) {
        try {
            SysAuthUserOrg sysAuthUserOrg = new SysAuthUserOrg();
            SysAuthUserPost sysAuthUserPost = new SysAuthUserPost();
            sysAuthUserMapper.updateEditTimeById(UserId, Calendar.getInstance().getTime());

            sysAuthUserOrg.setId(org_id);
            sysAuthUserOrg.setUserId(ToolUtil.toInteger(UserId));
            sysAuthUserOrg.setOrganizationId(OrganizationId);
            int orgUpdate = sysAuthUserOrgMapper.updateByPrimaryKey(sysAuthUserOrg);
            sysAuthUserPost.setId(post_id);
            sysAuthUserPost.setUserId(ToolUtil.toInteger(UserId));
            sysAuthUserPost.setPostId(postId);
            int postUpdate = sysAuthUserPostMapper.updateByPrimaryKey(sysAuthUserPost);
            return orgUpdate + postUpdate == 2 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean resetPassword(String ids) {
        String[] arrIds = ids.split(",");
        String password = ToolUtil.getPassword(10, "11", "SYSADMIN", "MD5");

        boolean bret = true;
        for (String id : arrIds) {
            sysAuthUserMapper.updateEditTimeById(id, Calendar.getInstance().getTime());
            bret = sysAuthUserMapper.resetPassword(id, password) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("reset faild");
            }
        }
        return bret;
    }

    @Override
    public String getUserPassword(String id) {
        return sysAuthUserMapper.getUserPassword(id);
    }

    @Override
    public boolean changePassword(String id, String newPassword) {
        sysAuthUserMapper.updateEditTimeById(id, Calendar.getInstance().getTime());
        return sysAuthUserMapper.changePassword(id, newPassword);
    }

    @Override
    public Map<String, Object> changeOrgPost(String userId) {
        Map<String, Object> user = new HashMap<String, Object>();
        String orgName;
        String orgId;
        String postName;
        String postId;
        if (sysAuthUserOrgMapper.findListByUserId(ToolUtil.toInteger(userId)).size() == 0) {
            orgName = "";
            orgId = "";
        } else {
            orgName = sysAuthUserOrgMapper.findListByUserId(ToolUtil.toInteger(userId)).get(0).get("organizationName");
            orgId = sysAuthUserOrgMapper.findListByUserId(ToolUtil.toInteger(userId)).get(0).get("organizationId");
        }

        if (sysAuthUserPostMapper.findListByUserId(ToolUtil.toInteger(userId)).size() == 0) {
            postName = "";
            postId = "";
        } else {
            postName = sysAuthUserPostMapper.findListByUserId(ToolUtil.toInteger(userId)).get(0).get("postName");
            postId = sysAuthUserPostMapper.findListByUserId(ToolUtil.toInteger(userId)).get(0).get("postId");
        }
        user.put("orgName", orgName);
        user.put("orgId", orgId);
        user.put("postName", postName);
        user.put("postId", postId);
        return user;

    }

    @Override
    public Map<String, String> getUserOrgPost(String id) {
        Map<String, String> userOrgPost = new HashMap<String, String>();
        String org_id = sysAuthUserOrgMapper.getUserOrgId(ToolUtil.toInteger(id));
        String post_id = sysAuthUserPostMapper.getUserPostId(ToolUtil.toInteger(id));
        userOrgPost.put("org_id", org_id);
        userOrgPost.put("post_id", post_id);
        return userOrgPost;
    }
}
