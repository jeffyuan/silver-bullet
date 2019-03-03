package com.silverbullet.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author jeffyuan
 * @version 1.0
 * @createDate 2019/2/28 12:57
 * @updateUser jeffyuan
 * @updateDate 2019/2/28 12:57
 * @updateRemark
 */
@Configuration
@PropertySource("classpath:system.properties")
public class SystemInfo {
    @Value("${system.version}")
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
