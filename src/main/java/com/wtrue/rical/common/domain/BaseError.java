package com.wtrue.rical.common.domain;

import com.wtrue.rical.common.enums.ErrorEnum;
import com.wtrue.rical.common.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:16 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseError extends BaseObject {

    private String code;

    private String message;

    public BaseError(String code, String message){
        this.code = code;
        this.message = message;
    }

    public BaseError(ErrorEnum errorEnum){
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }

    public BaseError(BusinessException ex){
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }

}
