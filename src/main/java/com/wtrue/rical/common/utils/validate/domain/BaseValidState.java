package com.wtrue.rical.common.utils.validate.domain;

import com.wtrue.rical.common.domain.BaseError;
import com.wtrue.rical.common.domain.BaseObject;
import com.wtrue.rical.common.enums.ErrorEnum;
import lombok.Getter;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 9:54 PM
 */
@Getter
public class BaseValidState extends BaseObject {
    private boolean valid = true;
    private BaseError error = new BaseError();

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
