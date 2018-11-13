package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.data.repository.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysAuthActionTreeMapper extends CrudRepository<SysAuthActionTree, String>{
    List<Map<String, Object>> findListByPostids(List<String> postIds);
    List<SysAuthActionTree> findTreeNode(String parentId);
    String findListByActionId(String actionId);

    Integer setTreeNodeSortDown(@Param("id") String id, @Param("parentId") String parentId, @Param("sort") String sort);

    Integer setTreeNodeSortUp(@Param("parentId") String parentId, @Param("sortNow") String sortNow, @Param("sort")String sort);
}