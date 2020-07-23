package com.ankoye.jelly.common.annotation;



import com.ankoye.jelly.common.constant.LogType;

import java.lang.annotation.*;

/**
 * @author ankoye@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logger {

    String module() default "";

    String operation() default "";

    LogType[] exclude() default {};
}
