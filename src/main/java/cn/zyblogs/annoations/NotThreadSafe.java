package cn.zyblogs.annoations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: ThreadSafe.java
 * @Package cn.zyblogs.annoations
 * @Description: TODO 自定义注解 用来标记线程不安全的类
 * @Author ZhangYB
 * @Version V1.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface NotThreadSafe {

    String value() default "";
}
