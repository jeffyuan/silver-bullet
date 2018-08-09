package com.silverbullet.cms.dao;

import com.silverbullet.cms.domain.CmsRepairFault;
import com.silverbullet.cms.domain.CmsRepairUser;
import com.silverbullet.data.repository.CrudRepository;
import org.apache.ibatis.annotations.Mapper;
import org.beetl.sql.core.annotatoin.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmsRepairFaultMapper extends CrudRepository<CmsRepairFault, String> {


    public List<CmsRepairFault> findDataByKeyandValue(@Param("search") Map<String, Object> data);

    public int searchCountNum(Map<String, Object> data);
}
