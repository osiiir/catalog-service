package com.osir.catalogservice.exception;

/**
 * 套餐启用失败异常
 */
public class SetmealEnableFailedException extends BaseException {

    public SetmealEnableFailedException(){}

    public SetmealEnableFailedException(String msg){
        super(msg);
    }
}
