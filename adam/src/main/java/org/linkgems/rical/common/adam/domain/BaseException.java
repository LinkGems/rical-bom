package org.linkgems.rical.common.adam.domain;

import org.linkgems.rical.common.adam.enums.ErrorEnum;
import lombok.Getter;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/9/5 11:46
 */
public class BaseException extends RuntimeException {

    /**
     * 异常编号
     */
    @Getter
    private String code;

    /**
     * 根据枚举构造业务类异常
     * @param error
     */
    public BaseException(ErrorEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
    }

    /**
     * 自定义异常
     * @param error
     * @param message
     */
    public BaseException(String error, String message) {
        super(message);
        this.code = error;
    }

    /**
     * 自定义消息体构造业务类异常
     * @param error
     * @param message
     */
    public BaseException(ErrorEnum error, String message) {
        super(message);
        this.code = error.getCode();
    }

    /**
     * 根据异常构造业务类异常
     * @param error
     * @param cause
     */
    public BaseException(ErrorEnum error, Throwable cause) {
        super(cause);
        this.code = error.getCode();
    }

    /**
     * 自定义消息体构造业务类异常
     * @param error
     * @param message
     * @param cause
     */
    public BaseException(ErrorEnum error, String message, Throwable cause) {
        super(message, cause);
        this.code = error.getCode();
    }

    /**
     * 根据Throwable构造业务类异常
     * @param cause
     */
    public BaseException(Throwable cause) {
        super(cause);
        this.code = ErrorEnum.XXX.getCode();
    }

    /**
     * 根据异常信息构造业务类异常
     * @param message
     */
    public BaseException(String message) {
        super(message);
        this.code = ErrorEnum.XXX.getCode();
    }
}
