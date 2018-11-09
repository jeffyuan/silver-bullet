package com.silverbullet.cms.dao;

import com.silverbullet.cms.domain.CmsRepairUser;
import com.silverbullet.data.repository.CrudRepository;
import org.beetl.sql.core.annotatoin.Param;

import java.util.List;
import java.util.Map;

public interface CmsRepairUserMapper extends CrudRepository<CmsRepairUser, String> {

    public List<CmsRepairUser> findDataByKeyAndValue(@Param("search") List<String> data);

    public List<CmsRepairUser> findDataByKeyandValue(@Param("search") Map<String, Object> data);

    public int searchCountNum(Map<String, Object> data);

    public int setBlackListById(CmsRepairUser cmsRepairUser);

}
