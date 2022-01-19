package com.wtrue.rical.common.utils.validate.exception;

import com.wtrue.rical.common.domain.BaseException;
import com.wtrue.rical.common.enums.ErrorEnum;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 10:17 PM
 */
public class ValidException extends BaseException {
    public ValidException(String message, String... params) {
        super(ErrorEnum.PARAM_ERROR.getCode(), String.format(message, params));
    }
}
