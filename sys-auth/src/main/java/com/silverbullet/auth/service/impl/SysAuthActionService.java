package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthActionMapper;
import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthActionService;
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

/**
 * 功能权限 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthActionService implements ISysAuthActionService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthAction.class);

    @Autowired
    private SysAuthActionMapper sysAuthActionMapper;

    @Override
    public int countNum() {
        return sysAuthActionMapper.countNum();
    }

    @Override
    public BaseDataResult<SysAuthAction> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthAction> listResults = new BaseDataResult<SysAuthAction>();
        listResults.setResultList(sysAuthActionMapper.findList());
        listResults.setTotalNum(sysAuthActionMapper.countNum());

             return listResults;
    }

    @Override
    public BaseDataResult<SysAuthAction> list() {
        BaseDataResult<SysAuthAction> listResults = new BaseDataResult<SysAuthAction>();
        listResults.setResultList(sysAuthActionMapper.findList());
        return listResults;
    }

    @Override
    public SysAuthAction getOneById(String id) {
        return sysAuthActionMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAuthAction sysAuthAction) {
        try{
            SysAuthAction sysAuthAction1 = getOneById(sysAuthAction.getId());
            if(sysAuthAction1 == null){
                return false;
            }
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthAction.setModifyTime(Calendar.getInstance().getTime());
            sysAuthAction.setModifyUser(userInfo.getName());
            sysAuthAction.setCreateUser(sysAuthAction1.getCreateUser());
            sysAuthAction.setCreateTime(sysAuthAction1.getCreateTime());
            sysAuthAction.setState(sysAuthAction1.getState());
            sysAuthAction.setPermission(ToolUtil.getPermission(sysAuthAction.getUrl()));

            return sysAuthActionMapper.updateByPrimaryKey(sysAuthAction) == 1 ? true : false;
        }catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }



    }

    @Override
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            bret = sysAuthActionMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }
        return bret;
    }

    @Override
    public boolean Insert(SysAuthAction sysAuthAction) {
        try{
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            sysAuthAction.setId(ToolUtil.getUUID());
            sysAuthAction.setCreateUser(userInfo.getName());
            sysAuthAction.setCreateTime(Calendar.getInstance().getTime());
            sysAuthAction.setModifyTime(Calendar.getInstance().getTime());
            sysAuthAction.setModifyUser(userInfo.getName());
            sysAuthAction.setState("1");
            sysAuthAction.setPermission(ToolUtil.getPermission(sysAuthAction.getUrl()));

            return sysAuthActionMapper.insert(sysAuthAction) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<SysAuthAction> getPostActionList(String postId) {
        return sysAuthActionMapper.findListByPostId(postId);
    }
}
