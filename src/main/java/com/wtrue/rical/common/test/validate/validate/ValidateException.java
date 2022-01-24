package com.wtrue.rical.common.test.validate.validate;

import com.wtrue.rical.common.domain.BaseException;
import com.wtrue.rical.common.enums.ErrorEnum;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 7:28 PM
 */
class ValidateException extends BaseException {
    public ValidateException(String message) {
        super(ErrorEnum.PARAM_ERROR.getCode(), message);
    }
    public ValidateException(String message, String... params) {
        super(ErrorEnum.PARAM_ERROR.getCode(), String.format(message, params));
    }
}
