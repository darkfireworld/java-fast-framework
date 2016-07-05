package org.darkgem.web.support.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动转换Token到对应的UserId
 * 如果不存在UserId, 则抛出异常
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
    /**
     * Token 在字段名称
     */
    String value() default "token";
}
