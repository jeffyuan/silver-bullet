package com.silverbullet.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.cms.dao.CmsRepairFaultMapper;
import com.silverbullet.cms.dao.CmsRepairServiceTypeMapper;
import com.silverbullet.cms.domain.CmsRepairFault;
import com.silverbullet.cms.domain.CmsRepairServiceType;
import com.silverbullet.cms.service.ICmsRepairServiceTypeService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CmsRepairServiceTypeServiceImpl implements ICmsRepairServiceTypeService{

    private static final Logger logger = LoggerFactory.getLogger(CmsRepairServiceTypeServiceImpl.class);

    @Autowired
    private CmsRepairServiceTypeMapper cmsRepairServiceTypeMapper;

    @Override
    public BaseDataResult<CmsRepairServiceType> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsRepairServiceType> listResults = new BaseDataResult<CmsRepairServiceType>();
        listResults.setResultList(cmsRepairServiceTypeMapper.findList());
        listResults.setTotalNum(cmsRepairServiceTypeMapper.countNum());

        return listResults;
    }

    @Override
    public CmsRepairServiceType getOneById(String id) {
        return cmsRepairServiceTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public BaseDataResult<CmsRepairServiceType> findListByModule(String parentId) {

        BaseDataResult<CmsRepairServiceType> listResults = new BaseDataResult<CmsRepairServiceType>();
        listResults.setResultList(cmsRepairServiceTypeMapper.findListByParentId(parentId));

        return listResults;
    }

    @Override
    public CmsRepairServiceType setParentId(String parentId) {

        CmsRepairServiceType cmsRepairServiceType = new CmsRepairServiceType();
        cmsRepairServiceType.setParentId(parentId);

        return cmsRepairServiceType;
    }

    @Override
    public CmsRepairServiceType findParentById(String parentId) {

        CmsRepairServiceType cmsRepairServiceType = cmsRepairServiceTypeMapper.findParentByParentId(parentId);

        return cmsRepairServiceType;
    }

    @Override
    public BaseDataResult<CmsRepairServiceType> findChildListByParentId(String parentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsRepairServiceType> listResults = new BaseDataResult<CmsRepairServiceType>();
        listResults.setResultList(cmsRepairServiceTypeMapper.findChildByParentId(parentId));
        listResults.setTotalNum(cmsRepairServiceTypeMapper.findChildByParentIdCountNum(parentId));

        return listResults;
    }

    @Override
    public boolean Insert(CmsRepairServiceType cmsRepairServiceType) {
        try{
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

           cmsRepairServiceType.setId(ToolUtil.getUUID());
           cmsRepairServiceType.setPath(cmsRepairServiceType.getParentId()+cmsRepairServiceType.getId());
           cmsRepairServiceType.setCreateUsername(userInfo.getName());
           cmsRepairServiceType.setCreateUser(userInfo.getId());
           cmsRepairServiceType.setCreateTime(Calendar.getInstance().getTime());
           cmsRepairServiceType.setModifyUsername(cmsRepairServiceType.getCreateUsername());
           cmsRepairServiceType.setModifyUser(cmsRepairServiceType.getCreateUser());
           cmsRepairServiceType.setModifyTime(cmsRepairServiceType.getCreateTime());
           cmsRepairServiceType.setType(0);

           cmsRepairServiceTypeMapper.ChangeTypeById(cmsRepairServiceType.getParentId(), 1);

            return cmsRepairServiceTypeMapper.insert(cmsRepairServiceType) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }

    }

    @Override
    public boolean Update(CmsRepairServiceType cmsRepairServiceType) {

        try{

            CmsRepairServiceType cmsRepairServiceTypeOld = getOneById(cmsRepairServiceType.getId());
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            cmsRepairServiceType.setCreateUsername(cmsRepairServiceTypeOld.getCreateUsername());
            cmsRepairServiceType.setCreateUser(cmsRepairServiceTypeOld.getCreateUser());
            cmsRepairServiceType.setCreateTime(cmsRepairServiceTypeOld.getCreateTime());
            cmsRepairServiceType.setModifyUsername(userInfo.getName());
            cmsRepairServiceType.setModifyUser(userInfo.getId());
            cmsRepairServiceType.setModifyTime(Calendar.getInstance().getTime());


            return cmsRepairServiceTypeMapper.updateByPrimaryKey(cmsRepairServiceType) == 1 ? true : false;

        }catch (Exception ex){
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }


    }

    @Override
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        boolean bretChildrenNode = true;
        Map<String, String> map = new HashMap<>();

        for (String id : arrIds) {
            CmsRepairServiceType cmsRepairServiceType = cmsRepairServiceTypeMapper.findParentByParentId(id);
            bret = cmsRepairServiceTypeMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            List<CmsRepairServiceType> list= cmsRepairServiceTypeMapper.findListByParentId(cmsRepairServiceType.getParentId());
            if(list.isEmpty()){
                cmsRepairServiceTypeMapper.ChangeTypeById(cmsRepairServiceType.getParentId(), 0);
            }
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    /**
     * 根据条件搜索用户数据
     *
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public BaseDataResult<CmsRepairServiceType> search(String search, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, String> data = ToolUtil.searchJsonData(search);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("search", data);
        BaseDataResult<CmsRepairServiceType> listResults = new BaseDataResult<>();

        listResults.setResultList(cmsRepairServiceTypeMapper.findDataByKeyAndValue(dataMap));
        listResults.setTotalNum(cmsRepairServiceTypeMapper.searchCountNum(dataMap));

        return listResults;
    }


}
