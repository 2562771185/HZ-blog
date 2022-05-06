package com.jhzz.jhzzblog.common.cache;

import java.lang.annotation.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/28
 * \* Time: 18:41
 * \* Description:
 * \
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    long expire() default 1 * 60 * 1000;

    String name() default "";
}
