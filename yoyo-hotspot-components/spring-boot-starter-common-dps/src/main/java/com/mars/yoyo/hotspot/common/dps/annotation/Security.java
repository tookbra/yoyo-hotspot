package com.mars.yoyo.hotspot.common.dps.annotation;

import java.lang.annotation.*;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface Security {

    boolean check() default true;
}
