package com.wtrue.rical.common.validation.wtvalidation;


import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.enums.ErrorEnum;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 6:06 PM
 */
class ValidateMsg {

    private Boolean valid = true;

    private BaseError error;

    public void populateError(String errorMsg, String... params){
        this.valid = false;
        error = new BaseError(ErrorEnum.PARAM_ERROR.getCode(), String.format(errorMsg, params));
    }

    public Boolean isValid() {
        return valid;
    }

    /*public Boolean isNotValid() {
        return !valid;
    }*/

    public BaseError getError() {
        return error;
    }

    public void setError(BaseError error) {
        this.error = error;
    }

    public void setError(String code, String errorMsg){
        this.error = new BaseError(code, errorMsg);
    }
}
