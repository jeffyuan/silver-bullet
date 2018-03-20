package com.silverbullet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.silverbullet.*.dao")
public class AmainApplication {

    public static void main(String[] args) {
        //SpringApplication.run(AmainApplication.class, args);

        SpringApplication springApplication = new SpringApplication(AmainApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);

    }
}
