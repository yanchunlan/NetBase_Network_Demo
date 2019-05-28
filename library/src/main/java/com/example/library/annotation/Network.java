package com.example.library.annotation;

import com.example.library.type.NetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * author:  ycl
 * date:  2019/5/28 22:53
 * desc:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Network {
    NetType netType() default NetType.AUTO;
}
