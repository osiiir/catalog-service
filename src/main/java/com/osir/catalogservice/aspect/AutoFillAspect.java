package com.osir.catalogservice.aspect;

import com.osir.catalogservice.annotation.AutoFill;
import com.osir.catalogservice.constant.AutoFillConstant;
import com.osir.catalogservice.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 公共字段自动更新
 * 技术点：枚举，自定义注解，AOP，反射
 *
 */

@Slf4j
@Aspect
@Component
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.osir.catalogservice.mapper.*.*(..)) && @annotation(com.osir.catalogservice.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 前置通知，在通知中进行公共字段的赋值
     * @param joinPoint
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始公共字段的填充...");
        // 获取当前操作方法是insert 还是 update
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType op = annotation.op();
        int pos = annotation.pos();
        // 要填充的信息
        LocalDateTime time = LocalDateTime.now();
        // TODO Need UserId
        Long id = 0L/*BaseContext.getCurrentId()*/;

        // 获取参数对象列表
        Object[] args = joinPoint.getArgs();
        if(args==null || args.length==0){
            return;
        }

        // 获取需要自动填充公共字段的对象.
        Object entity = args[pos];
        if(op==OperationType.INSERT){
            try {
                // 通过反射获取设置更新时间和更新用户的Method
                Method setCreateTime = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                // 通过反射调用方法，设置更新时间和更新用户
                setCreateTime.invoke(entity,time);
                setUpdateTime.invoke(entity,time);
                setCreateUser.invoke(entity,id);
                setUpdateUser.invoke(entity,id);

            } catch (Exception e) {
                log.error("填充字段发生错误:{}",e);
                throw new RuntimeException("填充字段发生错误:{}",e);
            }

            return;
        }
        if(op==OperationType.UPDATE){
            try {
                // 通过反射获取设置更新时间和更新用户的Method
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                // 通过反射调用方法，设置更新时间和更新用户
                setUpdateTime.invoke(entity,time);
                setUpdateUser.invoke(entity,id);
            } catch (Exception e) {
                log.error("填充字段发生错误:{}",e);
                throw new RuntimeException("填充字段发生错误:{}",e);
            }

        }
    }

}
