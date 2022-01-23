package com.wtrue.rical.common.test.validate;

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


    /**
     * 校验参数是否合法
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * 设置校验参数是否合法
     * @return
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * 填充错误信息
     * @param error
     */
    public void setError(BaseError error) {
        this.setValid(false);
        this.error = error;
    }

    /**
     * 填充错误信息
     * @param code
     * @param message
     */
    public void setError(Integer code, String message) {
        this.setValid(false);
        this.error.setCode(code);
        this.error.setMessage(message);
    }

    /**
     * 填充错误信息
     * @param message
     * @param params
     */
    public void setError(String message, String... params){
        message = String.format(message, params);
        setError(ErrorEnum.PARAM_ERROR.getCode(), message);
    }

    /**
     * 追加报错信息，如校验之前已有报错则不再填充错误信息
     * @param message
     * @param params
     */
    public void setFollowError(String message, String... params){
        message = String.format(message, params);
        setFollowError(ErrorEnum.PARAM_ERROR.getCode(), message);
    }

    /**
     * 追加报错信息，如校验之前已有报错则不再填充错误信息
     * @param code
     * @param message
     */
    public void setFollowError(Integer code, String message){
        if(!valid){
            setError(code, message);
        }
    }

    /**
     * 填充错误信息，并抛出异常
     * @param message
     * @param params
     */
    public void throwError(String message, String... params){
        setError(message, params);
        throw new VException(error.getMessage());
    }

    /**
     * 获取错误对象
     * @return
     */
    public BaseError getError() {
        return error;
    }

    /**
     * 获取错误信息
     * @return
     */
    public String getMessage(){
        return this.error.getMessage();
    }
}
