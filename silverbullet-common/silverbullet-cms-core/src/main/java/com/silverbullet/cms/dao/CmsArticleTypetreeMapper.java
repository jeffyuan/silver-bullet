package com.silverbullet.cms.dao;

import com.silverbullet.cms.domain.CmsArticleTypetree;
import com.silverbullet.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CmsArticleTypetreeMapper extends CrudRepository<CmsArticleTypetree, String>{

    public List<Map<String, Object>> findArticleTypeByModule(HashMap map);
}