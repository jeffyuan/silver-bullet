package com.silverbullet.cms.service.impl;


import com.github.pagehelper.PageHelper;
import com.silverbullet.cms.dao.CmsRepairFaultServiceMapper;
import com.silverbullet.cms.domain.CmsRepairFaultService;
import com.silverbullet.cms.domain.CmsRepairServiceInfo;
import com.silverbullet.cms.service.ICmsRepairFaultServiceService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class CmsRepairFaultServiceServiceImpl implements ICmsRepairFaultServiceService {

    @Autowired
    private CmsRepairFaultServiceMapper cmsRepairFaultServiceMapper;

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleCommentService.class);

    @Override
    public BaseDataResult<CmsRepairServiceInfo> list(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsRepairServiceInfo> listResults = new BaseDataResult<CmsRepairServiceInfo>();
        listResults.setResultList(cmsRepairFaultServiceMapper.findServiceList());
        listResults.setTotalNum(cmsRepairFaultServiceMapper.serviceCountNum());

        return listResults;

    }

    @Override
    public boolean Insert(CmsRepairFaultService cmsRepairFaultService) {
        try{

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            cmsRepairFaultService.setId(ToolUtil.getUUID());
            cmsRepairFaultService.setCreateUsername(userInfo.getName());
            cmsRepairFaultService.setCreateUser(userInfo.getId());
            cmsRepairFaultService.setCreateTime(Calendar.getInstance().getTime());
            cmsRepairFaultService.setModifyUsername(userInfo.getName());
            cmsRepairFaultService.setModifyUser(userInfo.getId());
            cmsRepairFaultService.setModifyTime(cmsRepairFaultService.getCreateTime());
            cmsRepairFaultService.setDelSign(0);
            cmsRepairFaultService.setStatus(0);

            return cmsRepairFaultServiceMapper.insert(cmsRepairFaultService) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean Update(CmsRepairFaultService cmsRepairFaultService) {

        try{

            CmsRepairFaultService cmsRepairFaultService1 = getOneById1(cmsRepairFaultService.getId());
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            cmsRepairFaultService.setCreateUsername(cmsRepairFaultService1.getCreateUsername());
            cmsRepairFaultService.setCreateUser(cmsRepairFaultService1.getCreateUser());
            cmsRepairFaultService.setCreateTime(cmsRepairFaultService1.getCreateTime());
            cmsRepairFaultService.setModifyUsername(userInfo.getName());
            cmsRepairFaultService.setModifyUser(userInfo.getId());
            cmsRepairFaultService.setModifyTime(Calendar.getInstance().getTime());
            cmsRepairFaultService.setDelSign(0);
            cmsRepairFaultService.setStatus(cmsRepairFaultService1.getStatus());
            cmsRepairFaultService.setStartTime(cmsRepairFaultService1.getStartTime());
            cmsRepairFaultService.setFinishTime(cmsRepairFaultService1.getFinishTime());

            return cmsRepairFaultServiceMapper.updateByPrimaryKey(cmsRepairFaultService) == 1 ? true : false;

        }catch (Exception ex){
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }


    @Override
    public CmsRepairFaultService getOneById1(String id){
        return cmsRepairFaultServiceMapper.selectByPrimaryKey(id);
    }

    @Override
    public CmsRepairServiceInfo getOneById(String id) {
        return cmsRepairFaultServiceMapper.selectByServicePrimaryKey(id);
    }

    @Override
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            bret = cmsRepairFaultServiceMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public BaseDataResult<CmsRepairServiceInfo> search(String search, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, String> data = ToolUtil.searchJsonData(search);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("search", data);
        BaseDataResult<CmsRepairServiceInfo> listResults = new BaseDataResult<>();

        listResults.setResultList(cmsRepairFaultServiceMapper.findDataByKeyAndValue(dataMap));
        listResults.setTotalNum(cmsRepairFaultServiceMapper.searchCountNum(dataMap));

        return listResults;
    }

    @Override
    public CmsRepairFaultService findStatusById(String id) {

        CmsRepairFaultService cmsRepairFaultService = cmsRepairFaultServiceMapper.findStatusById(id);

        return cmsRepairFaultService;
    }

    @Override
    public boolean updateStatusById(CmsRepairFaultService cmsRepairFaultService) {

        try{
            return cmsRepairFaultServiceMapper.updateStatusById(cmsRepairFaultService) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
