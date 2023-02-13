package common.exception;

import common.domain.BaseResponse;
import common.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/9/5 11:58
 */
@Slf4j
public class BaseExceptionHandler {

    /**
     * 拦截业务类异常
     * @param e
     * @return
     */
    public BaseResponse businessExceptionHandle(BusinessException e) {
        log.error("捕捉到业务类异常：", e);
        return BaseResponse.failure(e);
    }


    /**
     * 拦截运行时异常
     * @param e
     */
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
    public BaseResponse throwableHandle(Throwable th) {
        log.error("捕捉Throwable异常：", th);
        return BaseResponse.failure(
                ErrorEnum.SYSTEM_ERROR.getCode(),
                th.getMessage());
    }
}
