package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.data.repository.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysAuthActionMapper extends CrudRepository<SysAuthAction, String>{
    List<SysAuthAction> findListByPostId(String postId);
    List<Map<String, Object>> findTreeNode();
    List<Map<String, Object>> getActionByParentId(String parentId);
    List<SysAuthAction> findListByMapParams(HashMap mapParams);
    Integer countNumByMapParams(HashMap mapParams);
    int deleteByParentId(String parentId);
    int deleteByPathLike(@Param("actionId") String id);
}