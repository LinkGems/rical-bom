package com.whitrue.rical.common.exception;

import com.whitrue.rical.common.domain.BaseResponse;
import com.whitrue.rical.common.enums.ErrorEnum;
import com.whitrue.rical.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/9/5 11:58
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截业务类异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse businessExceptionHandle(BusinessException e) {
        log.error("捕捉到业务类异常：", e);

        return BaseResponse.failure(e);
    }


    /**
     * 拦截运行时异常
     * @param e
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse runtimeExceptionHandle(RuntimeException e) {
        log.error("捕捉到运行时异常：", e);

        return BaseResponse.failure(
                ErrorEnum.UNKNOWN_ERROR.getCode(),
                e.getMessage());
    }

    /**
     * 捕捉系统级异常
     * @param th
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public BaseResponse throwableHandle(Throwable th) {
        log.error("捕捉Throwable异常：", th);

        return BaseResponse.failure(
                ErrorEnum.SYSTEM_ERROR.getCode(),
                th.getMessage());
    }
}
