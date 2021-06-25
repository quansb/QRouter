package com.quansb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:quansb
 * Description:
 * Date:2020/5/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface QRouter {
    /**
     *
     * @return Class getSimpleName()
     */
    String name();

    /**
     *
     * @return  暂时无效
     */
    String path() default "";
}
