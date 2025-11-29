package com.osir.catalogservice.annotation;


import com.osir.catalogservice.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    // 数据库的操作类型，insert，update
    OperationType op();
    // 需要自动填充的参数所在的位置
    int pos();
}
