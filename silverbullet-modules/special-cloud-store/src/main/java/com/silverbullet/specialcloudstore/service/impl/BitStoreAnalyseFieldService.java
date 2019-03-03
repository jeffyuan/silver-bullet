package com.silverbullet.specialcloudstore.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.specialcloudstore.dao.BitStoreAnalyseFieldMapper;
import com.silverbullet.specialcloudstore.domain.BitStoreAnalyseField;
import com.silverbullet.specialcloudstore.service.IBitStoreAnalyseFieldService;
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
 * 设置库字段 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class BitStoreAnalyseFieldService implements IBitStoreAnalyseFieldService {

    private static final Logger logger = LoggerFactory.getLogger(BitStoreAnalyseFieldService.class);

    @Autowired
    private BitStoreAnalyseFieldMapper bitStoreAnalyseFieldMapper;

    @Override
    public int countNum() {
        return bitStoreAnalyseFieldMapper.countNum();
    }

    @Override
    public BaseDataResult<BitStoreAnalyseField> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<BitStoreAnalyseField> listResults = new BaseDataResult<BitStoreAnalyseField>();
        listResults.setResultList(bitStoreAnalyseFieldMapper.findList());
        listResults.setTotalNum(bitStoreAnalyseFieldMapper.countNum());

        return listResults;
    }

    @Override
    public BitStoreAnalyseField getOneById(Long id) {
        return bitStoreAnalyseFieldMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(BitStoreAnalyseField bitStoreAnalyseField) {
        try {
            BitStoreAnalyseField bitStoreAnalyseFieldNew = getOneById(bitStoreAnalyseField.getId());

            if (bitStoreAnalyseFieldNew == null) {
                return false;
            }

            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return bitStoreAnalyseFieldMapper.updateByPrimaryKey(bitStoreAnalyseField) == 1;
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

            bret = bitStoreAnalyseFieldMapper.deleteByPrimaryKey(id) == 1;
            if (!bret) {
                throw new RuntimeException("delete faild");
            }
        }

        return bret;
    }

    @Override
    public boolean Insert(BitStoreAnalyseField bitStoreAnalyseField) {
      try {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return bitStoreAnalyseFieldMapper.insert(bitStoreAnalyseField) == 1;
        } catch (Exception ex) {
            logger.error("Insert Error: " + ex.getMessage());
            return false;
        }
    }
}
