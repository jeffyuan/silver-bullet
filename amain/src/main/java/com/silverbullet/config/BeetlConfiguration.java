package com.silverbullet.config;

import com.silverbullet.auth.security.shiro.ShiroMan;
import com.silverbullet.tags.PageTag;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

/**
 * Created by jeffyuan on 2018/3/8.
 */
public class BeetlConfiguration extends BeetlGroupUtilConfiguration {
    @Override
    public void initOther() {
        groupTemplate.registerFunctionPackage("shiro", new ShiroMan());
        groupTemplate.registerTag("PageTag", PageTag.class);
    }
}
