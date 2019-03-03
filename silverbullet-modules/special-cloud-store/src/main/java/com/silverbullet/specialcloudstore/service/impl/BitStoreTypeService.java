package com.silverbullet.specialcloudstore.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.specialcloudstore.dao.BitStoreTypeMapper;
import com.silverbullet.specialcloudstore.domain.BitStoreType;
import com.silverbullet.specialcloudstore.service.IBitStoreTypeService;
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
 * 管道类别管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class BitStoreTypeService implements IBitStoreTypeService {

    private static final Logger logger = LoggerFactory.getLogger(BitStoreTypeService.class);

    @Autowired
    private BitStoreTypeMapper bitStoreTypeMapper;

    @Override
    public int countNum() {
        return bitStoreTypeMapper.countNum();
    }

    @Override
    public BaseDataResult<BitStoreType> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<BitStoreType> listResults = new BaseDataResult<BitStoreType>();
        listResults.setResultList(bitStoreTypeMapper.findList());
        listResults.setTotalNum(bitStoreTypeMapper.countNum());

        return listResults;
    }

    @Override
    public BitStoreType getOneById(Long id) {
        return bitStoreTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(BitStoreType bitStoreType) {
        try {
            BitStoreType bitStoreTypeNew = getOneById(bitStoreType.getId());

            if (bitStoreTypeNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return bitStoreTypeMapper.updateByPrimaryKey(bitStoreType) == 1;
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
        for (String idTmp : arrIds) {

            Long id = null;

            try {
            } catch (Exception ex) {
                throw new RuntimeException("delete faild");
            }

            bret = bitStoreTypeMapper.deleteByPrimaryKey(id) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(BitStoreType bitStoreType) {
      try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return bitStoreTypeMapper.insert(bitStoreType) == 1;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
