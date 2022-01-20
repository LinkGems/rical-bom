package com.wtrue.rical.common.utils.validate;

/**
 * @description:自定义异常
 * @author: meidanlong
 * @date: 2022/1/20 8:19 PM
 */

import com.wtrue.rical.common.domain.BaseException;
import com.wtrue.rical.common.enums.ErrorEnum;

public class VException extends BaseException {
    public VException(String message) {
        super(ErrorEnum.PARAM_ERROR.getCode(), message);
    }
}
