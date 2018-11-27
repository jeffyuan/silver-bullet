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

import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
    public SysAuthUser getOneById(String id) {

        return sysAuthUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map<String, String>> getOneByUserId(String id) {

        return sysAuthUserOrgMapper.findListByUserId(id);
    }

    @Override
    public List<Map<String, String>> getPostByUserId(String id) {

        return sysAuthUserPostMapper.findListByUserId(id);
    }
    @Override
    public String getUserOrgId(String UserId){
        return sysAuthUserOrgMapper.getUserOrgId(UserId);
    }

    @Override
    public String getUserPostId(String UserId){
        return sysAuthUserPostMapper.getUserPostId(UserId);
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
            sysAuthUserOrgMapper.deleteByUserId(id);
            sysAuthUserPostMapper.deleteByUserId(id);
            bret = sysAuthUserMapper.deleteByPrimaryKey(id) == 1 ? true : false;
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

            String userId = ToolUtil.getUUID();

            sysAuthUser.setId(userId);
            sysAuthUser.setCreateTime(Calendar.getInstance().getTime());
            sysAuthUser.setModifyTime(Calendar.getInstance().getTime());
            sysAuthUser.setModifyUser(userInfo.getId());
            sysAuthUser.setCreateUser(userInfo.getId());
            sysAuthUser.setState("0");
            sysAuthUser.setPassword(ToolUtil.getPassword(10, "11", "SYSADMIN", "MD5"));
            sysAuthUser.setSalt("11");
            sysAuthUser.setLoginTime(Calendar.getInstance().getTime());
            sysAuthUserOrg.setId(ToolUtil.getUUID());
            sysAuthUserOrg.setUserId(userId);
            sysAuthUserOrg.setOrganizationId(OrganizationId);
            sysAuthUserOrgMapper.insert(sysAuthUserOrg);
            sysAuthUserPost.setId(ToolUtil.getUUID());
            sysAuthUserPost.setUserId(userId);
            sysAuthUserPost.setPostId(postId);
            sysAuthUserPostMapper.insert(sysAuthUserPost);
            return sysAuthUserMapper.insert(sysAuthUser) == 1 ? true : false;
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
            sysAuthUserOrg.setUserId(UserId);
            sysAuthUserOrg.setOrganizationId(OrganizationId);
            int orgInsert = sysAuthUserOrgMapper.insert(sysAuthUserOrg);
            sysAuthUserPost.setId(ToolUtil.getUUID());
            sysAuthUserPost.setUserId(UserId);
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
            sysAuthUserMapper.updateEditTimeById(UserId,Calendar.getInstance().getTime());

            sysAuthUserOrg.setId(org_id);
            sysAuthUserOrg.setUserId(UserId);
            sysAuthUserOrg.setOrganizationId(OrganizationId);
            int orgUpdate = sysAuthUserOrgMapper.updateByPrimaryKey(sysAuthUserOrg);
            sysAuthUserPost.setId(post_id);
            sysAuthUserPost.setUserId(UserId);
            sysAuthUserPost.setPostId(postId);
            int postUpdate = sysAuthUserPostMapper.updateByPrimaryKey(sysAuthUserPost);
            return orgUpdate + postUpdate == 2 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
