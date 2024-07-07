package org.linkgems.rical.common.eve.domain.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Retry {

    /**
     * （失败）重试次数。重试最大次数为10
     * times=0则失败不重试
     * times=1则失败重试1次
     *
     * @return
     */
    int times() default 0;

    @AliasFor("times")
    int value() default 0;

    /**
     * 重试空窗期，单位ms。最大时长为10s（60000ms）
     */
    long emptyWindow() default 0;
}
