package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthUserMapper;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.core.pojo.UserInfo;;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthUserService implements ISysAuthUserService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthUserService.class);

    @Autowired
    private SysAuthUserMapper sysAuthUserMapper;

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
    public boolean Update(SysAuthUser sysAuthUser) {
        try {
            SysAuthUser sysAuthUserNew = getOneById(sysAuthUser.getId());

            if (sysAuthUserNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return sysAuthUserMapper.updateByPrimaryKey(sysAuthUser) == 1;
        } catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            bret = sysAuthUserMapper.deleteByPrimaryKey(id) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(SysAuthUser sysAuthUser) {
      try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthUser.setId(ToolUtil.getUUID());

            return sysAuthUserMapper.insert(sysAuthUser) == 1;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public SysAuthUser getOneByUserName(String userName) {
        return sysAuthUserMapper.selectByUserName(userName);
    }
}
