package com.silverbullet;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;

/**
 * Created by jeffyuan on 2018/3/5.
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // 初始化自定义标签
        TagInit tagInit = new TagInit();
        try {
            tagInit.Init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}