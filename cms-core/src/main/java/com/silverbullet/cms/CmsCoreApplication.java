package com.silverbullet.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.silverbullet.*.dao")
@ComponentScan(basePackages = {"com.silverbullet", "com.silverbullet.cms.service", "com.silverbullet.cms.service.ICmsRepairFaultService"})
public class CmsCoreApplication {

    public static void main(String[] args) {
        //SpringApplication.run(AmainApplication.class, args);

        SpringApplication springApplication = new SpringApplication(CmsCoreApplication.class);
        springApplication.run(args);

    }
}
