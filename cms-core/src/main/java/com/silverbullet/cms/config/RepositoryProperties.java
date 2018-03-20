package com.silverbullet.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * 存储文件服务的配置文件
 * Created by jeffyuan on 2018/3/19.
 */
@Configuration
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:repository.properties")
public class RepositoryProperties {
    private String path;   //文件存储绝对路径
    private String url;    //文件访问的URL地址
    private String engine; //存储引擎

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
