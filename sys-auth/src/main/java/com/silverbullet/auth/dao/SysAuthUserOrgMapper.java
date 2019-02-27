package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthUserOrg;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface SysAuthUserOrgMapper extends CrudRepository<SysAuthUserOrg, Integer>{
    public List<Map<String, String>> findListByUserId(Integer id);
    public int deleteByUserId(Integer id);
    public String getUserOrgId(Integer id);
}