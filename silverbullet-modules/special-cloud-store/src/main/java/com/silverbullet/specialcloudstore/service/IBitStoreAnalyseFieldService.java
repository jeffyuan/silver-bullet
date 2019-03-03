package com.silverbullet.specialcloudstore.service;

import com.silverbullet.specialcloudstore.domain.BitStoreAnalyseField;
import com.silverbullet.utils.BaseDataResult;


/**
 * 设置库字段 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface IBitStoreAnalyseFieldService {
    public int countNum();
    public BaseDataResult<BitStoreAnalyseField> list(int pageNum, int pageSize);
    public BitStoreAnalyseField getOneById(Long id);
    public boolean Update(BitStoreAnalyseField bitStoreAnalyseField);
    public boolean delete(String ids);
    public boolean Insert(BitStoreAnalyseField bitStoreAnalyseField);
}
