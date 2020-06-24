package org.feasy.cloud.permission.api.annotation;

import java.lang.annotation.*;

/**
 * 权限标识校验注解
 *
 * @author yangxiaohui
 * @since 2020/5/9
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface VerifyPermission {
    /**
     * 接口名称
     */
    String name() default "";

    /**
     * 权限标识集合
     */
    String[] keys() default {};
}
