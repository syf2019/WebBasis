package com.ims.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * 类名:com.toonan.common.annotation.LogTag
 * 描述:日志标签
 * 编写者:陈骑元
 * 创建时间:2018年12月31日 下午9:18:23
 * 修改说明:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogTag {

    /**
     * 日志类型
     *
     * @return
     */
    public String type() default "0";
    
    /**
     * 日志内容
     *
     * @return
     */
    public String value() default "";

}
