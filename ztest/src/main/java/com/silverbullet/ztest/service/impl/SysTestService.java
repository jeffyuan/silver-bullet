package com.silverbullet.ztest.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.ztest.dao.SysTestMapper;
import com.silverbullet.ztest.domain.SysTest;
import com.silverbullet.ztest.service.ISysTestService;
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
 * 测试 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysTestService implements ISysTestService {

    private static final Logger logger = LoggerFactory.getLogger(SysTestService.class);

    @Autowired
    private SysTestMapper sysTestMapper;

    @Override
    public int countNum() {
        return sysTestMapper.countNum();
    }

    @Override
    public BaseDataResult<SysTest> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysTest> listResults = new BaseDataResult<SysTest>();
        listResults.setResultList(sysTestMapper.findList());
        listResults.setTotalNum(sysTestMapper.countNum());

        return listResults;
    }

    @Override
    public SysTest getOneById(String id) {
        return sysTestMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysTest sysTest) {
        try {
            SysTest sysTestNew = getOneById(sysTest.getId());

            if (sysTestNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return sysTestMapper.updateByPrimaryKey(sysTest) == 1 ? true : false;
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
            bret = sysTestMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(SysTest sysTest) {
      try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysTest.setId(ToolUtil.getUUID());

            return sysTestMapper.insert(sysTest) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
