package com.silverbullet.params.service;

import com.silverbullet.params.domain.SysParamsDictionary;
import com.silverbullet.utils.BaseDataResult;


/**
 * 字典类 service接口
 * Created by jeffyuan on 2018/2/11.
 */
public interface ISysParamsDictionaryService {
    public int countNum();
    public BaseDataResult<SysParamsDictionary> list(int pageNum, int pageSize);
    public SysParamsDictionary getOneById(String id);
    public boolean Update(SysParamsDictionary sysParamsDictionary);
    public boolean delete(String ids);
    public boolean Insert(SysParamsDictionary sysParamsDictionary);
}
