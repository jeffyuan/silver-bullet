package com.silverbullet.cms.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.cms.dao.CmsRepairUserMapper;
import com.silverbullet.cms.domain.CmsRepairUser;
import com.silverbullet.cms.service.ICmsRepairUserService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.beetl.sql.core.annotatoin.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmsRepairUserImpl implements ICmsRepairUserService {

    private static final Logger logger = LoggerFactory.getLogger(CmsArticleCommentService.class);

    @Autowired
    private CmsRepairUserMapper cmsRepairUserMapper;

    @Override
    public BaseDataResult<CmsRepairUser> list(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<CmsRepairUser> listResults = new BaseDataResult<CmsRepairUser>();
        listResults.setResultList(cmsRepairUserMapper.findList());
        listResults.setTotalNum(cmsRepairUserMapper.countNum());

        return listResults;
    }

    @Override
    public boolean Insert(CmsRepairUser cmsRepairUser) {
        try {
            if(cmsRepairUser.getcName().isEmpty()){
                Map<String, String> sex = new HashMap<String, String>();
                sex.put("0", "先生");
                sex.put("1", "女士");
                cmsRepairUser.setcName(cmsRepairUser.getFamilyName()+sex.get(cmsRepairUser.getSex()));
            }
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            cmsRepairUser.setId(ToolUtil.getUUID());
            cmsRepairUser.setCreateUsername(userInfo.getName());
            cmsRepairUser.setCreateUser(userInfo.getId());
            cmsRepairUser.setCreateTime(Calendar.getInstance().getTime());
            cmsRepairUser.setModifyUsername(userInfo.getName());
            cmsRepairUser.setModifyUser(userInfo.getId());
            cmsRepairUser.setModifyTime(Calendar.getInstance().getTime());
            cmsRepairUser.setDelSign(0);
            cmsRepairUser.setBlackList(0);

            return cmsRepairUserMapper.insert(cmsRepairUser) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }



    @Override
    public CmsRepairUser getOneById(String id) {
        return cmsRepairUserMapper.selectByPrimaryKey(id);
    }


    @Override
    public boolean Update(CmsRepairUser cmsRepairUser) {
        try {
            CmsRepairUser cmsRepairUserNew = getOneById(cmsRepairUser.getId());

            if (cmsRepairUserNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            cmsRepairUser.setCreateUsername(cmsRepairUserNew.getCreateUsername());
            cmsRepairUser.setCreateUser(cmsRepairUserNew.getCreateUser());
            cmsRepairUser.setCreateTime(cmsRepairUserNew.getCreateTime());
            cmsRepairUser.setDelSign(cmsRepairUserNew.getDelSign());
            cmsRepairUser.setModifyUsername(userInfo.getName());
            cmsRepairUser.setModifyUser(userInfo.getId());
            cmsRepairUser.setModifyTime(Calendar.getInstance().getTime());
            cmsRepairUser.setBlackList(cmsRepairUserNew.getBlackList());

            return cmsRepairUserMapper.updateByPrimaryKey(cmsRepairUser) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }


    /**
     * 删除功能实现
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            bret = cmsRepairUserMapper.deleteByPrimaryKey(id) == 1 ? true : false;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public BaseDataResult<CmsRepairUser> search(String search, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        Map<String, String> data = ToolUtil.searchJsonData(search);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("search", data);
        BaseDataResult<CmsRepairUser> listResults = new BaseDataResult<>();

        listResults.setResultList(cmsRepairUserMapper.findDataByKeyandValue(dataMap));
        listResults.setTotalNum(cmsRepairUserMapper.searchCountNum(dataMap));

        return listResults;
    }

    @Override
    public boolean setBlackListById(CmsRepairUser cmsRepairUser) {

        try{
            return cmsRepairUserMapper.setBlackListById(cmsRepairUser) == 1 ? true : false;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }

}
