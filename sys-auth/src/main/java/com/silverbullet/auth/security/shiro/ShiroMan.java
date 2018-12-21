package com.silverbullet.auth.security.shiro;

import com.silverbullet.core.pojo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Shiro管理类，用于注册到网页中判断权限
 * Created by jeffyuan on 2018/3/8.
 */
public class ShiroMan {

    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 判断是否已经登录
     * @return
     */
    private boolean isLogin() {
        return getSubject() != null && getSubject().getPrincipal() != null;
    }

    /**
     * 是否有角色权限
     * @param roleNo 角色NO
     * @return
     */
    public boolean hasRole(String roleNo) {
        if (!isLogin()) {
            return false;
        }

        return getSubject().hasRole(roleNo);
    }

    /**
     * 是否具有功能权限
     * @return
     */
    public boolean hasPermission(String permission) {
        if (!isLogin()) {
            return false;
        }

        return getSubject().isPermitted(permission);
    }

    /**
     * 获取用户信息
     * @return
     */
    public UserInfo getUserInfo() {
        if (!isLogin()) {
            return new UserInfo();
        }

        UserInfo userInfo = (UserInfo) getSubject().getPrincipal();

        return userInfo;
    }


    public Subject getsSubject() {
        return SecurityUtils.getSubject();
    }
}
