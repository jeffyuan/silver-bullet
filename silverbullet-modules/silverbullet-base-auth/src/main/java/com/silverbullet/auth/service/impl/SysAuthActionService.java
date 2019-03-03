package com.silverbullet.auth.service.impl;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.dao.SysAuthActionMapper;
import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.service.ISysAuthActionService;
import com.silverbullet.core.pojo.UserInfo;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能权限 service接口
 * Created by jeffyuan on 2018/2/11.
 */
@Service
public class SysAuthActionService implements ISysAuthActionService {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthAction.class);

    @Autowired
    private SysAuthActionMapper sysAuthActionMapper;

    @Override
    public int countNum() {
        return sysAuthActionMapper.countNum();
    }

    /**
     * 获取功能清单
     * @param parentId 父ID
     * @param pageNum  页码
     * @param pageSize 每页功能个数
     * @return com.silverbullet.utils.BaseDataResult<com.silverbullet.auth.domain.SysAuthAction>
     * @author jeffyuan
     * @createDate 2019/2/22 18:07
     * @updateUser jeffyuan
     * @updateDate 2019/2/22 18:07
     * @updateRemark
     */
    @Override
    public BaseDataResult<SysAuthAction> list(String parentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BaseDataResult<SysAuthAction> listResults = new BaseDataResult<SysAuthAction>();

        HashMap<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("parentId", parentId);

        listResults.setResultList(sysAuthActionMapper.findListByMapParams(mapParams));
        listResults.setTotalNum(sysAuthActionMapper.countNumByMapParams(mapParams));

        return listResults;
    }

    @Override
    public BaseDataResult<SysAuthAction> list() {
        BaseDataResult<SysAuthAction> listResults = new BaseDataResult<SysAuthAction>();
        listResults.setResultList(sysAuthActionMapper.findList());
        return listResults;
    }

    @Override
    public SysAuthAction getOneById(String id) {
        return sysAuthActionMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean Update(SysAuthAction sysAuthAction) {
        try{
            SysAuthAction sysAuthAction1 = getOneById(sysAuthAction.getId());
            if(sysAuthAction1 == null){
                return false;
            }
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            sysAuthAction.setModifyTime(Calendar.getInstance().getTime());
            sysAuthAction.setModifyUser(userInfo.getId());
            sysAuthAction.setCreateUser(sysAuthAction1.getCreateUser());
            sysAuthAction.setCreateTime(sysAuthAction1.getCreateTime());
            sysAuthAction.setParentIds(sysAuthAction1.getParentIds());
            sysAuthAction.setState(sysAuthAction1.getState());
            sysAuthAction.setPermission(ToolUtil.getPermission(sysAuthAction.getUrl()));

            return sysAuthActionMapper.updateByPrimaryKey(sysAuthAction) == 1 ? true : false;
        }catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        String [] arrIds = ids.split(",");
        boolean bret = true;
        for (String id : arrIds) {
            if (id.isEmpty()) {
                continue;
            }

            // 县查找id是否存在
            SysAuthAction sysAuthAction = sysAuthActionMapper.selectByPrimaryKey(id);
            if (sysAuthAction == null) {
                continue;
            }

            // 删除该节点以及节点下的所有节点
            bret = sysAuthActionMapper.deleteByPathLike(id) >= 1? true : false;
            if (!bret) {
                throw new RuntimeException("delete failed");
            }

            if (!sysAuthAction.getParentId().isEmpty() && !sysAuthAction.getParentId().equals("NONE")) {
                // 找到父节点，修改子节点数量
                SysAuthAction sysAuthActionParent = sysAuthActionMapper.selectByPrimaryKey(sysAuthAction.getParentId());
                if (sysAuthActionParent != null) {
                    int childrenNum = sysAuthActionParent.getChildrenNum() - 1;
                    if (childrenNum < 0) {
                        childrenNum = 0;
                    }

                    sysAuthActionParent.setChildrenNum(childrenNum);
                    bret = sysAuthActionMapper.updateByPrimaryKey(sysAuthActionParent) >= 1? true : false;
                    if (!bret) {
                        throw new RuntimeException("Insert failed. update parent children num failed");
                    }
                }
            }
        }
        return bret;
    }

    @Override
    @Transactional
    public boolean Insert(SysAuthAction sysAuthAction) {
        try{
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            sysAuthAction.setId(ToolUtil.getUUID());
            sysAuthAction.setCreateUser(userInfo.getId());
            sysAuthAction.setCreateTime(Calendar.getInstance().getTime());
            sysAuthAction.setModifyTime(Calendar.getInstance().getTime());
            sysAuthAction.setModifyUser(userInfo.getId());
            sysAuthAction.setState("1");
            sysAuthAction.setPermission(ToolUtil.getPermission(sysAuthAction.getUrl()));

            SysAuthAction sysAuthActionParent = null;
            // 如果是根节点，设置path为ID号
            if (sysAuthAction.getParentId().equals("NONE")) {
                sysAuthAction.setParentIds(sysAuthAction.getId());
            } else {
                // 否则查找父节点，获取path 再加入本ID
                sysAuthActionParent = sysAuthActionMapper.selectByPrimaryKey(sysAuthAction.getParentId());
                if (sysAuthActionParent == null) {
                    throw new RuntimeException("get parent failed.");
                }

                sysAuthAction.setParentIds(sysAuthActionParent.getParentIds() + sysAuthAction.getId());
            }

            boolean bRet = sysAuthActionMapper.insert(sysAuthAction) == 1 ? true : false;
            if (bRet && sysAuthActionParent != null) {
                // 插入成功，修改父节点下的子节点数量
                sysAuthActionParent.setChildrenNum(sysAuthActionParent.getChildrenNum() + 1);
                bRet = sysAuthActionMapper.updateByPrimaryKey(sysAuthActionParent) >= 1? true : false;
                if (!bRet) {
                    throw new RuntimeException("Insert failed. update parent children num failed");
                }
            }

            return bRet;
        } catch (Exception ex) {
            logger.error("Update Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<SysAuthAction> getPostActionList(String postId) {
        return sysAuthActionMapper.findListByPostId(postId);
    }

    /**
     * 获取所有的功能
     * @param parentId 父ID
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author jeffyuan
     * @createDate 2019/2/23 18:51
     * @updateUser jeffyuan
     * @updateDate 2019/2/23 18:51
     * @updateRemark
     */
    @Override
    public List<Map<String, Object>> getActionByParentId(String parentId) {
        return sysAuthActionMapper.getActionByParentId(parentId);
    }
}
