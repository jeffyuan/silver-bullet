package com.silverbullet.auth.security.shiro.config;


import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by jeffyuan on 2018/3/12.
 */
public class SilverBulletFilter extends PathMatchingFilter {

    protected boolean pathsMatch(String path, ServletRequest request) {

        return true;
    }

    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return true;
    }
}
