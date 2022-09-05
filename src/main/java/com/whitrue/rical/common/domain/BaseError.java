package com.whitrue.rical.common.domain;

import com.whitrue.rical.common.exception.BusinessException;
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

    public BaseError(BusinessException ex){
        this.code = ex.getCode();
        this.message = ex.getMessage();
    }

}
