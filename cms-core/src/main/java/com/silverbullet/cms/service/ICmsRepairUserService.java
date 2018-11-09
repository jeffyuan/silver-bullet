package com.silverbullet.cms.service;

import com.silverbullet.cms.domain.CmsRepairUser;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 客户管理 service接口
 * Created by OFG on 2018/7/23.
 */
public interface ICmsRepairUserService {

    /**
     * 简单查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public BaseDataResult<CmsRepairUser> list(int pageNum, int pageSize);



    /**
     * 添加一条数据
     * @param cmsRepairUser
     * @return
     */
    public boolean Insert(CmsRepairUser cmsRepairUser);


    /**
     * 修改一条数据
     * @param cmsRepairUser
     * @return
     */
    public boolean Update(CmsRepairUser cmsRepairUser);


    /**
     * 根据id获取一条数据
     * @param id
     * @return
     */
    public CmsRepairUser getOneById(String id);


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
    public BaseDataResult<CmsRepairUser> search(String search, int pageNum, int pageSize);


    /**
     * 根据id设置blackList
     * @param cmsRepairUser
     * @return
     */
    public boolean setBlackListById(CmsRepairUser cmsRepairUser);

}
