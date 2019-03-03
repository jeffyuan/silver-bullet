package com.silverbullet.util;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 *
 * uuid生成工具类
 * @author jeffyuan
 * @date 2019/02/21
 **/

@Component
@Lazy(false)
public class IdGen implements IdGenerator{


    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    @Override
    public String getNextId() {
        return uuid();
    }

}
