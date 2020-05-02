package com.ankoye.jelly.web.log.annotation;


import com.ankoye.jelly.web.log.constant.LogType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logger {

    String module() default "";

    String operation() default "";

    LogType[] exclude() default {};
}
