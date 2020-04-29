package com.ankoye.jelly.log.annotation;


import com.ankoye.jelly.log.constant.LogType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";

    LogType[] exclude() default {};
}
