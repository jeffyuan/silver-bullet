package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthPostMapper;
import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.auth.service.ISysAuthPostService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.core.pojo.UserInfo;;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 机构管理角色 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthPostService implements ISysAuthPostService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthPostService.class);

    @Autowired
    private SysAuthPostMapper sysAuthPostMapper;

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
        try {
            SysAuthPost sysAuthPostNew = getOneById(sysAuthPost.getId());

            if (sysAuthPostNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return sysAuthPostMapper.updateByPrimaryKey(sysAuthPost) == 1;
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
            bret = sysAuthPostMapper.deleteByPrimaryKey(id) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(SysAuthPost sysAuthPost) {
      try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthPost.setId(ToolUtil.getUUID());

            return sysAuthPostMapper.insert(sysAuthPost) == 1;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
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
