package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.data.repository.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysAuthUserMapper extends CrudRepository<SysAuthUser, String>{
    SysAuthUser selectByUserName(String userName);
    List<SysAuthActionTree> findLists(SysAuthActionTree sysAuthActionTree);
    List<Map<String, Object>>findPostNameByActionTreeId(String id);
    int updateEditTimeById(@Param("UserId") String UserId, @Param("modifyTime") Date modifyTime );
}