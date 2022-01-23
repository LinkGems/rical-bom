package com.wtrue.rical.common.test.validate.validate.exception;

import com.wtrue.rical.common.domain.BaseException;
import com.wtrue.rical.common.enums.ErrorEnum;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 7:28 PM
 */
public class ValidateException extends BaseException {
    public ValidateException(String message) {
        super(ErrorEnum.PARAM_ERROR.getCode(), message);
    }
}
