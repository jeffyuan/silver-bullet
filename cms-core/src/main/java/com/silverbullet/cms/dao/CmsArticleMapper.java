package com.silverbullet.cms.dao;

import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CmsArticleMapper extends CrudRepository<CmsArticle, String>{
    public List<CmsArticle> findArticleByModule(HashMap map);
    public int countNumByModule(HashMap map);
}