package com.silverbullet.specialcloudstore.service;

import com.silverbullet.specialcloudstore.domain.BitStoreType;
import com.silverbullet.utils.BaseDataResult;


/**
 * 管道类别管理 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface IBitStoreTypeService {
    public int countNum();
    public BaseDataResult<BitStoreType> list(int pageNum, int pageSize);
    public BitStoreType getOneById(Long id);
    public boolean Update(BitStoreType bitStoreType);
    public boolean delete(String ids);
    public boolean Insert(BitStoreType bitStoreType);
}
