package com.silverbullet.params.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.params.dao.SysParamsDictionaryMapper;
import com.silverbullet.params.domain.SysParamsDictionary;
import com.silverbullet.params.service.ISysParamsDictionaryService;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * 字典类 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysParamsDictionaryService implements ISysParamsDictionaryService {

    private static final Logger logger = LoggerFactory.getLogger(SysParamsDictionary.class);

    @Autowired
    private SysParamsDictionaryMapper sysParamsDictionaryMapper;

    @Override
    public int countNum() {
        return sysParamsDictionaryMapper.countNum();
    }

    @Override
    public BaseDataResult<SysParamsDictionary> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysParamsDictionary> listResults = new BaseDataResult<SysParamsDictionary>();
        listResults.setResultList(sysParamsDictionaryMapper.findList());
        listResults.setTotalNum(sysParamsDictionaryMapper.countNum());

        return listResults;
    }

    @Override
    public SysParamsDictionary getOneById(String id) {
        return sysParamsDictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysParamsDictionary sysParamsDictionary) {
        try {
            SysParamsDictionary sysParamsDictionaryNew = getOneById(sysParamsDictionary.getId());
            if (sysParamsDictionaryNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            sysParamsDictionaryNew.setName(sysParamsDictionary.getName());
            sysParamsDictionaryNew.setDicKey(sysParamsDictionary.getDicKey());
            sysParamsDictionaryNew.setComments(sysParamsDictionary.getComments());
            sysParamsDictionaryNew.setType(sysParamsDictionary.getType());
            sysParamsDictionaryNew.setModifyUsername(userInfo.getUsername());
            sysParamsDictionaryNew.setModifyUser(userInfo.getName());
            sysParamsDictionaryNew.setModifyTime(Calendar.getInstance().getTime());


            return sysParamsDictionaryMapper.updateByPrimaryKey(sysParamsDictionaryNew) == 1 ? true : false;
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
            bret = sysParamsDictionaryMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(SysParamsDictionary sysParamsDictionary) {
        try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            sysParamsDictionary.setId(ToolUtil.getUUID());
            sysParamsDictionary.setCreateTime(Calendar.getInstance().getTime());
            sysParamsDictionary.setModifyTime(sysParamsDictionary.getCreateTime());
            sysParamsDictionary.setCreateUser(userInfo.getName());
            sysParamsDictionary.setCreateUsername(userInfo.getUsername());
            sysParamsDictionary.setModifyUser(userInfo.getName());
            sysParamsDictionary.setModifyUsername(userInfo.getUsername());

            return sysParamsDictionaryMapper.insert(sysParamsDictionary) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
