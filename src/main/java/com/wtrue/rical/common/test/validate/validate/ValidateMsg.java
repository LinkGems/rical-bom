package com.wtrue.rical.common.test.validate.validate;

import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.enums.ErrorEnum;

import javax.xml.bind.ValidationException;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 6:06 PM
 */
class ValidateMsg {

    private Boolean valid = true;

    private BaseError error;

    protected void populateError(String errorMsg){
        this.valid = false;
        error = new BaseError();
        error.setCode(ErrorEnum.PARAM_ERROR.getCode());
        error.setMessage(errorMsg);
    }

    public Boolean isValid() {
        return valid;
    }

    public BaseError getError() {
        return error;
    }

    public void setError(BaseError error) {
        this.error = error;
    }

    public void setError(Integer code, String errorMsg){
        this.error = new BaseError();
        this.error.setCode(code);
        this.error.setMessage(errorMsg);
    }
}
