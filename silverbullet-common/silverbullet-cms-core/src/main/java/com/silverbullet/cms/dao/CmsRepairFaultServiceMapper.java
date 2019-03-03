package com.silverbullet.cms.dao;

import com.silverbullet.cms.domain.CmsRepairFaultService;
import com.silverbullet.cms.domain.CmsRepairServiceInfo;
import com.silverbullet.data.repository.CrudRepository;
import org.beetl.sql.core.annotatoin.Param;

import java.util.List;
import java.util.Map;

public interface CmsRepairFaultServiceMapper extends CrudRepository<CmsRepairFaultService, String> {

    public List<CmsRepairServiceInfo> findServiceList();
    public CmsRepairServiceInfo selectByServicePrimaryKey(String id);
    public int serviceCountNum();

    public List<CmsRepairServiceInfo> findDataByKeyAndValue(@Param("search") Map<String, Object> data);

    public int searchCountNum(Map<String, Object> data);

    public CmsRepairFaultService findStatusById(String id);

    Integer updateStatusById(CmsRepairFaultService cmsRepairFaultService);

    public List<CmsRepairServiceInfo> findServiceList0();
    public int serviceCountNum0();

    public Integer findCountStatus(Map<String, Object> data);

    public Integer findWeekCountStatus(Map<String, Object> data);
}
