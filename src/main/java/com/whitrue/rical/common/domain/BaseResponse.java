package com.whitrue.rical.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:13 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResponse<T> extends BaseObject{

    private boolean success = true;

    private T data;

    private BaseError error;

    public BaseResponse<T> populateResponse(T data){
        return populateResponse(data, "sth. wrong, check log");
    }

    public BaseResponse<T> populateResponse(T data, String errMsg){
        BaseResponse<T> resp = new BaseResponse<T>();
        if(data != null){
            resp.setData(data);
        }else{
            resp.setSuccess(false);
            BaseError error = new BaseError();
            error.setCode(1);
            error.setMessage(errMsg);
            resp.setError(error);
        }
        return resp;
    }

}
