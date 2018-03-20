package com.silverbullet.cms.service.impl;

import com.silverbullet.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import com.silverbullet.cms.config.RepositoryProperties;
import com.silverbullet.cms.service.ICmsService;
import com.silverbullet.cms.service.IFileService;

/**
 * 内容管理总体service接口
 * Created by jeffyuan on 2018/3/19.
 */
@Service
public class CmsServiceImpl implements ICmsService {

    @Autowired
    private RepositoryProperties repositoryProperties;

    @Override
    public boolean createArticle(String title) {

        IFileService iFileService = (IFileService) SpringUtil.getBean(repositoryProperties.getEngine());

        return false;
    }
}
