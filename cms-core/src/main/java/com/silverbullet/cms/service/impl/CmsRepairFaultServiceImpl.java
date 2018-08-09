package com.silverbullet.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.cms.dao.CmsRepairFaultMapper;
import com.silverbullet.cms.dao.CmsRepairUserMapper;
import com.silverbullet.cms.domain.CmsRepairFault;
import com.silverbullet.cms.domain.CmsRepairUser;
import com.silverbullet.cms.service.ICmsRepairFaultService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class CmsRepairFaultServiceImpl implements ICmsRepairFaultService{

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleCommentService.class);

    @Autowired
    private CmsRepairFaultMapper cmsRepairFaultMapper;


    @Override
    public BaseDataResult<CmsRepairFault> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsRepairFault> listResults = new BaseDataResult<CmsRepairFault>();
        listResults.setResultList(cmsRepairFaultMapper.findList());
        listResults.setTotalNum(cmsRepairFaultMapper.countNum());
        System.out.println(listResults);

        return listResults;
    }

    @Override
    public boolean Insert(CmsRepairFault cmsRepairFault) {

        try{

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            cmsRepairFault.setId(ToolUtil.getUUID());
            cmsRepairFault.setCreateUsername(userInfo.getName());
            cmsRepairFault.setCreateUser(userInfo.getId());
            cmsRepairFault.setCreateTime(Calendar.getInstance().getTime());
            cmsRepairFault.setModifyUsername(userInfo.getName());
            cmsRepairFault.setModifyUser(userInfo.getId());
            cmsRepairFault.setModifyTime(cmsRepairFault.getCreateTime());
            cmsRepairFault.setDelSign(0);

            return cmsRepairFaultMapper.insert(cmsRepairFault) == 1?true : false;
        }catch(Exception ex){
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean Update(CmsRepairFault cmsRepairFault) {
        try{

            CmsRepairFault cmsRepairFault1 = getOneById(cmsRepairFault.getId());
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            cmsRepairFault.setCreateUsername(cmsRepairFault1.getCreateUsername());
            cmsRepairFault.setCreateUser(cmsRepairFault1.getCreateUser());
            cmsRepairFault.setCreateTime(cmsRepairFault1.getCreateTime());
            cmsRepairFault.setModifyUsername(userInfo.getName());
            cmsRepairFault.setModifyUser(userInfo.getId());
            cmsRepairFault.setModifyTime(Calendar.getInstance().getTime());
            cmsRepairFault.setDelSign(cmsRepairFault1.getDelSign());

            return cmsRepairFaultMapper.updateByPrimaryKey(cmsRepairFault) == 1?true : false;
        }catch(Exception ex){
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public CmsRepairFault getOneById(String id) {
        return cmsRepairFaultMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            bret = cmsRepairFaultMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public BaseDataResult<CmsRepairFault> search(String search, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        Map<String, String> data = ToolUtil.searchJsonData(search);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("search", data);
        BaseDataResult<CmsRepairFault> listResults = new BaseDataResult<>();

        listResults.setResultList(cmsRepairFaultMapper.findDataByKeyandValue(dataMap));
        listResults.setTotalNum(cmsRepairFaultMapper.searchCountNum(dataMap));

        return listResults;

    }
}
