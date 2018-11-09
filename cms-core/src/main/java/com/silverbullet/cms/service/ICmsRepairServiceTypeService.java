package com.silverbullet.cms.service;


import com.silverbullet.cms.domain.CmsRepairServiceType;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 业务类型 service接口
 * Created by OFG on 2018/8/21.
 */
@Service
public interface ICmsRepairServiceTypeService {

    /**
     * 简单查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public BaseDataResult<CmsRepairServiceType> list(int pageNum, int pageSize);



    /**
     * 根据id获取一条数据
     * @param id
     * @return
     */
    public CmsRepairServiceType getOneById(String id);


    /**
     * 查找树节点
     * @param parentId
     * @return
     */
    public BaseDataResult<CmsRepairServiceType> findListByModule(String parentId);


    /**
     * 设置Id
     * @param parentId
     * @return
     */
    public CmsRepairServiceType setParentId(String parentId);


    /**
     * 根据parentId查找父级菜单名称
     * @param parentId
     * @return
     */
    public CmsRepairServiceType findParentById(String parentId);


    /**
     *根据父级id查找所有子级列表
     * @param patentId
     * @return
     */
    public BaseDataResult<CmsRepairServiceType>findChildListByParentId(String patentId, int pageNum, int pageSize);


    /**
     * 添加一条数据
     * @param cmsRepairServiceType
     * @return
     */
    public boolean Insert(CmsRepairServiceType cmsRepairServiceType);




    /**
     * 添加一条数据
     * @param cmsRepairServiceType
     * @return
     */
    public boolean Update(CmsRepairServiceType cmsRepairServiceType);



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
    public BaseDataResult<CmsRepairServiceType> search(String search, int pageNum, int pageSize);






}
