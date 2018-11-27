package com.silverbullet;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import javafx.application.Application;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.silverbullet.*.dao")
@ComponentScan(basePackages = {"com.silverbullet", "com.silverbullet.cms.service"})
@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class})
public class AmainApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        //SpringApplication.run(AmainApplication.class, args);

        SpringApplication springApplication = new SpringApplication(AmainApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);

    }
}
