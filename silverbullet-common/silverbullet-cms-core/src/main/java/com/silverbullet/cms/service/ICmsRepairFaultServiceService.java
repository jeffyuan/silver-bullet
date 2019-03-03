package com.silverbullet.cms.service;

import com.silverbullet.cms.domain.CmsRepairFaultService;
import com.silverbullet.cms.domain.CmsRepairServiceInfo;
import com.silverbullet.utils.BaseDataResult;

import java.util.Date;
import java.util.Map;

public interface ICmsRepairFaultServiceService {

    /**
     * 简单多表查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public BaseDataResult<CmsRepairServiceInfo> list(int pageNum, int pageSize);

    /**
     * 根据状态查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public BaseDataResult<CmsRepairServiceInfo> list0(int pageNum, int pageSize);




    /**
     * 添加一条数据
     * @param cmsRepairFaultService
     * @return
     */
    public boolean Insert(CmsRepairFaultService cmsRepairFaultService);


    /**
     * 修改一条数据
     * @param cmsRepairFaultService
     * @return
     */
    public boolean Update(CmsRepairFaultService cmsRepairFaultService);


    /**
     * 根据id获取一条数据service
     * @param id
     * @return
     */
    public CmsRepairServiceInfo getOneById(String id);


    /**
     * 根据id获取一条数据
     * @param id
     * @return
     */
    public CmsRepairFaultService getOneById1(String id);



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
    public BaseDataResult<CmsRepairServiceInfo> search(String search, int pageNum, int pageSize);


    /**
     * 根据id查找status
     * @param id
     * @return
     */
    public CmsRepairFaultService findStatusById(String id);


    /**
     * 根据id设置status
     * @param cmsRepairFaultService
     * @return
     */
    public boolean updateStatusById(CmsRepairFaultService cmsRepairFaultService);


    /**
     * 根据时间查询相关业务
     * @param data
     * @return
     */
    public Map<String, Object> getCharB(String data);


    /**
     * 根据时间获取当前一周的业务情况
     * @param data
     * @return
     */
    public Map<String, Object> getCharSZ(Date data);


}
