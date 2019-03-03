package com.silverbullet.cms.dao;

import com.silverbullet.cms.domain.CmsArticleFileHistory;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface CmsArticleFileHistoryMapper extends CrudRepository<CmsArticleFileHistory, String>{
    public List<CmsArticleFileHistory> findListByFileId(String fileId);

}