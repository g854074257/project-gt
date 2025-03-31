package com.gt.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * iot通信用于SP鉴权
 *
 * @author : guitao
 * @since : 2024/06/21
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SpSecretAnnotation {
}
