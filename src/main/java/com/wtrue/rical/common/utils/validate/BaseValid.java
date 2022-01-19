package com.wtrue.rical.common.utils.validate;

import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.enums.ErrorEnum;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 10:12 AM
 */
class BaseValid {
    protected boolean valid = true;
    protected BaseError error = new BaseError();

    /**
     * 填充BaseError异常
     * @param msg
     */
    protected void populateError(String msg, String... params){
        msg = String.format(msg, params);
        setError(ErrorEnum.PARAM_ERROR.getCode(), msg);
    }

    public boolean isValid() {
        return valid;
    }

    public BaseError getError() {
        return error;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setError(BaseError error) {
        this.setValid(false);
        this.error = error;
    }

    public void setError(Integer code, String message) {
        this.setValid(false);
        this.error.setCode(code);
        this.error.setMessage(message);
    }
}
