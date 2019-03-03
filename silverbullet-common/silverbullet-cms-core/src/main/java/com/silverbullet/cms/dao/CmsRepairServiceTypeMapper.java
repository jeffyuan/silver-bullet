package com.silverbullet.cms.dao;

        import com.silverbullet.cms.domain.CmsRepairFaultService;
        import com.silverbullet.cms.domain.CmsRepairServiceType;
        import com.silverbullet.data.repository.CrudRepository;
        import org.apache.ibatis.annotations.Param;
//import org.beetl.sql.core.annotatoin.Param;


        import java.util.List;
        import java.util.Map;

public interface CmsRepairServiceTypeMapper extends CrudRepository<CmsRepairServiceType, String> {

    public List<CmsRepairServiceType> findListByParentId(String parentId);

    public CmsRepairServiceType findParentByParentId(String parentId);

    public List<CmsRepairServiceType>findChildByParentId(String patentId);

    public int findChildByParentIdCountNum(String patentId);

    public int ChangeTypeById(@Param("parentId") String parentId, @Param("type")Integer type);

    public int deleteNodeByPrimaryKey(String id);

    public List<String> findChildrenListByParentId(String id);

    public List<CmsRepairServiceType> findDataByKeyAndValue(@org.beetl.sql.core.annotatoin.Param("search") Map<String, Object> data);

    public int searchCountNum(Map<String, Object> data);
}
