package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthUserOrg;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface SysAuthUserOrgMapper extends CrudRepository<SysAuthUserOrg, String>{
    public List<Map<String, String>> findListByUserId(String id);
    public int deleteByUserId(String id);
    public String getUserOrgId(String id);
}