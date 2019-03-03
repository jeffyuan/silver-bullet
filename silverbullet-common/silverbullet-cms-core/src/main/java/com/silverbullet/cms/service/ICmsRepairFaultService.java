package com.silverbullet.cms.service;

import com.silverbullet.cms.domain.CmsRepairFault;
import com.silverbullet.cms.domain.CmsRepairUser;
import com.silverbullet.utils.BaseDataResult;


/**
 * 故障管理 service接口
 * Created by OFG on 2018/7/23.
 */
public interface ICmsRepairFaultService {

    /**
     * 简单查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public BaseDataResult<CmsRepairFault> list(int pageNum, int pageSize);


    /**
     * 添加一条数据
     * @param cmsRepairFault
     * @return
     */
    public boolean Insert(CmsRepairFault cmsRepairFault);


    /**
     * 修改一条数据
     * @param cmsRepairFault
     * @return
     */
    public boolean Update(CmsRepairFault cmsRepairFault);


    /**
     * 根据id获取一条数据
     * @param id
     * @return
     */
    public CmsRepairFault getOneById(String id);


    /**
     * 根据id删除一条数据
     * @param ids
     * @return
     */
    public boolean delete(String ids);


    /**
     * 根据条件搜索用户数据
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
    public BaseDataResult<CmsRepairFault> search(String search, int pageNum, int pageSize);
}
