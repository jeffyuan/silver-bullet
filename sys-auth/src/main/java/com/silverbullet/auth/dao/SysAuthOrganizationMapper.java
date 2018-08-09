package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.data.repository.CrudRepository;
import org.apache.ibatis.annotations.ResultType;

import java.util.List;
import java.util.Map;

public interface SysAuthOrganizationMapper extends CrudRepository<SysAuthOrganization, String>{

    List<SysAuthOrganization>findListByKeyId(String id);

    List<SysAuthOrganization>findListById(String id);

    List<Map<String, String>> findTreeNode();

    int countNumByKeyId(String id);

    int countNumById(String id);

}