package com.whitrue.rical.common.utils.validate;

import com.whitrue.rical.common.domain.BaseException;
import com.whitrue.rical.common.enums.ErrorEnum;

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
