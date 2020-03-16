package io.bytechat.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : denglinhai
 * @date : 21:24 2020/3/2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InitOrder {

    /**
     * 初始化加载最低优先级
     */
    int LOWEST_PRIORITY = Integer.MAX_VALUE;

    /**
     * 初始化加载最高优先级-
     */
    int HIGHEST_PRIORITY = Integer.MIN_VALUE;

    int value() default LOWEST_PRIORITY;
}
