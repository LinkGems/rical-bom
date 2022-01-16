package com.wtrue.rical.common.domain;

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
        BaseResponse<T> resp = new BaseResponse<T>();
        if(data != null){
            resp.setData(data);
        }else{
            resp.setSuccess(false);
            BaseError error = new BaseError();
            error.setCode(1);
            error.setMessage("sth. wrong when queryJobById");
            resp.setError(error);
        }
        return resp;
    }

}
