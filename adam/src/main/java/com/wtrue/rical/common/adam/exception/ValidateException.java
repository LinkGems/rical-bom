package com.wtrue.rical.common.adam.exception;

import com.wtrue.rical.common.adam.domain.BusinessException;
import com.wtrue.rical.common.adam.enums.ErrorEnum;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 7:28 PM
 */
public class ValidateException extends BusinessException {
    public ValidateException(String message) {
        super(ErrorEnum.PARAM_ERROR, message);
    }
    public ValidateException(String message, String... params) {
        super(ErrorEnum.PARAM_ERROR, String.format(message, params));
    }
}
