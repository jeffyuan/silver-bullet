package com.silverbullet.cms.dao;

import com.silverbullet.cms.domain.CmsRfArticleFile;
import com.silverbullet.data.repository.CrudRepository;
import java.util.List;
import java.util.Map;

public interface CmsRfArticleFileMapper extends CrudRepository<CmsRfArticleFile, String>{
    public int deleteByArtId(String artId);
}