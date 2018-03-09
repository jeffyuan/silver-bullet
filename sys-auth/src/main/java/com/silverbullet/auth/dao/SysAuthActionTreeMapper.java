package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface SysAuthActionTreeMapper extends CrudRepository<SysAuthActionTree, String>{
    List<Map<String, String>> findListByPostids(List<String> postIds);
}