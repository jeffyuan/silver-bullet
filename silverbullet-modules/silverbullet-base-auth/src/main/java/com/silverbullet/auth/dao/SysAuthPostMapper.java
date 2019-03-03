package com.silverbullet.auth.dao;

import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface SysAuthPostMapper extends CrudRepository<SysAuthPost, String>{
    public List<SysAuthPost> findListByUserId(String KeyId);

    public int countNumByOrganizationId(String organizationId);

    public List<SysAuthPost> findListByOrganizationId(String organizationId);

}