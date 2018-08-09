package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthActionTreeMapper;
import com.silverbullet.auth.dao.SysAuthPostActionMapper;
import com.silverbullet.auth.dao.SysAuthPostMapper;
import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.auth.domain.SysAuthPostAction;
import com.silverbullet.auth.service.ISysAuthPostService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * 机构管理角色 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthPostService implements ISysAuthPostService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthPost.class);


    @Autowired
    private SysAuthPostMapper sysAuthPostMapper;

    @Autowired
    private SysAuthPostActionMapper sysAuthPostActionMapper;

    @Autowired
    SysAuthActionTreeMapper sysAuthActionTreeMapper;

    @Override
    public int countNum() {
        return sysAuthPostMapper.countNum();
    }


    @Override
    public BaseDataResult<SysAuthPost> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthPost> listResults = new BaseDataResult<SysAuthPost>();
        listResults.setResultList(sysAuthPostMapper.findList());
        listResults.setTotalNum(sysAuthPostMapper.countNum());

             return listResults;
    }


    @Override
    public SysAuthPost getOneById(String id) {
        return sysAuthPostMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAuthPost sysAuthPost) {
        try{
            SysAuthPost sysAuthPostNew= getOneById(sysAuthPost.getId());
            if(sysAuthPostNew == null){
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            sysAuthPostNew.setName(sysAuthPost.getName());
            sysAuthPostNew.setModifyTime(Calendar.getInstance().getTime());
            sysAuthPostNew.setModifyUser(userInfo.getName());
            sysAuthPostNew.setModifyUsername(userInfo.getUsername());
            sysAuthPostNew.setIsExtends(sysAuthPost.getIsExtends());

            return sysAuthPostMapper.updateByPrimaryKey(sysAuthPostNew) == 1 ? true : false;
        }catch(Exception e){
            logger.error("Update Error: " + e.getMessage());
            return false;
        }


    }

    @Override
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            bret =sysAuthPostMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }
        return bret;
    }

    @Override
    public boolean Insert(SysAuthPost sysAuthPost) {
        try{
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthPost.setId(ToolUtil.getUUID());
            sysAuthPost.setCreateUser(userInfo.getId());
            sysAuthPost.setCreateUsername(userInfo.getUsername());
            sysAuthPost.setCreateTime(Calendar.getInstance().getTime());
            sysAuthPost.setModifyTime(Calendar.getInstance().getTime());
            sysAuthPost.setModifyUser(userInfo.getName());
            sysAuthPost.setModifyUsername(userInfo.getUsername());
            sysAuthPost.setState("1");
            return sysAuthPostMapper.insert(sysAuthPost) == 1 ? true : false;
        }catch(Exception e){
            logger.error("Update Error: " + e.getMessage());
            return false;
        }


    }

    @Override
    public boolean Handle(String data) {
        Map<String, String> datas = new HashMap<>();
        Map<String, String>result = new HashMap<>();
        String x[] = data.split("&");
        String m[] = new String[5];
        for(int i=0;i<x.length;i++){
            if(x[i].matches(".*actionId.*") == true){
                 m = x[i].split(":");
            }else{
                String y[] = x[i].split(":");
                datas.put(y[0],y[1]);
            }
        }
        String n[] = datas.get("postIds").split(",");
        for(int i=0;i<n.length;i++){
            result.put(m[1],n[i]);
        }
        String actionId[] = datas.get("postIds").split(",");

        sysAuthPostActionMapper.deleteByPrimaryKey(m[1]);
        SysAuthPostAction sysAuthPostAction = new SysAuthPostAction();
        for(int i=0;i< actionId.length;i++){
            sysAuthPostAction.setId(ToolUtil.getUUID());
            sysAuthPostAction.setPostId(m[1]);
            sysAuthPostAction.setActionId(actionId[i]);

            sysAuthPostActionMapper.insert(sysAuthPostAction);
        }
        return true;

    }

    @Override
    public List<SysAuthPostAction> findCheck(String postId) {
        return sysAuthPostActionMapper.findListByPostId(postId);
    }

    @Override
    public BaseDataResult<SysAuthPost> getPostByOrgId(String organizationId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthPost> listResults = new BaseDataResult<SysAuthPost>();
        listResults.setResultList(sysAuthPostMapper.findListByOrganizationId(organizationId));
        listResults.setTotalNum(sysAuthPostMapper.countNumByOrganizationId(organizationId));

        return listResults;
    }



    /**
     * 获取角色列表
     * @param id
     * @return
     */

    @Override
    public List<SysAuthPost> getPostList(String id) {
        return sysAuthPostMapper.findListByUserId(id);
    }
}


