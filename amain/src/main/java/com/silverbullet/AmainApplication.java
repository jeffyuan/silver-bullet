package com.silverbullet;

import com.silverbullet.utils.SpringUtil;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.silverbullet.*.dao")
@ComponentScan
public class AmainApplication {

	public static void main(String[] args) {
        //SpringApplication.run(AmainApplication.class, args);

        SpringApplication springApplication = new SpringApplication(AmainApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
	}

}
