package com.silverbullet.activiti.actimpl.factory;

import com.silverbullet.activiti.actimpl.ActGroupManager;
import com.silverbullet.activiti.actimpl.ActUserManager;
import com.silverbullet.auth.service.ISysAuthService;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jeffyuan on 2018/5/3.
 */
public class ActUserManagerFactory implements SessionFactory {

    @Autowired
    private ISysAuthService sysAuthService;

    public ActUserManagerFactory() {

    }

    @Override
    public Class<?> getSessionType() {
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return new ActUserManager(sysAuthService);
    }
}
