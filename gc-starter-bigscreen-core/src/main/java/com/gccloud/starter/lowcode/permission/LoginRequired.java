package com.gccloud.starter.lowcode.permission;

import java.lang.annotation.*;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/5/15 10:51
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {
    boolean required() default true;
}
