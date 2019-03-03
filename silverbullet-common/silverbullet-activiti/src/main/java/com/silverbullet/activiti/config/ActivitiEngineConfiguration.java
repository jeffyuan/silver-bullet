package com.silverbullet.activiti.config;

import com.silverbullet.activiti.actimpl.factory.ActGroupMagagerFactory;
import com.silverbullet.activiti.actimpl.factory.ActUserManagerFactory;

import com.silverbullet.activiti.ext.ActivitiExtendProperties;
import com.silverbullet.util.IdGen;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jeffyuan on 2018/5/3.
 */
@Configuration
@ComponentScan({"org.activiti.rest.diagram","org.activiti.rest.editor"})
@EnableConfigurationProperties(ActivitiExtendProperties.class)
public class ActivitiEngineConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ActivitiExtendProperties properties;

    @Bean
    public ProcessEngineConfigurationConfigurer processEngineConfigurationConfigurer(IdGen idGen) {
        ProcessEngineConfigurationConfigurer configurer = new ProcessEngineConfigurationConfigurer() {

            @Override
            public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
                processEngineConfiguration.setActivityFontName(properties.getActivityFontName());
                processEngineConfiguration.setLabelFontName(properties.getLabelFontName());
                processEngineConfiguration.setCustomSessionFactories(getCustomSessionFactories());
                processEngineConfiguration.setBeans(getBeans());
                processEngineConfiguration.setIdGenerator(idGen);
                processEngineConfiguration.setDatabaseSchemaUpdate(properties.getDatabaseSchemaUpdate());

            }

        };

        return configurer;
    }

    /**
     * 用户自定义权限工厂
     *
     * @return
     */
    private List<SessionFactory> getCustomSessionFactories() {
        List<SessionFactory> sessionFactoryList = new ArrayList<>();
        // TODO 等用户权限做好了再开启这两个
        sessionFactoryList.add(new ActGroupMagagerFactory());
        sessionFactoryList.add(new ActUserManagerFactory());
        return sessionFactoryList;
    }

    /**
     * 需要spring 代理的bean 请在该方法中加入
     *
     * @return
     */
    private Map<Object, Object> getBeans() {
        Map<Object, Object> beans = new HashMap<>();

        return beans;
    }

}