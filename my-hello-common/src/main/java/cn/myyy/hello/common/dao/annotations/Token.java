package cn.myyy.hello.common.dao.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
    /** 是否检查token，默认为true */
    boolean check() default true;
}
