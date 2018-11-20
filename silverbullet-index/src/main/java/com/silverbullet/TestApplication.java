package com.silverbullet;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by jeffyuan on 2018/11/19.
 */
@SpringBootApplication
@MapperScan("com.silverbullet.*.dao")
@ComponentScan(basePackages = {"com.silverbullet"})
@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class})
public class TestApplication {
    public static void main(String [] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
