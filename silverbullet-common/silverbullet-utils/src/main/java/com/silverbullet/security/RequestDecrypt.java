package com.silverbullet.security;

import java.lang.annotation.*;

/**
 * Created by jeffyuan on 2018/12/4.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestDecrypt {
    SecurityMethod method() default SecurityMethod.NULL;
}
