package com.silverbullet.config;

import com.silverbullet.security.SilverbulletEncrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jeffyuan on 2018/12/5.
 */
@Configuration
public class SilverbulletEncryptConfig {

    @Bean
    public SilverbulletEncrypt silverbulletEncryptUtils() {
        return new SilverbulletEncrypt();
    }
}
