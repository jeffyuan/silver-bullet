package com.silverbullet.params.service;

import com.silverbullet.params.domain.SysParamsDictionary;
import com.silverbullet.params.domain.SysParamsDictionaryItem;
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

    /**
     * 关键字 子ITEM
     * @param dictKeyId 关键字id
     * @param pageNum 当前页
     * @param pageSize 每页数量
     * @return
     */
    public BaseDataResult<SysParamsDictionaryItem> itemList(String dictKeyId, int pageNum, int pageSize);

    /**
     * 插入字典项
     * @param sysParamsDictionaryItem
     * @return
     */
    public boolean insertItem(SysParamsDictionaryItem sysParamsDictionaryItem);

    /**
     * 更新字典项
     * @param sysParamsDictionaryItem
     * @return
     */
    public boolean updateItem(SysParamsDictionaryItem sysParamsDictionaryItem);

    /**
     * 批量删除字典项
     * @param ids  用,号分割
     * @return
     */
    public boolean deleteItem(String ids);

    /**
     * 根据字典项ID获得，字典项信息
     * @param id
     * @return
     */
    public SysParamsDictionaryItem getOneItemById(String id);
}
