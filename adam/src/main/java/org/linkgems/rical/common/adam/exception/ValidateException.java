package org.linkgems.rical.common.adam.exception;

import org.linkgems.rical.common.adam.enums.ErrorEnum;
import org.linkgems.rical.common.adam.domain.BaseException;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 7:28 PM
 */
public class ValidateException extends BaseException {

    public ValidateException(String message) {
        super(ErrorEnum.PARAM_ERROR, message);
    }
    public ValidateException(Throwable cause) {
        super(ErrorEnum.PARAM_ERROR, cause);
    }
    public ValidateException(String message, Throwable cause) {
        super(ErrorEnum.PARAM_ERROR, message, cause);
    }
}
