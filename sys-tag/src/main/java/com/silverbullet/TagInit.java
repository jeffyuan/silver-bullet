package com.silverbullet;

import com.silverbullet.tags.PageTag;
import com.silverbullet.utils.SpringUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * Created by jeffyuan on 2018/3/5.
 */
public class TagInit {

    private static ApplicationContext applicationContext;

    /**
     * 注册所有标签
     * @throws IOException
     */
    public void Init() throws IOException {

        BeetlGroupUtilConfiguration beetlConfig = (BeetlGroupUtilConfiguration) SpringUtil.getBean("beetlConfig");
        GroupTemplate gt = beetlConfig.getGroupTemplate();

        gt.registerTag("PageTag", PageTag.class);
    }
}
