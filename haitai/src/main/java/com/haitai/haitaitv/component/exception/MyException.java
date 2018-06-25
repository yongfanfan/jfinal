package com.haitai.haitaitv.component.exception;

/**
 * 自定义异常
 *
 * @author liuzhou
 *         create at 2017-01-04 11:42
 */
public class MyException extends RuntimeException {

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

}
