package com.wtrue.rical.common.utils.validate;

import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.domain.BaseObject;
import com.wtrue.rical.common.enums.ErrorEnum;

/**
 * @description:校验类基础，定义成功失败和报错信息
 * @author: meidanlong
 * @date: 2022/1/20 6:50 PM
 */
public class BaseValid extends BaseObject {

    private boolean valid = true;
    private BaseError error = new BaseError();


    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
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

    public void setError(String message, String... params){
        message = String.format(message, params);
        this.setValid(false);
        this.error.setCode(ErrorEnum.PARAM_ERROR.getCode());
        this.error.setMessage(message);
    }

    public void throwError(String message, String... params){
        setError(message, params);
        throw new VException(error.getMessage());
    }

    public BaseError getError() {
        return error;
    }

    public String getMessage(){
        return this.error.getMessage();
    }
}
