package com.silverbullet;

import com.silverbullet.utils.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 系统加载完成后初始化工作
 * Created by jeffyuan on 2018/3/5.
 */
public class ApplicationStartup implements ApplicationListener {


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) applicationEvent).getApplicationContext();

            //SpringUtil.setApplicationContext(applicationContext);
        }
    }
}